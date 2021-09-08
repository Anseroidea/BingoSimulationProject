package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sim.BingoSimulationState;

import java.util.stream.Collectors;

public class BingoInput {
    @FXML private TextField bingoInputField;
    @FXML private TextField numCardsField;
    @FXML private Label errorLabel;

    public int getGameNumber() {
        return Integer.parseInt(bingoInputField.getText());
    }

    public void submit(){
        if (bingoInputField.getText().chars().anyMatch(c -> !Character.isDigit(c)) || bingoInputField.getText().isBlank()) {
            errorLabel.setText("Number of Cards must only be an integer!");
        } else if (numCardsField.getText().chars().anyMatch(c -> !Character.isDigit(c)) || numCardsField.getText().isBlank()){
            errorLabel.setText("Game Number must only be an integer!");
        } else {
            errorLabel.setText("");
            int gameNumber = Integer.parseInt(bingoInputField.getText());
            int numCards = Integer.parseInt(numCardsField.getText());
            BingoSimulationState.goToState(BingoSimulationState.BINGOSIM);
            BingoCardApplication.setSimulation(gameNumber, numCards);
            BingoCardApplication.refreshDisplay();
        }
    }

    public void checkIfNum(KeyEvent keyEvent) {
        /*
        if (!keyEvent.getCharacter().chars().allMatch(Character::isDigit)){
            int beforeLength = bingoInputField.getText().length();
            bingoInputField.setText(bingoInputField.getText().chars().filter(Character::isDigit).mapToObj(Character::toString).collect(Collectors.joining()));
            bingoInputField.positionCaret(bingoInputField.getText().length());
            if (bingoInputField.getText().length() < beforeLength)
                errorLabel.setText("Game Number must only be an integer!");
            else
                errorLabel.setText("");
        } else {
            errorLabel.setText("");
        }
         */
    }
}
