package sim;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jdk.jshell.spi.SPIResolutionException;
import ui.*;
import card.*;

import java.net.URL;
import java.util.*;

public class BingoSimulationLayout implements Initializable{

    @FXML private TableView<Integer> bingoWinnerTable;
    @FXML private TableView<Integer> rollsTable;
    private ObservableMap<Integer, Integer> map;
    private ObservableList<Integer> keys;
    private int selectedCard;
    private int numWinners;
    @FXML private Label ballLabel;
    @FXML private Label winLabel;
    @FXML private Spinner<Integer> idSpinner;
    @FXML private Label rollLabel;
    @FXML private Button rollNWinnersButton;


    public void rollBallFunction(MouseEvent mouseEvent) {
        BingoBall ball = BingoCardApplication.getSimulation().nextRoll();
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        map.put(BingoCardApplication.getSimulation().getBallsRolled(), ball.getI());
        rollsTable.setItems(keys);
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
        BingoBall b = null;
        while (BingoCardApplication.getSimulation().getBingoCardHandler().getWonCards().size() < numWinners){
            b = BingoCardApplication.getSimulation().nextRoll();
        }
        if (b == null)
            return;
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled() + ": " + b.getI());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        map = FXCollections.observableHashMap();
        keys = FXCollections.observableArrayList();
        rollsTable = new TableView<>(keys);
        map.addListener((MapChangeListener.Change<? extends Integer, ? extends Integer> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                if (removed) {
                    keys.remove(change.getKey());
                } else {
                    keys.add(change.getKey());
                }
            }
        });
        map.put(2, 4);
        TableColumn<Integer, Integer> rollColumn = new TableColumn<>("Roll");
        TableColumn<Integer, Integer> ballColumn = new TableColumn<>("Ball");
        rollColumn.setCellValueFactory(cd -> Bindings.createIntegerBinding(cd::getValue).asObject());
        ballColumn.setCellValueFactory(cd -> Bindings.valueAt(map, cd.getValue()));
        rollsTable.getColumns().clear();
        rollsTable.getColumns().setAll(rollColumn, ballColumn);
    }

}
