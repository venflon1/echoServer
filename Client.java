package it.ra.echoServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

	private Logger logger = Logger.getLogger(Client.class.getName());

	private Socket socket;
	private String addressServer;
	private int portServer;

	private Scanner stdIn;
	private Scanner sockIn;
	private PrintWriter sockOut;

	public Client() {
		super();
	}

	public Client(String addressServer, int portServer) throws UnknownHostException, IOException {
		this();
		this.addressServer = addressServer;
		this.portServer = portServer;
		this.socket = new Socket(addressServer, portServer);
		logger.info("CLIENT connected with server on port: " + this.socket.getLocalPort());

		this.sockIn = new Scanner(socket.getInputStream());
		this.sockOut = new PrintWriter(socket.getOutputStream());
		this.stdIn = new Scanner(System.in);
	}

	public void startClient() throws IOException {
		String input;
		
		do {
			input = stdIn.next();
			sockOut.println(input);
			logger.info("CLIENT send: " + input);
			sockOut.flush();
			
		}while(!input.equalsIgnoreCase("q"));

		logger.info("Done..");
		stdIn.close();
		sockIn.close();
		sockOut.close();
		socket.close();
	}

	public static void main(String[] args) throws IOException {
		Client clientEcho = new Client("localhost", 9099);
		clientEcho.startClient();
	}
}
