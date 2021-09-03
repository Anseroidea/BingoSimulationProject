import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BingoSimulationLayout{
    @FXML
    private Button rollBallFunction;
    @FXML private AnchorPane bingoCardLayout;

    public void rollBallFunction(MouseEvent mouseEvent) {
        BingoCardApplication.getSimulation().nextRoll();
        BingoCardLayout.displayBingoCard();
        BingoCardApplication.refreshDisplay();
    }

    public void setBingoCardLayout(){
        bingoCardLayout = BingoCardLayout.getAnchorPane();
    }
}
