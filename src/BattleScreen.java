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
    private boolean menuActive = false; // Track if a menu is open
    private int health = 92; // Player's health (Max: 92)

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load Sans PNG
        sansImage = new ImageIcon(getClass().getResource("/Resources/Sans.png")).getImage();

        // Battle box setup
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

                // Draw Sans PNG
                g.drawImage(sansImage, 750, 50, 400, 400, this);

                battleBox.draw(g);
                if (!menuActive) me.draw(g);

                // Draw health bar
                drawHealthBar(g);
            }
        };

        battlePanel.setFocusable(true);
        add(battlePanel);
        setVisible(true);

        // Add key listener to move the soul
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

        // Timer for soul movement and repainting
        Timer timer = new Timer(16, e -> {
            if (!menuActive) moveSoul();
            battlePanel.repaint();
        });
        timer.start();

        // Load button images
        ImageIcon fightIcon = new ImageIcon(getClass().getResource("/Resources/Fight1.png"));
        ImageIcon actIcon = new ImageIcon(getClass().getResource("/Resources/Act1.png"));
        ImageIcon itemIcon = new ImageIcon(getClass().getResource("/Resources/Item1.png"));
        ImageIcon mercyIcon = new ImageIcon(getClass().getResource("/Resources/Mercy1.png"));

        // Create buttons
        fightButton = new JButton(fightIcon);
        actButton = new JButton(actIcon);
        itemButton = new JButton(itemIcon);
        mercyButton = new JButton(mercyIcon);

        // Calculate button spacing
        int buttonWidth = fightIcon.getIconWidth();
        int buttonHeight = fightIcon.getIconHeight();
        int totalWidth = 4 * buttonWidth + 3 * 20;
        int startX = (getWidth() - totalWidth) / 2;

        // Set button positions
        fightButton.setBounds(startX, 850, buttonWidth, buttonHeight);
        actButton.setBounds(startX + buttonWidth + 20, 850, buttonWidth, buttonHeight);
        itemButton.setBounds(startX + 2 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);
        mercyButton.setBounds(startX + 3 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);

        // Add button actions
        fightButton.addActionListener(e -> selectOption("Fight"));
        actButton.addActionListener(e -> selectOption("Act"));
        itemButton.addActionListener(e -> selectOption("Item"));
        mercyButton.addActionListener(e -> selectOption("Mercy"));

        // Add buttons
        battlePanel.setLayout(null);
        battlePanel.add(fightButton);
        battlePanel.add(actButton);
        battlePanel.add(itemButton);
        battlePanel.add(mercyButton);
    }

    private void selectOption(String option) {
        menuActive = true; // Stop soul movement
        System.out.println(option + " button pressed!");

        if (option.equals("Fight")) {
            startAttack();
        }

        // Simulate closing menu after an action
        Timer timer = new Timer(1000, e -> {
            menuActive = false; // Allow movement again
            keysPressed.clear(); // Reset key presses
            requestFocusInWindow(); // Ensure the panel is focused to detect key presses
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void startAttack() {
        System.out.println("Starting attack sequence...");
        takeDamage(10); // Example damage when attacking
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
        g.drawRect(x - 2, y - 2, barWidth + 4, barHeight + 4); // White border

        g.setColor(Color.RED);
        int currentWidth = (int) ((health / 92.0) * barWidth);
        g.fillRect(x, y, currentWidth, barHeight); // Red health bar

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("HP: " + health + " / 92", x + 10, y + 22); // Display HP
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
