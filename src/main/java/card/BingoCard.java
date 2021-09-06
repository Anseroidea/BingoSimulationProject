package card;

import java.util.stream.IntStream;

public class BingoCard {

    private int id;
    private int[][] card; //-1 means free space

    private boolean[][] markedCard;
    public static final int COLUMN_B = 0;
    public static final int COLUMN_I = 1;
    public static final int COLUMN_N = 2;
    public static final int COLUMN_G = 3;
    public static final int COLUMN_O = 4;

    public BingoCard(int[] b, int[] i, int[] n, int[] g, int[] o, int id){
        this.id = id;
        markedCard = new boolean[5][5];
        markedCard[2][2] = true;
        if (n.length != 4){
            System.out.println("N Column length must be 4!");
        } else {
            card = new int[5][5];
            card[COLUMN_B] = b;
            card[COLUMN_I] = i;
            for (int in = 0; in <= n.length; in++){
                if (in < 2){
                    card[COLUMN_N][in] = n[in];
                } else if (in > 2){
                    card[COLUMN_N][in] = n[in - 1];
                } else {
                    card[COLUMN_N][2] = -1;
                }
            }
            card[COLUMN_G] = g;
            card[COLUMN_O] = o;
        }
    }

    public int getId(){
        return id;
    }

    public int[][] getCard(){
        return card;
    }

    public void markCard(int i){
        if (i < 0 || i > 75)
            return;
        int row = getRowFromNum((i - 1)/15, i);
        int col = (i - 1)/15;
        if (row >= 0)
            markedCard[row][col] = true;
    }

    private int getRowFromNum(int col, int n){
        for (int i = 0; i < card[col].length; i++){
            if (n == card[col][i]){
                return i;
            }
        }
        return -1;
    }



    public void markCard(int r, int c){
        markedCard[r][c] = true;
    }

    public boolean equals(Object o){
        if (!(o instanceof BingoCard)){
            return false;
        } else {
            BingoCard bc = (BingoCard) o;
            return bc.toString().equals(toString());
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("B\tI\tN\tG\tO\t");
        for(int r = 0; r < card.length; r++){
            for (int c = 0; c < card.length; c++){
                if (card[r][c] == -1){
                    sb.append("fs\t");
                } else {
                    sb.append(card[r][c]).append("\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public boolean[][] getMarkedCard() {
        return markedCard;
    }

    public boolean isWinner() {
        for (int r = 0; r < card.length; r++){
            final boolean[] row = markedCard[r];
            if (IntStream.range(0, card[r].length).mapToObj(i -> row[i]).allMatch(b -> b)){
                return true;
            }
        }
        for (int c = 0; c < markedCard.length; c++){
            final int c1 = c;
            if (IntStream.range(0, card.length).mapToObj(i -> markedCard[i][c1]).allMatch(b -> b)){
                return true;
            }
        }
        return IntStream.range(0, card.length).mapToObj(i -> markedCard[i][i]).allMatch(b -> b) || IntStream.range(0, card.length).mapToObj(i -> markedCard[markedCard.length - 1 - i][i]).allMatch(b -> b);
    }
}
