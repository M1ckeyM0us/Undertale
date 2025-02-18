import java.awt.*;

public class Soul {
    private int x, y, xSpeed, ySpeed, size;
    private Color color;

    public Soul(int x, int y, int xSpeed, int ySpeed, int size, Color color) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;
        this.color = color;
    }

    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
