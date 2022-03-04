package utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Socket {
	private DatagramSocket socket;
	
	public Socket(DatagramSocket socket) {
		this.socket = socket;
	}
	
	public void send(byte[] data, InetAddress address, int port) throws IOException {
		System.out.println("InetAddress: " + address + ", Port: " + port);
		DatagramPacket p = new DatagramPacket(data, data.length, address, port);
		send(p);
		return;
	}
	
	public void receive (DatagramPacket p) throws IOException {
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
		this.socket.send(p);
	}
	
}
