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

//    private static final String IP_ADDR = "0.0.0.0";
    private static final String IP_ADDR = "127.0.0.1";
    private static final int SERVER_PORT_NO = 2222;
    private static final int CLIENT_PORT_NO = 2223;

    public static void main(String[] args){
        Console console = new Console(new Scanner(System.in));
        try {
            address = InetAddress.getByName(IP_ADDR);
            socket = new Socket(new DatagramSocket(SERVER_PORT_NO, address));
            System.out.printf("Starting server listening to Port %d, IP %s.%n", CLIENT_PORT_NO, IP_ADDR);
            server = new Server(socket);
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Console.debug("Server error");
            e.printStackTrace();
        }
    }
}
