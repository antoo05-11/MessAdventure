import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Entity {
    public static final int HEIGHT = 45;
    public static final int WIDTH = 32;
    Set<KeyCode> keySet = new HashSet<>();
    KeyCode latestKeyCode = null;
    GameMap gameMap;

    int heightLand = 0;
    int heightJump = 40;
    private AtomicInteger indexOfSprite = new AtomicInteger(0);
    private AtomicInteger counter = new AtomicInteger(0);

    public Player(Sprite sprite, int xTile, int yTile, GameMap gameMap) {
        super(sprite, xTile, yTile);
        this.gameMap = gameMap;
    }

    /**
     * Save key code for player motion, called if key event happens.
     *
     * @param keyCode get from key event.
     * @param flag    true if pressed, false if released.
     */
    public void saveKeyCode(KeyCode keyCode, boolean flag) {
        if (keyCode.isArrowKey() || keyCode == KeyCode.SPACE) {
            if (flag) {
                switch (keyCode) {
                    case LEFT:
                    case RIGHT:
                        keySet.add(keyCode);
                        latestKeyCode = keyCode;
                        break;
                    case SPACE:
                        if (gameMap.getEntity(getDownLeft()) != null || gameMap.getEntity(getDownRight()) != null)
                            keySet.add(keyCode);
                        break;
                }
            } else {
                if (keyCode.isArrowKey()) {
                    keySet.remove(keyCode);
                }
            }
        }
    }

    public Point getDownLeft() {
        Point downLeft = topLeft.clone();
        downLeft.translate(Config.MovingStatus.DOWN, Player.HEIGHT + 4);
        return downLeft;
    }

    public Point getDownRight() {
        Point downRight = topLeft.clone();
        downRight.translate(Config.MovingStatus.DOWN, Player.HEIGHT + 4);
        downRight.translate(Config.MovingStatus.RIGHT, Player.WIDTH);
        return downRight;
    }

    private void setLatestDefaultImage() {
        if (latestKeyCode == KeyCode.LEFT) setImage(Sprite.player_left_1);
        else if (latestKeyCode == KeyCode.RIGHT) setImage(Sprite.player_right_1);
    }

    private void moving() {
        //Set image when stopping moving.
        if (keySet.isEmpty()) {
            setLatestDefaultImage();
        }

        if (keySet.contains(KeyCode.LEFT) && !gameMap.getCollision().colliding(this, Config.MovingStatus.LEFT, 2)) {
            setImage(Sprite.movingSprite(Arrays.asList(Sprite.player_left_1, Sprite.player_left_2), indexOfSprite, counter));
            topLeft.translate(Config.MovingStatus.LEFT, 2);
        }
        if (keySet.contains(KeyCode.RIGHT) && !gameMap.getCollision().colliding(this, Config.MovingStatus.RIGHT, 2)) {
            setImage(Sprite.movingSprite(Arrays.asList(Sprite.player_right_1, Sprite.player_right_2), indexOfSprite, counter));
            topLeft.translate(Config.MovingStatus.RIGHT, 2);
        }
        if (keySet.contains(KeyCode.SPACE)) {
            if (!gameMap.getCollision().colliding(this, Config.MovingStatus.UP, 4)) {
                topLeft.translate(Config.MovingStatus.UP, 4);
                heightLand++;
            }
            else {
                heightLand = 0;
                keySet.remove(KeyCode.SPACE);
            }
        }

        //Remove space keycode from key set if player get max height when jumping.
        if (keySet.contains(KeyCode.SPACE) && heightLand == heightJump) {
            heightLand = 0;
            keySet.remove(KeyCode.SPACE);
        }

        //Solve free fall case.
        if (!keySet.contains(KeyCode.SPACE)) {
            if (gameMap.getEntity(getDownLeft()) == null && gameMap.getEntity(getDownRight()) == null) {
                topLeft.translate(Config.MovingStatus.DOWN, 4);
            }
        }
    }

    @Override
    public void update() {
        moving();
        System.out.println(topLeft);
    }
}
