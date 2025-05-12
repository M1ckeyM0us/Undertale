import java.awt.event.KeyEvent;
import java.util.Set;

public class Movement {

    private Soul soul;
    private Box box;
    private Set<Integer> keys;
    private int speed = 4;

    public Movement(Soul soul, Box box, Set<Integer> keys) {

        this.soul = soul;
        this.box = box;
        this.keys = keys;

    }

    public void update() {

        int x = soul.getX();
        int y = soul.getY();

        if (keys.contains(KeyEvent.VK_W)){
            y -= speed;
        }

        if (keys.contains(KeyEvent.VK_S)){
            y += speed;
        }

        if (keys.contains(KeyEvent.VK_A)){
            x -= speed;
        }

        if (keys.contains(KeyEvent.VK_D)){
            x += speed;
        }

        if (box.contains(x, y, soul.getSize())) {

            soul.setX(x);
            soul.setY(y);

        }
    }
}