package game;

import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class GameMenu extends JPopupMenu {

	public GameMenu() {
		this.add(new GameMenuItem("Option 1"));
		this.add(new GameMenuItem("Option 2"));
	}
}
