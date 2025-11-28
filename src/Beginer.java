import java.util.Random;

/**
 * A Jatek osztály "Kezdő" (Beginner) szintű megvalósítása.
 * Ebben a módban a játékosnak több élete van (alapértelmezetten 3).
 * A rendszer véletlenszerű időközönként (4 vagy 5 laponként) kéri be a számolás értékét,
 * és hibás válasz esetén csökkenti a játékos életeinek számát.
 */
public class Beginer extends Jatek {
    /**
     * A játékos életeinek száma.
     * Ha eléri a 0-t, a játéknak vége. Kezdőérték: 3.
     */
    private int lives;

    /**
     * A legutóbb feltett kérdés óta húzott kártyák száma.
     * Minden körben (nextTurn) növekszik.
     */
    private int cardsSinceLastCheck;

    /**
     * A következő ellenőrzésig szükséges kártyák száma (határérték).
     * Véletlenszerűen változik 4 és 5 között.
     */
    private int checkInterval;

    /**
     * Véletlenszám-generátor az intervallumok meghatározásához.
     */
    private Random random;

    /**
     * Létrehoz egy új kezdő szintű játékot.
     * Inicializálja az őst, beállítja a 3 életet, és meghatározza az első
     * ellenőrzési pontot.
     *
     * @param decks A játékhoz használt paklik száma.
     */
    public Beginer(int decks) {
        super(decks);
        this.lives = 3;
        this.cardsSinceLastCheck = 0;
        this.random = new Random();
        setNextCheckInterval();
    }

    /**
     * Belső segédmetódus a következő ellenőrzés idejének beállítására.
     * Véletlenszerűen választ 4 vagy 5 kártyányi távolságot,
     * és nullázza az eddigi számlálót.
     */
    private void setNextCheckInterval() {
        // Randomly choose 4 or 5 cards
        this.checkInterval = 4 + random.nextInt(2);
        this.cardsSinceLastCheck = 0;
    }

    /**
     * A következő kör lebonyolítása.
     * Meghívja az ősosztály logikáját (laphúzás, számolás), majd
     * növeli a belső számlálót, ami az ellenőrzések gyakoriságát figyeli.
     */
    @Override
    public void nextTurn() {
        super.nextTurn();
        cardsSinceLastCheck++;
    }

    /**
     * Eldönti, hogy a rendszernek fel kell-e tennie kérdést a játékosnak.
     *
     * @return Igaz, ha az utolsó kérdés óta húzott lapok száma elérte
     * a véletlenszerűen generált intervallumot.
     */
    public boolean shouldAskUser() {
        return cardsSinceLastCheck >= checkInterval;
    }

    /**
     * Újraindítja a számlálót a következő kérdésig.
     * Akkor hívandó, miután a játékos válaszolt egy kérdésre.
     */
    public void resetCheck() {
        setNextCheckInterval();
    }

    /**
     * Ellenőrzi a játékos tippjét és kezeli az életeket.
     * Ha a tipp helytelen, a játékos veszít egy életet.
     *
     * @param playerGuess A játékos által megadott számolási érték.
     * @return Igaz, ha a tipp megegyezik a rendszer értékével (getCount()),
     * egyébként hamis.
     */
    public boolean checkAnswer(int playerGuess) {
        boolean correct = (playerGuess == getCount());
        if (!correct) {
            lives--;
        }
        return correct;
    }

    /**
     * Lekérdezi a játékos megmaradt életeinek számát.
     *
     * @return A hátralévő életek száma.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Ellenőrzi, hogy a játékos még játékban van-e.
     *
     * @return Igaz, ha az életek száma szigorúan nagyobb, mint 0.
     */
    public boolean isAlive() {
        return lives > 0;
    }
}