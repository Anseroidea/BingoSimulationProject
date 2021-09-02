import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.stream.Collectors;

public class BingoInput {

    private int gameNumber = -1;
    @FXML private TextField bingoInputField;
    @FXML private Label errorLabel;

    public int getGameNumber() {
        return Integer.parseInt(bingoInputField.getText());
    }

    public void submit(){
        gameNumber = Integer.parseInt(bingoInputField.getText());
        BingoSimulationState.goToState(BingoSimulationState.BINGOCARDLAYOUT);
        BingoCardApplication.setSimulation(gameNumber);
        BingoCardApplication.refreshDisplay();
    }

    public void checkIfNum(KeyEvent keyEvent) {
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
    }
}
