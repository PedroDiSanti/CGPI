import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;

public class MainController
{
    @FXML
    public ImageView imageView;
    public AnimationTimer imageTimer;

    public void displayWindow(Scene scene)
    {
        imageTimer = new ScreenshotsAnimationTimer(scene);
        imageTimer.start();
    }

    private class ScreenshotsAnimationTimer extends AnimationTimer
    {
        public Scene scene;

        public ScreenshotsAnimationTimer(Scene scene) {
            this.scene = scene;
        }

        @Override
        public void handle(long now)
        {
            imageView.setImage(scene.snapshot(null));
        }
    }

}