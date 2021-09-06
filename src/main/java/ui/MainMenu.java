package ui;

import javafx.scene.input.MouseEvent;
import sim.BingoSimulationState;

public class MainMenu {
    public void doMainMenuButton(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOINPUT);
        BingoCardApplication.refreshDisplay();
    }
}
