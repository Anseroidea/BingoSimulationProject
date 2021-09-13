package sim;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BallRoll {
    private IntegerProperty rollNum;
    private IntegerProperty ballNum;

    public BallRoll(Integer roll, Integer ball){
        rollNum = new SimpleIntegerProperty(roll);
        ballNum = new SimpleIntegerProperty(ball);
    }

    public int getRollNum() {
        return rollNum.get();
    }

    public IntegerProperty rollNumProperty() {
        return rollNum;
    }

    public void setRollNum(int rollNum) {
        this.rollNum.set(rollNum);
    }

    public int getBallNum() {
        return ballNum.get();
    }

    public IntegerProperty ballNumProperty() {
        return ballNum;
    }

    public void setBallNum(int ballNum) {
        this.ballNum.set(ballNum);
    }


}