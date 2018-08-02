import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class libforclient {

	Base64.Decoder decoder = Base64.getDecoder();
	Base64.Encoder encoder = Base64.getEncoder();

	protected byte[] signature(RSAPrivateKey privateKey ,byte[] data) throws Exception {
		  Signature signature = Signature.getInstance("MD5withRSA");
		  signature.initSign(privateKey);
		  signature.update(data);
		  return signature.sign();
	}
	protected byte[] encrypt(RSAPublicKey publicKey, RSAPrivateKey privateKey, byte[] obj) {
		if (publicKey != null) {
			try {

				Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] c1 = cipher.doFinal(obj);
				Cipher cipher2 = Cipher.getInstance("RSA/ECB/NoPadding");
				cipher2.init(Cipher.ENCRYPT_MODE, privateKey);
				return cipher2.doFinal(c1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	protected byte[] gentrapdoor(String keyword,RSAPrivateKey privateKey,RSAPublicKey publicKey) {
		
		try {
			byte[] ciphertext=encoder.encode(encrypt(publicKey, privateKey, keyword.getBytes()));
			byte[] trapdoor=signature(privateKey, ciphertext);

			return trapdoor;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
