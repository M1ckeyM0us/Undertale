import java.awt.*;

public class SansAttack {
    private int x, y, width, height, speed;
    private boolean active;

    public SansAttack(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.active = true;
    }

    public void update() {
        x -= speed;
        if (x + width < 0) {
            active = false; // Attack went off screen
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height); // Simple bone visual
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
