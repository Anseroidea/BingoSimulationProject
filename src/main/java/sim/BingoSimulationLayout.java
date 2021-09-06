package sim;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ui.*;
import card.*;

public class BingoSimulationLayout{
    @FXML private Label ballLabel;
    @FXML private Label winLabel;

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

}
