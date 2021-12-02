package pay.here.payheretest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SHA256 {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String encrypt(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.info("encrypt NoSuchAlgorithmException ...");
			logger.info("encrypt NoSuchAlgorithmException ...");
			e.printStackTrace();
			return null;
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		
		return builder.toString();
	}
}