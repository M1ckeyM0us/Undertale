import java.awt.*;

public class Movement {
    private Soul me;
    private Rectangle fightBox;

    public Movement(Soul me, Rectangle fightBox) {
        this.me = me;
        this.fightBox = fightBox;
    }

    public void checkSoulPosition(int newX, int newY) {
        // Create a Rectangle to represent the soul's bounds
        Rectangle soulBounds = new Rectangle(newX, newY, me.getWidth(), me.getHeight());

        // Check if the soul's bounds intersect with the fightBox
        if (fightBox.intersects(soulBounds)) {
            int soulWidth = me.getWidth();
            int soulHeight = me.getHeight();
            // Additional logic can be added here based on the intersection
        }
    }
}
