package game.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTest {
	public static void main(String[] args) {
		Socket server = null;

		try {
			server = new Socket("178.202.79.218", 27015);
			Scanner in = new Scanner(server.getInputStream());
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);

			out.println("Hallo Server");
		    String message = in.nextLine();	
		    System.out.println("in: " + message);
			System.out.println("Done");
			
			in.close();
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
				}
		}
	}
}
