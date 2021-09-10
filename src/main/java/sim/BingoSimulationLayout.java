package sim;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jdk.jshell.spi.SPIResolutionException;
import ui.*;
import card.*;

import java.net.URL;
import java.util.*;

public class BingoSimulationLayout implements Initializable{

    public TableView<Map.Entry<Integer, Integer>> rollsTable;
    @FXML private TableColumn<String, Integer> ballRolledColumn;
    @FXML private TableColumn<String, Integer> bingoIDColumn;
    @FXML private TableColumn<String, Integer> rollColumn2;
    @FXML private TableColumn<Map.Entry<Integer, Integer>, Integer> rollColumn;
    private int selectedCard;
    private int numWinners;
    @FXML private Label ballLabel;
    @FXML private Label winLabel;
    @FXML private Spinner<Integer> idSpinner;
    @FXML private Label rollLabel;
    @FXML private Button rollNWinnersButton;


    public void rollBallFunction(MouseEvent mouseEvent) {
        BingoCardApplication.getSimulation().nextRoll();
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
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
    }

    @FXML
    public void rollNWinnersFunction(MouseEvent mouseEvent) {
        if (BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size() == 0)
            return;
        while (BingoCardApplication.getSimulation().getBingoCardHandler().getWonCards().size() < numWinners){
            BingoCardApplication.getSimulation().nextRoll();
        }
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
    }

    @FXML
    public void setBingoCardLayout(){
        AnchorPane ap = BingoCardLayout.getAnchorPane();
        VBox v = (VBox) BingoSimulationState.BINGOSIM.getAnchorPane().getChildren().get(0);
        HBox h = (HBox) v.getChildren().get(1);
        h.getChildren().set(0, ap);
    }

    @FXML
    public void setNumCards(int numCards){
        idSpinner.setEditable(true);
        idSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, numCards - 1));
        idSpinner.getValueFactory().setValue(0);
        idSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            BingoCardLayout.setBc(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(Integer.parseInt(newValue)));
            BingoCardLayout.displayBingoCard();
            BingoCardApplication.refreshCurrentScene();
        });
    }

    @FXML
    public void setNumWinners(int winners){
        numWinners = winners;
        String label = "Roll Until " + winners + " Winner";
        if (winners > 1){
            label += "s";
        }
        rollNWinnersButton.setText(label);
    }


    public void setCardDisplayed(InputMethodEvent inputMethodEvent) {
        System.out.println(idSpinner);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ballRolledColumn.getTableView().setEditable(false);
        bingoIDColumn.getTableView().setEditable(false);
        Map<Integer, Integer> test = new HashMap<>();

        ObservableList<Map.Entry<Integer, Integer>> list = FXCollections.observableList(Arrays.asList(
        ));
        rollColumn.setCellValueFactory(
                new PropertyValueFactory<>("value")
        );
        rollColumn.getTableView().setItems(list);
    }

    /*
    private static <K, V> Map.Entry<K, V> toEntry(K input, V inputValue) {
        return new Map.Entry<>() {

            private final K key = input;
            public V value = inputValue;

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public V setValue(V value) {
                V prev = this.value;
                this.value = value;
                return prev;
            }

            public ObjectProperty valueProperty(){
                return value;
            }


        };
    }

     */

}
