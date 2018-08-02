import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class libforserver {

	Base64.Decoder decoder = Base64.getDecoder();
	Base64.Encoder encoder = Base64.getEncoder();

	protected boolean verify(RSAPublicKey publicKey,byte[] data,byte[] sign) throws Exception {
	    Signature signature = Signature.getInstance("MD5withRSA");
	    signature.initVerify(publicKey);
	    signature.update(data);	
	    return signature.verify(sign);
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
	protected byte[] gencipher(String keyword,RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		
		try {
			byte[] ciphertext=encoder.encode(encrypt(publicKey,privateKey,keyword.getBytes()));

			return ciphertext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
