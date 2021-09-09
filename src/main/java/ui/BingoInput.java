package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import sim.BingoSimulationState;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BingoInput implements Initializable {
    public Spinner<Integer> numCardsSpinner;
    public Spinner<Integer> dayNumSpinner;
    public Spinner<Integer> totalWinnersSpinner;
    @FXML private TextField bingoInputField;
    @FXML private Label errorLabel;

    public int getGameNumber() {
        return Integer.parseInt(bingoInputField.getText());
    }

    public void submit(){
        if (bingoInputField.getText().chars().anyMatch(c -> !Character.isDigit(c)) || bingoInputField.getText().isBlank()) {
            errorLabel.setText("Number of Cards must only be an integer!");
        } else {
            errorLabel.setText("");
            int gameNumber = Integer.parseInt(bingoInputField.getText());
            int numCards = numCardsSpinner.getValue();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numCardsSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            totalWinnersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.parseInt(newValue)));
        });
    }
}
