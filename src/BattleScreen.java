import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class BattleScreen extends JFrame {
    private Soul me;
    private Set<Integer> pressedKeys = new HashSet<>();

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        me = new Soul(400, 400, "/Resources/Soul.png");

        JPanel battlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                me.draw(g);
            }
        };

        battlePanel.setFocusable(true);
        battlePanel.setBackground(Color.BLACK);
        add(battlePanel);
        setVisible(true);

        // Key listener for holding keys
        battlePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        battlePanel.requestFocusInWindow();

        // Timer for smooth movement
        new Timer(16, e -> {
            moveSoul();
            battlePanel.repaint();
        }).start();
    }

    private void moveSoul() {
        int speed = 4;

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            me.setY(me.getY() - speed);
        }

        if (pressedKeys.contains(KeyEvent.VK_S)) {
            me.setY(me.getY() + speed);
        }

        if (pressedKeys.contains(KeyEvent.VK_A)) {
            me.setX(me.getX() - speed);
        }

        if (pressedKeys.contains(KeyEvent.VK_D)) {
            me.setX(me.getX() + speed);
        }
    }
}
