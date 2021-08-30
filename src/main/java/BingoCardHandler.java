import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class BingoCardHandler {

    private static Random r;
    private static ArrayList<BingoCard> bingoCards;

    public BingoCardHandler(int seed){
        r = new Random(seed);
        bingoCards = new ArrayList<>();
    }

    public BingoCard generateNewBingoCard(){
        while (true) {
            int[] b = getBingoColumn(BingoCard.COLUMN_B);
            int[] i = getBingoColumn(BingoCard.COLUMN_I);
            int[] n = getBingoColumn(BingoCard.COLUMN_N);
            int[] g = getBingoColumn(BingoCard.COLUMN_G);
            int[] o = getBingoColumn(BingoCard.COLUMN_O);
            BingoCard bc = new BingoCard(b, i, n, g, o, bingoCards.size());
            if (isOriginalNotInList(bc)){
                bingoCards.add(bc);
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
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < size){
            set.add(r.nextInt(14) + lower);
        }
        return Arrays.stream(set.toArray(new Integer[size])).mapToInt(Integer::intValue).toArray();


    }

    public static boolean isOriginalNotInList(BingoCard bc){
        for (BingoCard b : bingoCards) {
            if (b.equals(bc)) {
                return false;
            }
        }
        return true;
    }


}