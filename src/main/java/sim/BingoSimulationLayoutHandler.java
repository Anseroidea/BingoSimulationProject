package sim;

public class BingoSimulationLayoutHandler {

    public static BingoSimulationLayout bsl;

    public static void setBsl(BingoSimulationLayout bsl) {
        BingoSimulationLayoutHandler.bsl = bsl;
    }

    public static void setCardLayout(){
        bsl.setBingoCardLayout();
    }

    public static void setNumCards(int numCards){
        bsl.setNumCards(numCards);
    }

}
