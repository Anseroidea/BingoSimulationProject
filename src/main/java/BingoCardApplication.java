import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BingoCardApplication extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Bingo Card");
        primaryStage.setResizable(false);
        BingoCardHandler bch = new BingoCardHandler(100);
        BingoCardLayout.displayBingoCard(bch.generateNewBingoCard());
        primaryStage.setScene(MainMenuScene.getMainMenu());
        primaryStage.show();
    }

    public static void main(String[] args){

        Application.launch(args);

    }

}
