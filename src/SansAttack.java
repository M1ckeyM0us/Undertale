import java.awt.*;
import javax.swing.*;

public class SansAttack {
    private int x, y, width, height, speed;
    private Image image;
    private String type;

    public SansAttack(int x, int y, int width, int height, int speed, String type, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;
        this.image = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    public void update() {
        x -= speed; // Moves left, adjust as needed
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public boolean collidesWith(Soul soul) {
        Rectangle attackRect = new Rectangle(x, y, width, height);
        Rectangle soulRect = new Rectangle(soul.getX(), soul.getY(), soul.getSize(), soul.getSize());
        return attackRect.intersects(soulRect);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public String getType() {
        return type;
    }
}
