import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        Currency.CurrBld currencyBldPst = new Currency.CurrBld();
        Currency currencyPts = currencyBldPst.setIsoCode("PTS")
                .setName("Spain Peseta")
                .build();
        Currency.CurrBld currencyBldRub = new Currency.CurrBld();
        Currency currencyRub = currencyBldRub.setIsoCode("RUB")
                .setName("Rouble")
                .build();
        Currency.CurrBld currencyBldUsd = new Currency.CurrBld();
        Currency currencyUsd = currencyBldRub.setIsoCode("USD")
                .setName("USA Dollar")
                .build();

        Client.ClientBld clientBld = new Client.ClientBld();
        Client client1 = clientBld.setName("Arturo")
                .setMiddlename("Peres")
                .setSurname("Reverta")
                .build();

        Ammount.AmmountBld ammountBld1 = new Ammount.AmmountBld();
        Ammount ammount1 = ammountBld1.setCurrency(currencyPts)
                .setAmmount(100000)
                .build();
        Ammount.AmmountBld ammountBld2 = new Ammount.AmmountBld();
        Ammount ammount2 = ammountBld2.setCurrency(currencyUsd)
                .setAmmount(200)
                .build();
        Ammount[] arr1 = new Ammount[2];
        arr1[0] = ammount1;
        arr1[1] = ammount2;
        Account.AccountBld accountBld1 = new Account.AccountBld();
        Account account1 = accountBld1.setClient(client1)
                .setAmmount(arr1)
                .build();
        System.out.println(ammount1);
        System.out.println(ammount2);
        System.out.println(Arrays.toString(arr1));
        System.out.println(account1);

        AccCover savedAcc = new AccCover((Stateable) account1);
        checkState(savedAcc);
        System.out.println(savedAcc);

        client1.setSurname("Ivanov");
        System.out.println(account1);

    }
    static void checkState(Stateable acc){

    }
}
