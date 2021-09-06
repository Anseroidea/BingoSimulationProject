package sim;

public class BingoSimulationLayoutHandler {

    public static BingoSimulationLayout bsl;

    public static void setBsl(BingoSimulationLayout bsl) {
        BingoSimulationLayoutHandler.bsl = bsl;
    }

    public static void setCardLayout(){
        bsl.setBingoCardLayout();
    }

}
