import java.io.*;
import java.net.*;

public class Oneudpserv{
	public static void main(String[] args){
		int port = 3000;
		try(DatagramSocket socket = new DatagramSocket(port)){
			System.out.println("UDP server running on port 3000");
			byte[] receiveBuffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			socket.receive(receivePacket);
			String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
			InetAddress clientAddress = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			System.out.println("Client address: "+clientAddress+":"+clientPort);
			System.out.println("Message: "+message);
		}catch(Exception e){
			System.out.println("Server exception: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
