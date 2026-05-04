package uk.ac.soton.comp1206;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameBoard {
    private int size;
    private String[][] board;
    private StringProperty currentPlayer = new SimpleStringProperty("X");

    public GameBoard(int size){
        this.size = size;//determine the size of the grid
        this.board = new String[size][size];//create 2D array

        for (int i = 0; i < this.size; i++){
            for (int j = 0; j < this.size; j++){
                board[i][j] = "";
            }
        }
        currentPlayer.set("X");
    }

    public void resetBoard(Button[][] buttons, Label statusLabel){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setDisable(false);
            }
        }

        for (int i = 0; i < this.size; i++){
            for (int j = 0; j < this.size; j++){
                board[i][j] = "";
                buttons[i][j].setText("");
            }
        }
        currentPlayer.set("X");
    }

    public void switchPlayer(){
        if (currentPlayer.get().equals("X")){
            currentPlayer.set("O");
        } else {
            currentPlayer.set("X");
        }
    }

    public boolean makeMove(Button button, int row, int col){
        if (board[row][col].equals("")){
            board[row][col] = currentPlayer.get();//Updating the grid
            button.setText(currentPlayer.get());
            return true;
        } else {
            System.out.println("This block is occupied");
            return false;
        }
    }

    public String getCurrentPlayer(){
        return currentPlayer.get();
    }

    public StringProperty currentPlayerProperty() {
        return currentPlayer;
    }

    public boolean checkWinner(){
        boolean status = false;

        for (int row = 0; row < this.size; row++){
            String first = board[row][0];
            if (!first.equals("")){
                status = true;
                for (int col = 0; col < this.size; col++){
                    if (!board[row][col].equals(first)){
                        status = false;
                        break;
                    }
                }
                if (status){
                    System.out.println(first);
                    return status;
                }
            }
        }

        for (int col = 0; col < this.size; col++){
            String first = board[0][col];
            if (!first.equals("")){
                status = true;
                for (int row = 0; row < this.size; row++){
                    if (!board[row][col].equals(first)){
                        status = false;
                        break;
                    }
                }

                if (status){
                    System.out.println(first);
                    return status;
                }
            }
        }

        String firstdiag = board[0][0];
        if (!firstdiag.equals("")){
            status = true;
            for (int n = 0; n < this.size; n++){
                if (!board[n][n].equals(firstdiag)){
                    status = false;
                    break;
                }
            }

            if (status){
                System.out.println(firstdiag);
                return status;
            }
        }

        String seconddiag = board[0][this.size-1];
        if (!seconddiag.equals("")){
            status = true;
            for (int n = 0; n < this.size; n++){
                if (!board[n][this.size-1-n].equals(seconddiag)){
                    status = false;
                    break;
                }
            }

            if (status){
                System.out.println(seconddiag);
                return status;
            }
        }

        System.out.println("");
        return false;
    }

    public boolean checkdraw(){
        for (int row = 0; row < this.size; row++){
            for (int col = 0; col < this.size; col++){
                if (board[row][col] == ""){
                    return false;
                }
            }
        }

        return true;
    }
}
