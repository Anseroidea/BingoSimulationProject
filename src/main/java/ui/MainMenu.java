package ui;

import javafx.scene.input.MouseEvent;
import print.CardPrinterLayout;
import sim.BingoSimulationState;

public class MainMenu {
    public void doMainMenuButton(MouseEvent mouseEvent) {
        CardPrinterLayout cpl = new CardPrinterLayout();
        cpl.getLayout();
        BingoSimulationState.goToState(BingoSimulationState.BINGOINPUT);
        BingoCardApplication.refreshDisplay();
    }
}
