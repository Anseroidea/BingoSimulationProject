package sim;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ui.BingoCardApplication;

public class CardWin {
    private IntegerProperty winningID;
    private IntegerProperty rollNum;
    private StringProperty dayInfo;

    public CardWin(Integer roll, Integer card){
        winningID = new SimpleIntegerProperty(roll);
        rollNum = new SimpleIntegerProperty(card);
        dayInfo = new SimpleStringProperty(BingoCardApplication.getSimulation().getDayStringFromRoll(roll));
    }

    public int getWinningID() {
        return winningID.get();
    }

    public IntegerProperty winningIDProperty() {
        return winningID;
    }

    public void setWinningID(int winningID) {
        this.winningID.set(winningID);
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

    public String toString(){
        return winningID + ", " + rollNum;
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

}
