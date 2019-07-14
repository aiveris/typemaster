package typemaster;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import typemaster.Round;

import java.util.*;
public class Main extends Application {
    private static int MAX_TIME_IN_SECONDS = 10;
    private Timer timer;
    private Round round;
    private TilePane wordsPane = new TilePane(Orientation.HORIZONTAL);
    private Label currentWordLabel = new Label();
    private Button restartButton = new Button("Restart");
    private Label countdownLabel = new Label();
    private Label wordsPerMinuteLabel = new Label("Words per minute: -");
    @Override
    public void start(Stage stage) {
        reset();
        restartButton.setOnMouseClicked(e -> {
            if (round.getCurrentState() == Round.State.RUNNING) {
                stopRound();
            }
            reset();
        });
        TilePane root = new TilePane(Orientation.VERTICAL);
        root.getChildren().addAll(
                wordsPane,
                currentWordLabel,
                restartButton,
                countdownLabel,
                wordsPerMinuteLabel);
        Scene scene = new Scene(root, 500, 500);
        scene.setOnKeyTyped(e -> {
            if (round.getCurrentState() == Round.State.READY) {
                round.start();
                colorCurrentWord();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        long elapsedTimeMillis = round.getElapsedTimeInMillis();
                        long elapsedTimeInSeconds = elapsedTimeMillis / 1000;
                        long secondsRemaining = MAX_TIME_IN_SECONDS - elapsedTimeInSeconds;
                        if (secondsRemaining < 0) {
                            Platform.runLater(() -> {
                                countdownLabel.setText("It's over");
                                wordsPerMinuteLabel.setText(
                                        "Words per minute: " + round.getWordsPerMinute());
                            });
                            stopRound();
                        } else {
                            Platform.runLater(() ->
                                    countdownLabel.setText("Countdown: " + secondsRemaining + " seconds"));
                        }
                    }
                }, 0, 1000);
            }
            if (round.getCurrentState() == Round.State.RUNNING) {
                String characterJustTyped = e.getCharacter();
                if (round.getCurrentCharacterIndex() == round.getCurrentWord().length()) {
                    if (characterJustTyped.equals(" ")) {
                        System.out.println("A word was correctly typed");
                        round.setCurrentWordIndex(round.getCurrentWordIndex() + 1);
                        round.setCurrentCharacterIndex(0);
                        if (round.getCurrentWordIndex() == round.getWords().size()) {
                            currentWordLabel.setText("You're done!");
                            stopRound();
                        } else {
                            currentWordLabel.setText(
                                    "Current word: " + round.getCurrentWord());
                            colorCurrentWord();
                        }
                        wordsPerMinuteLabel.setText(
                                "Words per minute: " + round.getWordsPerMinute());
                        Label previousWordLabel = (Label) wordsPane.getChildren().get(round.getCurrentWordIndex() - 1);
                        previousWordLabel.setTextFill(Color.GRAY);
                    }
                } else {
                    String expectedCharacter = round.getNextExpectedCharacter();
                    if (characterJustTyped.equals(expectedCharacter)) {
                        round.setCurrentCharacterIndex(round.getCurrentCharacterIndex() + 1);
                        System.out.println("Good character entered");
                    } else {
                        System.out.println("Bad character");
                    }
                }
            }
        });
        stage.setTitle("Typing Master");
        stage.setScene(scene);
        stage.show();
    }
    private void stopRound() {
        round.setCurrentState(Round.State.STOPPED);
        timer.cancel();
    }
    private void reset() {
        round = new Round();
        currentWordLabel.setText("Current word: " + round.getWords().get(0));
        wordsPane.getChildren().clear();
        for (String word : round.getWords()) {
            wordsPane.getChildren().add(new Label(word));
        }
        countdownLabel.setText("Countdown: --:--");
    }
    private void colorCurrentWord() {
        Label currentWordLabel = (Label) wordsPane.getChildren().get(round.getCurrentWordIndex());
        currentWordLabel.setTextFill(Color.BLUE);
    }
    public static void main(String[] args) {
        launch(args);
    }
}