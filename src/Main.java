package src;

import src.gui.CLI;
import src.gui.GUI;
import src.gui.GameInterface;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        GameInterface gameInterface;

        try {
            if (Objects.equals(args[0], "0")) {
                gameInterface = new CLI();
            } else if(Objects.equals(args[0], "1")){
                gameInterface = new GUI();
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
