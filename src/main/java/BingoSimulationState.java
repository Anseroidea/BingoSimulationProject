import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public enum BingoSimulationState {

    MAINMENU,// (getScene("/fxml/mainmenu.fxml", 600, 400)),
    BINGOINPUT,
    BINGOSIM,
    BINGOCARDLAYOUT,
    BINGOFILE,
    HOWTOUSE;

    private Scene scene;
    private static BingoSimulationState currentState;

    void setScene(Scene s){
        scene = s;
    }
    public Scene getScene(){
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
