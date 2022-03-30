package service;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import entity.History;
import entity.History.Client;
import entity.Listeners;
import utils.DataUnpacker;
import main.Server;

public class OpenAccountService {
	
	public void handleService(byte[] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic, History history) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
		 		.setType("password",DataUnpacker.TYPE.INTEGER)
		 		.setType("currency",DataUnpacker.TYPE.STRING)
		 		.setType("balance",DataUnpacker.TYPE.DOUBLE).execute(data);
		
		String s;
		int messageId = (int) resultsMap.get("message_id");
		String name = (String) resultsMap.get("name");
		int password = (int) resultsMap.get("password");
		String currency = (String) resultsMap.get("currency");
		Double balance = (Double) resultsMap.get("balance");
		
		
		System.out.printf("Message id: %d \n",messageId);
//		System.out.println("balance test");
//		System.out.println(balance);
//		System.out.printf("------ Account name: %s%n",name);
//		System.out.printf("------ Account password: %s%n", password);
//		System.out.printf("------ Account currency: %s%n",currency);
//		System.out.printf("------ Account balance: %f%n",balance);
		
		
		if (semantic == 1) {
			int flag = server.bank.openAccount(name, password, currency, balance);
			if (flag == -1){
				s = "Account already existed. Try again.";

			}
			else
			{
				s = String.format("New account created. The account id is: %d%n",flag);
				
			}
		}
		else{
			Client client = history.findClient(clientAddress, clientPortNumber);
			s = client.filterDuplicates(messageId);
			if (s == null) {
				int flag = server.bank.openAccount(name, password, currency, balance);
				if (flag == -1){
					s = "Account already existed. Try again.";

				}
				else
				{
					s = String.format("New account created. The account id is: %d%n",flag);
					
				}
				client.addToHistory(messageId, s);
			}
		}
		
		
		byte[] buffer = new byte[s.length()];
		int index = 0;
		for(byte b: s.getBytes()){
			buffer[index++] = b;
		}
		try {
			server.designatedSocket.send(buffer,clientAddress,clientPortNumber);
		}
		catch(SocketTimeoutException e) {
			System.out.println("Packet loss, unable to send data");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
		
		
	}
	
}
