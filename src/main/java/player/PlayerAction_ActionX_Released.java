package player;

import game.Game;
import game.Player;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PlayerAction_ActionX_Released extends AbstractAction {
	private Player player;

	public PlayerAction_ActionX_Released(Player player, int actionNumber) {
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(player.getPlayerUnit() == null) return;
		if (player.getPlayerUnit().isMoving()) {
			Game.getGameInstance()
					.getNetMessageHandler()
					.sendUnitAction(player.getPlayerUnit(), 1,
							player.getPlayerUnit().getViewDirection(),
							player.getPlayerUnit().isMoving());
		} else {
			Game.getGameInstance()
					.getNetMessageHandler()
					.sendUnitAction(player.getPlayerUnit(), 0,
							player.getPlayerUnit().getViewDirection(),
							player.getPlayerUnit().isMoving());
		}
	}
}
