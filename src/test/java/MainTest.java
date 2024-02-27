import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMain() throws CloneNotSupportedException {
        Main.main(new String[]{"args"});
    }

    @Test
    void testCheckState() {
        Main.checkState(new AccCover(null));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme