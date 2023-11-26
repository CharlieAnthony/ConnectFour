package src.gui;

import src.game.Game;

import java.util.Scanner;

public class CLI implements GameInterface{
        private Game game;
        private int gameType;

        public CLI() {
            this.game = new Game();
        }

        public void displayWin(int winState) {
            // 0 == draw | 1 == player 1 wins | 2 == player 2 wins
            if (gameType == 1){
                if (winState == 1) {
                    System.out.println("White wins!");
                } else if (winState == 2) {
                    System.out.println("Black wins!");
                } else {
                    System.out.println("It's a draw!");
                    System.out.println("winState = " + winState);
                }
            } else if (gameType == 2) {
                if (winState == 1) {
                    System.out.println("You win!");
                } else if (winState == 2) {
                    System.out.println("You lose!");
                } else {
                    System.out.println("It's a draw!");
                }
            }
        }

        public void startGame() {
            System.out.println(
                    "Welcome to Connect Four!\n" +
                    "------------------------\n" +
                    "Select one of the following options:\n" +
                    "(1) Human vs Human game\n" +
                    "(2) Human vs AI game\n\n" +
                    "Enter your choice:");
            Scanner scanner = new Scanner(System.in);
            this.gameType = scanner.nextInt();
            if (this.gameType == 1 || this.gameType == 2) {
                this.game.startGameLoop(this.gameType, this);
            } else {
                System.out.println("No valid choice selected.");
            }
        }

        public void displayGame() {
            int[][] board = this.game.getBoard();
            String[][] stringBoard= new String[6][7];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (board[i][j] == 1) {
                        stringBoard[i][j] = "●";
                    } else if (board[i][j] == 2) {
                        stringBoard[i][j] = "○";
                    } else {
                        stringBoard[i][j] = " ";
                    }
                }
            }
            System.out.println(
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[0][0] + " | "+ stringBoard[0][1] +" | "+ stringBoard[0][2] +" | "+ stringBoard[0][3] +" | "+ stringBoard[0][4] +" | "+ stringBoard[0][5] +" | "+ stringBoard[0][6] +" |\n" + // row 1
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[1][0] + " | "+ stringBoard[1][1] +" | "+ stringBoard[1][2] +" | "+ stringBoard[1][3] +" | "+ stringBoard[1][4] +" | "+ stringBoard[1][5] +" | "+ stringBoard[1][6] +" |\n" + // row 2
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[2][0] + " | "+ stringBoard[2][1] +" | "+ stringBoard[2][2] +" | "+ stringBoard[2][3] +" | "+ stringBoard[2][4] +" | "+ stringBoard[2][5] +" | "+ stringBoard[2][6] +" |\n" + // row 3
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[3][0] + " | "+ stringBoard[3][1] +" | "+ stringBoard[3][2] +" | "+ stringBoard[3][3] +" | "+ stringBoard[3][4] +" | "+ stringBoard[3][5] +" | "+ stringBoard[3][6] +" |\n" + // row 4
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[4][0] + " | "+ stringBoard[4][1] +" | "+ stringBoard[4][2] +" | "+ stringBoard[4][3] +" | "+ stringBoard[4][4] +" | "+ stringBoard[4][5] +" | "+ stringBoard[4][6] +" |\n" + // row 5
                    "  +---+---+---+---+---+---+---+\n" +
                    "  | " + stringBoard[5][0] + " | "+ stringBoard[5][1] +" | "+ stringBoard[5][2] +" | "+ stringBoard[5][3] +" | "+ stringBoard[5][4] +" | "+ stringBoard[5][5] +" | "+ stringBoard[5][6] +" |\n" + // row 6
                    "  +---+---+---+---+---+---+---+\n" +
                    "    0   1   2   3   4   5   6 \n"
                    );
        }

        public void makeMove(int player) {
            if(this.gameType == 1)
            {
                if (player == 1) {
                    System.out.println("It's white's turn!\n");
                } else {
                    System.out.println("It's black's turn!\n");
                }
            }
            System.out.println("Please select a column: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                int column = scanner.nextInt();
                if (this.game.makeMove(column, player)) {
                    break;
                } else {
                    System.out.println("Invalid move. Please select a column: ");
                }
            }
        }

}
