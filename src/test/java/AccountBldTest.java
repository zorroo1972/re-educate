import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.function.Function;



class AccountBldTest {
    @Mock
    Client client;
    //Field arrAmmount of type Ammount[] - was not mocked since Mockito doesn't mock arrays
    @Mock
    List<Function<Integer, Integer>> action;
    @InjectMocks
    Account.AccountBld accountBld;


    @Test
    void testSetClient() {
        Account.AccountBld result = accountBld.setClient(null);
        Assertions.assertEquals(new Account.AccountBld(), result);
    }

    @Test
    void testSetAmmount() {
        Account.AccountBld result = accountBld.setAmmount(new Ammount[]{null});
        Assertions.assertEquals(new Account.AccountBld(), result);
    }

    @Test
    void testBuild() {
        Account result = accountBld.build();
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme