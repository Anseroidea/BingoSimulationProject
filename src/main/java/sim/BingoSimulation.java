package sim;

import java.util.ArrayList;
import java.util.Random;
import card.*;

public class BingoSimulation {

    private BingoCardHandler bch;
    private final ArrayList<BingoBall> newBalls;
    private final ArrayList<BingoBall> usedBalls;
    private final Random r;

    public BingoSimulation(int seed, int numCards){
        newBalls = new ArrayList<>();
        usedBalls = new ArrayList<>();
        for (int i = 1; i <= 75; i++){
            String col = switch((i - 1)/15){
                case 0 -> "B";
                case 1 -> "I";
                case 2 -> "N";
                case 3 -> "G";
                case 4 -> "O";
                default -> throw new IllegalStateException("Unexpected value: " + (i - 1) / 15);
            };
            newBalls.add(new BingoBall(i, col));
        }
        r = new Random(seed);
        bch = new BingoCardHandler(r, numCards);
    }

    public BingoBall nextRoll(){
        if (newBalls.size() == 0)
            return null;
        BingoBall ball = rollBall();
        bch.markCards(ball);
        return ball;
    }

    private BingoBall rollBall(){
        if (newBalls.size() <= 0){
            System.out.println("No more new balls!");
            return null;
        }
        BingoBall ball = newBalls.remove(r.nextInt(newBalls.size()));
        usedBalls.add(ball);
        return ball;
    }

    public int getBallsRolled(){
        return usedBalls.size();
    }

    public BingoCardHandler getBingoCardHandler() {
        return bch;
    }
}
