package service;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class CloseAccountService {

	public void handleService(byte [] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic, HashMap<Integer, String> history) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
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
		
		System.out.println("------ Closing Account.");
		if (semantic == 1) {
			int flag = server.bank.closeAccount(name, password, acc_no);
			if (flag < 0){
				if (flag == -1) {s = "Account does not exist. Try again.";}
				else if (flag == -2) {s = "Name does not match. Try again.";}
				else {
					System.out.println("3333333333");
					s = "Password does not match. Try again.";}
			}
			else
			{
				s = String.format("Account id %d closed.",acc_no);

			}
		}
		else {
			if (history.containsKey(messageId)) {
				s = history.get(messageId);
			}
			else {
				int flag = server.bank.closeAccount(name, password, acc_no);				
				if (flag < 0){
					if (flag == -1) {s = "Account does not exist. Try again.";}
					else if (flag == -2) {s = "Name does not match. Try again.";}
					else {
						System.out.println("3333333333");
						s = "Password does not match. Try again.";}
				}
				else
				{
					s = String.format("Account id %d closed.",acc_no);

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
		catch(SocketTimeoutException e) {
			System.out.println("Packet loss, unable to send data");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
		
//		// Construct payload
//		String s = "The account id is closed \nhello \nworld special character &**()";
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
