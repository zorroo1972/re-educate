import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmmountBldTest {
    @Mock
    Currency cur;
    @InjectMocks
    Ammount.AmmountBld ammountBld;
    private Object Assertions;
    @Test
    void testSetAmmount() {
        Ammount.AmmountBld result = ammountBld.setAmmount(0);
        assertEquals(Assertions, new Ammount.AmmountBld());
    }

    @Test
    void testSetCurrency() {
        Ammount.AmmountBld result = ammountBld.setCurrency(null);
        Assertions.equals(new Ammount.AmmountBld());
    }

    @Test
    void testBuild() {
        Ammount result = ammountBld.build();
        Assertions.equals(null);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme