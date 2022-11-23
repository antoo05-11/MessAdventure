import javafx.scene.canvas.GraphicsContext;

public class MysteryBox extends Entity {
    boolean status;

    public MysteryBox(GameMap gameMap, Sprite sprite, int xTile, int yTile, boolean status) {
        super(gameMap, sprite, xTile, yTile);
        this.status = status;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (status) super.render(gc);
    }

    private boolean insideCurrentBox(Point point) {
        if (point.getX() >= topLeft.getX() && point.getX() <= topLeft.getX() + Sprite.SCALE_SIZE
                && point.getY() >= topLeft.getY() && point.getY() <= topLeft.getY() + Sprite.SCALE_SIZE)
            return true;
        return false;
    }

    private boolean collideWithPlayer() {
        int xPlayer = gameMap.getPlayer().getTopLeft().getX();
        int yPlayer = gameMap.getPlayer().getTopLeft().getY();
        Point topLeft = new Point(xPlayer - 1, yPlayer - 1);
        Point topRight = new Point(xPlayer + 1 + Player.WIDTH, yPlayer - 1);
        Point downLeft = new Point(xPlayer - 1, yPlayer + 1 + Player.HEIGHT);
        Point downRight = new Point(xPlayer + 1 + Player.WIDTH, yPlayer + 1 + Player.HEIGHT);
        if (insideCurrentBox(topLeft) || insideCurrentBox(topRight) || insideCurrentBox(downRight) || insideCurrentBox(downLeft))
            return true;
        return false;
    }

    @Override
    public void update() {
        if (!status && collideWithPlayer()) status = true;
    }
}
