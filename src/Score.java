import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Egy egyedi játékeredményt (pontszámot) reprezentáló osztály.
 * <p>
 * Tárolja a játékmódot, az elért pontszámot, valamint a játék befejezésének
 * pontos dátumát és idejét. Támogatja a szerializációt, így az eredmények
 * listája könnyen fájlba menthető és visszatölthető.
 * </p>
 */
public class Score implements Serializable {
    /**
     * A játékmód neve, amelyben az eredmény született (pl. "Tanuló", "Kezdő", "Haladó").
     */
    private String gameMode; // Tanuló, Kezdő, Haladó

    /**
     * Az elért pontszám értéke (általában a sikeresen kijátszott lapok száma).
     */
    private int scoreValue;

    /**
     * A pontszám rögzítésének (a játék végének) pontos időpontja.
     */
    private LocalDateTime date;

    /**
     * Létrehoz egy új eredménybejegyzést.
     * A dátum mezőt automatikusan a létrehozás pillanatának időpontjára állítja be
     * ({@link LocalDateTime#now()}).
     *
     * @param gameMode A játékmód megnevezése.
     * @param scoreValue Az elért pontszám.
     */
    public Score(String gameMode, int scoreValue) {
        this.gameMode = gameMode;
        this.scoreValue = scoreValue;
        this.date = LocalDateTime.now();
    }

    /**
     * Visszaadja a játékmód nevét.
     *
     * @return A játékmód stringként.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Visszaadja az elért pontszámot.
     *
     * @return A pontszám egész számként.
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Visszaadja a rögzítés dátumát emberi olvasásra alkalmas, formázott szövegként.
     * <p>
     * A használt formátum: "yyyy-MM-dd HH:mm" (pl. 2023-10-27 14:30).
     * Ez a formátum biztosítja a könnyű olvashatóságot az eredménytáblán.
     * </p>
     *
     * @return A formázott dátum string.
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    /**
     * Visszaadja az objektum szöveges reprezentációját.
     * <p>
     * Formátum: "Játékmód: Pontszám (Dátum)"
     * </p>
     *
     * @return Az eredmény összefoglaló stringje, ami debuggolásra vagy
     * egyszerű listázásra használható.
     */
    @Override
    public String toString() {
        return gameMode + ": " + scoreValue + " (" + getFormattedDate() + ")";
    }
}