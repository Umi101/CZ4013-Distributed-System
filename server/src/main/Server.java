package main;

import entity.Listeners;
import utils.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import service.OpenAccountService;
import service.CloseAccountService;
import service.UpdateAccountService;
import service.CheckAccountBalance;
import service.MonitorAccountService;
import service.MoneyTransferService;

import entity.Bank;
import entity.History;

public class Server {
    public Socket designatedSocket;
    protected int portNumber;
    protected String ipAddress;
    protected byte[] buffer;
    public Bank bank;
    protected final int BUFFERSIZE = 2048;
    protected Listeners listeners;
    protected int semantic;
    public History history;

    /**
	 * Class constructor of Server	
	 * @param socket - socket object used to send and receive messages
	 * @param semantic - At-Least-Once or At-Most-Once
	 */
    
    public Server(Socket socket, int semantic) {
        this.designatedSocket = socket;
        this.buffer = new byte[BUFFERSIZE];
        this.bank = new Bank();
        this.listeners = new Listeners();
        this.semantic = semantic;
        this.history = new History();
    }

    /**
	 * Server starts listening for incoming request once called
	 */
    @SuppressWarnings("finally")
	public void start() {

        while (true) {
            try {
                portNumber = designatedSocket.getSocket().getLocalPort();
                ipAddress = designatedSocket.getSocket().getLocalAddress().getHostAddress();
                // System.out.printf("Server active. Port: %d, IP: %s.%n", portNumber, ipAddress);
                DatagramPacket p = receive();                
                if(p.getLength() != 0) {
                    InetAddress clientAddress = p.getAddress();
                    int clientPortNumber = p.getPort();
                    //Service ID from client is the second byte in the byte array sent from client
    				int serviceRequested = p.getData()[1];
    				
    				
    				System.out.printf("The service requested is %d.%n", serviceRequested);
    				byte[] data = p.getData();
    				switch(serviceRequested) {
    				case 1:
    			  		OpenAccountService s1 = new OpenAccountService();
    			  		s1.handleService(data,this,clientAddress,clientPortNumber, listeners, semantic, history);
    					break;
    				case 2:
    			  		CloseAccountService s2 = new CloseAccountService();
    			  		s2.handleService(data,this,clientAddress,clientPortNumber, listeners, semantic, history);
    					break;
    				case 3:
    					UpdateAccountService s3 = new UpdateAccountService();
    					s3.handleService(data, this, clientAddress, clientPortNumber, listeners, semantic, history);
    					break;
		            case 4:
		                MonitorAccountService s4 = new MonitorAccountService();
		                s4.handleService(data, this, clientAddress, clientPortNumber, listeners, semantic);
		                break;
    				case 5:
		                CheckAccountBalance s5 = new CheckAccountBalance();
		                s5.handleService(data, this, clientAddress, clientPortNumber, listeners, semantic);
		                break;
    				case 6:
    					MoneyTransferService s6 = new MoneyTransferService();
    					s6.handleService(data, this, clientAddress, clientPortNumber, listeners, semantic, history);
    				default:
    					break;
    				}
                    System.out.println(p);
                }
            
            }
            catch (SocketTimeoutException e) {
            	System.out.println("Packet loss, unable to receive data");
            }
            catch (IOException e) {	
                e.printStackTrace();   
            } 
            catch (NullPointerException e) {

                Console.debug("Received corrupted data");
                e.printStackTrace();
            }
            
            finally {
                continue;
            }
        }
    }
    
	/**
	 * Listen for incoming messages
	 * @return DatagramPacket with new message
	 * @throws IOException
	 */
    public DatagramPacket receive() throws IOException{
        Arrays.fill(buffer, (byte) 0);
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
        System.out.println("Waiting for request...");
        this.designatedSocket.receive(p);
        System.out.println("received packet...");
        return p;
    }
}