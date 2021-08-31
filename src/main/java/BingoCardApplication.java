import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;

public class BingoCardApplication extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Bingo Card");
        primaryStage.setResizable(false);
        BingoCardHandler bch = new BingoCardHandler(100);
        BingoCardLayout.displayBingoCard(bch.generateNewBingoCard());
        for (BingoSimulationState bss : BingoSimulationState.values()){
            try {
                bss.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(BingoCardApplication.class.getResource("/fxml/" + bss.name().toLowerCase() + ".fxml"))), 600, 400));
                System.out.println(bss.name() + bss.getScene());
            } catch (Exception e){
                switch(bss){
                    case BINGOCARDLAYOUT -> bss.setScene(new Scene(BingoCardLayout.getAnchorPane(), 400, 500));
                    default -> e.printStackTrace();
                }
            }
        }
        primaryStage.setScene(BingoSimulationState.BINGOCARDLAYOUT.getScene());
        primaryStage.show();
    }

    public static void main(String[] args){

        Application.launch(args);

    }

}
