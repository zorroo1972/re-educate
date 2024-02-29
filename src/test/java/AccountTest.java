

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;




public class AccountTest {
  Account account = new Account("name");
    @Test
    public void testSetName() {
       account.setName("name");
        Assertions.assertEquals(account.getName(),"name");
    }

    @Test
    public void testAddCurr() {
        account.addCurr(Account.Currency.RUB,100);
        Assertions.assertEquals(account.getValues(),"{RUB=100}");
        Assertions.assertThrows(Exception.class, () -> account.addCurr(Account.Currency.RUB,100));

    }

    @Test
    public void testSave() {
        Save result = account.save();
        Assertions.assertEquals(result, account);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme