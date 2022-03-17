package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.nio.ByteBuffer;

public class DataUnpacker {
	
	private ArrayList<String> keys;
//	private HashMap <String, TYPE> results;
	
	
	public enum TYPE { INTEGER, DOUBLE, STRING}
	public DataUnpacker(){
		keys = new ArrayList<>();
//		results = new HashMap<>();
	}
	
	public HashMap <String, Object> execute(byte[] data){
		HashMap<String, Object> map = new HashMap<>();
		try {
			int offset = 4;
			for(int i =0 ;i < keys.size();i++) {
				if (keys.get(i).equals("name")){
					offset += 4; // name == 4 , (string)
					int len = bytesToInt(data,offset);
					offset += 4;
					String name = bytesToString(data,offset,len);
					map.put("name",name);
					offset += len;
				}
				if (keys.get(i).equals("password")){
					offset += 8; // password  == 8, int()
					int password = bytesToInt(data,offset);
					map.put("password",password);
					offset += 4 ;
				}
				if (keys.get(i).equals("currency")){
					offset += 8; // currency == 8 , (string)
					int len = bytesToInt(data,offset);
					offset += 4;
					String currency = bytesToString(data,offset,len);
					map.put("currency",currency);
					offset += 4;
				}
				if (keys.get(i).equals("balance")){
					offset += 6; // balance == 8 , (double)
					Double balance = bytesToDouble(data,offset);
					map.put("balance",balance);
					offset += 4;
				}
				if (keys.get(i).equals("acc_no")){
					offset += 6; // acc_no  == 6, int()
					int acc_no = bytesToInt(data,offset);
					map.put("acc_no",acc_no);
					offset += 4 ;
				}
				if (keys.get(i).equals("withdraw_choice")){
					offset += 15; // acc_no  == 6, int()
					int withdraw_choice = bytesToInt(data,offset);
					map.put("withdraw_choice",withdraw_choice);
					offset += 4 ;
				}
				if (keys.get(i).equals("amount")){
					offset += 6; // balance == 8 , (double)
					Double amount = bytesToDouble(data,offset);
					map.put("amount",amount);
					offset += 4;
				}
				if (keys.get(i).equals("interval")){
					offset += 15; // acc_no  == 6, int()
					int interval = bytesToInt(data,offset);
					map.put("interval",interval);
					offset += 4 ;
				}
				
			}
			return map;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public String bytesToString(byte[] data, int offset, int length) {
		try{
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<length;i++,offset++){
				sb.append((char)data[offset]);
			}
			return sb.toString();
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	private Double bytesToDouble(byte[] data, int offset) {
		int doubleSize = 8;
		byte[] temp = new byte[doubleSize];
		for(int i =0;i<doubleSize;i++){
			temp[i] = data[offset+i];
		}
		double value = ByteBuffer.wrap(temp).getDouble();
		return value;
	}
	
	
	private Integer bytesToInt(byte[] data, int offset) {
		int intSize = 4;
		byte[] temp = new byte[intSize];
		for(int i=0;i<intSize;i++){
			temp[i] = data[offset+i];
		}
		int value = ByteBuffer.wrap(temp).getInt();
		return value;
	}
	
	public static class DataPackage{
		
		
		private DataUnpacker unpacker;
		
		public DataPackage() {
			unpacker = new DataUnpacker();
		}
		
		public DataPackage setType(String key, TYPE type) {
			unpacker.keys.add(key);
			return this;
		}
		
		public HashMap <String, Object> execute(byte[] data) {
			return unpacker.execute(data);
			
		}
		
	}

}
