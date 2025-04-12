import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import java.util.Calendar;

public class Clock extends Application {
    private Line hourHand;
    private Line minuteHand;
    private Line secondHand;
    private Text timeText;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane(); //порядок методов
        drawTrapezoid(pane);
        drawTrapezoidtwo(pane);
        drawWhiteOval(pane);
        drawClockFace(pane);
        drawHands(pane);

        Scene scene = new Scene(pane, 500, 600); //окно приложения или дословно "панель"
        primaryStage.setTitle("Часы");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {//синхрон времени на часах с реальным временем
            @Override
            public void handle(long now) {
                updateClock();
            }
        };
        timer.start();
    }

    private void drawTrapezoid(Pane pane) {
        Polygon trapezoid = new Polygon(); //черчу фигуру часов с помощью фигуры трапеция
        trapezoid.getPoints().addAll(
                5.0, 450.0, // Левый нижний угол
                480.0, 450.0, // Правый нижний угол
                395.0, 50.0, // Правый верхний угол
                100.0, 50.0  // Левый верхний угол
        );
        trapezoid.setFill(Color.LIGHTBLUE); // цвет трапеции
        pane.getChildren().add(trapezoid); //добавляем трапецию на панель
    }
    private void drawTrapezoidtwo(Pane pane) {
        Polygon trapezoid = new Polygon(); //ещё одна трапеция для ножек часов
        trapezoid.getPoints().addAll(
                60.0, 530.0, // Левый нижний угол
                425.0, 530.0, // Правый нижний угол
                480.0, 450.0, // Правый верхний угол
                5.0, 450.0  // Левый верхний угол
        );
        trapezoid.setFill(Color.rgb (95, 158, 160)); // Цвет трапеции
        pane.getChildren().add(trapezoid); // Добавляем трапецию на панель
    }
    private void drawClockFace(Pane pane) {
        // Рисуем круг часов и деления
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30);
            double x = 250 + 150 * Math.cos(angle - Math.PI / 2);
            double y = 250 + 150 * Math.sin(angle - Math.PI / 2);
            Text hourMark = new Text(x - 10, y + 10, String.valueOf(i));
            pane.getChildren().add(hourMark);
        }
    }
    private void drawWhiteOval(Pane pane) {
        //это овал для обрамления ножек часов, он должен сливаться с фоном
        //по другому я не знаю как нечертить эту фигуру
        Ellipse oval = new Ellipse(120, 40); // Полуоси: 50 по x и 30 по y
        oval.setFill(Color.WHITE); //цвет заливки
        //позиция овала
        oval.setCenterX(250); // Центр по оси X
        oval.setCenterY(530); // Центр по оси Y

        // Добавляем овал в контейнер
        pane.getChildren().add(oval);
    }
    private void drawHands(Pane pane) { //стрелки часов, их координаты и цвет
        hourHand = new Line(250, 250, 250, 150);
        hourHand.setStrokeWidth(6);
        hourHand.setStroke(Color.BLACK); //цвет

        minuteHand = new Line(250, 250, 250, 100);
        minuteHand.setStrokeWidth(4);
        minuteHand.setStroke(Color.BLACK);

        secondHand = new Line(250, 250, 250, 80);
        secondHand.setStrokeWidth(2);
        secondHand.setStroke(Color.RED); //секундная стрелка

        pane.getChildren().addAll(hourHand, minuteHand, secondHand);
        timeText = new Text(225, 70, "");
        pane.getChildren().add(timeText); //отображение времени

    }
    private void updateClock() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        // обновляю угол стрелок
        double hourAngle = (hour % 12 + minute / 60.0) * 30;
        double minuteAngle = (minute + second / 60.0) * 6;
        double secondAngle = second * 6;

        //новые позиции стрелок
        hourHand.setEndX(250 + 80 * Math.cos(Math.toRadians(hourAngle - 90)));
        hourHand.setEndY(250 + 80 * Math.sin(Math.toRadians(hourAngle - 90)));

        minuteHand.setEndX(250 + 120 * Math.cos(Math.toRadians(minuteAngle - 90)));
        minuteHand.setEndY(250 + 120 * Math.sin(Math.toRadians(minuteAngle - 90)));

        secondHand.setEndX(250 + 140 * Math.cos(Math.toRadians(secondAngle - 90)));
        secondHand.setEndY(250 + 140 * Math.sin(Math.toRadians(secondAngle - 90)));

        //текст времени
        timeText.setText(String.format("%02d:%02d:%02d", hour, minute, second));
    }
}
