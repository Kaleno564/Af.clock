import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalTime;

public class AnalogClock extends Application {
    private Line hourHand;
    private Line minuteHand;
    private Line secondHand;
    private Text timeText;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        drawTrapezoid(pane);
        drawClockFace(pane);
        drawHands(pane);

        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setTitle("Аналоговые часы");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawTrapezoid(Pane pane) {
        Polygon trapezoid = new Polygon();
        trapezoid.getPoints().addAll(
                100.0, 370.0, // Левый нижний угол
                400.0, 370.0, // Правый нижний угол
                350.0, 100.0, // Правый верхний угол
                150.0, 100.0  // Левый верхний угол
        );
        trapezoid.setFill(Color.LIGHTBLUE); // Цвет трапеции
        pane.getChildren().add(trapezoid); // Добавляем трапецию на панель
    }

    private void drawClockFace(Pane pane) {
        // Рисуем круг часов и деления
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30);
            double x = 250 + 100 * Math.cos(angle - Math.PI / 2);
            double y = 250 + 100 * Math.sin(angle - Math.PI / 2);
            Text hourMark = new Text(x - 5, y + 5, String.valueOf(i));
            pane.getChildren().add(hourMark);
        }
    }

    private void drawHands(Pane pane) {
        timeText = new Text(220, 400, "");
        pane.getChildren().add(timeText);

    }
}