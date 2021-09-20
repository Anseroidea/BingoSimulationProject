package print;

public class BingoPrintHandler {

    private static BingoPrint bp;

    public static void setNumCards(int numCards){
        bp.setCardInfo(numCards);
    }

    public static void setBp(BingoPrint bp) {
        BingoPrintHandler.bp = bp;
    }
}
