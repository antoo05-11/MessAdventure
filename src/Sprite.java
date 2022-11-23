import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    public static Sprite wall = new Sprite("res/abc.png");

}
