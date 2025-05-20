import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class EndScreen extends JFrame {
    private final boolean won;
    private final Random random = new Random();
    private double shakeX = 0;
    private double shakeY = 0;
    private String message;

    public EndScreen(boolean won) {

        this.won = won;

        if (won) {
            message = "You Won!";
        }

        else {
            message = "You Died...";
        }

        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                setBackground(Color.BLACK);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Monospaced", Font.BOLD, 60));

                int messageWidth = g.getFontMetrics().stringWidth(message);
                int x = (getWidth() - messageWidth) / 2 + (int)shakeX;
                int y = getHeight() / 2 - 20 + (int) shakeY;
                g.drawString(message, x, y);

                g.setFont(new Font("Monospaced", Font.PLAIN, 30));
                String subtext = "Press ESC to Exit";
                int subtextWidth = g.getFontMetrics().stringWidth(subtext);
                g.drawString(subtext, (getWidth() - subtextWidth) / 2, getHeight() / 2 + 40);

            }
        };

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

                    System.exit(0);

                }
            }
        });

        Timer timer = new Timer(16, e -> {

            shakeX = random.nextGaussian() * 5; // ±5 pixels
            shakeY = random.nextGaussian() * 5;
            panel.repaint();

        });

        timer.start();

        add(panel);
        setVisible(true);
    }
}
