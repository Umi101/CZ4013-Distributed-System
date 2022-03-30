package service;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import entity.History;
import entity.Listeners;
import entity.History.Client;
import main.Server;
import utils.DataUnpacker;

public class MoneyTransferService {
	
	public void handleService(byte [] data, Server server, InetAddress clientAddress, int clientPortNumber, Listeners listeners, int semantic, History history) {
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

		if (semantic == 1) {
			int acc_exist = server.bank.verifyAccount(name, password, acc_no);
			int transfer_acc_exist = server.bank.checkAccountExist(transfer_acc_no);
			if (acc_exist == -1) {
				s = "Your account No. does not exist. Try again.";
			}
			else if (acc_exist == -2){
				s = "Your name does not match the record. Try again.";
			}
			else if (acc_exist == -3){
				s = "Your password does not match. Try again.";
			}
			else if (transfer_acc_exist == -1) {
				s = "The account No. you are transferring to does not exist. Try again.";
			}
			else if (acc_no == transfer_acc_no){
				s = "You cannot transfer money to and from the same account. Try again.";
			}
			else {
				String currency = server.bank.checkAccountCurrency(name, password, acc_no);
				String target_currency = server.bank.checkAccountCurrency(name, password, transfer_acc_no);
				boolean flag = currency.equals(target_currency);
				double current_acc_bal = server.bank.updateAccount(name, password, acc_no, -transfer_amount);
				System.out.println("------ Transferring.");
				if (current_acc_bal<0) {
					if (current_acc_bal == -1) {s = "Account does not exist. Try again.";}
					else {s = "Insufficient Balance. Try again";}
				}else {
					if (currency.equals("S$") && flag == false){
						double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, 0.8*transfer_amount);
						s = String.format("The currency type of your account is SGD but target account is USD.%n" +
								"Today's exchange rate is 1SGD = 0.8USD.%n" +
								String.format("Converting your %.2fSGD to %.2fUSD.%n",
										transfer_amount,
										0.8*transfer_amount)
								+ String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f",
								target_currency,
								0.8*transfer_amount,
								transfer_acc_no,
								currency,
								current_acc_bal));

					}
					else if (currency.equals("$") && flag == false){
						double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, 1.2*transfer_amount);
						s = String.format("The currency type of your account is USD but target account is SGD.%n" +
								"Today's exchange rate is 1USD = 1.2SGD.%n" +
								String.format("Converting your %.2fUSD to %.2fSGD.%n",
										transfer_amount,
										1.2*transfer_amount)
								+ String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f",
								target_currency,
								1.2*transfer_amount,
								transfer_acc_no,
								currency,
								current_acc_bal));
					}
					else{
						double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, transfer_amount);
						s = String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f", currency, transfer_amount, transfer_acc_no, currency, current_acc_bal);
					}}
			}
		}
		else {
<<<<<<< HEAD
			Client client = history.findClient(clientAddress, clientPortNumber);
			s = client.filterDuplicates(messageId);
			if (s == null) {
=======
			if (history.containsKey(messageId)) {
				System.out.println("Duplicated request filtered. Retransmitting response.");
				s = history.get(messageId);
			}
			else{
				int acc_exist = server.bank.verifyAccount(name, password, acc_no);
>>>>>>> branch 'main' of https://github.com/Umi101/CZ4013-Distributed-System.git
				int transfer_acc_exist = server.bank.checkAccountExist(transfer_acc_no);
				if (acc_exist == -1) {
					s = "Your account No. does not exist. Try again.";
				}
				else if (acc_exist == -2){
					s = "Your name does not match the record. Try again.";
				}
				else if (acc_exist == -3){
					s = "Your password does not match. Try again.";
				}
				else if (transfer_acc_exist == -1) {
					s = "The account No. you are transferring to does not exist. Try again.";
				}
				else if (acc_no == transfer_acc_no){
					s = "You cannot transfer money to and from the same account. Try again.";
				}
				else {
					String currency = server.bank.checkAccountCurrency(name, password, acc_no);
					String target_currency = server.bank.checkAccountCurrency(name, password, transfer_acc_no);
					boolean flag = currency.equals(target_currency);
					double current_acc_bal = server.bank.updateAccount(name, password, acc_no, -transfer_amount);
					System.out.println("------ Transferring.");

					if (current_acc_bal<0) {
						if (current_acc_bal == -1) {s = "Account does not exist. Try again.";}
						else {s = "Insufficient Balance. Try again";}
					}else {
						if (currency.equals("S$") && flag == false){
							double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, 0.8*transfer_amount);
							s = String.format("The currency type of your account is SGD but target account is USD.%n" +
											"Today's exchange rate is 1SGD = 0.8USD.%n" +
											String.format("Converting your %.2fSGD to %.2fUSD.%n",
									transfer_amount,
									0.8*transfer_amount)
											+ String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f",
									target_currency,
									0.8*transfer_amount,
									transfer_acc_no,
									currency,
									current_acc_bal));

						}
						else if (currency.equals("$") && flag == false){
							double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, 1.2*transfer_amount);
							s = String.format("The currency type of your account is USD but target account is SGD.%n" +
											"Today's exchange rate is 1USD = 1.2SGD.%n" +
											String.format("Converting your %.2fUSD to %.2fSGD.%n",
									transfer_amount,
									1.2*transfer_amount)
											+ String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f",
									target_currency,
									1.2*transfer_amount,
									transfer_acc_no,
									currency,
									current_acc_bal));
						}
						else{
							double transfer_acc_bal = server.bank.updateAccount(name, password, transfer_acc_no, transfer_amount);
							s = String.format("You have successfully transfered %s%.2f to account No %d. Your current balance: %s%.2f", currency, transfer_amount, transfer_acc_no, currency, current_acc_bal);
						}
					}
				}
				client.addToHistory(messageId, s);
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
		catch(SocketTimeoutException e) {
			System.out.println("Packet loss, unable to send data");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (listeners.getCount()!=0){listeners.broadcast(s, server.designatedSocket, clientAddress);}
	}
}
