package service;

import java.net.InetAddress;
import java.util.HashMap;

import entity.Listeners;
import utils.DataUnpacker;
import main.Server;

public class OpenAccountService {
	
	public void handleService(byte[] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
		 		.setType("password",DataUnpacker.TYPE.INTEGER)
		 		.setType("currency",DataUnpacker.TYPE.STRING)
		 		.setType("balance",DataUnpacker.TYPE.DOUBLE).execute(data);

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
		
		

		int flag = server.bank.openAccount(name, password, currency, balance);
		if (flag == -1){
			String s = "Create account failed. Try again.";
			byte[] buffer = new byte[s.length()];
			int index = 0;
			for(byte b: s.getBytes()){
				buffer[index++] = b;
			}
			try {
				server.designatedSocket.send(buffer,clientAddress,clientPortNumber);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
		else
		{
			String s = String.format("New account created. The account id is: %d%n",flag);
			byte[] buffer = new byte[s.length()];
			int index = 0;
			for(byte b: s.getBytes()){
				buffer[index++] = b;
			}
			try {
				server.designatedSocket.send(buffer,clientAddress,clientPortNumber);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
		}
	}
}
