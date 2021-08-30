import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BingoCardApplication extends Application {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    @Override
    public void start(Stage primaryStage){
        System.out.println((byte) -98);
        primaryStage.setTitle("Bingo Card");
        primaryStage.setResizable(false);
        BingoCardHandler bch = new BingoCardHandler(100);
        BingoCardLayout.displayBingoCard(bch.generateNewBingoCard());
        primaryStage.setScene(new Scene(BingoCardLayout.getAnchorPane(), WIDTH, HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args){

        Application.launch(args);

    }

}
