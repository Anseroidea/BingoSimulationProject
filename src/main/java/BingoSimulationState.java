import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

public enum BingoSimulationState {

    MAINMENU,// (getScene("/fxml/mainmenu.fxml", 600, 400)),
    BINGOINPUT,
    BINGOSIM,
    BINGOCARDLAYOUT,
    BINGOFILE,
    HOWTOUSE;

    private Scene currentScene;
    private Class controller;

    void setScene(Scene s){
        currentScene = s;
    }
    public Scene getScene(){
        return currentScene;
    }
    void setClass(Class c){
        controller = c;
    }

    public void goToScene(){

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
