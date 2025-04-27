import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class BattleScreen extends JFrame {
    private JPanel battlePanel; // Changed to instance variable for access
    private Soul me;
    private Box battleBox;
    private final Set<Integer> keysPressed = new HashSet<>();
    private static final int SPEED = 5;
    private Image sansImage;
    private JButton fightButton, actButton, itemButton, mercyButton;
    private boolean menuActive = false;
    private int health = 92;
    private List<SansAttack> attacks = new ArrayList<>();

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

        // Initialize battlePanel as an instance variable
        battlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);

                g.drawImage(sansImage, 750, 50, 400, 400, this);

                battleBox.draw(g);
                if (!menuActive) me.draw(g);

                for (SansAttack attack : attacks) {
                    attack.draw(g);
                }

                drawHealthBar(g);
            }
        };

        // Ensure battlePanel is focusable
        battlePanel.setFocusable(true);
        battlePanel.requestFocusInWindow(); // Request focus initially

        add(battlePanel);
        setVisible(true);

        // Key listener for movement
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

        // Timer to keep the game moving
        Timer timer = new Timer(16, e -> {
            if (!menuActive) {
                moveSoul();
                updateAttacks();
                battlePanel.repaint();
            }
        });
        timer.start();

        // Set up buttons
        ImageIcon fightIcon = new ImageIcon(getClass().getResource("/Resources/Fight1.png"));
        ImageIcon actIcon = new ImageIcon(getClass().getResource("/Resources/Act1.png"));
        ImageIcon itemIcon = new ImageIcon(getClass().getResource("/Resources/Item1.png"));
        ImageIcon mercyIcon = new ImageIcon(getClass().getResource("/Resources/Mercy1.png"));

        fightButton = new JButton(fightIcon);
        actButton = new JButton(actIcon);
        itemButton = new JButton(itemIcon);
        mercyButton = new JButton(mercyIcon);

        styleButton(fightButton);
        styleButton(actButton);
        styleButton(itemButton);
        styleButton(mercyButton);

        int buttonWidth = fightIcon.getIconWidth();
        int buttonHeight = fightIcon.getIconHeight();
        int totalWidth = 4 * buttonWidth + 3 * 20;
        int startX = (getWidth() - totalWidth) / 2;

        fightButton.setBounds(startX, 850, buttonWidth, buttonHeight);
        actButton.setBounds(startX + buttonWidth + 20, 850, buttonWidth, buttonHeight);
        itemButton.setBounds(startX + 2 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);
        mercyButton.setBounds(startX + 3 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);

        fightButton.addActionListener(e -> selectOption("Fight"));
        actButton.addActionListener(e -> selectOption("Act"));
        itemButton.addActionListener(e -> selectOption("Item"));
        mercyButton.addActionListener(e -> selectOption("Mercy"));

        battlePanel.setLayout(null);
        battlePanel.add(fightButton);
        battlePanel.add(actButton);
        battlePanel.add(itemButton);
        battlePanel.add(mercyButton);
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    private void selectOption(String option) {
        menuActive = false; // Allow movement
        System.out.println(option + " button pressed!");

        if (option.equals("Fight")) {
            startAttack(); // Start the attack if 'Fight' is selected
        }

        // Remove keysPressed.clear() to avoid resetting key states unnecessarily
        // keysPressed.clear(); // Commented out

        // Force focus back to battlePanel to ensure key events are captured
        battlePanel.requestFocusInWindow();
    }

    private void startAttack() {
        System.out.println("Starting attack sequence...");
        // Spawn attacks
        for (int i = 0; i < 5; i++) {
            attacks.add(new SansAttack(1920, battleBox.getY() + (int) (Math.random() * battleBox.getHeight()), 20, 50, 7));
        }
    }

    private void updateAttacks() {
        Iterator<SansAttack> iter = attacks.iterator();
        while (iter.hasNext()) {
            SansAttack attack = iter.next();
            attack.update();
            if (attack.checkCollision(me)) {
                takeDamage(5);
                iter.remove();
            } else if (!attack.isActive()) {
                iter.remove();
            }
        }
    }

    private void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
        System.out.println("Player took " + damage + " damage! HP: " + health);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 300;
        int barHeight = 30;
        int x = 50;
        int y = getHeight() - 100;

        g.setColor(Color.WHITE);
        g.drawRect(x - 2, y - 2, barWidth + 4, barHeight + 4);

        g.setColor(Color.RED);
        int currentWidth = (int) ((health / 92.0) * barWidth);
        g.fillRect(x, y, currentWidth, barHeight);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("HP: " + health + " / 92", x + 10, y + 22);
    }

    private void moveSoul() {
        if (menuActive) return;

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
}