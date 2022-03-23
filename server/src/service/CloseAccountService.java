package service;

import java.net.InetAddress;
import java.util.HashMap;

import main.Server;
import utils.DataUnpacker;

public class CloseAccountService {
	public void handleService(byte [] data, Server server,InetAddress clientAddress,int clientPortNumber) {

		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.INTEGER)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
		 		.setType("acc_no",DataUnpacker.TYPE.INTEGER)
		 		.setType("password",DataUnpacker.TYPE.INTEGER).execute(data);

		String name = (String) resultsMap.get("name");
		int password = (int) resultsMap.get("password");
		int acc_no = (int) resultsMap.get("acc_no");
//		System.out.println(name);
//		System.out.println(password);
//		System.out.println(acc_no);

		int flag = server.bank.closeAccount(name, password, acc_no);
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
			String s = "Account successfully closed.";
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