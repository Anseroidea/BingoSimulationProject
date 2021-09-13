package print;

import card.BingoCardLayout;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
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
        new File("/bingoPictures").mkdirs();
    }

    public static void printToImg(int[] indexes){
        File file = new File("/bingoPictures/test.png");
        GridPane gp = new GridPane();
        for (int i = 0; i < indexes.length; i++) {
            gp.add(BingoCardLayout.getAnchorPane(), i/2, i % 2);
        }
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(gp.snapshot(new SnapshotParameters(), null), null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
