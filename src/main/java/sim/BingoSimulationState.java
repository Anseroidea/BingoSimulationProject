package sim;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public enum BingoSimulationState {

    MAINMENU,// (getScene("/fxml/mainmenu.fxml", 600, 400)),
    BINGOINPUT,
    BINGOSIM,
    BINGOCARDLAYOUT,
    BINGOPRINT,
    BINGOHELPSIM,
    BINGOHELPPRINT;

    private AnchorPane ap;
    private int width = 600;
    private int height = 400;
    private static BingoSimulationState currentState;
    private Scene scene;

    public void setAnchorPane(AnchorPane ap){
        this.ap = ap;
    }
    public void setAnchorPane(AnchorPane ap, double w, double h){
        this.ap = ap;
        width = (int) w;
        height = (int) h;
    }
    public AnchorPane getAnchorPane(){
        return ap;
    }
    public Scene getScene(){
        if (scene == null){
            scene = new Scene(ap, width, height);
        }
        return scene;
    }

    public static void goToState(BingoSimulationState bss){
        currentState = bss;
    }
    public static BingoSimulationState getCurrentState(){
        return currentState;
    }


    static Scene getScene(String url, int width, int height){
        try {
            FXMLLoader loader = FXMLLoader.load(BingoSimulationState.class.getResource(url));
            AnchorPane ap = (AnchorPane) loader.load();
            return new Scene(ap, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
