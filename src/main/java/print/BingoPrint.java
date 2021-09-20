package print;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sim.BingoSimulationState;
import ui.BingoCardApplication;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoPrint implements Initializable {
    @FXML private Label minMaxErrorLabel;
    @FXML private Spinner<Integer> minIndexSpinner;
    @FXML private Spinner<Integer> maxIndexSpinner;
    @FXML private RadioButton pngSelection;
    @FXML private RadioButton pdfSelection;
    @FXML private Label errorLabel;
    @FXML private ComboBox<Font> numberFontPicker;
    @FXML private ComboBox<Font> freeSpaceFontPicker;
    @FXML private ComboBox<Font> idFontPicker;
    @FXML private ColorPicker freeSpaceTileColorPicker;
    @FXML private ColorPicker tileColorPicker;
    @FXML private ColorPicker numberTextColorPicker;
    @FXML private ColorPicker bingoTextColorPicker;
    @FXML private ColorPicker freeSpaceTextColorPicker;
    @FXML private ColorPicker idTextColorPicker;
    @FXML private ComboBox<Font> bingoFontPicker;
    @FXML private ColorPicker backgroundColorPicker;
    private CardPrinterLayout cpl = new CardPrinterLayout();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backgroundColorPicker.getCustomColors().add(cpl.getBackgroundColor());
        backgroundColorPicker.setValue(cpl.getBackgroundColor());
        numberTextColorPicker.setValue(Color.BLACK);
        freeSpaceTileColorPicker.setValue(Color.WHITE);
        tileColorPicker.setValue(Color.WHITE);
        tileColorPicker.getCustomColors().add(Color.web("0483ff"));
        bingoTextColorPicker.setValue(Color.WHITE);
        freeSpaceTextColorPicker.setValue(Color.BLACK);
        idTextColorPicker.setValue(Color.WHITE);
        backgroundColorPicker.setOnAction(e -> {
            cpl.setBackgroundColor(backgroundColorPicker.getValue());
            updateDisplayPane();
        });
        freeSpaceTileColorPicker.setOnAction(e -> {
            cpl.setFreeSpaceTileColor(freeSpaceTileColorPicker.getValue());
            updateDisplayPane();
        });
        tileColorPicker.setOnAction(e -> {
            cpl.setTileColor(tileColorPicker.getValue());
            updateDisplayPane();
        });
        numberTextColorPicker.setOnAction(e ->{
            cpl.setNumberColor(numberTextColorPicker.getValue());
            updateDisplayPane();
        });
        bingoTextColorPicker.setOnAction(e -> {
            cpl.setBingoColor(bingoTextColorPicker.getValue());
            updateDisplayPane();
        });
        freeSpaceTextColorPicker.setOnAction(e -> {
            cpl.setFreeSpaceTextColor(freeSpaceTextColorPicker.getValue());
            updateDisplayPane();
        });
        idTextColorPicker.setOnAction(e -> {
            cpl.setIdColor(idTextColorPicker.getValue());
            updateDisplayPane();
        });
        convertToFontPicker(bingoFontPicker, cpl.getBingoFontSize(), cpl.getBingoFont(), cpl::setBingoFont);
        convertToFontPicker(numberFontPicker, cpl.getNumberFontSize(), cpl.getNumberFont(), cpl::setNumberFont);
        convertToFontPicker(freeSpaceFontPicker, cpl.getFreeSpaceFontSize(), cpl.getFreeSpaceFont(), cpl::setFreeSpaceFont);
        convertToFontPicker(idFontPicker, cpl.getIdFont().getSize(), cpl.getIdFont(), cpl::setIdFont);
    }

    private void convertToFontPicker(ComboBox<Font> fontPicker, double defaultSize, Font defaultFont, Consumer<Font> cplSetter){
        fontPicker.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Font> call(ListView<Font> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Font font, boolean empty) {
                        super.updateItem(font, empty);
                        if (font != null) {
                            if (font.getName().equalsIgnoreCase(defaultFont.getName())) {
                                param.scrollTo(font);
                                setText(font.getName() + " - Default");
                            } else {
                                setText(font.getName());
                            }
                            setFont(Font.font(font.getName(), Font.getDefault().getSize()));
                        }
                    }
                };
            }
        });
        fontPicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(Font object) {
                if (object == null) {
                    return null;
                }
                return object.getName();
            }

            @Override
            public Font fromString(String string) {
                if (string == null) {
                    return null;
                }
                return Font.font(string);
            }
        });
        if (defaultSize == cpl.getFreeSpaceFontSize()){
            fontPicker.getItems().setAll(cpl.getFonts(defaultSize).stream().filter(f -> {
                Text text = new Text("Free");
                text.setFont(f);
                return text.getLayoutBounds().getWidth() <= 66;
            }).collect(Collectors.toCollection(ArrayList::new)));
        } else if (defaultFont == cpl.getBingoFont()) {
            fontPicker.getItems().setAll(cpl.getFonts(defaultSize).stream().filter(f -> {
                String[] letters = {"B", "I", "N", "G", "O"};
                for (int i = 0; i < letters.length; i++){
                    Text text = new Text(letters[i]);
                    text.setFont(f);
                    if (text.getLayoutBounds().getWidth() > 66)
                        return false;
                }
                return true;
            }).collect(Collectors.toCollection(ArrayList::new)));
        } else {
            fontPicker.getItems().setAll(cpl.getFonts(defaultSize));
        }
        fontPicker.setOnAction(e -> {
            cplSetter.accept(fontPicker.getValue());
            updateDisplayPane();
        });
        fontPicker.setValue(defaultFont);
        fontPicker.getSelectionModel().select(defaultFont);
    }

    private void updateDisplayPane(){
        AnchorPane ap = cpl.getDefaultLayout();
        HBox h = (HBox) BingoSimulationState.BINGOPRINT.getAnchorPane().getChildren().get(0);
        BorderPane bp = (BorderPane) h.getChildren().get(1);
        bp.setCenter(ap);
    }

    public void printSelected(MouseEvent mouseEvent) {
        if (!(pngSelection.isSelected() || pdfSelection.isSelected())){
            minMaxErrorLabel.setVisible(false);
            errorLabel.setVisible(true);
        } else if (minIndexSpinner.getValue() > maxIndexSpinner.getValue()) {
            errorLabel.setVisible(false);
            minMaxErrorLabel.setVisible(true);
        } else {
            minMaxErrorLabel.setVisible(false);
            errorLabel.setVisible(false);
            CardPrinter.setTimeInfo();
            String exportText = "Simulation Info and Cards (";
            if (pngSelection.isSelected()){
                CardPrinter.printToImg(IntStream.range(minIndexSpinner.getValue(), maxIndexSpinner.getValue() + 1).toArray(), cpl);
                exportText += "png";
            }
            if (pdfSelection.isSelected()){
                CardPrinter.printToPdf(IntStream.range(minIndexSpinner.getValue(), maxIndexSpinner.getValue() + 1).toArray(), cpl);
                if (exportText.contains("png")){
                    exportText += " and ";
                }
                exportText += "pdf";
            }
            CardPrinter.printSimulationInformation(BingoCardApplication.getSimulation());
            exportText += ")";
            Stage stage = new Stage();
            VBox v = new VBox();
            Label label = new Label(exportText + " exported to " + CardPrinter.getAbsoluteFilePath());
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            Button b = new Button("Open File Location");
            b.setOnAction(e -> {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(new File(CardPrinter.getAbsoluteFilePath()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                BingoCardApplication.close();
            });
            v.getChildren().addAll(label, b);
            v.setAlignment(Pos.CENTER);
            v.setSpacing(20);
            Scene s = new Scene(new StackPane(v), 600, 100);
            stage.setScene(s);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(BingoCardApplication.getPrimaryStage());
            stage.setOnCloseRequest(e -> BingoCardApplication.close());
            stage.setResizable(false);
            stage.show();
        }
    }

    public void setCardInfo(int num){
        minIndexSpinner.setEditable(true);
        minIndexSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, num - 1));
        maxIndexSpinner.setEditable(true);
        maxIndexSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, num - 1));
        minIndexSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!(newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0)){
                minIndexSpinner.getValueFactory().setValue(0);
                minIndexSpinner.getEditor().textProperty().setValue("0");
            }
        });
        maxIndexSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!(newValue.matches("-?\\d+") && Integer.parseInt(newValue) > 0)){
                maxIndexSpinner.getValueFactory().setValue(num - 1);
                maxIndexSpinner.getEditor().textProperty().setValue(num - 1 + "");
            }
        });
        maxIndexSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return num - 1;
                }
            }
        });
        minIndexSpinner.getValueFactory().setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string.matches("-?\\d+")){
                    return Integer.parseInt(string);
                } else {
                    return 0;
                }
            }
        });
        minIndexSpinner.getValueFactory().setValue(0);
        maxIndexSpinner.getValueFactory().setValue(num - 1);
    }
}
