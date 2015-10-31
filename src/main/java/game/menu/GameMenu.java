package game.menu;

import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class GameMenu extends JPopupMenu{

	private GameMenuItem menuItem;

	public GameMenu() {
		init();
	}

	private void init() {
		menuItem = new GameMenuItem("Exit Game");
		this.add(menuItem);
	}
}
