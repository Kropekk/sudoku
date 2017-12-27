/**
 * Created by kamil on 19.02.17.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class Sudoku extends Application {
    public static Stage mainStage;
    private static HashMap<Integer, Color> groupColorMapping = new HashMap<>();

    static {
        groupColorMapping.put(0, Color.BLUE);
        groupColorMapping.put(1, Color.RED);
        groupColorMapping.put(2, Color.BROWN);
        groupColorMapping.put(3, Color.ORANGE);
        groupColorMapping.put(4, Color.GREEN);
        groupColorMapping.put(5, Color.VIOLET);
        groupColorMapping.put(6, Color.YELLOW);
        groupColorMapping.put(7, Color.DARKGREY);
        groupColorMapping.put(8, Color.PINK);
    }

    private Parent createContent(int boardSize) {
        Pane root = new Pane();
        Board board = new Board(boardSize);
        root.setPrefSize(board.getBoard().size() * 100, board.getBoard().size() * 100);
        board.setInitialValues();
        for (List<Cell> l : board.getBoard()) {
            for (Cell c : l) {
                if (c.getValue() != 0) {
                    c.getText().setText(Integer.toString(c.getValue()));
                }
                c.getCellBorder().setStroke(groupColorMapping.get(c.getGroupId()));
                root.getChildren().add(c);
            }
        }

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parent[] puzzles = new Parent[3];
        puzzles[0] = createContent(6);
        puzzles[1] = createContent(9);
        puzzles[2] = createContent(4);
        BorderPane borderPane = new BorderPane();
        final Integer[] activePuzzle = new Integer[1];
        activePuzzle[0] = 0;
        HBox topbar = new HBox(15.);
        borderPane.setTop(topbar);
        Button button1 = new Button("Puzzle 1");
        button1.setStyle("-fx-background-color: slateblue;");
        Button button2 = new Button("Puzzle 2");
        button2.setStyle("-fx-background-color: slateblue;");
        Button button3 = new Button("Puzzle 3");
        button3.setStyle("-fx-background-color: slateblue;");
        Button buttonClear = new Button("Clear");
        buttonClear.setStyle("-fx-background-color: darkorange;");
        topbar.getChildren().add(button1);
        topbar.getChildren().add(button2);
        topbar.getChildren().add(button3);
        topbar.getChildren().add(buttonClear);
        BorderPane.setMargin(topbar, new Insets(10, 0, 20, 0));
        BorderPane.setAlignment(topbar, Pos.CENTER);

        button1.setOnAction((event) -> {
            borderPane.setCenter(puzzles[0]);
            mainStage.sizeToScene();
            activePuzzle[0] = 0;
        });

        button2.setOnAction((event) -> {
            borderPane.setCenter(puzzles[1]);
            mainStage.sizeToScene();
            activePuzzle[0] = 1;
        });

        button3.setOnAction((event) -> {
            borderPane.setCenter(puzzles[2]);
            mainStage.sizeToScene();
            activePuzzle[0] = 2;
        });

        buttonClear.setOnAction((event) -> {
            if (activePuzzle[0] == 0) {
                puzzles[0] = createContent(6);
            } else if (activePuzzle[0] == 1) {
                puzzles[1] = createContent(9);
            } else {
                puzzles[2] = createContent(4);
            }
            borderPane.setCenter(puzzles[activePuzzle[0]]);
            mainStage.sizeToScene();
        });
        borderPane.setCenter(puzzles[0]);
        Scene scene = new Scene(borderPane);
        mainStage = stage;
        mainStage.setTitle("Sudoku TCS");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
        mainStage.toFront();
    }
}
