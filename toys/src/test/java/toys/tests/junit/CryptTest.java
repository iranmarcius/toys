package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.Crypt;
import toys.ToysSecretKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptTest {

  @Test
  void testEncryptDecrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
    var s = "Iran Marcius";
    var encoded = Base64.getUrlEncoder().encodeToString(Crypt.encrypt(s, ToysSecretKey.getInstance()));
    System.out.println(encoded);
    var decoded = Crypt.decrypt(Base64.getUrlDecoder().decode(encoded), ToysSecretKey.getInstance());
    assertEquals(s, decoded);
  }

}

