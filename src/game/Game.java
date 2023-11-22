package src.game;

import src.gui.GameInterface;
import java.util.*;

public class Game {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private int[][] board;
    private int gameType; // 1 == Player vs Player | 2 == Player vs Computer
    private GameInterface gameInterface;
    private ArrayList<int[]> successorEvaluations;

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
            // if player vs player
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
            // player vs ai
            else if(gameType == 2){
                if(currentTurn == 1){
                    gameInterface.displayGame();
                    gameInterface.makeMove(currentTurn);
                }else if(currentTurn == 2){
                    this.startEvaluations();
                    int c = this.getBestMove();
                    this.makeMove(c, 2);
                }
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

    public boolean removeMove(int column){
        // check column is valid
        if (column < 0 || column > 6) {
            return false;
        }
        // iterate through rows in col
        for (int i = 0; i < ROWS; i++) {
            // if row is empty, insert piece
            if (board[i][column] != 0) {
                board[i][column] = 0;
                return true;
            }
        }
        // if piece is never inserted, return false
        return false;
    }

    public int getBestMove(){
        int max = -10;
        int best = 0;
        for(int i=0; i < successorEvaluations.size(); i++){
            System.out.println("Col: " + successorEvaluations.get(i)[1] + " - Score: " + successorEvaluations.get(i)[0]);
            if(max < successorEvaluations.get(i)[0]){
                max = successorEvaluations.get(i)[0];
                best = i;
            }
        }
        return successorEvaluations.get(best)[1];
    }

    public void startEvaluations(){
        successorEvaluations = new ArrayList<>();
        minimax(0, 1);
    }

    public int minimax(int depth, int player){
        int bestScore = 0;
        if(player==1){
            bestScore = 1000;
        }else{
            bestScore = -1000;
        }
        List<Integer> positionsAvailable = getAvailableStates();
        int w = checkWin();
        if(w==1)return -1; // Human win
        if(w==2)return 1; // AI win
        if(w==0 || depth >= 5)return 0; // Draw
        int currentScore = 0;
        for(int i=0; i<positionsAvailable.size(); i++){
            int p = positionsAvailable.get(i);
            if(player==1){
                makeMove(p, 1);
                currentScore=minimax(depth+1, 2);
                if(currentScore < bestScore){
                    bestScore = currentScore;
                }
            }else if(player==2){
                makeMove(p,2);
                currentScore = minimax(depth+1, 1);
                if(currentScore > bestScore){
                    bestScore = currentScore;
                }
            }
            if(depth==0){
                System.out.println("Col: "+ p + "  - CurrentScore: " + currentScore);
                successorEvaluations.add(new int[]{currentScore, p});
            }
            this.removeMove(p);
        }
        return bestScore;
    }

    public List<Integer> getAvailableStates(){
        ArrayList<Integer> pos = new ArrayList<>();
        for(int i=0; i<COLUMNS; i++){
            if(board[0][i] == 0){
                pos.add(i);
            }
        }
        return pos;
    }

    public int[][] getBoard() {
        return board;
    }
}
