import java.awt.event.KeyEvent;
import java.util.Set;

public class Movement {
    private Soul me;
    private Box fightBox;
    private Set<Integer> pressedKeys;
    private int speed = 4;

    public Movement(Soul me, Box fightBox, Set<Integer> pressedKeys) {
        this.me = me;
        this.fightBox = fightBox;
        this.pressedKeys = pressedKeys;
    }

    public void update() {
        int newX = me.getX();
        int newY = me.getY();

        if (pressedKeys.contains(KeyEvent.VK_W)) newY -= speed;
        if (pressedKeys.contains(KeyEvent.VK_S)) newY += speed;
        if (pressedKeys.contains(KeyEvent.VK_A)) newX -= speed;
        if (pressedKeys.contains(KeyEvent.VK_D)) newX += speed;

        // Keep Soul inside the battle box
        if (fightBox.contains(newX, newY, me.getSize())) {
            me.setX(newX);
            me.setY(newY);
        }
    }
}