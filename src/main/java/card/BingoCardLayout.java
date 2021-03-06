package card;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import ui.BingoCardApplication;

public class BingoCardLayout {

    private static AnchorPane ap = new AnchorPane();
    private static BingoCard bc;

    private static final Font numberFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/Lora-Bold.ttf").toExternalForm(), 34);
    private static final Font idFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 26);
    private static final Image bingoCardImage = new Image(BingoCardApplication.class.getResource("/images/bingocard.png").toExternalForm());

    public static void displayBingoCard(BingoCard bc, boolean marked){
        if (bc == null)
            return;
        displayBingoCard(bc.getId(), bc.getCard(), bc.getMarkedCard(), marked, bc.isWinner());
    }

    public static void displayBingoCard(){
        displayBingoCard(bc, true);
    }

    public static AnchorPane getAnchorPane(){
        BackgroundImage bi = new BackgroundImage(bingoCardImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        ap.setBackground(new Background(bi));
        return ap;
    }

    public static BingoCard getBc() {
        return bc;
    }

    public static void setBc(BingoCard bc) {
        BingoCardLayout.bc = bc;
    }

    public static void displayBingoCard(int id, int[][] card, boolean[][] markedCard, boolean marked, boolean isWon){
        Label idLabel = new Label("ID: " + id);
        idLabel.setFont(idFont);
        idLabel.setTextFill(Color.WHITE);
        idLabel.setAlignment(Pos.CENTER_LEFT);
        idLabel.setPrefSize(350, 46);
        idLabel.setLayoutX(25);
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setLayoutX(25);
        gp.setLayoutY(124);
        for (int r = 0; r < 5; r++){
            for (int c = 0; c < 5; c++){
                StackPane sp = new StackPane();
                if (card[c][r] > 0){
                    Label l = new Label(card[c][r] + "");
                    l.setTextFill(Color.BLACK);
                    l.setFont(numberFont);
                    l.setAlignment(Pos.BASELINE_CENTER);
                    l.setMinSize(66, 66);
                    sp.getChildren().add(l);
                }
                if (markedCard[r][c] && marked){
                    Circle cir = new Circle();
                    cir.setOpacity(0.5);
                    cir.setFill(Color.RED);
                    cir.setRadius(33);
                    sp.getChildren().add(cir);
                }
                gp.add(sp, c, r);
            }
        }
        ap.getChildren().clear();
        if (isWon && marked){
            Label wonLabel = new Label("Won!");
            wonLabel.setFont(idFont);
            wonLabel.setTextFill(Color.WHITE);
            wonLabel.setLayoutX(300);
            wonLabel.setAlignment(Pos.CENTER_RIGHT);
            wonLabel.setPrefHeight(46);
            ap.getChildren().add(wonLabel);
        }
        ap.getChildren().addAll(gp, idLabel);
        ap.setPrefSize(400, 500);
    }

}
