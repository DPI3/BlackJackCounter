import java.util.Random;

/**
 * A Jatek osztály egy kiterjesztett változata ("Advanced" mód).
 * Ez az osztály extra funkcionalitást ad az alapjátékhoz: véletlenszerű időközönként
 * ellenőrzi a játékos kártyaszámolási képességét. Ha a játékos rossz értéket ad meg,
 * a játék azonnal véget ér
 */
public class AdvancedGame extends Jatek {
    /**
     * A következő ellenőrzésig hátralévő körök (laphúzások) száma.
     * Minden körben csökken, és ha eléri a 0-t, a rendszer kérdez.
     */
    private int turnsUntilCheck;

    /**
     * A játékos státusza. Igaz, amg a játékos nem rontja el a számolást.
     * Ha hamis, a játéknak vége.
     */
    private boolean isAlive;

    /**
     * Veletlenszam.
     */
    private Random random;

    /**
     * Létrehoz egy új haladó játékot.
     * Inicializálja az ősosztályt, beállítja az alapértelmezett "élő" státuszt,
     * és elindítja az első visszaszámlálást az ellenőrzésig.
     *
     * @param decks A játékhoz használt paklik száma.
     */
    public AdvancedGame(int decks) {
        super(decks);
        this.isAlive = true;
        this.random = new Random();
        resetCheckTimer();
    }

    /**
     * A következő kör lebonyolítása.
     * Felüldefiniálja az ősosztály metódusát: elvégzi a laphúzást és számolást,
     * majd csökkenti az ellenőrzésig hátralévő számlálót.
     */
    @Override
    public void nextTurn() {
        super.nextTurn();
        turnsUntilCheck--;
    }

    /**
     * Eldönti, hogy eljött-e az ideje a játékos ellenőrzésének.
     *
     * @return Igaz, ha a visszaszámláló <= 0, egyébként hamis.
     */
    public boolean shouldAskUser() {
        return turnsUntilCheck <= 0;
    }

    /**
     * Visszaállítja a visszaszámlálót egy véletlenszerű értékre.
     * A jelenlegi implementáció szerint ez 4 vagy 5 kártyahúzást jelent.
     * (4 + random(0 vagy 1)).
     */
    public void resetCheckTimer() {
        // Requirement: 4-5 cards interval
        turnsUntilCheck = 4 + random.nextInt(2);
    }

    /**
     * Ellenőrzi a játékos tippjét a tényleges számolási értékkel szemben.
     * Ha a tipp helytelen, a játékos elveszíti a játékot (isAlive = false).
     *
     * @param playerGuess A játékos által megadott számolási érték.
     * @return Igaz, ha a tipp megegyezik a rendszer által számolt értékkel, egyébként hamis.
     */
    public boolean checkAnswer(int playerGuess) {
        boolean correct = (playerGuess == getCount());
        if (!correct) {
            isAlive = false; // vege a jateknak
        }
        return correct;
    }

    /**
     * Lekérdezi, hogy a játékos még játékban van-e.
     *
     * @return Igaz, ha a játékos még nem rontott el egy ellenőrzést sem.
     */
    public boolean isAlive() {
        return isAlive;
    }
}