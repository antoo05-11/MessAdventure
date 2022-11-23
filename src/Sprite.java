import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Sprite {
    private Image image;
    public static final int SCALE_SIZE = 40;
    private Sprite(String path) {
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        }

    }
    public Image getImage() {
        return image;
    }
    public static Sprite movingSprite(List<Sprite> sprites, AtomicInteger currentSprite, AtomicInteger counter) {
        counter.getAndIncrement();
        if(counter.get() % (sprites.size() * 10) == currentSprite.get() * 10)
            currentSprite.getAndIncrement();
        if(currentSprite.get() == sprites.size()) currentSprite.set(0);
        return sprites.get(currentSprite.get());
    }
    public static Sprite wall = new Sprite("res/abc.png");
    public static Sprite player_left_1 = new Sprite("res/player_left_1.png");
    public static Sprite player_left_2 = new Sprite("res/player_left_2.png");
    public static Sprite player_right_1 = new Sprite("res/player_right_1.png");
    public static Sprite player_right_2 = new Sprite("res/player_right_2.png");

}
