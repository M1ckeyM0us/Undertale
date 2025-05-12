import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private JPanel panel;

    public GameWindow() {
        setTitle("Resizable Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Get current window dimensions
                int width = getWidth();
                int height = getHeight();

                // Calculate scaling factors based on window size (1000x1000 is a base size)
                double scaleX = width / 1000.0;
                double scaleY = height / 1000.0;

                // Scale the content
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, width, height);  // Rescale background

                // Draw a scaled circle in the center of the window
                g.setColor(Color.RED);
                int circleDiameter = (int) (100 * scaleX); // Scale the circle size
                g.fillOval((width - circleDiameter) / 2, (height - circleDiameter) / 2, circleDiameter, circleDiameter);
            }
        };

        // Initial window size
        setSize(1000, 1000);
        add(panel);

        // Allow window resizing
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}
