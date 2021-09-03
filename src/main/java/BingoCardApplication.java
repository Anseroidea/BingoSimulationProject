import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        BingoCardLayout.setBc(card);
        BingoCardLayout.displayBingoCard();
        BingoSimulationLayout bsl = new BingoSimulationLayout();
        bsl.setBingoCardLayout();
    }

    public static BingoSimulation getSimulation(){
        return bs;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bingo Card");
        primaryStage.setResizable(false);
        for (BingoSimulationState bss : BingoSimulationState.values()){
            try {
                FXMLLoader loader = new FXMLLoader(BingoCardApplication.class.getResource("/fxml/" + bss.name().toLowerCase() + ".fxml"));
                bss.setScene(new Scene(loader.load(), 600, 400));
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
