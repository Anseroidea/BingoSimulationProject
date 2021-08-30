import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MainMenuScene {

    private static Scene mainMenu;
    @FXML private static AnchorPane anchorPane;

    static {
        try {
            FXMLLoader loader = FXMLLoader.load(MainMenuScene.class.getResource("/fxml/MainMenuScene"));
            anchorPane = loader.load();
            mainMenu = new Scene(anchorPane);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void doMainMenuButton(){
        System.out.println("Hey");
    }

    public static Scene getMainMenu(){
        return mainMenu;
    }

}
