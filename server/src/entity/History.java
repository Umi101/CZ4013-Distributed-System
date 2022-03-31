package entity;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class History {
	
	private ArrayList<Client> clientList;
	
	/**
	 * Class constructor of History
	 */
	public History() {
		clientList = new ArrayList<>();
	}
	
	/**
	 * searches for existing client in clientList, else create a new client and insert into list
	 * @param address ipaddress
	 * @param port portnumber
	 * @return client object
	 */
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
	
	
	/**
	 * Represents each client that has sent the server a request before. 
	 */
	public class Client{
		private InetAddress address;
		private int portNumber;
		private HashMap<Integer, String> reply;
		public Client(InetAddress address, int portNumber) {
			this.address = address;
			this.portNumber = portNumber;
			this.reply = new HashMap<Integer, String>();
		}
		
		/**
		 * Searches if messageID exist in client hashmap
		 * @param messageId - messageId of incoming request
		 * @return reply to request if messageID does exists in the hashmap, null otherwise
		 */
		public String filterDuplicates(int messageId) {
			String reply = this.reply.get(messageId);
			if(reply != null) {
				System.out.println("Duplicated request. Resending reply");
			}
			return reply;
		}
		
		/**
		 * Adds a messageId and reply to hashmap after request is serviced.
		 * @param messageId - messageId of incoming request
		 * @param reply - reply sent to client for this request
		 */
		public void addToHistory(int messageId, String reply) {
			this.reply.put(messageId, reply);
		}
	}
	
	
}
