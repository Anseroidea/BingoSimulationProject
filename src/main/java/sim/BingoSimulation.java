package sim;

import java.util.ArrayList;
import java.util.Random;
import card.*;

public class BingoSimulation {

    private BingoCardHandler bch;
    private ArrayList<BingoBall> newBalls;
    private ArrayList<BingoBall> usedBalls;
    private Random r;

    public BingoSimulation(int seed){
        newBalls = new ArrayList<>();
        usedBalls = new ArrayList<>();
        for (int i = 1; i <= 75; i++){
            newBalls.add(new BingoBall(i));
        }
        r = new Random(seed);
        bch = new BingoCardHandler(r);
    }

    public BingoBall nextRoll(){
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

    public BingoCardHandler getBingoCardHandler() {
        return bch;
    }
}
