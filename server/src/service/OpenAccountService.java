package service;

import java.net.InetAddress;
import java.util.HashMap;
import utils.DataUnpacker;
import main.Server;


public class OpenAccountService {
	
	public void handleService(byte [] data, Server server,InetAddress clientAddress,int clientPortNumber) {
		
		
		
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.INTEGER)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("name",DataUnpacker.TYPE.STRING)
		 		.setType("password",DataUnpacker.TYPE.INTEGER)
		 		.setType("currency",DataUnpacker.TYPE.STRING)
		 		.setType("balance",DataUnpacker.TYPE.DOUBLE).execute(data);
		

		
		String name = (String) resultsMap.get("name");
		int password = (int) resultsMap.get("password");
		String currency = (String) resultsMap.get("currency");
		Double balance = (Double) resultsMap.get("balance");
		
		System.out.println(name);
		System.out.println(password);
		System.out.println(currency);
		System.out.println(balance);
		
		
		// Construct payload
		String s = "The account id is 1 \nhello \nworld special character &**()";
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

}
