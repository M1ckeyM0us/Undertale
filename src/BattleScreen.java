import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class BattleScreen extends JFrame {
    private Soul me;
    private Box battleBox;
    private final Set<Integer> keysPressed = new HashSet<>();
    private static final int SPEED = 5;

    public BattleScreen() {

        setTitle("Sans Fight - Battle");
        setSize(1920, 1080); // Full HD resolution
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Scale battle box relative to 1920x1080
        int boxWidth = 750;
        int boxHeight = 250;
        int boxX = (1920 - boxWidth) / 2;
        int boxY = (1080 - boxHeight) / 2 + 100;

        battleBox = new Box(boxX, boxY, boxWidth, boxHeight);

        me = new Soul(boxX + boxWidth / 2 - 8, boxY + boxHeight / 2 - 8, "/Resources/Soul.png", 16, 16);

        JPanel battlePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                setBackground(Color.BLACK);

                battleBox.draw(g);
                me.draw(g);

            }
        };

        battlePanel.setFocusable(true);
        add(battlePanel);
        setVisible(true);

        battlePanel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                keysPressed.add(e.getKeyCode());

            }
            @Override
            public void keyReleased(KeyEvent e) {

                keysPressed.remove(e.getKeyCode());

            }

        });

        Timer timer = new Timer(16, e -> {
            moveSoul();
            battlePanel.repaint();
        });
        timer.start();
    }

    private void moveSoul() {

        int newX = me.getX(), newY = me.getY();

        if (keysPressed.contains(KeyEvent.VK_W)) newY -= SPEED;
        if (keysPressed.contains(KeyEvent.VK_S)) newY += SPEED;
        if (keysPressed.contains(KeyEvent.VK_A)) newX -= SPEED;
        if (keysPressed.contains(KeyEvent.VK_D)) newX += SPEED;

        if (battleBox.contains(newX, newY, me.getSize())) {

            me.setX(newX);
            me.setY(newY);

        }
    }
}
