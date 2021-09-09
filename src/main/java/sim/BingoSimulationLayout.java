package sim;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.*;
import card.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BingoSimulationLayout{
    private int selectedCard;
    @FXML private Label ballLabel;
    @FXML private Label winLabel;
    @FXML private Spinner<Integer> idSpinner;
    @FXML private Label rollLabel;

    @FXML
    public void rollBallFunction(MouseEvent mouseEvent) {
        BingoCardApplication.getSimulation().nextRoll();
        rollLabel.setText("Roll " + BingoCardApplication.getSimulation().getBallsRolled());
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshCurrentScene();
    }

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

    public void rollAllWinnersFunction(MouseEvent mouseEvent) {
        if (BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size() == 0)
            return;
        while (BingoCardApplication.getSimulation().getBingoCardHandler().getRemainingCards().size() > 0){
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

    @FXML public void setNumCards(int numCards){
        idSpinner.setEditable(true);
        idSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, numCards - 1));
        idSpinner.getValueFactory().setValue(0);
        idSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            BingoCardLayout.setBc(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(Integer.parseInt(newValue)));
            BingoCardLayout.displayBingoCard();
            BingoCardApplication.refreshCurrentScene();
        });
    }

    public void setCardDisplayed(InputMethodEvent inputMethodEvent) {
        System.out.println(idSpinner);
    }

}
