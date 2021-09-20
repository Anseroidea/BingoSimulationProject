package print;

import card.BingoCard;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import ui.BingoCardApplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class CardPrinterLayout {
    private Font bingoFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/CascadiaMono-SemiBold.ttf").toExternalForm(), 80);
    private final double bingoFontSize = 80;
    private Font numberFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Lora-Bold.ttf").toExternalForm(), 34);
    private final double numberFontSize = 34;
    private Font freeSpaceFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Candara_Bold.ttf").toExternalForm(),25);
    private final double freeSpaceFontSize = 25;
    private Font idFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 26);
    private Color bingoColor = Color.WHITE;
    private Color numberColor = Color.BLACK;
    private Color freeSpaceTextColor = Color.BLACK;
    private Color freeSpaceTileColor = Color.WHITE;
    private Color backgroundColor = Color.rgb(255, 0, 8);
    private Color tileColor = Color.rgb(255, 255, 255);
    private Color idColor = Color.WHITE;

    private ArrayList<String> bingoFontNames = new ArrayList<>(new LinkedHashSet<>(Font.getFamilies().stream().map(f -> Font.font(f).getName()).collect(Collectors.toList())));

    public Font getBingoFont() {
        return bingoFont;
    }

    public void setBingoFont(Font bingoFont) {
        this.bingoFont = bingoFont;
    }

    public double getBingoFontSize() {
        return bingoFontSize;
    }

    public Font getNumberFont() {
        return numberFont;
    }

    public void setNumberFont(Font numberFont) {
        this.numberFont = numberFont;
    }

    public Font getFreeSpaceFont() {
        return freeSpaceFont;
    }

    public void setFreeSpaceFont(Font freeSpaceFont) {
        this.freeSpaceFont = freeSpaceFont;
    }

    public double getNumberFontSize() {
        return numberFontSize;
    }

    public double getFreeSpaceFontSize() {
        return freeSpaceFontSize;
    }

    public Font getIdFont() {
        return idFont;
    }

    public void setIdFont(Font idFont) {
        this.idFont = idFont;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

    public AnchorPane getDefaultLayout(){
        return getLayoutFromCard(null);
    }

    public AnchorPane getLayoutFromCard(BingoCard bc){
        if (bc == null)
            bc = new BingoCard(new int[]{1, 2, 3, 4, 5}, new int[]{16, 17, 18, 19, 20}, new int[]{31, 32, 34, 35}, new int[]{46, 47, 48, 49, 50}, new int[]{61, 62, 63, 64, 64}, -1);
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(400, 500);
        ap.setMinSize(400, 500);
        ap.setStyle("-fx-background-color: rgb(" + backgroundColor.getRed() * 255 + "," + backgroundColor.getGreen() * 255 + "," + backgroundColor.getBlue() * 255 + ");");
        HBox h = new HBox();
        Label idLabel = new Label(bc.getId() >= 0 ? "ID: " + bc.getId() : "ID: Preview");
        idLabel.setFont(idFont);
        idLabel.setTextFill(idColor);
        idLabel.setAlignment(Pos.CENTER_LEFT);
        idLabel.setPrefSize(350, 46);
        idLabel.setLayoutX(25);
        h.setLayoutX(25);
        h.setLayoutY(47);
        h.setPrefSize(350, 72);
        h.setMaxSize(350, 72);
        h.setPadding(new Insets(0));
        h.setSpacing(5);
        String[] letters = new String[]{"B", "I", "N", "G", "O"};
        for (int i = 0; i < letters.length; i++){
            StackPane sap = new StackPane();
            sap.setPrefSize(66, 72);
            Text text = new Text(letters[i]);
            text.setFont(bingoFont);
            text.setFill(bingoColor);
            text.setBoundsType(TextBoundsType.VISUAL);
            text.setLayoutX(0);
            text.setLayoutY(0);
            text.setTextAlignment(TextAlignment.CENTER);
            sap.getChildren().add(text);
            h.getChildren().add(sap);
        }
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                StackPane sp = new StackPane();
                sp.setPrefSize(66, 66);
                sp.setAlignment(Pos.CENTER);
                Text text = new Text("" + bc.getCard()[i][j]);
                text.setTextAlignment(TextAlignment.CENTER);
                text.setBoundsType(TextBoundsType.VISUAL);
                /*
                Label l = new Label("" + bc.getCard()[i][j]);
                l.setPrefSize(66, 66);
                l.setLineSpacing(-10);
                l.setAlignment(Pos.BASELINE_CENTER);
                l.setTextAlignment(TextAlignment.CENTER);
                l.setContentDisplay(ContentDisplay.TOP);
                if (i == 2 && j == 2) {
                    l.setText("Free\nSpace");
                    l.setFont(freeSpaceFont);
                    l.setTextFill(freeSpaceTextColor);
                    l.setStyle("-fx-background-color: rgb(" + freeSpaceTileColor.getRed() * 255 + "," + freeSpaceTileColor.getGreen() * 255 + "," + freeSpaceTileColor.getBlue() * 255 + ");");
                } else {
                    l.setFont(numberFont);
                    l.setTextFill(numberColor);
                    l.setStyle("-fx-background-color: rgb(" + tileColor.getRed() * 255 + "," + tileColor.getGreen() * 255 + "," + tileColor.getBlue() * 255 + ");");
                }
                gp.add(l, i, j);

                 */
                if (i == 2 && j == 2){
                    text.setText("Free");
                    text.setFont(freeSpaceFont);
                    text.setFill(freeSpaceTextColor);
                    sp.setStyle("-fx-background-color: rgb(" + freeSpaceTileColor.getRed() * 255 + "," + freeSpaceTileColor.getGreen() * 255 + "," + freeSpaceTileColor.getBlue() * 255 + ");");
                } else {
                    text.setFont(numberFont);
                    text.setFill(numberColor);
                    sp.setStyle("-fx-background-color: rgb(" + tileColor.getRed() * 255 + "," + tileColor.getGreen() * 255 + "," + tileColor.getBlue() * 255 + ");");
                }
                sp.getChildren().add(text);
                gp.add(sp, i, j);
            }
        }
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setLayoutX(25);
        gp.setLayoutY(124);
        gp.setPrefSize(350, 350);
        ap.getChildren().addAll(h, gp, idLabel);
        return ap;
    }

    public Color getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(Color numberColor) {
        this.numberColor = numberColor;
    }

    public Color getFreeSpaceTileColor() {
        return freeSpaceTileColor;
    }

    public void setFreeSpaceTileColor(Color freeSpaceTileColor) {
        this.freeSpaceTileColor = freeSpaceTileColor;
    }

    public Color getBingoColor() {
        return bingoColor;
    }

    public void setBingoColor(Color bingoColor) {
        this.bingoColor = bingoColor;
    }

    public Color getFreeSpaceTextColor() {
        return freeSpaceTextColor;
    }

    public void setFreeSpaceTextColor(Color freeSpaceTextColor) {
        this.freeSpaceTextColor = freeSpaceTextColor;
    }

    public Color getIdColor() {
        return idColor;
    }

    public void setIdColor(Color idColor) {
        this.idColor = idColor;
    }

    public ArrayList<Font> getFonts(double fontSize) {
        LinkedHashSet<Font> fonts = bingoFontNames.stream().map(i -> Font.font(i, fontSize)).collect(Collectors.toCollection(LinkedHashSet::new));
        fonts.add(Font.loadFont(BingoCardApplication.class.getResource("/fonts/CascadiaMono-SemiBold.ttf").toExternalForm(), fontSize));
        fonts.add(Font.loadFont(BingoCardApplication.class.getResource("/fonts/Lora-Bold.ttf").toExternalForm(), fontSize));
        fonts.add(Font.loadFont(BingoCardApplication.class.getResource("/fonts/Candara_Bold.ttf").toExternalForm(), fontSize));
        fonts.add(Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), fontSize));
        return fonts.stream().sorted(Comparator.comparing(Font::getName)).collect(Collectors.toCollection(ArrayList::new));
    }
}
