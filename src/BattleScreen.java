import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BattleScreen extends JFrame {
    private Soul me = new Soul(400, 400, 0, 0, 50, Color.BLACK);

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel for movement
        JPanel battlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(me.getColor());
                g.fillOval(me.getX(), me.getY(), me.getSize(), me.getSize());
            }
        };

        battlePanel.setFocusable(true);
        battlePanel.setBackground(Color.WHITE);
        battlePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) me.setY(me.getY() - 4);
                if (e.getKeyCode() == KeyEvent.VK_S) me.setY(me.getY() + 4);
                if (e.getKeyCode() == KeyEvent.VK_A) me.setX(me.getX() - 4);
                if (e.getKeyCode() == KeyEvent.VK_D) me.setX(me.getX() + 4);
                battlePanel.repaint(); // Update position
            }
        });

        add(battlePanel);
        setVisible(true);
    }
}

