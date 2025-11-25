import java.io.*;
import java.util.*;
import java.net.*;

public class Onetcpcli{
	public static void main(String args[]){
		String host = "localhost";
		int port = 3000;
		try(Socket socket = new Socket(host, port)){
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Connected to the server");
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter message to send: ");
			String msg = sc.nextLine();
			out.println(msg);
			System.out.println("Sent: "+msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Connection closed");
	}
}
