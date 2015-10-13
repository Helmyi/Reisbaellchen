package Game;
import java.awt.event.KeyEvent;


public class Player {
	private Unit playerUnit;
	private int key_MoveUp = KeyEvent.VK_W;
	private int key_MoveDown = KeyEvent.VK_S;
	private int key_MoveLeft = KeyEvent.VK_A;
	private int key_MoveRight = KeyEvent.VK_D;
	
	public Player(){
	}
	
	public void setPlayerUnit(Unit playerUnit){
		this.playerUnit = playerUnit;
	}
	
	public Unit getPlayerUnit(){
		return playerUnit;
	}
	
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == key_MoveUp){
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_UP);
			playerUnit.setMoving(true);
		}
		if(arg0.getKeyCode() == key_MoveDown){
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_DOWN);
			playerUnit.setMoving(true);
		}
		if(arg0.getKeyCode() == key_MoveRight){
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_RIGHT);
			playerUnit.setMoving(true);
		}
		if(arg0.getKeyCode() == key_MoveLeft){
			playerUnit.setUnitAction(Unit.UnitAction.MOVE_LEFT);
			playerUnit.setMoving(true);
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_J){
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_LEFT);
			playerUnit.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_K){
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_DOWN);
			playerUnit.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_I){
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_UP);
			playerUnit.setFighting(true);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_L){
			playerUnit.setUnitAction(Unit.UnitAction.ATTACK_RIGHT);
			playerUnit.setFighting(true);
		}
	}

	public void keyReleased(KeyEvent arg0) {
		playerUnit.setMoving(false);
		playerUnit.setFighting(false);
	}

	public void keyTyped(KeyEvent arg0) {
	}
	
}
