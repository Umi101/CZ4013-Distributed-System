package main;

import utils.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;
import service.OpenAccountService;
import service.CloseAccountService;
import service.UpdateAccountService;

import entity.Bank;

public class Server {
    public Socket designatedSocket;
    protected int portNumber;
    protected String ipAddress;
    protected byte[] buffer;
    public Bank bank;
    protected final int BUFFERSIZE = 2048;

    public Server(Socket socket) {
        this.designatedSocket = socket;
        this.buffer = new byte[BUFFERSIZE];
        this.bank = new Bank();
    }

    public void start() {

        while (true) {
            try {
                portNumber = designatedSocket.getSocket().getLocalPort();
                ipAddress = designatedSocket.getSocket().getLocalAddress().getHostAddress();
                // System.out.printf("Server active. Port: %d, IP: %s.%n", portNumber, ipAddress);
                DatagramPacket p = receive();
                InetAddress clientAddress = p.getAddress();
                int clientPortNumber = p.getPort();
				int serviceRequested = p.getData()[1];
				System.out.printf("The service requested is %d.%n", serviceRequested);
				byte[] data = p.getData();
				switch(serviceRequested) {
				case 1:
			  		OpenAccountService s1 = new OpenAccountService();
			  		s1.handleService(data,this,clientAddress,clientPortNumber);
					break;
				case 2:
			  		CloseAccountService s2 = new CloseAccountService();
			  		s2.handleService(data,this,clientAddress,clientPortNumber);
					break;
				case 3:
					UpdateAccountService s3 = new UpdateAccountService();
					s3.handleService(data, this, clientAddress, clientPortNumber);
				default:
					break;
				}
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