import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CurrBldTest {
    Currency.CurrBld currBld = new Currency.CurrBld();

    @Test
    void testSetName() {
        Currency.CurrBld result = currBld.setName("name");
        Assertions.assertEquals(new Currency.CurrBld(), result);
    }

    @Test
    void testSetIsoCode() {
        Currency.CurrBld result = currBld.setIsoCode("isoCode");
        Assertions.assertEquals(new Currency.CurrBld(), result);
    }

    @Test
    void testBuild() {
        Currency result = currBld.build();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testToString() {
        String result = currBld.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme