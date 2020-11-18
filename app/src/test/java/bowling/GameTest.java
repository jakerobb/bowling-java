package bowling;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    @Test public void testAppHasAGreeting() {
        Game classUnderTest = new Game();
        classUnderTest.roll(0);
        assertEquals(0, classUnderTest.score());
    }
}
