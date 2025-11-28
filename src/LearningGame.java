/**
 * A Jatek osztály "Tanuló" (Learning) módú megvalósítása.
 * <p>
 * Ez a játékmód a kártyaszámolás alapjainak elsajátítását szolgálja.
 * Különlegessége az egyszerűség: nincsenek megszakítások, nincsenek
 * felugró ellenőrző kérdések (kvíz) és nincs "élet" rendszer vagy azonnali vereség.
 * A játékos szabadon húzhatja a lapokat a pakli végéig, hogy lássa a számolás alakulását.
 * </p>
 */
public class LearningGame extends Jatek {

    /**
     * Létrehoz egy új tanuló módú játékot.
     * <p>
     * Csak az ősosztály konstruktorát hívja meg a paklik inicializálásához,
     * mivel ebben a módban nincs szükség extra állapotváltozókra (pl. életek, időzítők).
     * </p>
     *
     * @param decks A játékhoz használt paklik száma.
     */
    public LearningGame(int decks) {
        super(decks);
    }

    // Learning mode is simple: just draw the next card.
    // No special stops or questions needed.
}