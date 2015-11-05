package player;

import game.Player;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PlayerAction_MoveRight_Released extends AbstractAction {
	private Player player;

	public PlayerAction_MoveRight_Released(Player player) {
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.setKeyMoveRight(false);
		player.calcMoveDirection();
	}
}
