package service;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class MonitorAccountService {

	public void handleService(byte[] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
				.setType("service_id",DataUnpacker.TYPE.TWO_BYTE_INT)
				.setType("message_id",DataUnpacker.TYPE.INTEGER)
		 		.setType("interval",DataUnpacker.TYPE.INTEGER).execute(data);

		int interval = (int) resultsMap.get("interval");
		int messageId = (int) resultsMap.get("message_id");
		
//		System.out.println(interval);
//		System.out.printf("Message id: %d \n",messageId);
		listeners.addListener(clientPortNumber, interval);
		// Construct payload
		String s = "Monitoring ...";
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
	}
}
