import java.util.ArrayList;

public class BingoSimulation {

    private static BingoCardHandler bch;
    private static ArrayList<BingoBall> balls;

    static {

    }

    public static void initBingoHandler(int seed){
        bch = new BingoCardHandler(seed);
    }



}
