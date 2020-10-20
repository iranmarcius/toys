package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.ToysMessages;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToysMessagesTest {

    @Test
    void getErro() {
        assertEquals("Erro", ToysMessages.erro());
    }

}
