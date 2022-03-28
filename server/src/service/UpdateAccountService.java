package service;

import java.net.InetAddress;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class UpdateAccountService {

	public void handleService(byte[] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic, HashMap<Integer, String> history) {
		

		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
				.setType("withdraw_choice",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
				.setType("acc_no",DataUnpacker.TYPE.INTEGER)
		 		.setType("password",DataUnpacker.TYPE.INTEGER)
		 		.setType("amount",DataUnpacker.TYPE.DOUBLE).execute(data);
		

		String s = null;
		String name = (String) resultsMap.get("name");
		int messageId = (int) resultsMap.get("message_id");
		int password = (int) resultsMap.get("password");
		int toWithdraw = (int) resultsMap.get("withdraw_choice");
		int acc_no = (int) resultsMap.get("acc_no");
		double amount = (double) resultsMap.get("amount");
		
		System.out.printf("Message id: %d \n",messageId);
		
		System.out.println(toWithdraw);
		System.out.println(name);
		System.out.println(password);
		if (toWithdraw == 1) {
			amount = -amount;
		}
//		System.out.println(amount);
		System.out.println("------ Updating Account.");
		if (semantic == 1) {
			double flag = server.bank.updateAccount(name, password, acc_no, amount);

			if (flag < 0){
				if (flag == -1) {s = "Account does not exist. Try again.";}
				else {s = "Insufficient Balance. Try again.";}
				
			}
			else
			{
				String currency = server.bank.checkAccountCurrency(name, password, acc_no);
				s = String.format("Account successfully updated. Your current account balance is: %s%.2f", currency, flag);
			}
		}
		else {
			if (history.containsKey(messageId)) {
				s = history.get(messageId);
			}
			else {
				double flag = server.bank.updateAccount(name, password, acc_no, amount);
				if (flag < 0){
					if (flag == -1) {s = "Account does not exist. Try again.";}
					else {s = "Insufficient Balance. Try again.";}
					
				}
				else
				{
					String currency = server.bank.checkAccountCurrency(name, password, acc_no);
					s = String.format("Account successfully updated. Your current account balance is: %s%.2f", currency, flag);
				}
				history.put(messageId, s);
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
		catch(Exception e) {
			e.printStackTrace();
		}
		if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
		
//		// Construct payload
//		String s = "Updaing account in progress ... \nhello \nworld special character &**()";
//		byte[] buffer = new byte[s.length()];
//		int index = 0;
//		for(byte b: s.getBytes()){
//			buffer[index++] = b;
//		}
//		try {
//			server.designatedSocket.send(buffer,clientAddress,clientPortNumber);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
	}
}
