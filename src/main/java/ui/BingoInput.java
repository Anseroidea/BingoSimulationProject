package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import sim.BingoSimulationState;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BingoInput implements Initializable {
    @FXML private Spinner<Integer> numCardsSpinner;
    @FXML private Spinner<Integer> dayNumSpinner;
    @FXML private Spinner<Integer> totalWinnersSpinner;
    @FXML private CheckBox updateWonCards;
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
            int numWinners = totalWinnersSpinner.getValue();
            int dayNum = dayNumSpinner.getValue();
            boolean isUpdateWonCards = updateWonCards.isSelected();
            BingoSimulationState.goToState(BingoSimulationState.BINGOSIM);
            BingoCardApplication.setSimulation(gameNumber, numCards, numWinners, dayNum, isUpdateWonCards);
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
        numCardsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        totalWinnersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1));
        numCardsSpinner.setEditable(true);
        totalWinnersSpinner.setEditable(true);
        numCardsSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                int winners = totalWinnersSpinner.getValue();
                totalWinnersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.parseInt(newValue)));
                totalWinnersSpinner.getValueFactory().setValue(winners);
            } catch(Exception e){

            }
        });
        dayNumSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
        dayNumSpinner.setEditable(true);
    }
}
