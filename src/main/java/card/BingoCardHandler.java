package card;

import java.lang.reflect.Array;
import java.util.*;

public class BingoCardHandler {

    private Random r;
    private ArrayList<BingoCard> bingoCards;
    private ArrayList<BingoCard> remainingCards;
    private ArrayList<BingoCard> wonCards;

    public BingoCardHandler(int seed){
        this(new Random(seed), 1);
    }
    public BingoCardHandler(Random r){
        this(r, 1);
    }

    public BingoCardHandler(Random r, int numCards){
        this.r = r;
        bingoCards = new ArrayList<>();
        remainingCards = new ArrayList<>();
        wonCards = new ArrayList<>();
        for (int i = 0; i < numCards; i++){
            generateNewBingoCard();
        }
    }

    public void setWonCards(ArrayList<BingoCard> wonCards) {
        this.wonCards = wonCards;
    }

    public ArrayList<BingoCard> getWonCards(){
        return wonCards;
    }

    public void markCards(BingoBall b){
        for (int i = 0; i < bingoCards.size(); i++){
            BingoCard bc = bingoCards.get(i);
            bc.markCard(b.getI());
            if (bc.isWinner() && !wonCards.contains(bc)) {
                wonCards.add(bc);
                remainingCards.remove(remainingCards.indexOf(bc));
            }
        }
    }

    public BingoCard generateNewBingoCard(){
        while (true) {
            int[] b = getBingoColumn(BingoCard.COLUMN_B);
            int[] i = getBingoColumn(BingoCard.COLUMN_I);
            int[] n = getBingoColumn(BingoCard.COLUMN_N);
            int[] g = getBingoColumn(BingoCard.COLUMN_G);
            int[] o = getBingoColumn(BingoCard.COLUMN_O);
            BingoCard bc = new BingoCard(b, i, n, g, o, bingoCards.size());
            if (isOriginalNotInList(bc)) {
                bingoCards.add(bc);
                remainingCards.add(bc);
                return bc;
            }
        }
    }

    public int[] getBingoColumn(int column){
        int size = 5 ;
        if (column == BingoCard.COLUMN_N){
            size = 4;
        }
        int lower = column * 15 + 1;
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        while (set.size() < size){
            set.add(r.nextInt(14) + lower);
        }
        return Arrays.stream(set.toArray(new Integer[size])).mapToInt(Integer::intValue).toArray();
    }

    public boolean isOriginalNotInList(BingoCard bc){
        for (BingoCard b : bingoCards) {
            if (b.equals(bc)) {
                return false;
            }
        }
        return true;
    }

    public void resetCards(){
        remainingCards.clear();
        wonCards.clear();
        bingoCards.sort(Comparator.comparingInt(BingoCard::getId));
    }

    public BingoCard getCard(int i) {
        if (i >= bingoCards.size()){
            return null;
        } else {
            return bingoCards.get(i);
        }
    }

    public ArrayList<BingoCard> getRemainingCards() {
        return remainingCards;
    }
}
