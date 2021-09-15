package print;

import card.BingoCard;
import card.BingoCardLayout;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import ui.BingoCardApplication;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class CardPrinter {

    static {
        new File("./BingoSimulationProject").mkdirs();
    }

    public static void printToImg(int[] indexes){
        String filePath = "./BingoSimulationProject/" + BingoCardApplication.getGameNumber() + "/";
        new File(filePath).mkdirs();
        new File(filePath + "/pictures/").mkdirs();
        for (int i = 0; i < indexes.length;) {
            File file = new File(filePath + "/pictures/" + i + "to" + (Math.min(i + 4, indexes.length - 1)) + ".png");
            GridPane gp = new GridPane();
            for (int j = 0; j < 4 && i < indexes.length; i++, j++) {
                BingoCardLayout.displayBingoCard(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(i), false);
                gp.add(new ImageView(BingoCardLayout.getAnchorPane().snapshot(new SnapshotParameters(), null)), j % 2, j / 2);
            }
            BingoCardLayout.displayBingoCard();
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(gp.snapshot(new SnapshotParameters(), null), null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
