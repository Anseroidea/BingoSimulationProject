package ui;

import javafx.scene.input.MouseEvent;
import sim.BingoSimulationState;

public class BingoHelpPrint {
    public void goToMenu(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.MAINMENU);
        BingoCardApplication.refreshDisplay();
    }

    public void goToPrevious(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOHELPSIM);
        BingoCardApplication.refreshDisplay();
    }
}
