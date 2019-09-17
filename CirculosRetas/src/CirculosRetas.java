import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Application;


public class CirculosRetas extends Application
{
    @Override
    public void start(Stage stage) {
        int x = 300;
        int y = 150;

        Circle circulo0 = desenharCirculo(x, y);
        Circle circulo1 = desenharCirculo(x-50, y);
        Circle circulo2 = desenharCirculo(x+50, y);
        Circle circulo3 = desenharCirculo(x-25, y+40);
        Circle circulo4 = desenharCirculo(x+25, y+40);
        Circle circulo5 = desenharCirculo(x-25, y-50);
        Circle circulo6 = desenharCirculo(x+25, y-50);

        Line linha0 = desenharLinha(x-75, y+45, x+75, y+45);
        Line linha1 = desenharLinha(x-75, y-45, x+75, y-45);
        Line linha2 = desenharLinha(x-75, y-45, x-75, y+45);
        Line linha3 = desenharLinha(x+75, y-45, x+75, y+45);
        Line linha4 = desenharLinha(x, y-95, x, y+85);
        Line linha5 = desenharLinha(x-75, y-45, x+75, y+45);
        Line linha6 = desenharLinha(x+75, y-45, x-75, y+45);
        Line linha7 = desenharLinha(x-50, y-5, x+50, y-5);
        Line linha8 = desenharLinha(x-75, y-45, x, y+85);
        Line linha9 = desenharLinha(x, y+85, x+75, y-45);
        Line linha10 = desenharLinha(x-75, y+45, x, y-95);
        Line linha11 = desenharLinha(x+75, y+45, x, y-95);
        Line linha12 = desenharLinha(x-75, y-45, x, y-95);
        Line linha13 = desenharLinha(x+75, y-45, x, y-95);
        Line linha14 = desenharLinha(x-75, y+45, x, y+85);
        Line linha15 = desenharLinha(x+75, y+45, x, y+85);


        Group root = new Group(
                circulo0, circulo1, circulo2, circulo3, circulo4, circulo5, circulo6,
                linha0, linha1, linha2, linha3, linha4, linha5, linha6, linha7, linha8,
                linha9, linha10, linha11, linha12, linha13, linha14, linha15
        );
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Trabalho CGPI");
        stage.setScene(scene);
        stage.show();
    }

    public Circle desenharCirculo(int x, int y)
    {
        Circle circle = new Circle(x,y,50);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.GREEN);

        return circle;
    }

    public Line desenharLinha(int x0, int y0, int x1, int y1)
    {
        Line line = new Line();
        line.setStroke(Color.RED);
        line.setStartX(x0);
        line.setStartY(y0);
        line.setEndX(x1);
        line.setEndY(y1);

        return line;
    }

    public static void main(String args[]){
        launch(args);
    }

}