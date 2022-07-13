package bowling;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @After
    public void tearDown() throws Exception {
        game.printScoreSheet();
    }

    @Test public void testCompleteGutterGame() {
        for (int frameNumber = 1; frameNumber <= 10; frameNumber++) {
            assertFalse(game.roll(0));
            assertEquals(frameNumber == 10, game.roll(0));
        }

        assertEquals(0, game.score());
    }

    @Test
    public void testSimpleGameNoStrikesOrSpares() {
        game.roll(4);
        game.roll(3);

        game.roll(7);
        game.roll(1);

        assertEquals(15, game.score());
    }

    @Test
    public void testSpareBonusDoublesNextRoll() {
        game.roll(8);
        game.roll(2);

        game.roll(7);
        game.roll(1);

        assertEquals(25, game.score());
    }

    @Test
    public void testTwoSparesInARow() {
        game.roll(8);
        game.roll(2);

        game.roll(7);
        game.roll(3);

        game.roll(7);
        game.roll(1);

        assertEquals(42, game.score());
    }

    @Test
    public void testStrikeBonusDoublesNextTwoRolls() {
        game.roll(10);

        game.roll(7); // counts twice
        game.roll(2); // counts twice

        game.roll(7);
        game.roll(1);

        assertEquals(36, game.score());
    }

    @Test
    public void testStrikeFollowedBySpare() {
        game.roll(10);

        game.roll(7); // counts twice
        game.roll(3); // counts twice

        game.roll(7); // counts twice
        game.roll(1);

        assertEquals(45, game.score());
    }

    @Test
    public void testTwoStrikesInARow() {
        game.roll(10);

        game.roll(10); // counts twice

        game.roll(3); // counts 3x
        game.roll(2);

        assertEquals(43, game.score());
    }

    @Test
    public void testThreeStrikesInARow() {
        game.roll(10); // 30

        game.roll(10); // counts in frame 1 and frame 2, frame score 23, running total 53

        game.roll(10); // counts in frames 1-3. frame score 17, running total 70

        game.roll(3); // counts twice
        game.roll(4); // counts twice. frame score 7, running total 77

        game.roll(5);
        game.roll(2); // frame score 7, running total 84
        assertEquals(84, game.score());
    }

    @Test
    public void testPerfectGame() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);

        assertEquals(300, game.score());
    }

    @Test
    public void testAllSpares() {
        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5); // 15

        game.roll(5);
        game.roll(5);
        game.roll(5); // 15

        assertEquals(150, game.score());
    }

    @Test(expected = IllegalStateException.class)
    public void testStrikeInFrameTenEndsGameAfterTwoBonusRolls() {
        game.roll(10); // frame 1
        game.roll(10); // frame 2
        game.roll(10); // frame 3
        game.roll(10); // frame 4
        game.roll(10); // frame 5
        game.roll(10); // frame 6
        game.roll(10); // frame 7
        game.roll(10); // frame 8
        game.roll(10); // frame 9
        game.roll(10); // frame 10
        game.roll(10); // bonus roll 1
        game.roll(10); // bonus roll 2
        game.roll(10); // should not be allowed!
    }


}
