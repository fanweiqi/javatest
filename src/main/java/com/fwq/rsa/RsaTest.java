package com.fwq.rsa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.apache.commons.net.util.Base64;



/**
 * @date 2016年2月2日 下午10:48:23
 * @author FWQ
 * 
 * 通常使用base64来转码是因为，
 * 我们知道在计算机中任何数据都是按ascii码存储的，而ascii码的128～255之间的值是不可见字符。
 * 而在网络上交换数据时，比如说从A地传到B地，往往要经过多个路由设备，
 * 由于不同的设备对字符的处理方式有一些不同，这样那些不可见字符就有可能被处理错误，
 * 这是不利于传输的。所以就先把数据先做一个Base64编码，统统变成可见字符，这样出错的可能性就大降低了。
 */
public class RsaTest {
	private static String publicKeyFile="d:/desktop/publicKey";
	private static String privatekeyFile="d:/desktop/privatekey";

	public static void main(String[] args) throws Exception {
		
		String source="hello";
		String sign = sign(source);
		System.out.println(sign);
		
		boolean verifySign = verifySign(source, sign);
		
		System.out.println(verifySign);
		
	}
	
	public static String  sign(String source){
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(publicKeyFile));
			PublicKey publicKey = (PublicKey) ois.readObject();
			ois.close();
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] doFinal = cipher.doFinal(source.getBytes());
			byte[] encodeBase64 = Base64.encodeBase64(doFinal);
			return new String(encodeBase64);
		}catch (Exception e) {
		}
		
		return null;
	}
	
	
	public static boolean verifySign(String source,String sign){
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(privatekeyFile));
			PrivateKey privatekey = (PrivateKey) ois.readObject();
			ois.close();
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privatekey);
			byte[] doFinal = cipher.doFinal(Base64.decodeBase64(sign.getBytes()));
			String string = new String(doFinal);
			if(source.equals(string)){
				return true;
			}
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	public static void generateKeyPair(){
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			
			FileOutputStream publicFos = new FileOutputStream(publicKeyFile);
			
			FileOutputStream privateFos = new FileOutputStream(privatekeyFile);
			
			ObjectOutputStream oos = new ObjectOutputStream(publicFos);
			oos.writeObject(publicKey);
			oos.flush();
			oos.close();
			
			ObjectOutputStream oos1 = new ObjectOutputStream(privateFos);
			oos1.writeObject(privateKey);
			oos1.flush();
			oos1.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
