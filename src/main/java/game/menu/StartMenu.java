package game.menu;

import game.Game;
import game.hero.Hero_Base;
import game.hero.Hero_FA;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartMenu extends JPanel implements ActionListener {
	private JPanel singleplayer;
	private JPanel startmenu;

	JPanel currentPanel;

	public StartMenu() {
		setBounds(0, 0, 200, 400);
		setOpaque(false);
		
		createStartMenuPanel();
		createSingleplayerPanel();
		
		setCurrentPanel(startmenu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Exit":
			System.exit(0);
			break;
		case "Singleplayer":
			setCurrentPanel(singleplayer);
			break;
		case "Singleplayer_Back":
			setCurrentPanel(startmenu);
			break;
		case "Hero1":
			Game.getGameInstance().setChosenPlayerHero(Hero_Base.class);
			break;
		case "Hero2":
			Game.getGameInstance().setChosenPlayerHero(Hero_FA.class);
			break;
		case "Singleplayer_Start":
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
	
	private void setCurrentPanel(JPanel panel){
		if(currentPanel != null) remove(currentPanel);
		currentPanel = panel;
		add(panel);
		panel.setVisible(true);
		validate();
	}
	
	private void createStartMenuPanel(){
		startmenu = new JPanel();
		startmenu.setOpaque(false);
		
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
		button4.setMaximumSize(new Dimension(150, 30));
		button4.setActionCommand("Exit");
		button4.addActionListener(this);

		
		//layout
		startmenu.setLayout(new BoxLayout(startmenu, BoxLayout.Y_AXIS));
		
		startmenu.add(Box.createRigidArea(new Dimension(100, 5)));
		startmenu.add(button1);
		startmenu.add(Box.createRigidArea(new Dimension(0, 10)));
		startmenu.add(button2);
		startmenu.add(Box.createRigidArea(new Dimension(0, 10)));
		startmenu.add(button3);
		startmenu.add(Box.createRigidArea(new Dimension(0, 10)));
		startmenu.add(button4);
	}

	private void createSingleplayerPanel(){
		singleplayer = new JPanel();
		singleplayer.setOpaque(false);
		
		JButton button1 = new JButton("Back");
		button1.setMaximumSize(new Dimension(150, 30));
		button1.setActionCommand("Singleplayer_Back");
		button1.addActionListener(this);

		JButton button2 = new JButton("Hero1");
		button2.setMaximumSize(new Dimension(150, 30));
		button2.setActionCommand("Hero1");
		button2.addActionListener(this);

		JButton button3 = new JButton("Hero2");
		button3.setMaximumSize(new Dimension(150, 30));
		button3.setActionCommand("Hero2");
		button3.addActionListener(this);
		
		JButton button4 = new JButton("Start");
		button4.setMaximumSize(new Dimension(150, 30));
		button4.setActionCommand("Singleplayer_Start");
		button4.addActionListener(this);
		
		//layout
		singleplayer.setLayout(new BoxLayout(singleplayer, BoxLayout.Y_AXIS));
		
		singleplayer.add(Box.createRigidArea(new Dimension(100, 5)));
		singleplayer.add(button1);
		singleplayer.add(Box.createRigidArea(new Dimension(0, 10)));
		singleplayer.add(button2);
		singleplayer.add(Box.createRigidArea(new Dimension(0, 10)));
		singleplayer.add(button3);
		singleplayer.add(Box.createRigidArea(new Dimension(0, 10)));
		singleplayer.add(button4);
	}
}
