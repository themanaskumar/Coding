import java.io.*;
import java.net.*;

public class Twoudpcli {
    public static void main(String args[]) throws Exception {
        // 1. Create socket (No port needed, OS assigns a random available one)
        DatagramSocket clientSocket = new DatagramSocket();
        // 2. Define Server Address and Port (Must match Server's listening port)
        InetAddress IPAddress = InetAddress.getByName("localhost");
        int serverPort = 9877; 
        byte[] sendBuffer;
        byte[] receiveBuffer = new byte[1024];
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Client started. Type a message to connect.");
        while (true) {
            // STEP 1: SEND FIRST (Unlike Server which Receives first)
            System.out.print("You: ");
            String clientMessage = consoleReader.readLine();
            if (clientMessage == null || clientMessage.equalsIgnoreCase("exit")) {
                // Send exit message to server so it knows to stop
                String exitMsg = "exit";
                sendBuffer = exitMsg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, IPAddress, serverPort);
                clientSocket.send(sendPacket);
                System.out.println("You disconnected.");
                break;
            }
            sendBuffer = clientMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, IPAddress, serverPort);
            clientSocket.send(sendPacket);
            // STEP 2: RECEIVE REPLY
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);
            String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (serverMessage.equalsIgnoreCase("exit")) {
                System.out.println("Server disconnected.");
                break;
            }
            System.out.println("Server: " + serverMessage);
        }
        clientSocket.close();
    }
}
