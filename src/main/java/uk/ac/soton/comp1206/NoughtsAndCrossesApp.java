package uk.ac.soton.comp1206;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NoughtsAndCrossesApp extends Application {
    private Label statusLabel = new Label("Game in progress...");
    private GameBoard gameBoard;
    private Button[][] buttons;
    private Integer size = 3;
    private StringProperty playerXName = new SimpleStringProperty();
    private StringProperty playerOName = new SimpleStringProperty();
    private Scene menuScene;
    private Scene gameScene;
    private IntegerProperty xWins = new SimpleIntegerProperty(0);
    private IntegerProperty oWins = new SimpleIntegerProperty(0);
    private IntegerProperty draws = new SimpleIntegerProperty(0);

    @Override
    public void start(Stage stage) {
        stage.setTitle("Noughts and Crosses");

        stage.setResizable(true);
        stage.setMinWidth(450);
        stage.setMaxWidth(450);
        stage.setMinHeight(500);
        stage.setMaxHeight(800);
        menuScene = createMenuScene(stage);
        stage.setScene(menuScene);
        stage.show();
    }

    private void updateTurnLabel() {
        String current = gameBoard.getCurrentPlayer();

        if (current.equals("X")) {
            statusLabel.setText(playerXName.get() + "'s turn");
        } else {
            statusLabel.setText(playerOName.get() + "'s turn");
        }
    }

    private Scene createMenuScene(Stage stage) {
        VBox menu = new VBox(10);
        menu.setAlignment(Pos.CENTER);

        Label menuLabel = new Label("Tic Tac Toe");
        menuLabel.setStyle("-fx-font-family: Arial; -fx-text-fill: lightyellow;");
        menuLabel.setFont(new Font(40));

        TextField xName = new TextField();
        xName.setPromptText("Player X name");

        xName.setPrefWidth(200);
        xName.setMinWidth(200);
        xName.setMaxWidth(200);

        TextField oName = new TextField();
        oName.setPromptText("Player O name");

        oName.setPrefWidth(200);
        oName.setMinWidth(200);
        oName.setMaxWidth(200);

        TextField sizeField = new TextField("3");

        sizeField.setPrefWidth(100);
        sizeField.setMinWidth(100);
        sizeField.setMaxWidth(100);

        xName.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");
        oName.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");
        sizeField.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");
        startButton.setDisable(true);

        xName.textProperty().addListener((obs, oldV, newV) -> {
            startButton.setDisable(xName.getText().isEmpty() || oName.getText().isEmpty());
        });

        oName.textProperty().addListener((obs, oldV, newV) -> {
            startButton.setDisable(xName.getText().isEmpty() || oName.getText().isEmpty());
        });

        startButton.setOnAction(e -> {
            playerXName.set(xName.getText());
            playerOName.set(oName.getText());

            try {
                size = Integer.parseInt(sizeField.getText());
            } catch (NumberFormatException ex) {
                size = 3;
            }

            setupGameScene(stage);
            stage.setScene(gameScene);
        });

        menu.getChildren().addAll(menuLabel, xName, oName, sizeField, startButton);
        menu.setStyle("-fx-background-color: lightblue");

        return new Scene(menu, 350, 400);
    }

    private void setupGameScene(Stage stage) {

        VBox root = new VBox();

        gameBoard = new GameBoard(size);
        buttons = new Button[size][size];

        GridPane grid = new GridPane();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++){
                Button button = new Button("");
                button.setMinSize(300 / size, 300 / size);

                int finalRow = row;
                int finalCol = col;

                button.setOnAction(e -> handleMove(button, finalRow, finalCol));
                button.setStyle(
                        "-fx-background-color: lightyellow;" +
                                "-fx-text-fill: lightblue;"
                );
                button.setFont(new Font(120/size));

                buttons[row][col] = button;
                grid.add(button, col, row);
            }
        }

        grid.setPadding(new Insets(70));
        grid.setHgap(5);
        grid.setVgap(5);

        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        Button resetbutton = new Button("Reset");
        resetbutton.setPrefSize(70,10);
        resetbutton.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");
        resetbutton.setOnAction(e -> resetButtonMove());

        Button scoreButton = new Button("Scoreboard");
        scoreButton.setOnAction(e -> showScoreboard());
        scoreButton.setStyle("-fx-background-color: lightyellow; -fx-text-fill: black;");

        gameBoard.currentPlayerProperty().addListener((obs, oldVal, newVal) -> {
            updateTurnLabel();
        });

        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(grid);

        root.getChildren().add(resetbutton);
        root.getChildren().add(centerPane);
        root.getChildren().add(statusLabel);
        root.getChildren().add(scoreButton);
        root.setStyle("-fx-background-color: lightblue");
        root.setAlignment(Pos.CENTER);
        stage.setTitle("Noughts and Crosses");
        stage.setResizable(true);
        stage.setMinWidth(450);
        stage.setMaxWidth(450);
        stage.setMinHeight(500);
        stage.setMaxHeight(800);

        gameScene = new Scene(root, 450, 550);

        updateTurnLabel();
    }

    private void showScoreboard() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        Label title = new Label("Scoreboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label xLabel = new Label();
        xLabel.textProperty().bind(
                playerXName.concat(": ").concat(xWins.asString())
        );

        Label oLabel = new Label();
        oLabel.textProperty().bind(
                playerOName.concat(": ").concat(oWins.asString())
        );

        Label dLabel = new Label();
        dLabel.textProperty().bind(
                new SimpleStringProperty("Draws: ").concat(draws.asString())
        );

        box.getChildren().addAll(title, xLabel, oLabel, dLabel);
        box.setStyle("-fx-background-color: lightblue");

        Stage scoreStage = new Stage();
        scoreStage.setTitle("Scoreboard");
        scoreStage.setScene(new Scene(box, 250, 180));
        scoreStage.show();
    }

    private void resetButtonMove(){
        gameBoard.resetBoard(buttons, statusLabel);
    }

    private void handleMove(Button button, int row, int col){
        if (gameBoard.makeMove(button, row, col)){
            String winner = null;

            if (gameBoard.checkWinner()){
                winner = gameBoard.getCurrentPlayer();
                if (winner.equals("X")){
                    xWins.set(xWins.get() + 1);
                } else if (winner.equals("O")){
                    oWins.set(oWins.get() + 1);
                }
                disableBoard();
                showResultPopup(winner);
                return;
            } else if (gameBoard.checkdraw()){
                draws.set(draws.get() + 1);
                disableBoard();
                showResultPopup("");
                return;
            }
            gameBoard.switchPlayer();
        }
    }

    private void showResultPopup(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (winner.equals("")) {
            alert.setTitle("Game Over");
            alert.setHeaderText("Draw!");
        } else {
            String name = winner.equals("X") ? playerXName.get() : playerOName.get();
            alert.setTitle("Game Over");
            alert.setHeaderText(name + " wins!");
        }

        alert.setContentText("Click OK to start a new game...");

        alert.showAndWait(); //waits until user clicks OK

        gameBoard.resetBoard(buttons, statusLabel);
    }

    public void disableBoard(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    public static void main(String[] args){
        launch();
    }
}
