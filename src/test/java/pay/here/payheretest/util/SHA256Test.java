package pay.here.payheretest.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SHA256Test {
	
	@Autowired
	SHA256 sha256;

	@Test
	void SHA256으로_암호화가된다() throws Exception {
		// given
		String plainText = "payhere";
		
		// when
		String encryptedText = sha256.encrypt(plainText);
		
		// then
		assertNotEquals(plainText, encryptedText);
	}

}
