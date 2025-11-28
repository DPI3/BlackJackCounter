/**
 * Egy egyedi francia kártyalapot reprezentáló osztály.
 * Tárolja a kártya színét (suit) és névértékét (number).
 */
public class Card {
    /**
     * A kártya numerikus értéke.
     * <p>
     * A lehetséges értékek 2 és 14 között vannak:
     * <ul>
     * <li>2-10: A számkártyák értékei.</li>
     * <li>11: Bubi (Jack)</li>
     * <li>12: Dáma (Queen)</li>
     * <li>13: Király (King)</li>
     * <li>14: Ász (Ace)</li>
     * </ul>
     */
    short number;

    /*
     * The number can be an integer between 2 and 14
     * 11 means jack
     * 12 means queen
     * 13 means king
     * 14 means ace
     */

    /**
     * Segédmetódus, amely a kártya numerikus értékét szöveges reprezentációvá alakítja.
     * A figurás lapokat (11-14) névvel, a többit számmal adja vissza.
     *
     * @param num A kártya numerikus értéke.
     * @return A kártya neve (pl. "Jack", "Ace") vagy a száma stringként.
     */
    String symbol(short num){
        if(num == 11)
            return "Jack";
        if(num == 12)
            return "Queen";
        if(num == 13)
            return "King";
        if(num == 14)
            return "Ace";
        return String.valueOf(num);
    }

    /**
     * A kártya színe (pl. Pikk, Kőr, Káró, Treff).
     * A `suits` enum típus határozza meg.
     */
    suits suit;

    /**
     * Létrehoz egy új kártya objektumot.
     *
     * @param n A kártya száma/értéke (2-14).
     * @param s A kártya színe (suits enum).
     */
    Card(short n, suits s){
        number = n;
        suit = s;
    }

    /**
     * Kiírja a kártya adatait a standard kimenetre (konzolra).
     * A formátum: "Szín Érték" (pl. "HEARTS King").
     */
    public void printcard(){
        System.out.print(suit + " ");
        System.out.print(symbol(number));
    }

    /**
     * Visszaadja a kártya numerikus értékét.
     * Ez a metódus hasznos a kártyaszámolási logika (Hi-Lo) során.
     *
     * @return A kártya értéke (short típusban).
     */
    public int getNumber(){
        return number;
    }
}