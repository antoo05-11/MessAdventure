import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Player extends Entity {
    Set<KeyCode> keySet = new HashSet<>();
    GameMap gameMap;

    int heightLand = 0;
    int heightJump = 30;

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
                        break;
                    case SPACE:
                        if(gameMap.getEntity(getDownLeft()) != null)
                            keySet.add(keyCode);
                        break;
                }
            } else {
                if(keyCode.isArrowKey()) keySet.remove(keyCode);
            }
        }
    }

    private void moving() {
        if(keySet.contains(KeyCode.LEFT)) topLeft.translate(Config.MovingStatus.LEFT, 2);
        if(keySet.contains(KeyCode.RIGHT)) topLeft.translate(Config.MovingStatus.RIGHT, 2);
        if(keySet.contains(KeyCode.SPACE)) {
            topLeft.translate(Config.MovingStatus.UP, 2);
            heightLand++;
        }

        //Remove if player get max height when jumping.
        if(keySet.contains(KeyCode.SPACE) && heightLand == heightJump) {
            heightLand = 0;
            keySet.remove(KeyCode.SPACE);
        }

        //Solve free fall case.
        if(!keySet.contains(KeyCode.SPACE)) {
            if(gameMap.getEntity(getDownLeft()) == null) topLeft.translate(Config.MovingStatus.DOWN, 2);
        }
    }

    @Override
    public void update() {
        moving();
    }
}
