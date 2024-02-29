

public class Main {
    public static void main(String[] args) {
        Account account = new Account("Ivanov");
        System.out.println(account);
        account.addCurr(Account.Currency.RUB,100);
        System.out.println(account);
        account.undo();
        System.out.println(account);
        account.addCurr(Account.Currency.USD,200);
        account.save();
        SaveManager saveManager = new SaveManager();
        System.out.println(account);
        account.addCurr(Account.Currency.EUR,300);
        System.out.println(account);
    }

}
