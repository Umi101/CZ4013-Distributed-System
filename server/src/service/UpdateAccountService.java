package service;

import java.net.InetAddress;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class UpdateAccountService {

	public void handleService(byte[] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners) {
		

		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
				.setType("withdraw_choice",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
				.setType("acc_no",DataUnpacker.TYPE.INTEGER)
		 		.setType("password",DataUnpacker.TYPE.INTEGER)
		 		.setType("amount",DataUnpacker.TYPE.DOUBLE).execute(data);
		

		String s;
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
		double flag = server.bank.updateAccount(name, password, acc_no, amount);
		System.out.println("------ Updating Account.");
		if (flag < 0){
			if (flag == -1) {s = "Account does not exist. Try again.";}
			else {s = "Insufficient Balance. Try again.";}
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
			s = "Account successfully updated.";
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
