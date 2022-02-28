package sim;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.StringConverter;
import print.BingoPrintHandler;
import ui.*;
import card.*;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

public class BingoSimulationLayout implements Initializable{

    @FXML private StackPane cardWonPane;
    @FXML private Label maxWinnerLabel;
    @FXML private Label maxBallLabel;
    @FXML private TableColumn<BallRoll, String> dayInfoRollColumn;
    @FXML private TableColumn<CardWin, String> dayInfoColumn;
    @FXML private TableView<CardWin> winsTable;
    @FXML private TableColumn<CardWin, Integer> winRollsColumn;
    @FXML private TableColumn<CardWin, Integer> winColumn;
    @FXML private TableColumn<BallRoll, Integer> rollsColumn;
    @FXML private TableColumn<BallRoll, Integer> ballsColumn;
    @FXML private TableView<BallRoll> rollsTable;
    private ObservableList<BallRoll> ballRolls;
    private ObservableList<CardWin> cardWins;
    private boolean updateWinners;
    @FXML private Spinner<Integer> idSpinner;
    @FXML private Label rollLabel;
    @FXML private Button rollNWinnersButton;
    private int currentCard;
    private int currentRoll = 0;

    @FXML
    public void rollBallFunction(MouseEvent mouseEvent) {
        if (currentRoll >= 75){
            return;
        }
        currentRoll++;
        rollLabel.setText("(" + BingoCardApplication.getSimulation().getDayStringFromRoll(currentRoll) + ") Roll " + currentRoll);
        updateAll();
        viewCard(currentCard);
    }

    @FXML
    public void rollNextWinnerFunction(MouseEvent mouseEvent) {
        ArrayList<CardWin> totalCardWins = BingoCardApplication.getSimulation().getBingoCardHandler().getCardWins();
        int currentWinners = totalCardWins.size();
        for (int i = 0; i < totalCardWins.size(); i++){
            CardWin cardWin = totalCardWins.get(i);
            if (currentRoll < cardWin.getRollNum()){
                currentWinners = i;
                break;
            }
        }
        if (currentWinners == totalCardWins.size()){
            return; //set label to finished simulation;
        }
        currentRoll = totalCardWins.get(currentWinners).getRollNum();
        rollLabel.setText("(" + BingoCardApplication.getSimulation().getDayStringFromRoll(currentRoll) + ") Roll " + currentRoll);
        viewCard(currentCard);
        updateAll();
    }

    @FXML
    public void rollNWinnersFunction(MouseEvent mouseEvent){
        int rollsToWinners = BingoCardApplication.getSimulation().getRollsToWinners();
        if (rollsToWinners < currentRoll) {
            return;
        }
        currentRoll = rollsToWinners;
        rollLabel.setText("(" + BingoCardApplication.getSimulation().getDayStringFromRoll(currentRoll) + ") Roll " + currentRoll);
        viewCard(currentCard);
        updateAll();;
    }

    public void setBingoCardLayout(){
        cardWonPane.getChildren().add(new Label("Card 0 not yet won"));
        AnchorPane ap = BingoCardLayout.getAnchorPane();
        ap.setOnScroll(event -> {
            if (event.getDeltaY() < 0 && currentCard > 0){
                viewCard(currentCard - 1);
            } else if (event.getDeltaY() > 0 && currentCard < BingoCardApplication.getSimulation().getBingoCardHandler().getCards().size() - 1){
                viewCard(currentCard + 1);
            }
        });
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
                if (!(newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0)){
                    idSpinner.getValueFactory().setValue(0);
                    idSpinner.getEditor().textProperty().setValue("0");
                    viewCard(0);
                } else {
                    viewCard(Integer.parseInt(newValue));
                }
            } catch(Exception e){

            }
        });
        idSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return 0;
                }
            }
        });
    }

    private void viewCard(int id){
        cardWonPane.getChildren().clear();
        currentCard = id;;
        idSpinner.getValueFactory().setValue(id);
        BingoCard bc = BingoCardApplication.getSimulation().getBingoCardHandler().getCard(currentCard);
        CardWin cw = BingoCardApplication.getSimulation().getBingoCardHandler().getCardWins().stream().filter(c -> c.getWinningID() == bc.getId()).findFirst().get();
        Label wonText = new Label();
        wonText.setTextAlignment(TextAlignment.CENTER);
        if (cw.getRollNum() > currentRoll){
            wonText.setText("Card " + id + " not yet won");
        } else {
            wonText.setText("Card " + id + " won on roll " + cw.getRollNum() + " (" + BingoCardApplication.getSimulation().getDayStringFromRoll(cw.getRollNum()) + ")");
        }
        cardWonPane.getChildren().add(wonText);
        BingoCardLayout.displayBingoCard(bc.getId(), bc.getCard(), bc.getMarkedCardFromInt(BingoCardApplication.getSimulation().getBallRolls().subList(0, updateWinners ? currentRoll : Math.min(cw.getRollNum(), currentRoll)).stream().mapToInt(BallRoll::getBallNum).toArray()), true, currentRoll >= cw.getRollNum());
        BingoCardApplication.refreshCurrentScene();
    }

    public void setWinnerInfo(int winners, boolean updateWinners){
        //numWinners = winners;
        String label = "Roll Until " + winners + " Winner";
        if (winners > 1){
            label += "s";
        }
        rollNWinnersButton.setText(label);
        this.updateWinners = updateWinners;
    }

    private void updateAll(){
        updateRollsTable();
        updateWinsTable();
        updateLabels();
    }

    private void updateRollsTable(){
        ballRolls.setAll(BingoCardApplication.getSimulation().getBallRolls().subList(0, currentRoll));
    }

    private void updateWinsTable(){
        cardWins.clear();
        ArrayList<CardWin> tempCards = BingoCardApplication.getSimulation().getBingoCardHandler().getCardWins();
        for (int i = 0; i < tempCards.size(); i++){
            if (tempCards.get(i).getRollNum() <= currentRoll) {
                cardWins.add(tempCards.get(i));
            }
        }
    }

    private void updateLabels(){
        if (currentRoll >= 75){
            maxBallLabel.setVisible(true);
        }
        if (currentRoll >= BingoCardApplication.getSimulation().getRollsToWinners()){
            maxWinnerLabel.setVisible(true);
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
        dayInfoColumn.setCellValueFactory(new PropertyValueFactory<>("dayInfo"));
        dayInfoRollColumn.setCellValueFactory(new PropertyValueFactory<>("dayInfo"));
        rollsColumn.setEditable(false);
        ballsColumn.setEditable(false);
        rollsTable.setEditable(false);
        dayInfoRollColumn.setEditable(false);
        dayInfoColumn.setEditable(false);
        rollsColumn.setResizable(false);
        ballsColumn.setResizable(false);
        dayInfoRollColumn.setResizable(false);
        rollsTable.setItems(ballRolls);
        winRollsColumn.setEditable(false);
        winColumn.setEditable(false);
        winsTable.setEditable(false);
        winRollsColumn.setResizable(false);
        dayInfoColumn.setResizable(false);
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

    public void goToPrint(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOPRINT);
        BingoCardApplication.refreshDisplay();
        BingoPrintHandler.setNumCards(BingoCardApplication.getSimulation().getBingoCardHandler().getNumCards());
    }
}
