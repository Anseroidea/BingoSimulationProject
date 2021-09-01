import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainMenu {
    public void doMainMenuButton(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOINPUT);
        BingoCardApplication.refreshDisplay();
    }
}
