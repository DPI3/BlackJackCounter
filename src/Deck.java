import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Egy kártyapaklit (vagy több pakli egyesítését) reprezentáló osztály.
 * <p>
 * Ez az osztály felelős a kártyák ({@link Card}) létrehozásáért, tárolásáért,
 * keveréséért és a húzások kezeléséért.
 * Támogatja a szerializációt, így a pakli aktuális állapota elmenthető.
 * </p>
 */
public class Deck implements Serializable {
    /**
     * A pakliban lévő kártyák listája.
     * Ez a lista dinamikusan csökken, ahogy a kártyákat kihúzzák.
     */
    private ArrayList<Card> cards;

    /**
     * Létrehoz egy új pakli objektumot.
     * <p>
     * A konstruktor létrehozza a megadott számú 52 lapos csomagot,
     * hozzáadja őket a közös listához, majd megkeveri az egészet.
     * </p>
     *
     * @param numberOfDecks A játékhoz használt paklik száma (pl. 1, 6, 8).
     */
    public Deck(int numberOfDecks) {
        cards = new ArrayList<>(numberOfDecks * 52);
        for (int i = 0; i < numberOfDecks; i++) {
            cards.addAll(createSingleDeck());
        }
        Collections.shuffle(cards);
    }

    /**
     * Létrehoz egyetlen, 52 lapos standard francia kártyapaklit.
     * Végigiterál az összes színen (suits) és értéken (2-14),
     * hogy generálja a lapokat.
     *
     * @return Egy 52 darab {@link Card} objektumot tartalmazó lista.
     */
    private ArrayList<Card> createSingleDeck() {
        ArrayList<Card> newDeck = new ArrayList<>(52);
        for (suits s : suits.values()) {
            for (short j = 2; j <= 14; j++) {
                newDeck.add(new Card(j, s));
            }
        }
        return newDeck;
    }

    /**
     * Húz egy kártyát a pakli tetejéről.
     * <p>
     * A metódus eltávolítja a legfelső kártyát (0. index) a listából és visszaadja azt.
     * </p>
     *
     * @return A húzott {@link Card} objektum, vagy {@code null}, ha a pakli üres.
     */
    public Card drawCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }

    /**
     * Lekérdezi a pakliban maradt kártyák számát.
     *
     * @return A még ki nem húzott kártyák darabszáma.
     */
    public int getRemainingCards() {
        return cards.size();
    }
}