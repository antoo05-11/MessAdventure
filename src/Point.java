import javafx.scene.input.KeyCode;

public class Point implements Cloneable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void translate(Config.MovingStatus status, int displacement) {
        switch (status) {
            case LEFT:
                x -= displacement;
                break;
            case RIGHT:
                x += displacement;
                break;
            case UP:
                y -= displacement;
                break;
            case DOWN:
                y += displacement;
                break;
        }
    }

    @Override
    public Point clone() {
        try {
            Point clone = (Point) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
