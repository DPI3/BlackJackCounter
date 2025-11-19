import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score implements Serializable {
    private String gameMode; // "Tanuló", "Kezdő", "Haladó"
    private int scoreValue;
    private LocalDateTime date;

    public Score(String gameMode, int scoreValue) {
        this.gameMode = gameMode;
        this.scoreValue = scoreValue;
        this.date = LocalDateTime.now();
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    @Override
    public String toString() {
        return gameMode + ": " + scoreValue + " (" + getFormattedDate() + ")";
    }
}