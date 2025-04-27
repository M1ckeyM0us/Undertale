import javax.swing.*;
import java.awt.*;

public class SansAttack {
    private int x, y, width, height, speed;
    private boolean active;
    private Image boneImage;

    public SansAttack(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.active = true;

        boneImage = new ImageIcon(getClass().getResource("/Resources/Bone.png")).getImage();
        this.width = 50;
        this.height = 100;
    }

    public void update() {
        x -= speed;
        if (x + width < 0) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(boneImage, x, y, width, height, null);
    }

    public boolean checkCollision(Soul soul) {
        Rectangle bone = new Rectangle(x, y, width, height);
        Rectangle soulHitbox = new Rectangle(soul.getX(), soul.getY(), soul.getWidth(), soul.getHeight());
        return bone.intersects(soulHitbox);
    }

    public boolean isActive() {
        return active;
    }
}