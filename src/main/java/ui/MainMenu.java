package ui;

import javafx.scene.input.MouseEvent;
import print.CardPrinterLayout;
import sim.BingoSimulationState;

public class MainMenu {
    public void doMainMenuButton(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOINPUT);
        BingoCardApplication.refreshDisplay();
    }

    public void goToHelp(MouseEvent mouseEvent) {
        BingoSimulationState.goToState(BingoSimulationState.BINGOHELPSIM);
        BingoCardApplication.refreshDisplay();
    }
}
