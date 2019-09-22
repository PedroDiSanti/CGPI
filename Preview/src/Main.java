import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Window");
        primaryStage.setResizable(true);
        primaryStage.show();

        ((MainController) loader.getController()).displayWindow(scene);
    }

}