package src.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import game.Game;

public class GUI extends Application implements GameInterface {

    private Game game;
    private int gameType;
    private GridPane gameGrid;
    private Stage stage;
    private int selectedCol;
    private BooleanProperty moveMade = new SimpleBooleanProperty(false);

    private Label label;

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
        // loop through all circles and make them unclickable
        for(Circle circle : this.gameGrid.getChildren().toArray(new Circle[0])) {
            circle.setOnMouseClicked(e -> {});
        }
        if(winState == 0) {
            displayMessage(2);
        } else if(winState == 1) {
            displayMessage(0);
        } else if(winState == 2) {
            displayMessage(1);
        }
    }

    private void gameView() {
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10);

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

        this.label = new Label("Click on the board to start!");
        this.label.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(this.gameGrid, this.label);


        Scene scene = new Scene(vb, 640, 480);

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

    public void displayMessage(int i){
        /*
        0 = Player 1 wins
        1 = Player 2 wins
        2 = Draw
        3 = Invalid move
        4 = Player 1's turn
        5 = Player 2's turn
        6 = AI's thinking
         */
        switch (i) {
            case 0:
                this.label.setText("Player 1 wins! Thanks for playing!");
                break;
            case 1:
                this.label.setText("Player 2 wins! Thanks for playing!");
                break;
            case 2:
                this.label.setText("Draw! Thanks for playing!");
                break;
            case 3:
                this.label.setText("Invalid move!");
                break;
            case 4:
                this.label.setText("Player 1's turn");
                break;
            case 5:
                this.label.setText("Player 2's turn");
                break;
            case 6:
                this.label.setText("AI's thinking");
                break;
            default:
                this.label.setText("");
                break;
        }
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