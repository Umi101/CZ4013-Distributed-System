package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 2222;

        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();

        byte[] buffer = new byte[512];

        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);

        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()){
            byte[] temp = s.nextLine().getBytes(StandardCharsets.UTF_8);
            request.setData(temp);
            socket.send(request);
            System.out.println("sent");
        }

    }
}
