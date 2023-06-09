package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.StringToys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringToysTestCase {

    @Test
    void testSpacesRight() {
        assertEquals("teste1    ", StringToys.spacesRight("teste1", 10));
        assertEquals("teste", StringToys.spacesRight("teste2", 5));
        assertEquals("00230", StringToys.zerosLeft(2.3, 5, 2));
        assertEquals("00002", StringToys.zerosLeft(2, 5));
    }

    @Test
    void formatMasks() {
        assertEquals("111.111.111-11", StringToys.formatCPF("11111111111"));
        assertEquals("11.111.111/1111-11", StringToys.formatCNPJ("11111111111111"));
        assertEquals("03399.84742 99000.000051 00532.901014 9 80040000113843", StringToys.formatIPTE("03399847429900000005100532901014980040000113843"));
    }

    @Test
    void normalizarTelefone() {
        assertEquals("9999999999", StringToys.normalizarTelefone("(099) 9999-9999"));
        assertEquals("9999999999", StringToys.normalizarTelefone("(99) 9999-9999"));
        assertEquals("9999999999", StringToys.normalizarTelefone("099 99999999"));
        assertNull(StringToys.normalizarTelefone(null));
    }

}
