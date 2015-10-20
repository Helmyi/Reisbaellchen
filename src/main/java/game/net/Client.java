package game.net;

public abstract class Client extends Thread{
	private int clientNumber;
	public abstract void sendMessage(String message);
	
	public void setClientNumber(int clientNumber){
		this.clientNumber = clientNumber;
	}
	
	public int getClientNumber(){
		return clientNumber;
	}
}
