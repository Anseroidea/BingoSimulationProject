package ui;

import javafx.scene.input.MouseEvent;
import sim.BingoSimulationState;

public class BingoHelpSim {
    public void goToMenu(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.MAINMENU);
        BingoCardApplication.refreshDisplay();
    }

    public void goToNext(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOHELPPRINT);
        BingoCardApplication.refreshDisplay();
    }
}
