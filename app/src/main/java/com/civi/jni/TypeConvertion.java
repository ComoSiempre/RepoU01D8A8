package com.civi.jni;

public class TypeConvertion {
	private static final String TAG = "SerialPort";
	public static byte GetByte(byte src){
		byte bRet = 0x10;
		if((src >= 0x30) && (src <= 0x39)){
			bRet = (byte)(src - 0x30);
		}
		else if((src >= 0x41) && (src <= 0x46)){
			bRet = (byte)(src - 0x41 + 0x0A);
		}
		
		return bRet;
	}
	
	public static int String2Hex(String strData, byte[] desData){
		int length = 0;
		
		strData = strData.toUpperCase();
		byte[] strByte = strData.getBytes();
		for(int i=0; i<strData.length(); i+=2){
			desData[i/2] = (byte)(GetByte(strByte[i])*0x10 + GetByte(strByte[i+1]));
			length++;
		}		
		
		return length;
	}
	
	public static String Bytes2HexString(byte[] b, int length) {
	    String ret = "";
	    for (int i = 0; i < length; i++) {
	      String hex = Integer.toHexString(b[i] & 0xFF);
	      if (hex.length() == 1) {
	        hex = "0" + hex;
	      }
	      ret += hex.toUpperCase();
	      ret += " ";
	    }
	    return ret;
	}
}
