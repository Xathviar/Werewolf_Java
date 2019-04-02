package Werewolf;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox hBox = new HBox(10);
        VBox button_VBox = new VBox(10);
        VBox text_VBox = new VBox(10);
        Scene scene = new Scene(hBox, 500, 500);
        final Narrator[] narrator = {null};

        Button start_bot = new Button("Start Bot");
        Button stop_bot = new Button("Stop Bot");
        TextField textField = new TextField("Insert Bot Token here");
        Label status = new Label("Status: ");
        Label oof = new Label("Stopped");
        start_bot.setOnAction(actionEvent -> {
            try {
                narrator[0] = new Narrator(textField.getText());
                oof.setText("Started");

            } catch (LoginException | InterruptedException e) {
                e.printStackTrace();
                oof.setText("Wrong Token!");
            }
        });
        stop_bot.setOnAction(actionEvent -> {
            try {
                if (narrator[0] == null) {
                    narrator[0] = new Narrator(textField.getText());
                    narrator[0].stopDiscord();
                    oof.setText("Stopped");
                } else {
                    narrator[0].stopDiscord();
                    oof.setText("Stopped");
                }
            } catch (InterruptedException | LoginException e) {
                e.printStackTrace();
                oof.setText("Wrong Token!");
            }
        });
        text_VBox.getChildren().addAll(status, oof);
        button_VBox.getChildren().addAll(start_bot, stop_bot);
        hBox.getChildren().addAll(textField, text_VBox, button_VBox);

        stage.setScene(scene);
        stage.show();

    }
}
