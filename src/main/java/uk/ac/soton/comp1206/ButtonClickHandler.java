package uk.ac.soton.comp1206;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ButtonClickHandler implements EventHandler<ActionEvent>{
    private static String turn = "X";
    private Integer row;
    private Integer col;
    private Label statusLabel;
    private String[][] board;
    private Integer xwin = 0;
    private Integer owin = 0;
    private Integer draw = 0;
    private Label countLabel;

    public ButtonClickHandler(Integer xwin, Integer owin, Integer draw, Label countLabel, String[][] board, Label statusLabel, Integer col, Integer row){
        this.row = row;
        this.col = col;
        this.statusLabel = statusLabel;
        this.board = board;
        this.xwin = xwin;
        this.owin = owin;
        this.draw = draw;
        this.countLabel = countLabel;
    }

    public boolean checkWinner(){
        for (int row = 0; row < 3; row++){
            if (board[row][0] != null &&
                    board[row][0].equals(board[row][1]) &&
                    board[row][0].equals(board[row][2])){
                return true;
            }
        }

        for (int col = 0; col < 3; col++){
            if (board[0][col] != null &&
                    board[0][col].equals(board[1][col]) &&
                    board[0][col].equals(board[2][col])){
                return true;
            }
        }

        if (board[0][0] != null &&
                board[0][0].equals(board[1][1]) &&
                board[0][0].equals(board[2][2])){
            return true;
        }

        if (board[0][2] != null &&
                board[0][2].equals(board[1][1]) &&
                board[0][2].equals(board[2][0])){
            return true;
        }

        return false;
    }

    public boolean checkdraw(){
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                if (board[row][col] == null){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void handle(ActionEvent actionEvent){
        Button button = (Button) actionEvent.getSource();

        if (board[row][col] != null || checkWinner()){
            return;
        } else {
            button.setText(turn);
            board[row][col] = turn;
        }

        if (checkWinner() || checkdraw()){
            if (checkWinner()){
                statusLabel.setText(turn + " Wins!");
                if (turn.equals("X")){
                    this.xwin++;
                } else if (turn.equals("O")){
                    this.owin++;
                }
            } else if (checkdraw()){
                statusLabel.setText("Draw!");
                this.draw++;
            }

            countLabel.setText("X wins: " + this.xwin + " O wins: " + this.owin + " Draw: " + this.draw);
        }

        if (turn.equals("X")){
            turn = "O";
        } else if (turn.equals("O")){
            turn = "X";
        }
    }
}
