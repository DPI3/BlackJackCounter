import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void drawCard() {
        // Arrange
        Deck deck = new Deck(1);
        int initialSize = deck.getRemainingCards();

        // Act
        Card drawnCard = deck.drawCard();

        // Assert
        assertNotNull(drawnCard, "Drawn card nem lehet null ha a pakli nem ures");

        assertEquals(initialSize - 1, deck.getRemainingCards(), "A pakli meret egyel csokkennie kell");

        for (int i = 0; i < initialSize - 1; i++) {
            deck.drawCard();
        }

        assertEquals(0, deck.getRemainingCards());

        // Act
        Card emptyDraw = deck.drawCard();

        // Assert
        assertNull(emptyDraw, "Null kell legyen mert ures a pakli");
    }

    @Test
    void getRemainingCards() {
        Deck deck1 = new Deck(1);
        assertEquals(52, deck1.getRemainingCards(), "1 Deck 52 lapot kell tartalmazzon");

        Deck deck2 = new Deck(2);
        assertEquals(104, deck2.getRemainingCards(), "2 Deck 104 lapot kell tartalmazzon");

        deck1.drawCard();
        deck1.drawCard();
        assertEquals(50, deck1.getRemainingCards(), "50 kell legyen 2 eldobas utan");
    }
}