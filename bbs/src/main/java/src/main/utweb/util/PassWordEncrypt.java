package src.main.utweb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassWordEncrypt {

	public static String convertByteToString(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			int temp = bytes[i] & 0xff;
			String tempHex = Integer.toHexString(temp);
			if (tempHex.length() < 2) {
				result += "0" + tempHex;
			} else {
				result += tempHex;
			}
		}
		return result;
	}

	public static String md5Encode(String str) {
		String temp = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(str.getBytes());
			temp = convertByteToString(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static String getRandomString() {
		String s = "";
		for (int i = 0; i < 9; i++) {
			int str = (int) (Math.random() * 10);
			s += str;
		}
		return s;
	}
}
