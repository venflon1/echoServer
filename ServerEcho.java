package it.ra.echoServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerEcho {

	private Logger logger = Logger.getLogger(ServerEcho.class.getName());

	private ServerSocket serverSocket;
	int serverPortListen;
	private Socket socketClient;
	private Scanner in;
	private PrintWriter out;

	public ServerEcho() {
		super();
	}

	public ServerEcho(int port) throws IOException {
		this();
		this.serverPortListen = port;
		this.serverSocket = new ServerSocket(serverPortListen);
		logger.info("SERVER listening on port: " + serverPortListen);

		this.socketClient = this.serverSocket.accept(); // questo metodo e' bloccante per il server
		logger.info("SERVER accepted client");

		this.in = new Scanner(socketClient.getInputStream());
		this.out = new PrintWriter(socketClient.getOutputStream());
	}

	public void handle() throws IOException {
		String echo;
		do{
			echo = in.next();
			logger.info("SERVERecho " + echo);
		}
		while (!echo.equalsIgnoreCase("quit"));

		logger.info("SERVER disconnected....");
		this.in.close();
		this.out.close();
		this.socketClient.close();
		this.serverSocket.close();
	}

	public static void main(String[] args) throws IOException {
		ServerEcho serverEcho = new ServerEcho(9099);
		serverEcho.handle();
	}
}