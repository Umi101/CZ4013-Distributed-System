package service;

import java.net.InetAddress;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class CheckAccountBalance {
	
	public void handleService(byte [] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
						.setType("service_id", DataUnpacker.TYPE.TWO_BYTE_INT)
						.setType("message_id",DataUnpacker.TYPE.INTEGER)			
				 		.setType("name",DataUnpacker.TYPE.STRING)
				 		.setType("acc_no",DataUnpacker.TYPE.INTEGER)
				 		.setType("password",DataUnpacker.TYPE.INTEGER).execute(data);
		
						
		String s;
		int messageId = (int) resultsMap.get("message_id");
		String name = (String) resultsMap.get("name");
		int password = (int) resultsMap.get("password");
		int acc_no = (int) resultsMap.get("acc_no");
		
		System.out.printf("Message id: %d \n",messageId);
		System.out.println(name);
		System.out.println(password);
		System.out.println(acc_no);
		
		double balance = server.bank.checkAccountBalance(name, password, acc_no);
		System.out.println("------ Checking Account Balance.");
		if (balance == -1) {
			s = "Account does not exist. Try again.";
		}
		else {
			String currency = server.bank.checkAccountCurrency(name, password, acc_no);
			s = String.format("Your Current Account Balance is: %s%.2f", currency, balance);
		}
		
		byte[] buffer = new byte[s.length()];
		int index = 0;
		for(byte b: s.getBytes()) {
			buffer[index++] = b;
		}
		try {
			server.designatedSocket.send(buffer, clientAddress, clientPortNumber);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
	}
}
