package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private List<Image> unitImages;
	private List<Entity> entityList;

	public Game() {
		entityList = new ArrayList<Entity>();
		unitImages = new ArrayList<Image>();
		player = new Player();
		

		try {
			unitImages.add(ImageIO.read(new File("resources/Hero_Base.png")));
			entityList.add(new Unit(unitImages.get(0), 10 * 32, 5 * 32));
			player.setPlayerUnit((Unit) entityList.get(0));
			PlayerCamera cam = new PlayerCamera((int)player.getPlayerUnit().getX(), (int)player.getPlayerUnit().getY());
			cam.setPlayerTileWidth(player.getPlayerUnit().getTileWidth());
			cam.setPlayerTileHeight(player.getPlayerUnit().getTileHeight());
			player.setPlayerCamera(cam);

			entityList.add(new Unit(unitImages.get(0), 11 * 32, 5 * 32));
			entityList.add(new Unit(unitImages.get(0), 13 * 32, 7 * 32));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		viewBegin = new Point(0, 0);
		setFocusable(true); // needed for listeners to work
		addKeyListener(this);

		map = new Map("resources/Zones/TestMap/Wüste1.tmx");
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
		if (imageBuffer == null)
			imageBuffer = this.createImage(width, height);
		graphicBuffer = imageBuffer.getGraphics();
		graphicBuffer.setColor(Color.BLACK);
		graphicBuffer.fillRect(0, 0, width, height);

		Graphics2D g2d = (Graphics2D) graphicBuffer;
		map.paint(g2d);

		for (Entity ent : entityList) {
			ent.paint(g2d);
		}

		g.drawImage(imageBuffer, 0, 0, this);
	}

	/**
	 * game logic
	 */
	private void tick() {
		for (Entity ent : entityList) {
			ent.tick();
		}
		Unit testChar = player.getPlayerUnit();

		// adjust top left corner of image source to player position in order to
		// scroll map
		// check and adjust x position
		if (testChar.getX() < this.getWidth() / 2 - 16
				|| map.getTileCountX() * map.getTileSize() <= this.getWidth()) {
			viewBegin.setLocation(0, viewBegin.getY());
		} else {
			if (testChar.getX() > map.getTileCountX() * map.getTileSize()
					- this.getWidth() / 2 - 16) {
				viewBegin.setLocation(map.getTileCountX() * map.getTileSize()
						- this.getWidth(), viewBegin.getY());
			} else {
				viewBegin.setLocation((int) testChar.getX() - this.getWidth()
						/ 2 + 16, viewBegin.getY());
			}
		}
		// check and adjust y position
		if (testChar.getY() < this.getHeight() / 2 - 16
				|| map.getTileCountY() * map.getTileSize() <= this.getHeight()) {
			viewBegin.setLocation(viewBegin.getX(), 0);
		} else {
			if (testChar.getY() > map.getTileCountY() * map.getTileSize()
					- this.getHeight() / 2 - 16) {
				viewBegin.setLocation(viewBegin.getX(), map.getTileCountY()
						* map.getTileSize() - this.getHeight());
			} else {
				viewBegin.setLocation(viewBegin.getX(), (int) testChar.getY()
						- this.getHeight() / 2 + 16);
			}
		}
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

	public Point getViewBegin() {
		return viewBegin;
	}

	public Map getMap() {
		return map;
	}
}
