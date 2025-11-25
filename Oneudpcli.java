import java.io.*;
import java.net.*;
import java.util.*;

public class Oneudpcli{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		try(DatagramSocket socket = new DatagramSocket()){
			InetAddress serverAddress = InetAddress.getByName("localhost");
			int serverPort = 3000;
			System.out.print("Enter message to send: ");
			String message = sc.nextLine();
			byte[] sendBuffer = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
			socket.send(sendPacket);
			System.out.println("Message sent: " + message);
		}catch(Exception e){
			System.out.println("Client error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
