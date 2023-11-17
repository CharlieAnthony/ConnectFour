package src.game;

import src.gui.GameInterface;

public class Game {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private int[][] board;
    private int gameType; // 1 == Player vs Player | 2 == Player vs Computer
    private GameInterface gameInterface;

    // Constructor
    public Game() {
        board = new int[ROWS][COLUMNS];
    }

    public void startGameLoop(int gameType, GameInterface gameInterface){
        this.gameInterface = gameInterface;
        this.gameType = gameType;
        int currentTurn = 1;
        int winState = -1;
        while(validMoves()){
            if(gameType == 1){
                gameInterface.displayGame();
                gameInterface.makeMove(currentTurn);
                winState = this.checkWin();
                if (winState != -1) {
                    gameInterface.displayGame();
                    gameInterface.displayWin(winState);
                    return;
                }
            }
            System.out.println("next turn");
            currentTurn = currentTurn == 1 ? 2 : 1;
        }
        gameInterface.displayGame();
        gameInterface.displayWin(0);
    }

    private boolean validMoves(){
        for(int i = 0; i < COLUMNS; i++){
            if(board[0][i] == 0){
                return true;
            }
        }
        return false;
    }

    private int checkWin(){
        // Horizontal check
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i][j+1] &&
                        board[i][j] == board[i][j+2] &&
                        board[i][j] == board[i][j+3]){
                    return board[i][j];
                }
            }
        }
        // Vertical check
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j] &&
                        board[i][j] == board[i+2][j] &&
                        board[i][j] == board[i+3][j]){
                    return board[i][j];
                }
            }
        }
        // Left Diagonal check
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j+1] &&
                        board[i][j] == board[i+2][j+2] &&
                        board[i][j] == board[i+3][j+3]){
                    return board[i][j];
                }
            }
        }
        // Right Diagonal check
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 3; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j-1] &&
                        board[i][j] == board[i+2][j-2] &&
                        board[i][j] == board[i+3][j-3]){
                    return board[i][j];
                }
            }
        }
        return -1;
    }

    public boolean makeMove(int column, int player) {
        // check column is valid
        if (column < 0 || column > 6) {
            return false;
        }
        // iterate through rows in col
        for (int i = ROWS - 1; i >= 0; i--) {
            // if row is empty, insert piece
            if (board[i][column] == 0) {
                board[i][column] = player;
                return true;
            }
        }
        // if piece is never inserted, return false
        return false;
    }

    public int[][] getBoard() {
        return board;
    }
}
