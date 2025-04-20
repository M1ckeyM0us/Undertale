
import java.awt.*;

public class Box {
    private int x, y, width, height;

    public Box(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(x - 2, y - 2, width + 4, height + 4); // White border
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height); // Black fill

    }

    public boolean contains(int soulX, int soulY, int soulSize) {

        return (soulX >= x && soulX + soulSize <= x + width && soulY >= y && soulY + soulSize <= y + height);

    }
}
