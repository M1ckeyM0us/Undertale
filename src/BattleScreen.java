import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;

public class BattleScreen extends JFrame {
    private JPanel battlePanel;
    private Soul me;
    private Box battleBox;
    private final Set<Integer> keysPressed = new HashSet<>();
    private static final int SPEED = 5;
    private Image sansImage;
    private JButton fightButton, actButton, itemButton, mercyButton;
    private boolean menuActive = false;
    private int health = 92;
    private List<SansAttack> attacks = new ArrayList<>();
    private int attackPhase = 0;
    private boolean attackInProgress = false;
    private Clip backgroundMusic;

    public BattleScreen() {
        setTitle("Sans Fight - Battle");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sansImage = new ImageIcon(getClass().getResource("/Resources/Sans.png")).getImage();

        // Initialize background music
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    getClass().getResource("/Resources/megalovania.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            System.out.println("Error loading background music: " + e.getMessage());
        }

        int boxWidth = 750;
        int boxHeight = 250;
        int boxX = (1920 - boxWidth) / 2;
        int boxY = (1080 - boxHeight) / 2 + 100;

        battleBox = new Box(boxX, boxY, boxWidth, boxHeight);
        me = new Soul(boxX + boxWidth / 2 - 8, boxY + boxHeight / 2 - 8, "/Resources/Soul.png", 16, 16);

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

        battlePanel.setFocusable(true);
        battlePanel.requestFocusInWindow();

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
            if (!menuActive) {
                moveSoul();
                updateAttacks();
                battlePanel.repaint();
            }
        });
        timer.start();

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
        if (attackInProgress) return;

        if (option.equals("Fight")) {
            attackInProgress = true;
            hideButtons();
            startAttack();
        } else {
            menuActive = false;
            System.out.println(option + " button pressed!");
        }

        battlePanel.requestFocusInWindow();
    }

    private void startAttack() {
        if (attackPhase >= 10) {
            attackInProgress = false;
            showButtons();
            return;
        }

        attackPhase++;
        System.out.println("Starting attack phase " + attackPhase + ": " + attackPhase + " large bone(s) in " + attackPhase + " wave(s)");

        attacks.clear();

        int numWaves = attackPhase;
        int waveDelay = 500;
        int boneHeight = 100;
        Timer waveTimer = new Timer(waveDelay, null);
        ActionListener waveListener = new ActionListener() {
            int currentWave = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWave < numWaves) {
                    int boxHeight = battleBox.getHeight();
                    int maxY = boxHeight - boneHeight;
                    int y = battleBox.getY() + (int) (Math.random() * maxY);

                    attacks.add(new SansAttack(1920, y, 7));

                    currentWave++;
                } else {
                    waveTimer.stop();
                }
            }
        };
        waveTimer.addActionListener(waveListener);
        waveTimer.setInitialDelay(0);
        waveTimer.start();

        if (attackPhase == 10) {
            Timer winTimer = new Timer(5000, e -> {
                if (attacks.isEmpty()) {
                    showWinScreen();
                }
            });
            winTimer.setRepeats(false);
            winTimer.start();
        }
    }

    private void hideButtons() {
        fightButton.setVisible(false);
        actButton.setVisible(false);
        itemButton.setVisible(false);
        mercyButton.setVisible(false);
        battlePanel.revalidate();
        battlePanel.repaint();
    }

    private void showButtons() {
        fightButton.setVisible(true);
        actButton.setVisible(true);
        itemButton.setVisible(true);
        mercyButton.setVisible(true);
        battlePanel.revalidate();
        battlePanel.repaint();
    }

    private void showWinScreen() {
        menuActive = true;
        battlePanel.removeKeyListener(battlePanel.getKeyListeners()[0]);
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
        dispose();
        new EndScreen(true);
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

        if (attacks.isEmpty() && attackInProgress && attackPhase < 10) {
            attackInProgress = false;
            showButtons();
        }
    }

    private void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            System.out.println("Game Over!");
            if (backgroundMusic != null) {
                backgroundMusic.stop();
                backgroundMusic.close();
            }
            dispose();
            new EndScreen(false);
        }
        System.out.println("Player took " + damage + " damage! HP: " + health);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 300;
        int barHeight = 30;

        // Adjust the y position to place the health bar between the battle box and buttons
        int x = (getWidth() - barWidth) / 2;  // Center the bar horizontally
        int y = (getHeight() - 275) - 0;   // Adjusted position to move it higher

        // Draw the health bar border
        g.setColor(Color.WHITE);
        g.drawRect(x - 2, y - 2, barWidth + 4, barHeight + 4);

        // Fill the health bar with yellow color based on current health
        g.setColor(Color.YELLOW);
        int currentWidth = (int) ((health / 92.0) * barWidth);
        g.fillRect(x, y, currentWidth, barHeight);

        // Draw text ("Chara  Lv 19 HP: currentHealth/92")
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        String text = "Chara  Lv 19   HP: " + health + " / 92";
        int textWidth = g.getFontMetrics().stringWidth(text);

        // Position the text horizontally centered above the health bar (same position as health bar)
        g.drawString(text, (getWidth() - textWidth) / 2, y - 10);  // Slightly above the health bar
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