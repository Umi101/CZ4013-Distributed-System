package main;

import utils.Socket;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server {
    protected Socket designatedSocket;
    protected int portNumber;
    protected String ipAddress;
    protected byte[] buffer;

    protected final int BUFFERSIZE = 2048;

    public Server(Socket socket) {
        this.designatedSocket = socket;
        this.buffer = new byte[BUFFERSIZE];
    }

    public void start() {
        while (true) {
            try {
                portNumber = designatedSocket.getSocket().getLocalPort();
                ipAddress = designatedSocket.getSocket().getLocalAddress().getHostAddress();
                System.out.printf("Server active. Port: %d, IP: %s.%n", portNumber, ipAddress);
                DatagramPacket p = receive();
                InetAddress clientAddress = p.getAddress();
                int clientPortNumber = p.getPort();
                System.out.println(p);

            } catch (IOException e) {
                e.printStackTrace();

            } catch (NullPointerException e) {
                Console.debug("Received corrupted data");
                e.printStackTrace();
            } finally {
                continue;
            }
        }
    }

    public DatagramPacket receive() throws IOException{
        Arrays.fill(buffer, (byte) 0);
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
        System.out.println("Waiting for request...");
        this.designatedSocket.receive(p);
        System.out.println("received packet...");
        return p;
    }

}