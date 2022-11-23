public class Collision {
    private GameMap gameMap;

    public Collision(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public boolean colliding(Entity entity, Config.MovingStatus movingStatus, int OBJECT_SPEED) {
        Entity topLeftCorner;
        Entity topRightCorner;
        Entity downLeftCorner;
        Entity downRightCorner;

        int curX;
        curX = gameMap.getPlayer().getTopLeft().getX();
        int curY;
        curY = gameMap.getPlayer().getTopLeft().getY();
        switch (movingStatus) {
            case UP:
                curY -= OBJECT_SPEED;
                break;
            case DOWN:
                curY += OBJECT_SPEED;
                break;
            case LEFT:
                curX -= OBJECT_SPEED;
                break;
            case RIGHT:
                curX += OBJECT_SPEED;
                break;
        }

        topLeftCorner = gameMap.getEntity(new Point(curX, curY));
        topRightCorner = gameMap.getEntity(new Point(curX + Player.WIDTH, curY));
        downLeftCorner = gameMap.getEntity(new Point(curX, curY + Player.HEIGHT));
        downRightCorner = gameMap.getEntity(new Point(curX + Player.WIDTH, curY + Player.HEIGHT));
        if (topRightCorner != null || downRightCorner != null || topLeftCorner != null || downLeftCorner != null) {
            System.out.println(topLeftCorner + " " + downLeftCorner + " " + topRightCorner + " " + downRightCorner);
            return true;
        }
        return false;
    }
}
