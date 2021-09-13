package sim;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CardWin {
    private IntegerProperty winningID;
    private IntegerProperty rollNum;

    public CardWin(Integer roll, Integer card){
        winningID = new SimpleIntegerProperty(roll);
        rollNum = new SimpleIntegerProperty(card);
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
}
