import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Az eredmények (High Scores) kezeléséért felelős osztály.
 * <p>
 * Ez az osztály végzi az eredmények ({@link Score} objektumok) fájlba mentését
 * és betöltését. Emellett funkciókat biztosít az eredmények rendezésére
 * és szűrésére (pl. játékmód szerint).
 * Az adatokat binárisan szerializálva tárolja a 'scores.dat' fájlban.
 * </p>
 */
public class ScoreManager {
    /**
     * A szerializált adatokat tartalmazó fájl neve.
     */
    private static final String FILE_NAME = "scores.dat";

    /**
     * A memóriában tárolt eredmények listája.
     */
    private List<Score> scores;

    /**
     * Létrehoz egy új ScoreManager példányt.
     * A konstruktor megpróbálja betölteni a korábban elmentett eredményeket
     * a fájlból. Ha a fájl nem létezik, egy üres listával indul.
     */
    public ScoreManager() {
        scores = loadScores();
    }

    /**
     * Hozzáad egy új eredményt a listához, és azonnal elmenti az állapota
     * a háttértárra.
     *
     * @param score A menteni kívánt {@link Score} objektum.
     */
    public void addScore(Score score) {
        scores.add(score);
        saveScores();
    }

    /**
     * Visszaadja az összes tárolt eredményt pontszám szerint csökkenő sorrendben.
     * <p>
     * A metódus módosítja a belső lista rendezettségét.
     * </p>
     *
     * @return A rendezett eredmények listája.
     */
    public List<Score> getScores() {
        // Return sorted by score (descending)
        Collections.sort(scores, Comparator.comparingInt(Score::getScoreValue).reversed());
        return scores;
    }

    /**
     * Visszaadja egy adott játékmódhoz tartozó eredményeket,
     * pontszám szerint csökkenő sorrendben.
     *
     * @param mode A keresett játékmód neve (pl. "Kezdő"). A szűrés nem érzékeny a kis/nagybetűkre.
     * @return A szűrt és rendezett eredmények listája.
     */
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

    /**
     * Segédmetódus a teljes eredménylista fájlba mentéséhez.
     * ObjectOutputStream-et használ a szerializációhoz.
     * Hiba esetén a veremkivonatot (stack trace) írja ki a konzolra.
     */
    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Segédmetódus az eredmények betöltéséhez a fájlból.
     * <p>
     * Ha a fájl nem létezik, vagy hiba történik olvasás közben (pl. sérült fájl),
     * egy üres listával tér vissza, hogy a program ne omoljon össze.
     * </p>
     *
     * @return A fájlból beolvasott lista, vagy üres lista hiba esetén.
     */
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