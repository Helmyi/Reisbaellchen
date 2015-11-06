package game.menu;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartMenu extends JPanel implements ActionListener {
	JButton singleplayer;
	JButton multiplayer;

	JPanel currentPanel;

	public StartMenu() {
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 0, 200, 200);
		setOpaque(false);
//		setBorder(new EmptyBorder(new Insets(0, 0, 1000, 600)));
		createStartMenuPanel();
	}
	
	private void createStartMenuPanel(){
		JPanel tempPanel = new JPanel();
		tempPanel.setOpaque(false);
		
		JButton button1 = new JButton("Singleplayer");
		button1.setMaximumSize(new Dimension(150, 30));
		button1.setActionCommand("Singleplayer");
		button1.addActionListener(this);

		JButton button2 = new JButton("Multiplayer");
		button2.setMaximumSize(new Dimension(150, 30));
		button2.setActionCommand("Multiplayer");
		button2.addActionListener(this);

		JButton button3 = new JButton("Options");
		button3.setMaximumSize(new Dimension(150, 30));
		button3.setActionCommand("Options");
		button3.addActionListener(this);

		JButton button4 = new JButton("Exit");
//		button4.setMinimumSize(new Dimension(150, 30));
//		button4.setPreferredSize(new Dimension(150, 30));
		button4.setMaximumSize(new Dimension(150, 30));
		button4.setActionCommand("Exit");
		button4.addActionListener(this);

		
		//layout
		tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
		
		tempPanel.add(button1);
		tempPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		tempPanel.add(button2);
		tempPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		tempPanel.add(button3);
		tempPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		tempPanel.add(button4);
		
		this.add(tempPanel, BorderLayout.WEST);
		currentPanel = tempPanel;
	}

//	@Override
//	public void paint(Graphics g) {
//		paintComponents(g);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Exit":
			System.exit(0);
			break;
		case "Singleplayer":
			Game.getGameInstance().startSinglePlayer();
			break;
		case "Multiplayer":
			Game.getGameInstance().startMultiPlayer();
			break;
		default:
			System.out.println("test " + e.getActionCommand());
			break;
		}
	}
}
