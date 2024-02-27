import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccCover implements Stateable {
    Stateable acc;

    List<Function<Integer,Integer>> action;

    public AccCover(Stateable acc) {
        this.acc = acc;
    }


    public List<Function<Integer, Integer>> getAction() {
        this.action = action.stream().toList();
        return this.action;
    }

    @Override
    public List<Function<Integer, Integer>> getAction(int i) {
        return null;
    }

    @Override
    public String toString() {
        return "AccCover{" +
                "acc=" + acc +
                '}';
    }


}
