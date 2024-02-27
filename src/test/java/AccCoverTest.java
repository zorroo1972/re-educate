import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


class AccCoverTest {
    @Mock
    Stateable acc;
    @Mock
    List<Function<Integer, Integer>> action;
    @InjectMocks
    AccCover accCover;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAction2() {
        List<Function<Integer, Integer>> result = accCover.getAction(0);
        Assertions.assertEquals(List.of(null), result);
    }

    @Test
    void testToString() {
        String result = accCover.toString();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    private static class MockitoAnnotations {
        public static void initMocks(AccCoverTest accCoverTest) {
        }
    }
}


//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme