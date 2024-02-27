import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

abstract class Account implements Stateable{
    private Client client;
    private Ammount [] arrAmmount;
    List<Function<Integer,Integer>> action;

    private Account(Client client) {
        this.client = client;
    }



    private Account(List<Function<Integer, Integer>> action) {
        this.action = action;
    }

    private Account(Client client, Ammount[] arrAmmount) {
        this.client = client;
        this.arrAmmount = arrAmmount;
    }

    @Override
    public String toString() {
        return "Account{" + client + Arrays.toString(arrAmmount) +
                '}';
    }

    public Client getClient() {
        return client;
    }

    public Ammount[] getArrAmmount() {
        return arrAmmount;
    }

    static class AccountBld {
        Client client;
        Ammount [] arrAmmount;
        List<Function<Integer,Integer>> action;
        public AccountBld setClient(Client client) {
            if(client != null) {
                this.client = client;
                return this;
            }
            else {
                throw new RuntimeException("Fill client");
            }
        }

        public AccountBld setAmmount(Ammount [] arrAmmount) {
            this.arrAmmount = arrAmmount;
            return this;
        }

        public Account build() {
            return new Account(client, arrAmmount) {


                @Override
                public List<Function<Integer, Integer>> getAction() {
                    return null;
                }

                @Override
                public List<Function<Integer, Integer>> getAction(int i) {
                    return null;
                }
            };
        }

    }


}

