package sim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import card.*;

public class BingoSimulation {

    private BingoCardHandler bch;
    private final ArrayList<BingoBall> newBalls;
    private final ArrayList<BingoBall> usedBalls;
    private final ArrayList<BallRoll> ballRolls;
    private final Day[] days;
    private final Random r;
    private int rollsToWinners = -1;
    private int winners;

    public BingoSimulation(int seed, int numCards, int numWinners, int dayNum, boolean isUpdateWonCards){
        newBalls = new ArrayList<>();
        usedBalls = new ArrayList<>();
        ballRolls = new ArrayList<>();
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
        bch = new BingoCardHandler(r, numCards, isUpdateWonCards);
        days = new Day[dayNum];
        for (int i = 0; i < dayNum; i++){
            days[i] = new Day(Day.DAY_NAMES[i]);
        }
        winners = numWinners;
    }

    public BingoBall nextRoll(){
        if (newBalls.size() == 0)
            return null;
        BingoBall ball = rollBall();
        bch.markCards(ball, getBallsRolled());
        return ball;
    }

    private BingoBall rollBall(){
        if (newBalls.size() <= 0){
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

    public ArrayList<BingoBall> getUsedBalls() {
        return usedBalls;
    }

    public int  getRollsToWinners(){
        return rollsToWinners;
    }

    public int getSetWinners(){
        return winners;
    }

    public ArrayList<BallRoll> getBallRolls(){ return ballRolls; }

    private void setWinnerInfo(){
        for(int i = 0; i < 2 * days.length; i++){
            Day d = days[i/2];
            if (i % 2 == 0) {
                d.setAmRolls(rollsToWinners / (2 * days.length) + ((i + 1) <= rollsToWinners % (2 * days.length) ? 1 : 0));
            } else {
                d.setPmRolls(rollsToWinners / (2 * days.length) + ((i + 1) <= rollsToWinners % (2 * days.length) ? 1 : 0));
            }
        }
    }

    public void finishSimulation(){
        while (getBallsRolled() < 75){
            ballRolls.add(new BallRoll(getBallsRolled() + 1, nextRoll().getI()));
            if (rollsToWinners < 0 && bch.getWonCards().size() == winners){
                rollsToWinners = getBallsRolled();
                setWinnerInfo();
            }
        }
        for (CardWin cardWin : bch.getCardWins()){
            cardWin.setDayInfo(getDayStringFromRoll(cardWin.getRollNum()));
        }
        for (BallRoll br : ballRolls){
            br.setDayInfo(getDayStringFromRoll(br.getRollNum()));
        }
        bch.resetCards();
        resetBalls();
    }

    public String getDayStringFromRoll(int rollNum){
        int rolls = 0;
        for (int i = 0; i < days.length; i++){
            Day d = days[i];
            rolls += d.getAmRolls();
            if (rollNum <= rolls)
                return d.getName().substring(0, 3) + ", AM";
            rolls += d.getPmRolls();
            if(rollNum <= rolls)
                return d.getName().substring(0, 3) + ", PM";
        }
        return "No Day";
    }

    public void resetBalls(){
        newBalls.addAll(usedBalls);
        usedBalls.clear();
    }

    public int getDays(){
        return days.length;
    }

}
