package service;

import java.net.InetAddress;
import java.util.HashMap;

import main.Server;
import utils.DataUnpacker;

public class MonitorAccountService {

	public void handleService(byte [] data, Server server,InetAddress clientAddress,int clientPortNumber) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("interval",DataUnpacker.TYPE.INTEGER).execute(data);

		int interval = (int) resultsMap.get("interval");
		int messageId = (int) resultsMap.get("message_id");
		
		System.out.println(interval);
		System.out.printf("Message id: %d \n",messageId);
		// Construct payload
		String s = "Monitoring ... \nhello \nworld special character &**()";
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
