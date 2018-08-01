

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Initial {
	public static void main(String[] args){
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("test\\privatekey"));
			oos.writeObject(privateKey);
			oos.close();
			ObjectOutputStream oos2=new ObjectOutputStream(new FileOutputStream("test\\publickey"));
			oos2.writeObject(publicKey);
			oos2.close();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException eio){
			eio.printStackTrace();
		}
	}
}
