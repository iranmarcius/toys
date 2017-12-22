package toys.tests.junit;

import org.junit.Test;
import toys.ToysMessages;

import static org.junit.jupiter.api.Assertions.*;

public class ToysMessagesTest {

    @Test
    public void getErro() {
        assertEquals(ToysMessages.getErro(), "Erro");
    }

}
