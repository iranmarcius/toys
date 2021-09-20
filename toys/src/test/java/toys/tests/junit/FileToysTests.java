package toys.tests.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import toys.FileToys;

class FileToysTests {

  @Test
  void testTextSizeToLong() {
    Assertions.assertEquals(100, FileToys.textSizeToLong("100"));
    Assertions.assertEquals(100, FileToys.textSizeToLong("100b"));
    Assertions.assertEquals(100, FileToys.textSizeToLong("100B"));
    Assertions.assertEquals(100 * 1024, FileToys.textSizeToLong("100K"));
    Assertions.assertEquals(100 * 1024 * 1024, FileToys.textSizeToLong("100M"));
    Assertions.assertEquals(100L * 1024 * 1024 * 1024, FileToys.textSizeToLong("100G"));
  }

}
