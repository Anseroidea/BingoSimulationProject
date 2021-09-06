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

    private static final Font numberFont = Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 26);
    private static final Image bingoCardImage = new Image(BingoCardApplication.class.getResource("/images/bingocard.png").toExternalForm());

    public static void displayBingoCard(){
        if (bc == null)
            return;
        Label idLabel = new Label("ID: " + bc.getId());
        idLabel.setFont(numberFont);
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
        int[][] card = bc.getCard();
        boolean[][] markedCard = bc.getMarkedCard();
        for (int r = 0; r < 5; r++){
            for (int c = 0; c < 5; c++){
                StackPane sp = new StackPane();
                if (card[r][c] > 0){
                    Label l = new Label(card[r][c] + "");
                    l.setTextFill(Color.BLACK);
                    l.setFont(Font.loadFont(BingoCardApplication.class.getResource("/fonts/ArialBlack.ttf").toExternalForm(), 34));
                    l.setAlignment(Pos.BASELINE_CENTER);
                    l.setMinSize(66, 66);
                    sp.getChildren().add(l);
                }
                if (markedCard[r][c]){
                    Circle cir = new Circle();
                    cir.setOpacity(0.5);
                    cir.setFill(Color.RED);
                    cir.setRadius(33);
                    sp.getChildren().add(cir);
                }
                gp.add(sp, r, c);
            }
        }
        ap.getChildren().clear();
        ap.getChildren().addAll(gp, idLabel);
        ap.setPrefSize(400, 500);
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
}
