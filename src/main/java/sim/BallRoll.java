package sim;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ui.BingoCardApplication;

public class BallRoll {
    private IntegerProperty rollNum;
    private IntegerProperty ballNum;
    private StringProperty dayInfo;

    public BallRoll(Integer roll, Integer ball){
        rollNum = new SimpleIntegerProperty(roll);
        ballNum = new SimpleIntegerProperty(ball);
        dayInfo = new SimpleStringProperty(BingoCardApplication.getSimulation().getDayStringFromRoll(roll));
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


    public String getDayInfo() {
        return dayInfo.get();
    }

    public StringProperty dayInfoProperty() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo.set(dayInfo);
    }

    public String toString(){
        return rollNum + ", " + ballNum;
    }
}