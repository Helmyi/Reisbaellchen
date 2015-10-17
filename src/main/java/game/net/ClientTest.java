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
			server = new Socket("127.0.0.1", 27015);
			Scanner in = new Scanner(server.getInputStream());
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);

			out.println("Hallo Server");
			System.out.println("Done");
			

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
