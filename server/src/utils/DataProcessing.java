package utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DataProcessing {
	
	public static byte[] intToBytes(int data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
		byte[] intBytes = byteBuffer.putInt(data).array();
		return intBytes;
	}
	
	public static int bytesToInt(byte[] data) {
		int byteInt = ByteBuffer.wrap(data).getInt();
		return byteInt;
		
	}
	
	public static byte[] stringToBytes(String data) {
		return data.getBytes(StandardCharsets.UTF_8);
	}
	
	public static String bytesToString(byte[] data) {
		return new String(data, StandardCharsets.UTF_8);
	}
	
	public static byte[] doubleToBytes(Double data) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
		byte[] doubleBytes = byteBuffer.putDouble(data).array();
		return doubleBytes;
	}
	
	public static Double bytesToDouble(byte[] data) {
		Double byteDouble = ByteBuffer.wrap(data).getDouble();
		return byteDouble;
	}
}
