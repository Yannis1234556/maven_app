package uk.ac.soton.comp1206;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ResetButtonClickHandler implements EventHandler<ActionEvent>{
   private Label statusLabel;
   private String[][] board;
   private Button[][] buttons;

   public ResetButtonClickHandler(Button[][] buttons, Label statusLabel, String[][] board){
       this.statusLabel = statusLabel;
       this.board = board;
       this.buttons = buttons;
   }

    @Override
    public void handle(ActionEvent actionEvent){
        statusLabel.setText("Game in progress...");

        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                board[row][col] = (null);
                buttons[row][col].setText("");
            }
        }
    }
}
