package toys.tests.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArrayToysTest.class,
    CollectionToysTest.class,
    DateToysTestCase.class,
    NumberToysTestCase.class,
    RegExprsTest.class,
    StringToysTestCase.class })
public class AllTests {

}
