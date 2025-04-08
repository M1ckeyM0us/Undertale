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
    private Image sansImage;
    private JButton fightButton, actButton, itemButton, mercyButton;
    private boolean menuActive = false;
    private int health = 92;

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sansImage = new ImageIcon(getClass().getResource("/Resources/Sans.png")).getImage();

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
                g.drawImage(sansImage, 750, 50, 400, 400, this);
                battleBox.draw(g);
                if (!menuActive) me.draw(g);
                drawHealthBar(g);
            }
        };

        battlePanel.setFocusable(true);
        add(battlePanel);
        setVisible(true);

        battlePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!menuActive) keysPressed.add(e.getKeyCode());
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed.remove(e.getKeyCode());
            }
        });

        Timer timer = new Timer(16, e -> {
            if (!menuActive) moveSoul();
            battlePanel.repaint();
        });
        timer.start();
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 300;
        int barHeight = 30;
        int x = 50;
        int y = getHeight() - 100;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Chara", x, y - 10);
        g.drawString("LV 19", x + 200, y - 10);

        g.setColor(Color.BLACK);
        g.fillRect(x - 2, y - 2, barWidth + 4, barHeight + 4);
        g.setColor(Color.WHITE);
        g.drawRect(x - 2, y - 2, barWidth + 4, barHeight + 4);
        g.setColor(Color.YELLOW);
        int currentWidth = (int) ((health / 92.0) * barWidth);
        g.fillRect(x, y, currentWidth, barHeight);

        g.setColor(Color.WHITE);
        g.drawString("HP " + health + "/92", x + 120, y + 22);
    }

    private void moveSoul() {
        int newX = me.getX();
        int newY = me.getY();
        if (keysPressed.contains(KeyEvent.VK_W)) newY -= SPEED;
        if (keysPressed.contains(KeyEvent.VK_S)) newY += SPEED;
        if (keysPressed.contains(KeyEvent.VK_A)) newX -= SPEED;
        if (keysPressed.contains(KeyEvent.VK_D)) newX += SPEED;
        if (battleBox.contains(newX, newY, me.getSize())) {
            me.setX(newX);
            me.setY(newY);
        }
    }

    public static void main(String[] args) {
        new BattleScreen();
    }
}
