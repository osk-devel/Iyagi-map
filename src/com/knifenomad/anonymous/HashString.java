package com.knifenomad.anonymous;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashString {
	
	public static String digest(String src)
	{
		MessageDigest md5 = null;
		String encoded = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(src.getBytes(), 0, src.length());
			encoded = new BigInteger(1, md5.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encoded;
	}
}
