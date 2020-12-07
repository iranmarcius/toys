package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.Crypt;
import toys.ToysSecretKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptTest {

    @Test
    void testEncodeDecode() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        var expr = "S?i/~.RzT;#wVbP`f3hW";
        var encoded = Crypt.encode(expr, ToysSecretKey.getInstance());
        System.out.println(encoded);
        var decoded = Crypt.decode(encoded, ToysSecretKey.getInstance());
        assertEquals(expr, decoded);
    }
}

