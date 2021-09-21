package ui;

import card.BingoCard;
import card.BingoCardHandler;
import card.BingoCardLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import print.BingoPrintHandler;
import print.CardPrinterLayout;
import sim.BingoSimulation;
import sim.BingoSimulationLayoutHandler;
import sim.BingoSimulationState;

public class BingoCardApplication extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static Stage primaryStage;
    private static int gameNumber;
    private static BingoSimulation bs;

    public static void setSimulation(int seed, int numCards, int numWinners, int dayNum, boolean isUpdateWonCards) {
        gameNumber = seed;
        bs = new BingoSimulation(seed, numCards, numWinners, dayNum, isUpdateWonCards);
        bs.finishSimulation();
        BingoCardHandler bch = bs.getBingoCardHandler();
        BingoCard card = bch.getCard(0);
        BingoCardLayout.setBc(card);
        BingoCardLayout.displayBingoCard();
        BingoSimulationLayoutHandler.setCardLayout();
        BingoSimulationLayoutHandler.setNumCards(numCards);
        BingoSimulationLayoutHandler.setWinnerInfo(numWinners, isUpdateWonCards);
    }

    public static BingoSimulation getSimulation(){
        return bs;
    }



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bingo Project > MAINMENU");
        primaryStage.setResizable(false);
        for (BingoSimulationState bss : BingoSimulationState.values()){
            try {
                FXMLLoader loader = new FXMLLoader(BingoCardApplication.class.getResource("/fxml/" + bss.name().toLowerCase() + ".fxml"));
                AnchorPane ap = loader.load();
                bss.setAnchorPane(ap, ap.getPrefWidth(), ap.getPrefHeight());
                if (bss.name().equalsIgnoreCase("bingosim")) {
                    BingoSimulationLayoutHandler.setBsl(loader.getController());
                } else if (bss.name().equalsIgnoreCase("bingoprint")){
                    AnchorPane ap1 = new CardPrinterLayout().getDefaultLayout();
                    HBox h = (HBox) BingoSimulationState.BINGOPRINT.getAnchorPane().getChildren().get(0);
                    BorderPane bp = (BorderPane) h.getChildren().get(1);
                    bp.setCenter(ap1);
                    BingoPrintHandler.setBp(loader.getController());
                }
            } catch (Exception e){
                switch(bss){
                    case BINGOCARDLAYOUT -> bss.setAnchorPane(BingoCardLayout.getAnchorPane());
                    default -> e.printStackTrace();
                }
            }
        }
        BingoSimulationState.goToState(BingoSimulationState.MAINMENU);
        primaryStage.setScene(BingoSimulationState.getCurrentState().getScene());
        primaryStage.show();
    }

    public static void refreshDisplay(){
        primaryStage.setTitle("Bingo Project > " + BingoSimulationState.getCurrentState().name());
        primaryStage.setScene(BingoSimulationState.getCurrentState().getScene());
    }
    public static void refreshCurrentScene(){
        primaryStage.getScene().setRoot(BingoSimulationState.getCurrentState().getAnchorPane());
    }

    public static void main(String[] args){
        Application.launch(args);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static int getGameNumber(){
        return gameNumber;
    }

    public static void close(){
        primaryStage.close();
    }

}
