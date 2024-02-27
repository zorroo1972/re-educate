import java.util.List;
import java.util.function.Function;

public interface Stateable {
    List<Function<Integer, Integer>> getAction();

    List<Function<Integer,Integer>> getAction(int i);
}
