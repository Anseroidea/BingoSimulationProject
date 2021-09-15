package card;

import sim.CardWin;
import java.util.*;

public class BingoCardHandler {

    private Random r;
    private ArrayList<BingoCard> bingoCards;
    private ArrayList<BingoCard> remainingCards;
    private ArrayList<BingoCard> wonCards;
    private ArrayList<CardWin> cardWins = new ArrayList<>();
    private boolean isUpdateWonCards;

    public BingoCardHandler(int seed){
        this(new Random(seed), 1, false);
    }
    public BingoCardHandler(Random r){
        this(r, 1, false);
    }

    public BingoCardHandler(Random r, int numCards, boolean isUpdateWonCards){
        this.r = r;
        bingoCards = new ArrayList<>();
        remainingCards = new ArrayList<>();
        wonCards = new ArrayList<>();
        for (int i = 0; i < numCards; i++){
            generateNewBingoCard();
        }
        this.isUpdateWonCards = isUpdateWonCards;
    }

    public void setWonCards(ArrayList<BingoCard> wonCards) {
        this.wonCards = wonCards;
    }

    public ArrayList<BingoCard> getWonCards(){
        return wonCards;
    }

    public void markCards(BingoBall b, int rollNum){
        if (isUpdateWonCards) {
            for (BingoCard bc : bingoCards) {
                bc.markCard(b.getI());
                if (bc.isWinner() && !wonCards.contains(bc)) {
                    cardWins.add(new CardWin(bc.getId(), rollNum));
                    wonCards.add(bc);
                    remainingCards.remove(bc);
                }
            }
        } else {
            for (int i = 0; i < remainingCards.size(); i++) {
                BingoCard bc = remainingCards.get(i);
                bc.markCard(b.getI());
                if (bc.isWinner() && !wonCards.contains(bc)) {
                    cardWins.add(new CardWin(bc.getId(), rollNum));
                    wonCards.add(bc);
                    remainingCards.remove(bc);
                    i--;
                }
            }
        }
    }

    public void generateNewBingoCard(){
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
                return;
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

    public ArrayList<CardWin> getCardWins(){
        return cardWins;
    }

    public ArrayList<BingoCard> getCards() {
        return bingoCards;
    }
}
