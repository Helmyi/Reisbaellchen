package game.player;

import game.Player;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PlayerAction_MoveUp_Pressed extends AbstractAction {
	private Player player;

	public PlayerAction_MoveUp_Pressed(Player player) {
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player.getKeyMoveUp())
			return;
		player.setKeyMoveUp(true);
		player.calcMoveDirection();
	}
}
