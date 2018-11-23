package toys.tests.junit;

import org.junit.Assert;
import org.junit.Test;
import toys.utils.NumeroExtenso;

import java.math.BigDecimal;

public class NumeroExtensoTest {

    @Test
    public void testNumeroExtenso() {
        NumeroExtenso ne = new NumeroExtenso();
        Assert.assertEquals("dez reais", ne.toString(new BigDecimal(10)));
        Assert.assertEquals("oitenta reais", ne.toString(new BigDecimal(80)));
        Assert.assertEquals("um mil, duzentos e trinta e cinco reais", ne.toString(new BigDecimal(1235)));
        Assert.assertEquals("sessenta e cinco mil, quatrocentos e sessenta e cinco reais e sessenta e cinco centavos", ne.toString(new BigDecimal(65465.65)));
    }

}
