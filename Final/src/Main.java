import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.application.Application;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Pane root = new GUIBorderPane();
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trabalho Final CGPI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
