import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JatekTest {

    private static class TestGame extends Jatek {
        public TestGame(int numberOfDecks) {
            super(numberOfDecks);
        }
    }

    @Test
    void nextTurn() {
        // Arrange
        Jatek game = new TestGame(1);
        int initialRemaining = game.getRemainingCards();
        int initialScore = game.getScore();

        // Act
        game.nextTurn();

        // Assert
        assertNotNull(game.getCurrentCard(), "A current kartya nem lehet null");

        assertEquals(initialScore + 1, game.getScore(), "A score egyel nagyobb kell legyen");

        assertEquals(initialRemaining - 1, game.getRemainingCards(), "A maradek kartyak szama egyel csokkenie kell");

        Card drawn = game.getCurrentCard();
        int expectedCount = 0;

        if (drawn.getNumber() >= 10 || drawn.getNumber() == 14) {
            expectedCount = -1;
        } else if (drawn.getNumber() <= 6) {
            expectedCount = 1;
        } else {
            expectedCount = 0;
        }

        assertEquals(expectedCount, game.getCount(), "A count a kartya alapjan kell valtozzon: " + drawn.getNumber());
    }

    @Test
    void getCount() {
        // Arrange
        Jatek game = new TestGame(1);

        // Act es Assert
        assertEquals(0, game.getCount(), "A kezdo ertek 0");

        game.nextTurn();
        int countAfterFirst = game.getCount();
        game.nextTurn();

        assertNotEquals(Integer.MAX_VALUE, game.getCount());
    }

    @Test
    void getCurrentCard() {
        // Arrange
        Jatek game = new TestGame(1);

        // Act es Assert
        assertNull(game.getCurrentCard(), "null kell hogy legyen");

        game.nextTurn();
        assertNotNull(game.getCurrentCard(), "ki kell hogy menjen a lap");
    }

    @Test
    void isGameOver() {
        // Arrange
        Jatek game = new TestGame(1);

        // Act
        for (int i = 0; i < 52; i++) {
            assertFalse(game.isGameOver(), "Meg kellene, hogy tartson a jatek");
            game.nextTurn();
        }

        // Assert
        assertTrue(game.isGameOver(), "Ures pakli eseten vege kell legyen");
    }

    @Test
    void getRemainingCards() {
        // Arrange
        Jatek game = new TestGame(2);

        // Assert
        assertEquals(104, game.getRemainingCards(), "104 kartya kell legyen");

        game.nextTurn();
        assertEquals(103, game.getRemainingCards(), "Csokkennie kell");
    }

    @Test
    void getScore() {
        Jatek game = new TestGame(1);

        assertEquals(0, game.getScore(), "0 kell hogy legyen");

        game.nextTurn();
        assertEquals(1, game.getScore(), "1 kell legyen az elso kor utan");

        game.nextTurn();
        assertEquals(2, game.getScore(), "2 kell legyen 2 kor utan");
    }
}