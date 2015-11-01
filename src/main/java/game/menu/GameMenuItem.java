package game.menu;

import javax.swing.JMenuItem;

import game.menu.actions.MenuActionExit;

@SuppressWarnings("serial")
public class GameMenuItem extends JMenuItem {

	public GameMenuItem(String optionLabel) {
		super(optionLabel);
		this.addActionListener(new MenuActionExit());
//		this.setRolloverEnabled(true);
//		this.setRolloverIcon(new ImageIcon("")); TODO
		this.setToolTipText("Click to exit game");
	}
	
	
}