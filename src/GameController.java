import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private static GameController gameController = null;
    private final Stage stage;
    /**
     * GameController constructor.
     * @param stage primary stage of application.
     */
    private GameController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Get game controller.
     * @return Unique game controller.
     */
    public static GameController getGameController(Stage stage) {
        if(gameController == null) {
            gameController = new GameController(stage);
        }
        return gameController;
    }

    AnimationTimer animationTimer =  new AnimationTimer() {
        @Override
        public void handle(long now) {
            render();
            update();
        }
    };
    private void render() {
        gc.clearRect(canvas.getLayoutX(), canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight());
        gameMap.render(gc);
    }
    private void update() {
        gameMap.update();
    }

    HBox hBox = new HBox();

    Scene gameScene = new Scene(hBox, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
    Canvas canvas = new Canvas(gameScene.getWidth(), gameScene.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();

    GameMap gameMap;

    /**
     * Run game engine.
     */
    public void run() {
        gameMap = new GameMap();

        hBox.getChildren().add(canvas);
        stage.setScene(gameScene);
        stage.show();

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gameMap.getPlayer().saveKeyCode(event.getCode(), true);
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                gameMap.getPlayer().saveKeyCode(event.getCode(), false);
            }
        });

        animationTimer.start();
    }
}
