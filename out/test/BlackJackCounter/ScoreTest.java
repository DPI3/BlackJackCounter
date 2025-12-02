import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void getGameMode() {
        // Arrange
        String expectedMode = "Tanuló";
        Score score = new Score(expectedMode, 10);

        // Act
        String actualMode = score.getGameMode();

        // Assert
        assertEquals(expectedMode, actualMode, "A game mode a konstruktornak megfelelo kell hogy legyen");
    }

    @Test
    void getScoreValue() {
        // Arrange
        int expectedScore = 150;
        Score score = new Score("Kezdő", expectedScore);

        // Act
        int actualScore = score.getScoreValue();

        // Assert
        assertEquals(expectedScore, actualScore, "A score a konstruktornak megfelelo kell hogy legyen");
    }

    @Test
    void testToString() {
        // Arrange
        String mode = "TestMode";
        int value = 999;
        Score score = new Score(mode, value);

        // Act
        String result = score.toString();

        // Assert
        String expectedDate = score.getFormattedDate();
        String expectedString = mode + ": " + value + " (" + expectedDate + ")";

        assertEquals(expectedString, result, "toString a helyes eredmenyt kellene visszaadja");
    }
}