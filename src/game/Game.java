package game;

import src.gui.GameInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private int[][] board;
    private int gameType; // 1 == Player vs Player | 2 == Player vs Computer
    private GameInterface gameInterface;
    private ArrayList<int[]> successorEvaluations;
    public int currentTurn;

    // Constructor
    public Game() {
        board = new int[ROWS][COLUMNS];
    }


    public void startGameLoop(int gameType, GameInterface gameInterface){
        this.gameInterface = gameInterface;
        this.gameType = gameType;
        this.currentTurn = 1;
        int winState = -1;
        while(validMoves()){
            // if player vs player
            if(gameType == 1){
                gameInterface.displayGame();
                gameInterface.makeMove(this.currentTurn);
                winState = this.checkWin();
                if (winState != -1) {
                    gameInterface.displayGame();
                    gameInterface.displayWin(winState);
                    return;
                }
            }
            // player vs ai
            else if(gameType == 2){
                if(this.currentTurn == 1){
                    gameInterface.displayGame();
                    gameInterface.makeMove(this.currentTurn);
                }else if(this.currentTurn == 2){
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
            this.currentTurn = this.currentTurn == 1 ? 2 : 1;

        }
        gameInterface.displayGame();
        gameInterface.displayWin(0);
    }

    public void guiTurn(int col, GameInterface gi){
        if(makeMove(col, currentTurn)){
            gi.displayGame();
            this.currentTurn = this.currentTurn == 1 ? 2 : 1;
            if(this.currentTurn == 1 && gameType == 1) gi.displayMessage(4);
            else if (this.currentTurn == 2 && gameType == 1) gi.displayMessage(5);
        }else{
            gi.displayMessage(3);
        }
        if(checkWin() != -1){
            gi.displayWin(checkWin());
        }
        if(gameType == 2 && currentTurn == 2){
            gi.displayMessage(6);
            this.startEvaluations();
            int c = this.getBestMove();
            this.makeMove(c, 2);
            gi.displayGame();
            this.currentTurn = 1;
            gi.displayMessage(4);
        }
        if(checkWin() != -1){
            gi.displayWin(checkWin());
        }
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
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

    public int getBestMove() {
        int max = -10000;
        List<Integer> bestMoves = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < successorEvaluations.size(); i++) {
            if (max < successorEvaluations.get(i)[0]) {
                max = successorEvaluations.get(i)[0];
                bestMoves.clear(); // Clear the list as we found a better score
                bestMoves.add(i);
            } else if (max == successorEvaluations.get(i)[0]) {
                bestMoves.add(i); // Add index of this move as it has the best score so far
            }
        }

        // Select a random move from the best moves
        int randomBestIndex = bestMoves.get(rand.nextInt(bestMoves.size()));
        return successorEvaluations.get(randomBestIndex)[1];
    }

    public void startEvaluations(){
        successorEvaluations = new ArrayList<>();
        minimax(0, 2);
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
        if(w==1)return -1000; // Human win
        if(w==2)return 1000; // AI win
        if(w==0 || depth >= 8)return this.evaluateBoard(board); // Draw
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
                successorEvaluations.add(new int[]{currentScore, p});
            }
            this.removeMove(p);
        }
        return bestScore;
    }

    private int evaluateBoard(int[][] board){
        int score = 0;

        // Horizontal check
        score += evalutateHorizontal(board);
        // Vertical check
        score += evalutateVertical(board);
        // Left Diagonal check
        score += evalutateLeftDiagonal(board);
        // Right Diagonal check
        score += evalutateRightDiagonal(board);

        return score;
    }

    private int evalutateHorizontal(int[][] board){
        // 3 in a row = 100 points
        // 2 in a row = 10 points
        int score = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i][j+1] &&
                        board[i][j] == board[i][j+2]){
                    if(board[i][j] == 1){
                        score -= 100;
                    }else{
                        score += 100;
                    }
                }
            }
        }
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS - 2; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i][j+1]){
                    if(board[i][j] == 1){
                        score -= 10;
                    }else{
                        score += 10;
                    }
                }
            }
        }
        return score;
    }

    private int evalutateVertical(int[][] board){
        int score = 0;
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j] &&
                        board[i][j] == board[i+2][j]){
                    if(board[i][j] == 1){
                        score -= 100;
                    }else{
                        score += 100;
                    }
                }
            }
        }
        for(int i = 0; i < ROWS - 2; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j]){
                    if(board[i][j] == 1){
                        score -= 10;
                    }else{
                        score += 10;
                    }
                }
            }
        }
        return score;
    }

    private int evalutateLeftDiagonal(int[][] board){
        int score = 0;
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 0; j < COLUMNS - 3; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j+1] &&
                        board[i][j] == board[i+2][j+2]){
                    if(board[i][j] == 1){
                        score -= 100;
                    }else{
                        score += 100;
                    }
                }
            }
        }
        for(int i = 0; i < ROWS - 2; i++){
            for(int j = 0; j < COLUMNS - 2; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j+1]){
                    if(board[i][j] == 1){
                        score -= 10;
                    }else{
                        score += 10;
                    }
                }
            }
        }
        return score;
    }

    private int evalutateRightDiagonal(int[][] board){
        int score = 0;
        for(int i = 0; i < ROWS - 3; i++){
            for(int j = 3; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j-1] &&
                        board[i][j] == board[i+2][j-2]){
                    if(board[i][j] == 1){
                        score -= 100;
                    }else{
                        score += 100;
                    }
                }
            }
        }
        for(int i = 0; i < ROWS - 2; i++){
            for(int j = 3; j < COLUMNS; j++){
                if(     board[i][j] != 0 &&
                        board[i][j] == board[i+1][j-1]){
                    if(board[i][j] == 1){
                        score -= 10;
                    }else{
                        score += 10;
                    }
                }
            }
        }
        return score;
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
