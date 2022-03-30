package entity;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class History {
	
	private ArrayList<Client> clientList;
	
	public History() {
		clientList = new ArrayList<>();
	}
	
	public Client findClient(InetAddress address, int port) {
		for (Client c: clientList) {
			if(c.address.equals(address) && c.portNumber==port) {
				return c;
			}
		}
		Client newClient = new Client(address, port);
		clientList.add(newClient);
		return newClient;
	}
	
	
	public class Client{
		private InetAddress address;
		private int portNumber;
		private HashMap<Integer, String> reply;
		public Client(InetAddress address, int portNumber) {
			this.address = address;
			this.portNumber = portNumber;
			this.reply = new HashMap<Integer, String>();
		}
		
		
		public String filterDuplicates(int messageId) {
			String reply = this.reply.get(messageId);
			if(reply != null) {
				System.out.println("Duplicated request. Resending reply");
			}
			return reply;
		}
		
		
		public void addToHistory(int messageId, String reply) {
			this.reply.put(messageId, reply);
		}
	}
	
	
}
