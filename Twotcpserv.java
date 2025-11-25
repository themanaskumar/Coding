import java.util.*;
import java.io.*;
import java.net.*;

public class Twotcpserv{
	public static void main(String args[]){
		int port = 3000;
		try(ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server started on port "+port+". Waiting for client to connect.");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected: "+clientSocket.getInetAddress().getHostAddress());
			System.out.println("Enter message to send. Type exit to end.");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			String clientMessage, serverMessage;
			while(true){
				if((clientMessage = in.readLine()) != null){
					if(clientMessage.equalsIgnoreCase("exit")){
						System.out.println("Client disconnected.");
						break;
					}
					System.out.println("Client: "+ clientMessage);
				}
				System.out.print("You: " );
				serverMessage = consoleReader.readLine();
				out.println(serverMessage);
				if(serverMessage.equalsIgnoreCase("exit")){
					System.out.println("You disconnected.");
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Server error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
