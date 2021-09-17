package print;

import com.sun.security.auth.NTSidPrimaryGroupPrincipal;
import javafx.application.Preloader;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ui.BingoCardApplication;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;

public class CardPrinterLayout {
    private Font bingoFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/CascadiaMono-SemiBold.ttf").toExternalForm(), 96);
    private final double bingoFontSize = 96;
    private Font numberFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Lora-Bold.ttf").toExternalForm(), 34);
    private final double numberFontSize = 34;
    private Font freeSpaceFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Candara.ttf").toExternalForm(),19);
    private final double freeSpaceFontSize = 19;
    private Font idFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 26);
    private Color bingoColor = Color.WHITE;
    private Color numberColor = Color.BLACK;
    private Color backgroundColor = Color.rgb(255, 0, 8);
    private Color tileColor = Color.rgb(255, 255, 255);

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
        System.out.println(backgroundColor.getBlue() + " " + backgroundColor.getGreen() + " " + backgroundColor.getRed());
        ap.setStyle("-fx-background-color: rgb(" + backgroundColor.getRed() * 255 + "," + backgroundColor.getGreen() * 255 + "," + backgroundColor.getBlue() * 255 + ");");
        HBox h = new HBox();
        h.setLayoutX(25);
        h.setLayoutY(25);
        h.setPrefSize(350, 72);
        h.setSpacing(5);
        String[] letters = new String[]{"B", "I", "N", "G", "O"};
        for (int i = 0; i < letters.length; i++){
            Label bText = new Label(letters[i]);
            bText.setPrefHeight(72);
            bText.setFont(bingoFont);
            bText.setTextFill(bingoColor);
            bText.setAlignment(Pos.CENTER);
            h.getChildren().add(bText);
        }
        GridPane gp = new GridPane();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                Label l = new Label("2");
                l.setPrefSize(66, 66);
                l.setStyle("-fx-background-color: rgb(" + tileColor.getRed() * 255 + "," + tileColor.getGreen() * 255 + "," + tileColor.getBlue() * 255 + ");");
                gp.add(l, i, j);
            }
        }
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setLayoutX(25);
        gp.setLayoutY(124);
        gp.setPrefSize(350, 350);
        ap.getChildren().addAll(h, gp);
        Stage primaryStage = new Stage();
        Scene s = new Scene(ap);
        primaryStage.setScene(s);
        primaryStage.show();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(ap.snapshot(new SnapshotParameters(), null), null), "png", new File("test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
