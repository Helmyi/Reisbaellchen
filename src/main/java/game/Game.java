package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.menu.GameMenu;
import game.net.NetMessageHandler;
import game.net.UDPClient;
import game.net.UDPTestClient;
import map.Map;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener, Runnable {
	Image imageBuffer;
	Graphics graphicBuffer;
	NetMessageHandler messageHandler;

	private long gameTime;
	private static Game theGame;
	private int fps = 55;
	private static int width = 1000;
	private static int height = 600;
	private GameMenu menu;
	private boolean isMenuVisible;

	private Player player;
	private List<Level> levels;

	public Game() {
		this.setFocusable(true); // needed for listeners to work
		this.requestFocusInWindow();
		this.addKeyListener(this);
		this.menu = new GameMenu();
		this.isMenuVisible = false;
		levels = new ArrayList<Level>();

	}

	public static void main(String[] args) {
		theGame = new Game();
		theGame.init();
		JFrame frame = new JFrame("Reisbaellchen Game");
		frame.add(theGame);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		theGame.run();
	}

	// initializing the game
	private void init() {
		theGame.add(menu);
		menu.setLocation(width / 2, height / 2);
	}
	
	/**
	 * initializing when running begins. theGame instance exists here, but not in normal init
	 */
	private void runInit(){
		// load test level
		gameTime = 0;
		try {
			int customDelay = 20;
			int customJitter = 10;
			messageHandler = new NetMessageHandler(new UDPTestClient("127.0.0.1", 27015, 2, customDelay, customJitter));
		} catch (Exception e) {
			messageHandler = new NetMessageHandler(null);
			e.printStackTrace();
		}

		player = new Player();

		levels.add(Level.createTestLevel());
		
		// set player unit depending on clientNumber
		if (messageHandler.getClient() != null) {
			if(getEnityById(messageHandler.getClient().getClientNumber()) != null){
				player.setPlayerUnit((Unit) getEnityById(messageHandler.getClient().getClientNumber()));
			}else{
				player.setPlayerUnit((Unit) getEnityById(0));
			}
		}else{
			player.setPlayerUnit((Unit) getEnityById(0));
		}
	}

	@Override
	public void paint(Graphics g) {
		paintAndTickSynchronizer(g, 0);
	}

	/**
	 * only paint, no logic
	 */
	public void realPaint(Graphics g) {
		if (imageBuffer == null) {
			imageBuffer = this.createImage(width, height);
			graphicBuffer = imageBuffer.getGraphics();
		}

		if (player != null && player.getPlayerCamera() != null)
			player.getPlayerCamera().paint(graphicBuffer);
		g.drawImage(imageBuffer, 0, 0, this);
	}

	/**
	 * game logic
	 */
	private void tick(int elapsedTime) {
		for (Level level : levels) {
			level.tick(elapsedTime);
		}
	}

	/**
	 * because otherwise it happens that paint and tick run in parallel because
	 * paint is an extra threat and sometimes takes it times before it start
	 * from the repaint call g == null -> tick; g != null -> paint
	 */
	private synchronized void paintAndTickSynchronizer(Graphics g, int elapsedTime) {
		if (g == null)
			tick(elapsedTime);
		else
			realPaint(g);
	}

	@Override
	public void run() {
		runInit();
		
		long timeBefore, timePassed, sleepTime;
		timeBefore = System.currentTimeMillis();

		// loop
		while (true) {
			// tick
			messageHandler.tick();
			paintAndTickSynchronizer(null, 1000 / fps);
			
			repaint();

			timePassed = System.currentTimeMillis() - timeBefore;

			sleepTime = (1000 / fps) - timePassed;
			gameTime += (1000 / fps);

			if (sleepTime < 0) {
				sleepTime = 0;
				// System.out.println("Game.run: no sleep");
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			timeBefore = System.currentTimeMillis();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			toggleMenu();
			return;
		case KeyEvent.VK_ENTER:
			// TODO: open chat
			return;
		}

		player.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		player.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		player.keyTyped(arg0);
	}

	public static Game getGameInstance() {
		return theGame;
	}

	public List<Entity> getEntityList(int levelId) {
		return getLevelById(levelId).getEntityList();
	}

	public Map getMap(int levelId) {
		return getLevelById(levelId).getMap();
	}

	public Player getPlayer() {
		return player;
	}

	public long getGameTime() {
		return gameTime;
	}

	public NetMessageHandler getNetMessageHandler() {
		return messageHandler;
	}

	public List<Unit> getUnitsAt(Point2D point, int levelId) {
		double x = point.getX();
		double y = point.getY();
		List<Unit> units = new ArrayList<>();
		for (Unit unit : getAllUnits(levelId)) {
			if (unit.getX() < x && x < (unit.getX() + unit.getTileWidth()) && unit.getY() < y
					&& y < (unit.getY() + unit.getTileHeight())) {
				units.add(unit);
			}
		}
		return units;
	}

	public List<Unit> getAllUnits(int levelId) {
		List<Unit> units = new ArrayList<>();
		for (Level level : levels) {
			if(level.getLevelId() == levelId){
				for (Entity entity : level.getEntityList()) {
					if (entity instanceof Unit) {
						units.add((Unit) entity);
					}
				}
				return units;
			}
		}
		System.out.println("Game.getAllUnits: levelId not found: " + levelId);
		return null;
	}

	public List<Unit> getAllUnitsFromAllLevels() {
		List<Unit> units = new ArrayList<>();
		for (Level level : levels) {
			for (Entity entity : level.getEntityList()) {
				if (entity instanceof Unit) {
					units.add((Unit) entity);
				}
			}
		}
		return units;
	}

	public Entity getEnityById(int id) {
		for (Level level : levels) {
			for (Entity entity : level.getEntityList()) {
				if (entity.getId() == id)
					return entity;
			}
		}
		System.out.println("Entity id not found: " + id);
		return null;
	}
	
	public Level getLevelById(int levelId){
		for (Level level : levels) {
			if(level.getLevelId() == levelId){
				return level;
			}
		}
		System.out.println("Level id not found: " + levelId);
		return null;
	}
	
	/**
	 * toggles the game menu
	 */
	public void toggleMenu() {
		// toggle menu visibility flag
		isMenuVisible = !isMenuVisible;
		// set to toggled state
		menu.setVisible(isMenuVisible);
	}
}
