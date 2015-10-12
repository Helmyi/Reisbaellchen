
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel{
	private static Game theGame;
	private int fps = 60; 
	
	private Character testChar;
	public Game()
	{
		try {
			testChar = new Character(ImageIO.read(new File("resources/RPG_Hero_Walk_Trans.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		theGame = new Game();
        
        JFrame frame = new JFrame("Reisbaellchen Game");
		frame.add(theGame);
		frame.setSize(600, 600);
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
		
		testChar.paint(g2d);
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
}