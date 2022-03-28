package utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Socket {
	private DatagramSocket socket;
	private int socketType;
	private double lossRate;
	private final Random random;
	
	public Socket(DatagramSocket socket, int socketType, double lossRate) {
		this.socket = socket;
		this.socketType = socketType; // 1 for normal socket, 2 for simulate loss of requests/reply
		this.random = new Random();
		this.lossRate = lossRate;
//		this.lossRate = 0.97;
	}
	
	public int getSocketType() {
		return this.socketType;
	}
	
	
	public void send(byte[] data, InetAddress address, int port) throws IOException {
		System.out.println("InetAddress: " + address + ", Port: " + port);
		DatagramPacket p = new DatagramPacket(data, data.length, address, port);
		send(p);
		return;
	}
	
	public void receive (DatagramPacket p) throws IOException {

		if (socketType == 2) {
			double prob = random.nextDouble();
			System.out.printf("Prob : %f\n", prob);
			if (prob < lossRate) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				throw new SocketTimeoutException();
				
			}
		}
		this.socket.receive(p);
		return;
	}
	
	public void close() {
		this.socket.close();
		return;
	}
	
	public void setTimeOut(int timeout) throws SocketException {
		this.socket.setSoTimeout(timeout);
		return;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public void send(DatagramPacket p) throws IOException {
		if (socketType == 2) {
			double prob = random.nextDouble();
			System.out.printf("Prob : %f\n", prob);
			if (prob < lossRate) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				throw new SocketTimeoutException();
				
			}
		}
		this.socket.send(p);
	}
	
}
