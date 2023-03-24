package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptTest {

  @Test
  void testEncryptDecrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

    // Gera uma chave
    var kg = KeyGenerator.getInstance("DES");
    kg.init(56);
    var key = kg.generateKey();

    // Realiza os testes de codificação.
    var s = "Iran Marcius";
    var encoded = Base64.getUrlEncoder().encodeToString(Crypt.encrypt(s, key));
    System.out.println(encoded);
    var decoded = Crypt.decrypt(Base64.getUrlDecoder().decode(encoded), key);
    assertEquals(s, decoded);
  }

}
