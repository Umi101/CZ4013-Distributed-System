package utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class DataPacker {
	
	private ArrayList<String> keys;
//	private HashMap<String, Object> results;
	
	public DataPacker() {
		keys = new ArrayList<>();
//		results = new HashMap<>();
	}
	
	public void setValue(String key, Object value) {
		keys.add(key);
//		results.put(key, value);
	}
	
	public byte[] getByteArray(Object ...values) {
		ArrayList<Byte> byteList = new ArrayList<Byte>();
		for (Object value: values) {
			if (value instanceof Integer) {
				byte[] bytes = intToBytes((Integer)value);
				for (byte b: bytes) byteList.add(b);
			}
			else if (value instanceof String) {
				byte[] bytes = stringToBytes((String)value);
				for (byte b: bytes) byteList.add(b);
			}
			else if (value instanceof Double) {
				byte[] bytes = doubleToBytes((Double)value);
				for (byte b: bytes) byteList.add(b);
			}
		}
		
		byte[] byteArrays = new byte[byteList.size()];
		for (int i=0; i < byteList.size(); i++) {
			byteArrays[i] = byteList.get(i);
		}
		return byteArrays;
	}

	public static byte[] intToBytes(int data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
		byte[] intBytes = byteBuffer.putInt(data).array();
		return intBytes;
	}
	
	
	public static byte[] stringToBytes(String data) {
		return data.getBytes(StandardCharsets.UTF_8);
	}

	
	public static byte[] doubleToBytes(Double data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
		byte[] doubleBytes = byteBuffer.putDouble(data).array();
		return doubleBytes;
	}
	
	
}
