import java.io.*;
import java.net.*;

public class Twotcpcli{
	public static void main(String args[]){
		int port = 3000;
		String host = "localhost";
		try(Socket socket = new Socket(host, port)){
			System.out.println("Connected to chat server.");
			System.out.println("Enter message to send. Enter exit to disconnect.");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			String serverMessage, clientMessage;
			while(true){
				System.out.print("You: ");
				clientMessage = consoleReader.readLine();
				out.println(clientMessage);
				if(clientMessage.equalsIgnoreCase("exit")){
					System.out.println("You disconnected.");
					break;
				}
				if((serverMessage = in.readLine()) != null){
					if(serverMessage.equalsIgnoreCase("exit")){
						System.out.println("Server disconnected.");
						break;
					}
					System.out.println("Server: "+serverMessage);
				}
			}
		}
		catch(Exception e){
			System.out.println("client error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
