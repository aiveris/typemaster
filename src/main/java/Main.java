import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.naming.TimeLimitExceededException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application{
    boolean running = false;
    Timer timer;
    int startTime;


    Label word1 = new Label("page");
    Label word2 = new Label("true");
    Label word3 = new Label("center");
    Label nameLabel = new Label("Enter text here:");
    TextField nameTextField = new TextField();
    Button controlButton = new Button("Restar");
    Label wpmLabel = new Label("Words per minute:");

    @Override
    public void start(Stage stage) {

        TilePane rootPane = new TilePane(Orientation.HORIZONTAL);
        rootPane.getChildren().addAll(word1, word2, word3);
        rootPane.getChildren().addAll(nameLabel);
        rootPane.getChildren().addAll(nameTextField);
        rootPane.getChildren().addAll(controlButton);
        rootPane.getChildren().addAll(wpmLabel);

        stage.setTitle("TypingMaster");
        stage.setScene(new Scene(rootPane, 500, 500, Color.BLACK));
        stage.show();
    }
}
