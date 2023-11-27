package src.gui;

public interface GameInterface {
    void startGame();
    void displayGame();
    void makeMove(int player);
    void displayWin(int winState);

    void displayMessage(int i);
}
