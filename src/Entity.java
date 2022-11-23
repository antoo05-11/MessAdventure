import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected Point topLeft;
    private Image image;
    protected GameMap gameMap;
    public Entity(GameMap gameMap, Sprite sprite, int xTile, int yTile) {
        this.gameMap = gameMap;
        this.image = sprite.getImage();
        topLeft = new Point(xTile * Sprite.SCALE_SIZE, yTile * Sprite.SCALE_SIZE);
    }

    public Point getTopLeft() {
        return topLeft;
    }


    public void setImage(Sprite sprite) {
        this.image = sprite.getImage();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, topLeft.getX() - GameMap.getDx_gc(), topLeft.getY() - GameMap.getDy_gc());
    }

    public abstract void update();
}
