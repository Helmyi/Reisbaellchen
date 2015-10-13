
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
public class Game extends JPanel implements KeyListener{
	private static Game theGame;
	private int fps = 60; 
	private Map map;
	private Player player;
	
	private List<Image> unitImages;
	private List<Entity> entityList;
	
	private Character testChar;
	public Game()
	{
		entityList = new ArrayList<Entity>();
		unitImages = new ArrayList<Image>();
		try {
			unitImages.add(ImageIO.read(new File("resources/Hero_Base.png")));
			testChar = new Character(unitImages.get(0));
			entityList.add(new Unit(unitImages.get(0), 10*32, 5*32));
			entityList.add(new Unit(unitImages.get(0), 11*32, 5*32));
			entityList.add(new Unit(unitImages.get(0), 13*32, 7*32));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFocusable(true); // needed for listeners to work
		addKeyListener(this);
		
		map = new Map();
		
		
	}
	
	public static void main(String[] args) {
		theGame = new Game();
        
        JFrame frame = new JFrame("Reisbaellchen Game");
		frame.add(theGame);
		frame.setSize(1000, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		theGame.run();
	}
	
	/**
	 * only paint, no logic
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g); //clears the content of the last frame
		Graphics2D g2d = (Graphics2D) g;
		
		map.paint(g2d);
		testChar.paint(g2d);
		
		for(Entity ent: entityList){
			ent.paint(g2d);
		}
	}
	

	/**
	 * game logic
	 */
	private void tick(){
		testChar.tick();
	}
	
	public void run() {
		long timeBefore, timePassed, sleepTime;		
		timeBefore = System.currentTimeMillis();
		
		//loop
		while (true) {
			tick();
			repaint();

			timePassed = System.currentTimeMillis() - timeBefore;

			sleepTime = (1000/fps) - timePassed;
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
		if(arg0.getKeyCode() == KeyEvent.VK_W){
			testChar.setCharacterAction(Character.CharacterAction.UP);
			testChar.setMoving(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_S){
			testChar.setCharacterAction(Character.CharacterAction.DOWN);
			testChar.setMoving(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D){
			testChar.setCharacterAction(Character.CharacterAction.RIGHT);
			testChar.setMoving(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_A){
			testChar.setCharacterAction(Character.CharacterAction.LEFT);
			testChar.setMoving(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_J){
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_LEFT);
			testChar.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_K){
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_DOWN);
			testChar.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_I){
			testChar.setCharacterAction(Character.CharacterAction.ATTACK_UP);
			testChar.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_L){
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
}