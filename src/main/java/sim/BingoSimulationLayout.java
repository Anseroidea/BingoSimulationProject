package sim;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import print.CardPrinter;
import ui.*;
import card.*;

import javafx.print.PrinterJob;
import java.net.URL;
import java.util.*;

public class BingoSimulationLayout implements Initializable{

    @FXML private TableView<CardWin> winsTable;
    @FXML private TableColumn<CardWin, Integer> winRollsColumn;
    @FXML private TableColumn<CardWin, Integer> winColumn;
    @FXML private TableColumn<BallRoll, Integer> rollsColumn;
    @FXML private TableColumn<BallRoll, Integer> ballsColumn;
    @FXML private TableView<BallRoll> rollsTable;
    private ObservableList<BallRoll> ballRolls;
    private ObservableList<CardWin> cardWins;
    private int numWinners;
    @FXML private Spinner<Integer> idSpinner;
    @FXML private Label rollLabel;
    @FXML private Button rollNWinnersButton;

    @FXML
    public void rollBallFunction(MouseEvent mouseEvent) {
        BingoBall ball = BingoCardApplication.getSimulation().nextRoll();
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
        updateRollsTable();
        updateWinsTable();
    }

    @FXML
    public void rollNextWinnerFunction(MouseEvent mouseEvent) {
        int currentSize = BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size();
        if (currentSize == 0){
            return; //set label to finished simulation;
        }
        while (BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size() == currentSize){
            BingoCardApplication.getSimulation().nextRoll();
        }
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
        updateRollsTable();
        updateWinsTable();
    }

    @FXML
    public void rollNWinnersFunction(MouseEvent mouseEvent) {
        if (BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size() == 0)
            return;
        BingoBall b = null;
        while (BingoCardApplication.getSimulation().getBingoCardHandler().getWonCards().size() < numWinners){
            b = BingoCardApplication.getSimulation().nextRoll();
        }
        if (b == null)
            return;
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled() + ": " + b.getI());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
        updateRollsTable();
        updateWinsTable();
        CardPrinter.printToImg(new int[]{1, 2, 3, 4});
    }

    public void setBingoCardLayout(){
        AnchorPane ap = BingoCardLayout.getAnchorPane();
        VBox v = (VBox) BingoSimulationState.BINGOSIM.getAnchorPane().getChildren().get(0);
        HBox h = (HBox) v.getChildren().get(1);
        h.getChildren().set(0, ap);
    }

    public void setNumCards(int numCards){
        idSpinner.setEditable(true);
        idSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, numCards - 1));
        idSpinner.getValueFactory().setValue(0);
        idSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                viewCard(Integer.parseInt(newValue));
            } catch(Exception e){

            }
        });
    }

    private void viewCard(int id){
        idSpinner.getValueFactory().setValue(id);
        BingoCardLayout.setBc(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(id));
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
    }

    public void setNumWinners(int winners){
        numWinners = winners;
        String label = "Roll Until " + winners + " Winner";
        if (winners > 1){
            label += "s";
        }
        rollNWinnersButton.setText(label);
    }

    private void updateRollsTable(){
        ArrayList<BingoBall> usedBalls = BingoCardApplication.getSimulation().getUsedBalls();
        for (int i = ballRolls.size(); i < usedBalls.size(); i++){
            BingoBall ball = usedBalls.get(i);
            ballRolls.add(new BallRoll(i + 1, ball.getI()));
        }
    }

    private void updateWinsTable(){
        ArrayList<CardWin> tempCards = BingoCardApplication.getSimulation().getBingoCardHandler().getCardWins();
        for (int i = cardWins.size(); i < tempCards.size(); i++){
            cardWins.add(tempCards.get(i));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ballRolls = FXCollections.observableArrayList();
        cardWins = FXCollections.observableArrayList();
        rollsColumn.setCellValueFactory(new PropertyValueFactory<>("rollNum"));
        ballsColumn.setCellValueFactory(new PropertyValueFactory<>("ballNum"));
        winRollsColumn.setCellValueFactory(new PropertyValueFactory<>("rollNum"));
        winColumn.setCellValueFactory(new PropertyValueFactory<>("winningID"));
        rollsColumn.setEditable(false);
        ballsColumn.setEditable(false);
        rollsTable.setEditable(false);
        rollsColumn.setResizable(false);
        ballsColumn.setResizable(false);
        rollsTable.setItems(ballRolls);
        winRollsColumn.setEditable(false);
        winColumn.setEditable(false);
        winsTable.setEditable(false);
        winRollsColumn.setResizable(false);
        winColumn.setResizable(false);
        winsTable.setItems(cardWins);
        rollsTable.getStylesheets().add(BingoSimulationLayout.class.getResource("/css/hideScrollBarSimLayout.css").toString());
        winsTable.getStylesheets().add(BingoSimulationLayout.class.getResource("/css/hideScrollBarSimLayout.css").toString());
        winsTable.setRowFactory(tv -> {
            TableRow<CardWin> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                viewCard(row.getItem().getWinningID());
            });
            return row;
        });
    }
}
