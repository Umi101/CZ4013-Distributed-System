package main;

import entity.Listeners;
import utils.Socket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import service.OpenAccountService;
import service.CloseAccountService;
import service.UpdateAccountService;
import service.CheckAccountBalance;
import service.MonitorAccountService;
import service.MoneyTransferService;

import entity.Bank;

public class Server {
    public Socket designatedSocket;
    protected int portNumber;
    protected String ipAddress;
    protected byte[] buffer;
    public Bank bank;
    protected final int BUFFERSIZE = 2048;
    protected Listeners listeners;

    public Server(Socket socket) {
        this.designatedSocket = socket;
        this.buffer = new byte[BUFFERSIZE];
        this.bank = new Bank();
        this.listeners = new Listeners();
    }

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
    				int serviceRequested = p.getData()[1];
    				
    				
    				System.out.printf("The service requested is %d.%n", serviceRequested);
    				byte[] data = p.getData();
    				switch(serviceRequested) {
    				case 1:
    			  		OpenAccountService s1 = new OpenAccountService();
    			  		s1.handleService(data,this,clientAddress,clientPortNumber, listeners);
    					break;
    				case 2:
    			  		CloseAccountService s2 = new CloseAccountService();
    			  		s2.handleService(data,this,clientAddress,clientPortNumber, listeners);
    					break;
    				case 3:
    					UpdateAccountService s3 = new UpdateAccountService();
    					s3.handleService(data, this, clientAddress, clientPortNumber, listeners);
		            case 4:
		                MonitorAccountService s4 = new MonitorAccountService();
		                s4.handleService(data, this, clientAddress, clientPortNumber, listeners);
		                break;
    				case 5:
		                CheckAccountBalance s5 = new CheckAccountBalance();
		                s5.handleService(data, this, clientAddress, clientPortNumber, listeners);
		                break;
    				case 6:
    					MoneyTransferService s6 = new MoneyTransferService();
    					s6.handleService(data, this, clientAddress, clientPortNumber, listeners);
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

    public DatagramPacket receive() throws IOException{
        Arrays.fill(buffer, (byte) 0);
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
        System.out.println("Waiting for request...");
        this.designatedSocket.receive(p);
        System.out.println("received packet...");
        return p;
    }
}