package sim;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ui.*;
import card.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BingoSimulationLayout implements Initializable {
    @FXML private Label ballLabel;
    @FXML private Label winLabel;
    @FXML private Spinner idSpinner;

    @FXML
    public void rollBallFunction(MouseEvent mouseEvent) {
        if (BingoCardApplication.getSimulation().getBingoCardHandler().getWonCards().size() > 0) {
            ballLabel.setVisible(false);
            winLabel.setVisible(true);
        } else {
            BingoBall ball = BingoCardApplication.getSimulation().nextRoll();
            ballLabel.setText("Rolled a " + ball.getI());
            BingoCardLayout.displayBingoCard();
            if (BingoCardApplication.getSimulation().getBingoCardHandler().getWonCards().size() > 0) {
                ballLabel.setVisible(false);
                winLabel.setVisible(true);
            }
            BingoCardApplication.refreshCurrentScene();
        }
    }

    @FXML
    public void setBingoCardLayout(){
        AnchorPane ap = BingoCardLayout.getAnchorPane();
        HBox h = (HBox) BingoSimulationState.BINGOSIM.getAnchorPane().getChildren().get(0);
        h.getChildren().set(0, ap);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
