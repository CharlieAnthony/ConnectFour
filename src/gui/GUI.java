package src.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import src.game.Game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GUI extends Application implements GameInterface {

    private Game game;
    private int gameType;
    private GridPane gameGrid;
    private Stage stage;
    private int selectedCol;
    private BooleanProperty moveMade = new SimpleBooleanProperty(false);

    public GUI() {
        this.game = new Game();
        this.selectedCol = -1;
    }

    public void startGame() {
        // create game and display gui
        launch();
    }

    public void displayGame() {
        int[][] board = this.game.getBoard();
        for(Circle circle : this.gameGrid.getChildren().toArray(new Circle[0])) {
            int col = GridPane.getColumnIndex(circle);
            int row = GridPane.getRowIndex(circle);
            if (board[row][col] == 1) {
                circle.setFill(Color.YELLOW);
            } else if (board[row][col] == 2) {
                circle.setFill(Color.RED);
            } else {
                circle.setFill(Color.LIGHTGRAY);
            }
        }
    }

    public void makeMove(int player) {
        // just existing because it can <3
    }

    public void displayWin(int winState) {
        // TODO: display win state
        // also need to add a dialog box of some description
        System.out.println("GUI displayWin");
    }

    private void gameView() {
        this.gameGrid = new GridPane();
        this.gameGrid.setAlignment(Pos.CENTER);
        this.gameGrid.setVgap(5); // Vertical gap between nodes
        this.gameGrid.setHgap(5); // Horizontal gap between nodes

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Circle circle = new Circle(30); // Set a suitable radius for the circle
                circle.setFill(Color.LIGHTGRAY); // Neutral color to start with
                gameGrid.add(circle, col, row);

                // Event handling for player move (if needed)
                int finalCol = col;
                circle.setOnMouseClicked(e -> game.guiTurn(finalCol, this));
            }
        }



        System.out.println("setup complete");

        Scene scene = new Scene(this.gameGrid, 640, 480);

        Stage new_stage = new Stage();
        new_stage.setScene(scene);
        new_stage.setTitle("Game Screen");
        new_stage.getIcons().add(new javafx.scene.image.Image("logo.png"));
        new_stage.show();

        this.stage.close();

        this.stage = new_stage;
    }

    private void handlePlayerMove(int column) {
        this.selectedCol = column;
        moveMade.set(true); // Signal that a move has been made
//        game.makeMove(column, getCurrentPlayer()); // getCurrentPlayer() needs to be implemented
        displayGame(); // Refresh the game board display
    }

    private int getCurrentPlayer() {
        return game.currentTurn;
    }



    @Override
    public void start(Stage stage){
        this.stage = stage;
        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER); // Align to center
        gridPane.setVgap(10); // Vertical gap between nodes
        gridPane.setHgap(10); // Horizontal gap between nodes

        // Buttons
        Button btnPvP = new Button("Player vs Player");
        Button btnPvAI = new Button("Player vs AI");
        Button btnQuit = new Button("Quit");

        // Button sizes
        btnPvP.setPrefSize(200, 50);
        btnPvAI.setPrefSize(200, 50);
        btnQuit.setPrefSize(200, 50);

        // Event handlers
        btnPvP.setOnAction(e ->
                {
                    this.gameType = 1;
                    this.gameView();
                    System.out.println("Gameview");
                    this.game.setGameType(1);
//                    this.game.startGameLoop(this.gameType, this);
                }
        );
        btnPvAI.setOnAction(e ->
            {
                this.gameType = 2;
                this.gameView();
                this.game.setGameType(2);
//                this.game.startGameLoop(this.gameType, this);
            }
        );
        btnQuit.setOnAction(e -> Platform.exit());

        // Add buttons to the GridPane
        gridPane.add(btnPvP, 0, 0);
        gridPane.add(btnPvAI, 0, 1);
        gridPane.add(btnQuit, 0, 2);

        // Create scene with GridPane as root
        Scene scene = new Scene(gridPane, 640, 480);
        this.stage.setScene(scene);
        this.stage.setTitle("Connect Four");
        this.stage.getIcons().add(new javafx.scene.image.Image("logo.png"));
        this.stage.show();
    }
}