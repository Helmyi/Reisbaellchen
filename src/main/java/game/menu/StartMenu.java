package game.menu;

import game.Game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class StartMenu extends JPanel implements ActionListener {
	private JPanel singleplayer;
	private JPanel multiplayer;
	private JPanel startmenu;

	JPanel currentPanel;

	public StartMenu() {
		setBounds(0, 0, 200, 400);
		setOpaque(false);
		
		createStartMenuPanel();
		createSingleplayerPanel();
		createMultiplayerPanel();
		
		setCurrentPanel(startmenu);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Exit":
			System.exit(0);
			break;
		case "BackToStartmenu":
			setCurrentPanel(startmenu);
			break;
		case "Singleplayer":
			setCurrentPanel(singleplayer);
			break;
		case "Hero1":
			Game.getGameInstance().setHeroPickNumber(0);
			break;
		case "Hero2":
			Game.getGameInstance().setHeroPickNumber(1);
			break;
		case "Singleplayer_Start":
			Game.getGameInstance().startSinglePlayer();
			break;
		case "Multiplayer":
			setCurrentPanel(multiplayer);
			break;
		case "Multiplayer_Connect":
			int textFieldCounter = 0;
			for(int i=0; i< multiplayer.getComponentCount(); i++){
				//first text field IP, second text field Port
				if(textFieldCounter == 0){
					if(multiplayer.getComponent(i) instanceof JTextField){
						textFieldCounter++;
						System.out.println(((JTextField) multiplayer.getComponent(i)).getText());
						Game.getGameInstance().setIp(((JTextField) multiplayer.getComponent(i)).getText());
					}
				}else if(textFieldCounter == 1){
					if(multiplayer.getComponent(i) instanceof JTextField){
						textFieldCounter++;
						String tempPortText = ((JTextField) multiplayer.getComponent(i)).getText();
						System.out.println(tempPortText);
						Game.getGameInstance().setPort(Integer.parseInt(tempPortText));
						break;
					}
				}
			}
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

	private void createMultiplayerPanel(){
		multiplayer = new JPanel();
		multiplayer.setOpaque(false);
		
		JButton button1 = new JButton("Back");
		button1.setMaximumSize(new Dimension(150, 30));
		button1.setActionCommand("BackToStartmenu");
		button1.addActionListener(this);

		JButton button2 = new JButton("Connect");
		button2.setMaximumSize(new Dimension(150, 30));
		button2.setActionCommand("Multiplayer_Connect");
		button2.addActionListener(this);
		
		JTextField textField1 = new JTextField(Game.getGameInstance().getIp());
		JTextField textField2 = new JTextField("" + Game.getGameInstance().getPort());

		//layout
		multiplayer.setLayout(new BoxLayout(multiplayer, BoxLayout.Y_AXIS));
		
		multiplayer.add(Box.createRigidArea(new Dimension(100, 5)));
		multiplayer.add(button1);
		multiplayer.add(Box.createRigidArea(new Dimension(0, 5)));
		multiplayer.add(textField1);
		multiplayer.add(Box.createRigidArea(new Dimension(0, 5)));
		multiplayer.add(textField2);
		multiplayer.add(Box.createRigidArea(new Dimension(0, 5)));
		multiplayer.add(button2);
	}

	private void createSingleplayerPanel(){
		singleplayer = new JPanel();
		singleplayer.setOpaque(false);
		
		JButton button1 = new JButton("Back");
		button1.setMaximumSize(new Dimension(150, 30));
		button1.setActionCommand("BackToStartmenu");
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
