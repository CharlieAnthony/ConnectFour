package src;

import src.game.Game;
import src.gui.GUI;
import src.gui.CLI;
import src.gui.GameInterface;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        GameInterface gameInterface;

        try {
            if (Objects.equals(args[0], "0")) {
                gameInterface = new CLI(game);
            } else if(Objects.equals(args[0], "1")){
                gameInterface = new GUI(game);
            } else{
                throw new ArrayIndexOutOfBoundsException();
            }

            gameInterface.startGame();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter valid arguments");
        }
    }

}
