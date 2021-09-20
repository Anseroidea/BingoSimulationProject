package print;

import card.BingoCard;
import card.BingoCardLayout;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import sim.BallRoll;
import sim.BingoSimulation;
import sim.BingoSimulationState;
import sim.CardWin;
import ui.BingoCardApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardPrinter {

    private static String timeInfo;

    static {
        new File("./BingoSimulationProject").mkdirs();
    }

    public static void setTimeInfo(){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH.mm.ss");
        Date date = new Date(System.currentTimeMillis());
        timeInfo = format.format(date);
    }

    public static void printToImg(int[] indexes, CardPrinterLayout cpl){
        String filePath = "./BingoSimulationProject/" + BingoCardApplication.getGameNumber() + "/";
        new File(filePath).mkdirs();
        filePath += timeInfo + "/";
        new File(filePath).mkdirs();
        new File(filePath + "pictures/").mkdirs();
        for (int i = 0; i < indexes.length;) {
            File file = new File(filePath + "pictures/" + i + "to" + (Math.min(i + 4, indexes.length - 1)) + ".png");
            GridPane gp = new GridPane();
            for (int j = 0; j < 4 && i < indexes.length; i++, j++) {
                gp.add(cpl.getLayoutFromCard(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(i)), j % 2, j / 2);
            }
            Scene s = new Scene(gp);
            try {
                BufferedImage bi = SwingFXUtils.fromFXImage(gp.snapshot(new SnapshotParameters(), null), null);
                ImageIO.write(SwingFXUtils.fromFXImage(gp.snapshot(new SnapshotParameters(), null), null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printToPdf(int[] indexes, CardPrinterLayout cpl){
        String filePath = "./BingoSimulationProject/" + BingoCardApplication.getGameNumber() + "/";
        new File(filePath).mkdirs();
        filePath += timeInfo + "/";
        new File(filePath).mkdirs();
        new File(filePath + "pictures/").mkdirs();
        PDDocument pd = new PDDocument();
        for (int i = 0; i < indexes.length;) {
            GridPane gp = new GridPane();
            for (int j = 0; j < 4 && i < indexes.length; i++, j++) {
                gp.add(cpl.getLayoutFromCard(BingoCardApplication.getSimulation().getBingoCardHandler().getCard(i)), j % 2, j / 2);
            }
            Scene s = new Scene(gp);
            try {
                BufferedImage bi = SwingFXUtils.fromFXImage(gp.snapshot(new SnapshotParameters(), null), null);
                PDPage page = new PDPage(new PDRectangle(bi.getWidth(), bi.getHeight()));
                pd.addPage(page);
                PDImageXObject pixo = LosslessFactory.createFromImage(pd, bi);
                PDPageContentStream cs = new PDPageContentStream(pd, page, PDPageContentStream.AppendMode.APPEND, false);
                cs.drawImage(pixo, 0, 0);
                cs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pd.save(filePath + "pictures/cards.pdf");
            pd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void printSimulationInformation(BingoSimulation sim){
        String filePath = "./BingoSimulationProject/" + BingoCardApplication.getGameNumber() + "/";
        new File(filePath).mkdirs();
        filePath += timeInfo + "/";
        new File(filePath).mkdirs();
        StringBuilder gameInformation = new StringBuilder();
        gameInformation.append("===================================================\n");
        gameInformation.append("Game Information\n");
        gameInformation.append("\n");
        gameInformation.append("Game Number - ").append(BingoCardApplication.getGameNumber()).append("\n");
        gameInformation.append("\n");
        gameInformation.append("Number of Cards - ").append(sim.getBingoCardHandler().getCards().size()).append("\n");
        gameInformation.append("\n");
        gameInformation.append("Number of Winners - ").append(sim.getSetWinners()).append("\n");
        gameInformation.append("\n");
        gameInformation.append("Number of Days - ").append(sim.getDays()).append("\n");
        gameInformation.append("===================================================");
        try {
            PrintWriter pw = new PrintWriter(filePath + "simulationinfo.txt");
            pw.println(gameInformation);
            pw.println();
            pw.println("Rolls:");
            pw.println("===================================================");
            StringBuilder rollInfo = new StringBuilder();
            rollInfo.append("Roll\t\tDay Info\t\tBall Rolled\n");
            for (int i = 0; i < sim.getRollsToWinners(); i++){
                BallRoll br = sim.getBallRolls().get(i);
                rollInfo.append(br.getRollNum()).append("\t\t").append(br.getDayInfo()).append("\t\t\t").append(br.getBallNum()).append("\n");
            }
            rollInfo.append("===================================================");
            pw.println(rollInfo);
            pw.println();
            pw.println("Winners:");
            pw.println("===================================================");
            StringBuilder winInfo = new StringBuilder();
            winInfo.append("Card ID\t\tRoll/Day\t       Winning Ball\n");
            for (CardWin cw : sim.getBingoCardHandler().getCardWins().subList(0, sim.getSetWinners())){
                winInfo.append(cw.getWinningID()).append("\t\t").append(cw.getDayInfo()).append(" (").append(cw.getRollNum()).append(")\t\t").append(sim.getBallRolls().get(cw.getRollNum() - 1).getBallNum()).append("\n");
            }
            winInfo.append("===================================================");
            pw.println(winInfo);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getAbsoluteFilePath(){
        return new File(".").getAbsolutePath() + "\\BingoSimulationProject\\" + BingoCardApplication.getGameNumber() + "\\" + timeInfo + "\\";
    }

}
