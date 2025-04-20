import java.awt.Graphics;
import java.awt.Color;

public class Soul {
    private int x, y;
    private String color;
    private int width, height;

    public Soul(int x, int y, String color, int width, int height) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Move method to update position
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Method to draw the soul
    public void draw(Graphics g) {
        g.setColor(Color.RED); // Color of the soul, you can change it
        g.fillRect(x, y, width, height); // Draw the soul as a rectangle
    }
}
