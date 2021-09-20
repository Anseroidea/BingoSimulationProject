package sim;

import java.util.ArrayList;
import java.util.Arrays;

public class Day {

    public static final String[] DAY_NAMES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private String name;
    private int amRolls;
    private int pmRolls;

    public Day(String name){
        this.name = name;
    }

    public void setAmRolls(int amRolls) {
        this.amRolls = amRolls;
    }

    public void setPmRolls(int pmRolls) {
        this.pmRolls = pmRolls;
    }

    public int getAmRolls() {
        return amRolls;
    }

    public int getPmRolls() {
        return pmRolls;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Integer> getRollsAsList(){
        return new ArrayList<>(Arrays.asList(amRolls, pmRolls));
    }

    public String toString(){
        return name + ":\n\t" + "AM Rolls: " + amRolls + "\n\tPM Rolls: " + pmRolls + "\n";
    }

}
