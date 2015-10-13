package Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import map.Map;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {
	private static Game theGame;
	private int fps = 60;
	private static int width = 500;
	private static int height = 600;
	private Point viewBegin;
	private Map map;

	private Character testChar;

	public Game() {
		try {
			testChar = new Character(ImageIO.read(new File("resources/Hero_Base.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		viewBegin = new Point(0, 0);
		setFocusable(true); // needed for listeners to work
		addKeyListener(this);

		map = new Map("resources/Zones/TestMap/Wüste.tmx");
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
		super.paint(g); // clears the content of the last frame
		Graphics2D g2d = (Graphics2D) g;

		map.paint(g2d);
		testChar.paint(g2d);
	}

	/**
	 * game logic
	 */
	private void tick() {
		testChar.tick();
		if (testChar.getX() < this.getWidth() / 2 - 16 || map.getTileCountX() * map.getTileSize() <= this.getWidth()) {
			viewBegin.setLocation(0, viewBegin.getY());
		} else {
			if (testChar.getX() > map.getTileCountX() * map.getTileSize() - this.getWidth() / 2 - 16) {
				viewBegin.setLocation(map.getTileCountX() * map.getTileSize() - this.getWidth(), viewBegin.getY());
			} else {
				viewBegin.setLocation((int) testChar.getX() - this.getWidth() / 2 + 16, viewBegin.getY());
			}
		}
		if (testChar.getY() < this.getHeight() / 2 - 16
				|| map.getTileCountY() * map.getTileSize() <= this.getHeight()) {
			viewBegin.setLocation(viewBegin.getX(), 0);
		} else {
			if (testChar.getY() > map.getTileCountY() * map.getTileSize() - this.getHeight() / 2 - 16) {
				viewBegin.setLocation(viewBegin.getX(), map.getTileCountY() * map.getTileSize() - this.getHeight());
			} else {
				viewBegin.setLocation(viewBegin.getX(), (int) testChar.getY() - this.getHeight() / 2 + 16);
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
		if (arg0.getKeyCode() == KeyEvent.VK_W) {
			testChar.setCharacterAction(Character.CharacterAction.UP);
			testChar.setMoving(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_S) {
			testChar.setCharacterAction(Character.CharacterAction.DOWN);
			testChar.setMoving(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			testChar.setCharacterAction(Character.CharacterAction.RIGHT);
			testChar.setMoving(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			testChar.setCharacterAction(Character.CharacterAction.LEFT);
			testChar.setMoving(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_J) {
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_LEFT);
			testChar.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_K) {
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_DOWN);
			testChar.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_I) {
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_UP);
			testChar.setFighting(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_L) {
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_RIGHT);
			testChar.setFighting(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		testChar.setMoving(false);
		testChar.setFighting(false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public static Game getGameInstance() {
		return theGame;
	}

	public Point getViewBegin() {
		return viewBegin;
	}
}