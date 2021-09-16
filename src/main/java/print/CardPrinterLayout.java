package print;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ui.BingoCardApplication;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CardPrinterLayout {
    private Font bingoFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/CascadiaCode-Bold.ttf").toExternalForm(), 72);
    private final double bingoFontSize = 72;
    private Font numberFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Lora-Bold.ttf").toExternalForm(), 34);
    private final double numberFontSize = 34;
    private Font freeSpaceFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Candara.ttf").toExternalForm(),19);
    private final double freeSpaceFontSize = 19;
    private Font idFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 26);
    private Color backgroundColor = Color.rgb(255, 0, 8);
    private Color tileColor = Color.rgb(0, 0, 0);

    public CardPrinterLayout() {
    }

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

    public AnchorPane getLayout(){
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(400, 500);
        ap.setMinSize(400, 500);
        ap.setStyle("-fx-background-color: rgb(" + backgroundColor.getRed() + "," + backgroundColor.getGreen() + "," + backgroundColor.getBlue() + ")");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(ap.snapshot(new SnapshotParameters(), null), null), "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
