package game.player;

import game.Game;
import game.Player;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PlayerAction_ActionX_Pressed extends AbstractAction {
	private Player player;
	private int actionNumber;

	public PlayerAction_ActionX_Pressed(Player player, int actionNumber) {
		this.actionNumber = actionNumber;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(player.getPlayerUnit() == null) return;
		if (player.getPlayerUnit().getAction() != actionNumber) {
			Game.getGameInstance()
					.getNetMessageHandler()
					.sendUnitAction(player.getPlayerUnit(), actionNumber,
							player.getPlayerUnit().getViewDirection(),
							player.getPlayerUnit().isMoving());
		} else {
			return;
		}
	}
}
