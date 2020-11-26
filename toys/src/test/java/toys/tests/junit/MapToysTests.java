package toys.tests.junit;

import org.junit.jupiter.api.Test;
import toys.MapToys;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapToysTests {

    @Test
    void testAsString() {
        var m = new HashMap<String, Object>();
        m.put("a", "string");
        m.put("b", 10);
        assertEquals("{a=string, b=10}", MapToys.asString(m));
    }

    @Test
    void testAsStringEmpty() {
        assertEquals("{}", MapToys.asString(new HashMap<>()));
    }

    @Test
    void testAsStringNull() {
        assertNull(MapToys.asString(null));
    }

}
