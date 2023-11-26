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

public class GUI extends Application implements GameInterface {

        private Game game;
        private int gameType;
        private GridPane gameGrid;
        private Stage stage;

        public GUI() {
            this.game = new Game();
        }

        public void startGame() {
            // create game and display gui
            launch();
        }

        public void displayGame() {
            System.out.println("GUI displayGame");
        }

        public void makeMove(int player) {
            boolean moveMade = false;
            while (!moveMade) {
                // wait for player to make move
                // TODO: implement
            }
        }

        public void displayWin(int winState) {
            System.out.println("GUI displayWin");
        }

        private void gameView() {
            gameGrid = new GridPane();
            gameGrid.setAlignment(Pos.CENTER);
            gameGrid.setVgap(5); // Vertical gap between nodes
            gameGrid.setHgap(5); // Horizontal gap between nodes

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    Circle circle = new Circle(30); // Set a suitable radius for the circle
                    circle.setFill(Color.LIGHTGRAY); // Neutral color to start with
                    gameGrid.add(circle, col, row);

                    // Event handling for player move (if needed)
                    // Example: circle.setOnMouseClicked(e -> handlePlayerMove(col));
                }
            }



            Scene scene = new Scene(gameGrid, 640, 480);
            this.stage.setScene(scene);
            this.stage.show();
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
                        this.game.startGameLoop(this.gameType, this);
                    }
            );
            btnPvAI.setOnAction(e ->
                {
                    this.gameType = 2;
                    this.gameView();
                    this.game.startGameLoop(this.gameType, this);
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