package game;

import game.ai.AI_MoveRandom;
import game.ai.UnitAI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import map.Map;
import map.Map3;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {
	Image imageBuffer;
	Graphics graphicBuffer;

	private static Game theGame;
	private int fps = 60;
	private static int width = 1000;
	private static int height = 600;
	private Point viewBegin;
	private Map map;

	private Player player;
	private List<UnitAI> unitAIs;
	private List<Image> unitImages;
	private List<Entity> entityList;

	public Game() {
		createTestLevel();

		setFocusable(true); // needed for listeners to work
		addKeyListener(this);
	}

	public static void main(String[] args) {
		theGame = new Game();

		JFrame frame = new JFrame("Reisbaellchen Game");
		frame.add(theGame);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		theGame.run();
	}

	/**
	 * only paint, no logic
	 */
	@Override
	public void paint(Graphics g) {
		if (imageBuffer == null) {
			imageBuffer = this.createImage(width, height);
			graphicBuffer = imageBuffer.getGraphics();
		}

		player.getPlayerCamera().paint(graphicBuffer);
		g.drawImage(imageBuffer, 0, 0, this);
	}

	/**
	 * game logic
	 */
	private void tick() {
		for (UnitAI ai : unitAIs) {
			ai.tick();
		}

		for (Entity ent : entityList) {
			ent.tick();
		}
	}

	public void createTestLevel() {
		entityList = new ArrayList<Entity>();
		unitImages = new ArrayList<Image>();
		unitAIs = new ArrayList<UnitAI>();
		player = new Player();
		viewBegin = new Point(0, 0);

		try {
			unitImages.add(ImageIO.read(new File("resources/Hero_Base.png")));
			entityList.add(new Unit(unitImages.get(0), 10 * 32, 5 * 32));
			player.setPlayerUnit((Unit) entityList.get(0));
			PlayerCamera cam = new PlayerCamera((int) player.getPlayerUnit().getX(),
					(int) player.getPlayerUnit().getY());
			cam.setPlayerTileWidth(player.getPlayerUnit().getTileWidth());
			cam.setPlayerTileHeight(player.getPlayerUnit().getTileHeight());
			player.setPlayerCamera(cam);

			entityList.add(new Unit(unitImages.get(0), 11 * 32, 5 * 32, 2));
			entityList.add(new Unit(unitImages.get(0), 13 * 32, 7 * 32, 15));
			entityList.add(new Unit(unitImages.get(0), 13 * 32, 7 * 32, 8));
			// entityList.add(new Unit(unitImages.get(0), 11 * 32, 8 * 32, 14));

			unitAIs.add(new AI_MoveRandom((Unit) entityList.get(1)));
			unitAIs.get(0).addUnit((Unit) entityList.get(2));
			// unitAIs.get(0).addUnit((Unit)entityList.get(3));
			// unitAIs.add(new AI_MoveRandom((Unit)entityList.get(4)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map = new Map3("resources/Zones/TestMap/Wüste1.tmx");
	}

	public void run() {
		long timeBefore, timePassed, sleepTime;
		timeBefore = System.currentTimeMillis();

		// loop
		while (true) {
			tick();
			repaint();

			timePassed = System.currentTimeMillis() - timeBefore;

			sleepTime = (1000 / fps) - timePassed;
			if (sleepTime < 0) {
				sleepTime = 0;
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
		if (arg0.getKeyCode() == KeyEvent.VK_0) {
			map = new Map3("resources/Zones/TestMap/Wüste1.tmx");

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

	public List<Entity> getEntityList() {
		return entityList;
	}

	public Point getViewBegin() {
		return viewBegin;
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}
}
