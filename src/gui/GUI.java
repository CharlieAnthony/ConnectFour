package src.gui;

import src.game.Game;

public class GUI implements GameInterface{

        public GUI(Game game) {
            System.out.println("GUI constructor");
        }

        public void startGame() {
            System.out.println("GUI startGame");
        }

        public void displayGame() {
            System.out.println("GUI displayGame");
        }

        public void makeMove(int player) {
            System.out.println("GUI makeMove");
        }

        public void displayWin(int winState) {
            System.out.println("GUI displayWin");
        }
}
