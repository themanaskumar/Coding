import java.util.*;
import java.net.*;
import java.io.*;

public class Twoudpserv {
    public static int serverPort = 9877;
    public static void main(String[] args) throws Exception { // Added throws Exception
        DatagramSocket serverSocket = new DatagramSocket(serverPort);
        // We keep the receive buffer separate and constant size
        byte[] receiveBuffer = new byte[1024];
        byte[] sendBuffer;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Server running on port: " + serverPort);
        while (true) {
            // 1. Reset packet for receiving (use the constant receiveBuffer)
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            // 2. Wait for message
            serverSocket.receive(receivePacket);
            String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (clientMessage.equalsIgnoreCase("exit")) {
                System.out.println("Client Disconnected");
                break;
            }
            System.out.println("Client: " + clientMessage);
            // 3. Get input from Server user
            System.out.print("You: ");
            String serverMessage = bufferReader.readLine();
            // 4. Prepare data to send
            sendBuffer = serverMessage.getBytes();
            // 5. ESSENTIAL: Get IP and Port from the RECEIVED packet, not hardcoded
            InetAddress senderAddress = receivePacket.getAddress();
            int senderPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, senderAddress, senderPort);
            serverSocket.send(sendPacket);
            if (serverMessage.equalsIgnoreCase("exit")) {
                System.out.println("You disconnected.");
                break;
            }
        }
        serverSocket.close();
    }
}
