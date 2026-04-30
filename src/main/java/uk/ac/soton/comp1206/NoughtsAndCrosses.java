package uk.ac.soton.comp1206;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class NoughtsAndCrosses extends Application{
    private Label statusLabel = new Label("Game in progress...");
    private String[][] board = new String[3][3];
    private Button[][] buttons = new Button[3][3];
    private Integer xwin = 0;
    private Integer owin = 0;
    private Integer draw = 0;
    private Label countLabel = new Label("X wins: " + xwin + " O wins: " + owin + " Draw: " + draw);

    @Override
    public void start(Stage stage){
        VBox root = new VBox();
        GridPane grid = new GridPane();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++){
                Button button = new Button();
                button.setPrefSize(100,100);
                button.setStyle("-fx-background-color: lightyellow; -fx-text-fill: lightblue;");

                buttons[row][col] = button;
                button.setOnAction(new ButtonClickHandler(xwin, owin, draw, countLabel, board, statusLabel, col, row));

                grid.add(button, col, row);
            }
        }
        grid.setPadding(new Insets(20));
        grid.setHgap(5);
        grid.setVgap(5);

        Button resetbutton = new Button("Reset");
        resetbutton.setPrefSize(70,10);
        resetbutton.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");
        resetbutton.setOnAction(new ResetButtonClickHandler(buttons, statusLabel, board));

        root.getChildren().add(resetbutton);
        root.getChildren().add(grid);
        root.getChildren().add(statusLabel);
        root.getChildren().add(countLabel);
        root.setStyle("-fx-background-color: lightblue");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 400);

        stage.setTitle("Noughts and Crosses");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
