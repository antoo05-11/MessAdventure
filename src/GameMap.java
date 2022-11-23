import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMap {
    Entity[][] entities;
    int widthTile;
    int heightTile;
    List<Entity> movingObject = new ArrayList<>();
    public GameMap() {
        readMapFromFile();
    }
    public void readMapFromFile() {
        File file = new File("res/map.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        assert scanner != null;

        widthTile = Integer.parseInt(scanner.nextLine());
        heightTile = Integer.parseInt(scanner.nextLine());
        entities = new Entity[widthTile][heightTile];

        movingObject.add(new Player(Sprite.player_right_1, Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine()), this));

        for(int row = 0; row < heightTile; row ++) {
            String line = scanner.nextLine();
            String[] rowString = line.split(",");
            for(int column = 0; column < widthTile; column ++) {
                switch (rowString[column]) {
                    case "0":
                        entities[column][row] = null;
                        break;
                    case "1":
                        entities[column][row] = new Entity(Sprite.wall, column, row) {
                            @Override
                            public void update() {

                            }
                        };
                        break;
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        setUpMapCamera();
        for(int i = 0; i < widthTile; i++) {
            for(int j = 0; j < heightTile; j++) {
                if(entities[i][j] != null) {
                    entities[i][j].render(gc);
                }
            }
        }
        for(Entity i : movingObject) {
            i.render(gc);
        }
    }
    public void update() {
        for(Entity i : movingObject) {
            i.update();
        }
        if(movingObject.get(0).getTopLeft().getY() - 100 > heightTile * Sprite.SCALE_SIZE) System.exit(0) ;
    }
    public Player getPlayer() {
        return (Player) movingObject.get(0);
    }
    /**
     * dx_gc and dy_gc is displacement of graphic context gc.
     * These values are set along with the position of Bomber.
     */
    private static int dx_gc = 0, dy_gc = 0;

    public static int getDx_gc() {
        return dx_gc;
    }

    public static int getDy_gc() {
        return dy_gc;
    }

    /**
     * If xPixel of bomber < SCREEN_WIDTH/2, gc render (xPixel,yPixel) as usual.
     * If SCREEN_WIDTH/2 < xPixel of bomber < MAP_WIDTH - SCREEN_WIDTH/2, gc decrease position of image by xPixel-SCREEN_WIDTH/2.
     * If xPixel of bomber > MAP_WIDTH - SCREEN_WIDTH/2, gc  decrease position of image by MAP_WIDTH - SCREEN_WIDTH.
     * All operations do same for y_pos rendering of gc.
     */
    public void setUpMapCamera() {
        int bomber_x = movingObject.get(0).getTopLeft().getX();
        int bomber_y = movingObject.get(0).getTopLeft().getY();
        if (bomber_y < (Config.SCREEN_HEIGHT) / 2) {
            dy_gc = 0;
        } else if (bomber_y < heightTile * Sprite.SCALE_SIZE - Config.SCREEN_HEIGHT / 2) {
            dy_gc = bomber_y - Config.SCREEN_HEIGHT / 2;
        } else {
            dy_gc = heightTile * Sprite.SCALE_SIZE  - Config.SCREEN_HEIGHT;
        }
        if (bomber_x < Config.SCREEN_WIDTH / 2) {
            dx_gc = 0;
        } else if (bomber_x < widthTile * Sprite.SCALE_SIZE - Config.SCREEN_WIDTH / 2) {
            dx_gc = bomber_x - Config.SCREEN_WIDTH / 2;
        } else {
            dx_gc = widthTile * Sprite.SCALE_SIZE - Config.SCREEN_WIDTH;
        }

    }
    public Entity getEntity(Point point) {
        int xTilePos = point.getX()/Sprite.SCALE_SIZE;
        int yTilePos = point.getY()/Sprite.SCALE_SIZE;
        if(xTilePos >= 0 && xTilePos < widthTile && yTilePos >= 0 && yTilePos < heightTile)
            return entities[point.getX()/Sprite.SCALE_SIZE][point.getY()/Sprite.SCALE_SIZE];
        return null;
    }
}
