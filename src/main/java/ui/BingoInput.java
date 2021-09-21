package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
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
            errorLabel.setText("Number of Cards must only be a positive integer!");
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numCardsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        totalWinnersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1));
        numCardsSpinner.setEditable(true);
        totalWinnersSpinner.setEditable(true);
        numCardsSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return 1;
                }
            }
        });
        numCardsSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0){
                    int winners = totalWinnersSpinner.getValue();
                    totalWinnersSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.parseInt(newValue)));
                    totalWinnersSpinner.getValueFactory().setValue(winners);
                } else {
                    numCardsSpinner.getEditor().textProperty().setValue("1");
                    numCardsSpinner.getValueFactory().setValue(1);
                }

            } catch(Exception ignored){

            }
        });
        totalWinnersSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return 1;
                }
            }
        });
        totalWinnersSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!(newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0)){
                totalWinnersSpinner.getValueFactory().setValue(1);
                totalWinnersSpinner.getEditor().textProperty().setValue("1");
            }
        });
        dayNumSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
        dayNumSpinner.setEditable(true);
        dayNumSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return 1;
                }
            }
        });
        dayNumSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.isBlank() && !(newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0)){
                dayNumSpinner.getValueFactory().setValue(1);
                dayNumSpinner.getEditor().textProperty().setValue("1");
            }
        });
        /*
        dayNumSpinner.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (!evt.getPickResult().getIntersectedNode().equals(dayNumSpinner) && dayNumSpinner.getEditor().getText().isBlank()){
                dayNumSpinner.getValueFactory().setValue(1);
                dayNumSpinner.getEditor().textProperty().setValue("1");
            }
        });

         */
    }
}
