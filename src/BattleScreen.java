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
                me.draw(g);
            }
        };

        battlePanel.setFocusable(true);
        add(battlePanel);
        setVisible(true);

        // Add key listener to move the soul
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

        // Timer for soul movement and repainting
        Timer timer = new Timer(16, e -> {
            moveSoul();
            battlePanel.repaint();
        });
        timer.start();

        // Button icons (make sure to replace these with the correct image files)
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
        int totalWidth = 4 * buttonWidth + 3 * 20; // Total width of buttons + spacing (20 pixels between buttons)
        int startX = (getWidth() - totalWidth) / 2; // Center the buttons horizontally

        // Set the buttons' bounds (center them horizontally and adjust the vertical positioning)
        fightButton.setBounds(startX, 850, buttonWidth, buttonHeight);
        actButton.setBounds(startX + buttonWidth + 20, 850, buttonWidth, buttonHeight);
        itemButton.setBounds(startX + 2 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);
        mercyButton.setBounds(startX + 3 * (buttonWidth + 20), 850, buttonWidth, buttonHeight);

        // Action listeners for buttons (replace with actual actions)
        fightButton.addActionListener(e -> System.out.println("Fight button pressed!"));
        actButton.addActionListener(e -> System.out.println("Act button pressed!"));
        itemButton.addActionListener(e -> System.out.println("Item button pressed!"));
        mercyButton.addActionListener(e -> System.out.println("Mercy button pressed!"));

        // Add the buttons to the panel
        battlePanel.setLayout(null);  // Use null layout for absolute positioning
        battlePanel.add(fightButton);
        battlePanel.add(actButton);
        battlePanel.add(itemButton);
        battlePanel.add(mercyButton);
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

    public static void main(String[] args) {
        new BattleScreen();
    }
}
