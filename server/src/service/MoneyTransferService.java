package service;

import java.net.InetAddress;
import java.util.HashMap;

import entity.Listeners;
import main.Server;
import utils.DataUnpacker;

public class MoneyTransferService {
	
	public void handleService(byte [] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic) {
		HashMap <String, Object> resultsMap = new DataUnpacker.DataPackage()
						.setType("service_id", DataUnpacker.TYPE.TWO_BYTE_INT)
						.setType("message_id",DataUnpacker.TYPE.INTEGER)			
				 		.setType("name",DataUnpacker.TYPE.STRING)
				 		.setType("acc_no",DataUnpacker.TYPE.INTEGER)
				 		.setType("password",DataUnpacker.TYPE.INTEGER)
				 		.setType("transfer_acc_no", DataUnpacker.TYPE.INTEGER)
				 		.setType("transfer_amount", DataUnpacker.TYPE.DOUBLE).execute(data);
						
		
		String s;
		int messageId = (int) resultsMap.get("message_id");
		String name = (String) resultsMap.get("name");
		int password = (int) resultsMap.get("password");
		int acc_no = (int) resultsMap.get("acc_no");
		int transfer_acc_no = (int) resultsMap.get("transfer_acc_no");
		double transfer_amount = (double) resultsMap.get("transfer_amount");
		
		System.out.printf("Message id: %d \n",messageId);
		System.out.println(name);
		System.out.println(password);
		System.out.println(acc_no);
		System.out.println(transfer_acc_no);
		System.out.println(transfer_amount);
		
		int transfer_acc_exist = server.bank.checkAccountExist(transfer_acc_no);
		if (transfer_acc_exist == -1) {
			s = "The account you are transferring to does not exist. Try Again";
		}else {
			double current_acc_bal = server.bank.updateAccount(name, password, acc_no, -transfer_amount);
			System.out.println("------ Transferring.");
			if (current_acc_bal<0) {
				if (current_acc_bal == -1) {s = "Account does not exist. Try again.";}
				else {s = "Insufficient Balance. Try again";}
			}else {
				double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, transfer_amount);
				String currency = server.bank.checkAccountCurrency(name, password, acc_no);
				s = String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f", currency, transfer_amount, transfer_acc_no, currency, current_acc_bal);
			}
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
