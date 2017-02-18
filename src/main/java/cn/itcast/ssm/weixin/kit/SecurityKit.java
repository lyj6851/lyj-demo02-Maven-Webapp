package cn.itcast.ssm.weixin.kit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//微信sha1加密工具类
public class SecurityKit {
	
	public static String sha1(String str){
		try {
			StringBuffer sb = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance("sha1");
			md.update(str.getBytes());
			byte[] msg = md.digest();
			for(byte b:msg){
				sb.append(String.format("%02x",b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
