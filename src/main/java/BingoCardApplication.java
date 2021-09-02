import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;

public class BingoCardApplication extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static Stage primaryStage;
    private static int gameNumber;
    private static BingoSimulation bs;

    public static void setSimulation(int seed) {
        gameNumber = seed;
        bs = new BingoSimulation(seed);
        BingoCardHandler bch = bs.getBingoCardHandler();
        BingoCard card = bch.generateNewBingoCard();
        BingoCardLayout.displayBingoCard(card);
        while(!card.isWinner()){
            bs.nextRoll();
            BingoCardLayout.displayBingoCard(card);
            refreshDisplay();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bingo Card");
        primaryStage.setResizable(false);
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
        BingoSimulationState.goToState(BingoSimulationState.MAINMENU);
        primaryStage.setScene(BingoSimulationState.getCurrentState().getScene());
        primaryStage.show();
    }

    public static void refreshDisplay(){
        primaryStage.setScene(BingoSimulationState.getCurrentState().getScene());
    }

    public static void main(String[] args){

        Application.launch(args);

    }

}
