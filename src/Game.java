
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel{
	private static Game tehGame;
	private int fps = 60; 
	
	private int posX = 0;
	private int posY = 0;
	private int counter = 0;
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
		tehGame = new Game();
        
        JFrame frame = new JFrame("Reisbaellchen Game");
		frame.add(tehGame);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tehGame.run();
	}
	
	/**
	 * only paint, no logic
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g); //clears the content of the last frame
		Graphics2D g2d = (Graphics2D) g;
		
		
//		Color colors[] = {Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.GREEN, Color.ORANGE};
//		
//		for(int i=0; i < colors.length; i++){
//			g2d.setColor(colors[i]);
//			g2d.fillOval(posX+i*30, posY+i*30, 30, 30);
//		}
		testChar.paint(g2d);
	}
	

	/**
	 * game logic
	 */
	private void tick(){
		counter++;
		posX = (int)(Math.sin(counter/100.0)*70 + 100);
		posY = (int)(Math.cos(counter/100.0)*70 + 100);
	}
	
	public void run() {
		long beforeTime, timeDiff, sleep;		
		beforeTime = System.currentTimeMillis();
		
		//loop
		while (true) {
			tick();
			repaint();
			//passed time
			timeDiff = System.currentTimeMillis() - beforeTime;
			//calc sleep time
			sleep = (1000/fps) - timeDiff;
			if (sleep < 0) {
				sleep = 0;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			beforeTime = System.currentTimeMillis();
		}
	}
}