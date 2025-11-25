import java.io.*;
import java.net.*;

public class Onetcpserv{
	public static void main(String args[]){
		int port = 3000;
		try(ServerSocket serverSocket = new ServerSocket(port)){
			System.out.println("Server running on port: "+port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected to the server.");
			try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
				String line;
				while((line = in.readLine()) != null){
					System.out.println("Received: "+line);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
