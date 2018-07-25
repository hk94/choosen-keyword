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
	protected byte[] encrypt(RSAPublicKey publicKey, byte[] obj) {
		if (publicKey != null) {
			try {
				Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				return cipher.doFinal(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	protected byte[] gencipher(String keyword,RSAPublicKey publicKey) {
		
		try {
			byte[] ciphertext=encoder.encode(encrypt(publicKey,keyword.getBytes()));

			return ciphertext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
