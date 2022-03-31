package main;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

import utils.Socket;

public class Application {

    private static Server server;
    private static Socket socket;
    private static InetAddress address;
    private static int portNumber;
    private static double lossRate;

    public static void main(String[] args){
        Console console = new Console(new Scanner(System.in));
        try {
        	System.out.println("Starting server");
        	
        	/*-----------------Start of code to set server configurations-----------------------------------*/
			String addressInput = console.askForString("Input IP address hosting server on:");
			address = InetAddress.getByName(addressInput);
			portNumber = console.askForInteger("Input port number for server to listen at:");
            
            
            /*Specify what type of socket to use*/
            int socketType = console.askForInteger(1,2,"Select socket type : \n1) Normal socket \n2) Packet loss socket");
            if (socketType == 1){
            	lossRate = 0.0; //loss rate is 0 for normal socket
            }
            else{
            	/*Specify loss rate*/
            	lossRate = console.askForDouble(0.0, 0.99, "Input packet loss rate (min: 0.0 max: 0.99): ");
            }
            
            /*Specify type of invocation semantics*/
            int semantic = console.askForInteger(1,2,"Select invocation semantic (1 At-least-once  2 At-most-once): ");
 
            System.out.printf("Socket type: %s \n",socketType);
            socket = new Socket(new DatagramSocket(portNumber, address),socketType,lossRate);
            
            server = new Server(socket, semantic);
            /*Start server*/
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Console.debug("Server error");
            e.printStackTrace();
        }
    }
}
