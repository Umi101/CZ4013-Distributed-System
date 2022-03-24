package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.nio.ByteBuffer;

public class DataUnpacker {
	
	private ArrayList<String> keys;
	private HashMap <String, TYPE> keysToType;
	
	
	public enum TYPE { INTEGER, DOUBLE, STRING, TWO_BYTE_INT}
	public DataUnpacker(){
		keys = new ArrayList<>();
		keysToType = new HashMap<>();
	}
	
	public HashMap <String, Object> execute(byte[] data){
		HashMap<String, Object> map = new HashMap<>();
		try {
			int offset = 2;
			for(int i =0 ;i < keys.size();i++) {
				String key = keys.get(i);
				TYPE value = keysToType.get(keys.get(i));
//				System.out.printf("Key : %s, Value: %s , Key Length : :%d \n", key,value,key.length());
				
				switch (value){
					case TWO_BYTE_INT:
						break;
					case INTEGER:
						offset += key.length();
						int k = bytesToInt(data,offset);
						map.put(key,k);
						offset += 4 ;
						break;
					case DOUBLE:
						offset +=  key.length(); 
						Double d = bytesToDouble(data,offset);
						map.put(key,d);
						offset += 4;
						break;
					case STRING:
						offset += key.length();
						int len = bytesToInt(data,offset);
						offset += 4;
						String s = bytesToString(data,offset,len);
						map.put(key,s);
						offset += len;
						break;
					default:
						System.out.println("Default case");
						break;
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
			unpacker.keysToType.put(key, type);
			return this;
		}
		
		public HashMap <String, Object> execute(byte[] data) {
			return unpacker.execute(data);
			
		}
		
	}

}
