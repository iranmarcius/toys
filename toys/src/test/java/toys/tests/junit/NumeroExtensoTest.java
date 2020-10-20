package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.NumeroExtenso;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumeroExtensoTest {

    @Test
    void testNumeroExtenso() {
        NumeroExtenso ne = new NumeroExtenso();
        assertEquals("dez reais", ne.toString(new BigDecimal(10)));
        assertEquals("oitenta reais", ne.toString(new BigDecimal(80)));
        assertEquals("um mil, duzentos e trinta e cinco reais", ne.toString(new BigDecimal(1235)));
        assertEquals("sessenta e cinco mil, quatrocentos e sessenta e cinco reais e sessenta e cinco centavos", ne.toString(BigDecimal.valueOf(65465.65)));
    }

}
