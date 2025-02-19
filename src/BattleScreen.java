import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BattleScreen extends JFrame {
    private Soul me;

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        me = new Soul(400, 400, "/Resources/Soul.png"); // Load soul image

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

        battlePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_W) {
                    me.setY(me.getY() - 4);
                }

                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    me.setY(me.getY() + 4);
                }

                else if (e.getKeyCode() == KeyEvent.VK_D) {
                    me.setX(me.getX() + 4);
                }

                else if (e.getKeyCode() == KeyEvent.VK_A) {
                    me.setX(me.getX() - 4);
                }
                battlePanel.repaint(); // Update the screen after movement
            }
        });

        battlePanel.requestFocusInWindow(); // Ensure key events are captured
    }
}
