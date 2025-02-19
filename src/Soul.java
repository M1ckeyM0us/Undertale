import javax.swing.*;
import java.awt.*;

public class Soul {

    private int x, y, size;
    private Image image;

    public Soul(int x, int y, String path, int sizeX, int sizeY) {

        this.x = x;
        this.y = y;
        this.size = 32; // Correct Undertale size
        this.image = new ImageIcon(getClass().getResource(path)).getImage();

    }

    public void draw(Graphics g) {

        g.drawImage(image, x, y, size, size, null);
    }

    public void setX(int x) {

        this.x = x;

    }
    public void setY(int y) {

        this.y = y;

    }
    public int getX() {

        return x;

    }
    public int getY() {

        return y;

    }
    public int getSize() {

        return size;

    }
}
