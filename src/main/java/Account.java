import javax.xml.crypto.Data;
import java.util.*;

class SaveManager{
    Map<Data, List<Save>> map;
    public void loadTo(Date date){
        for(Save s:map.get(date)) s.load();
    }
}

interface Save{
public void load();
}
interface Action{
    void make();
}
class Account {
    private class SaveImpl implements Save {
        private String name = Account.this.name;
        private Map<Account.Currency, Integer> values = new HashMap<>(Account.this.values);

        private SaveImpl(String name, Map<Currency, Integer> values) {
            this.name = name;
            this.values = values;
        }

        public void load(){
            Account.this.name=name;
            Account.this.values= new HashMap<>(values);
        }
    }
    private String name;
    private Map<Currency, Integer> values = new HashMap<>();
    private Deque<Action> deque = new ArrayDeque<>();

    public Account(String name) {
        if(name==null||name.isBlank())
            throw new IllegalArgumentException("Fill name");
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(name==null||name.isBlank())
            throw new IllegalArgumentException("Fill name");
        String tmp = this.name;
        deque.push(()-> Account.this.name=tmp);
        this.name = name;
    }
    public void addCurr(Currency cur, int val ){
        if(val<0)
            throw new IllegalArgumentException("val must be positive");
        try {
            int tmp = values.get(cur);
            deque.push(()-> Account.this.values.put(cur,tmp));

        } catch (NullPointerException e) {
            deque.push(()-> Account.this.values.clear());
        }
        values.put(cur,val);
    }
    public Map<Currency, Integer> getValues() {
        return new HashMap<>(values);
    }

    public void undo(){
        deque.pop().make();

    }
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }

    public Save save(){
        return new SaveImpl(name,values);
    }

    enum Currency {
        RUB,EUR,USD
    }


}

