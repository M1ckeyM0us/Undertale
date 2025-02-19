import javax.swing.*;
import java.awt.*;

public class Soul {
    private int x, y;
    private Image image;

    public Soul(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
