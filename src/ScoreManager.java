import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {
    private static final String FILE_NAME = "scores.dat";
    private List<Score> scores;

    public ScoreManager() {
        scores = loadScores();
    }

    public void addScore(Score score) {
        scores.add(score);
        saveScores();
    }

    public List<Score> getScores() {
        // Return sorted by score (descending)
        Collections.sort(scores, Comparator.comparingInt(Score::getScoreValue).reversed());
        return scores;
    }

    public List<Score> getScoresByMode(String mode) {
        List<Score> filtered = new ArrayList<>();
        for (Score s : scores) {
            if (s.getGameMode().equalsIgnoreCase(mode)) {
                filtered.add(s);
            }
        }
        Collections.sort(filtered, Comparator.comparingInt(Score::getScoreValue).reversed());
        return filtered;
    }

    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Score> loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}