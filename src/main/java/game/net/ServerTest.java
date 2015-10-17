package game.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTest {
	
	public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(27015);

			Socket client = null;
			try {
				System.out.println("server started, waiting for Client");
				client = server.accept();
				Scanner in = new Scanner(client.getInputStream());
				PrintWriter out = new PrintWriter(client.getOutputStream(),	true);

			    String factor1 = in.nextLine();	
			    System.out.println("in: " + factor1);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
