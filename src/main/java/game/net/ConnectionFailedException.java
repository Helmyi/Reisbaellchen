package game.net;

@SuppressWarnings("serial")
public class ConnectionFailedException extends Exception{
	  public ConnectionFailedException() { super(); }
	  public ConnectionFailedException(String message) { super(message); }
}
