import org.junit.jupiter.api.*;

public class CacheHandlerTests {
    @Test
    @DisplayName("methods without annotation working without sideffects")
    public void testNoAnnotationMethods(){
        TestFraction fraction= new TestFraction(1,4);
        Fractionable fraction2= Utils.cache(fraction);
        fraction2.toString();
        fraction2.toString();
        fraction2.toString();
        Assertions.assertEquals(fraction.count,3);
    }
    @Test
    @DisplayName("methods without annotation dont clear cache")
    public void testNoAnnotationMethodCachNoClear(){
        TestFraction fraction= new TestFraction(1,4);
        Fractionable fraction2= Utils.cache(fraction);
        fraction2.doubleValue();
        fraction2.doubleValue();
        fraction2.toString();
        fraction2.doubleValue();
        Assertions.assertEquals(fraction.count,2);
    }
}
