import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BattleScreen extends JFrame {
    private JPanel panel;
    private Soul soul;
    private Box box;
    private Image sans;
    private JButton fight, mercy;
    private boolean inMenu = false;
    private int hp = 92;
    private ArrayList<SansAttack> bones = new ArrayList<>();
    private int phase = 0;
    private boolean attacking = false;
    private boolean up, down, left, right;

    public BattleScreen() {

        setResizable(true); // Enables resizing
        setSize(1920, 1080);

        // Load Sans image
        sans = new ImageIcon(getClass().getResource("/Resources/boi GIF.gif")).getImage();

        // Set up box and soul
        box = new Box(585, 490, 750, 250);
        soul = new Soul(960, 615, "/Resources/Soul.png", 16, 16);

        // Create panel
        panel = new JPanel() {
            public void paintComponent(Graphics g) {

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 1920, 1080);
                g.drawImage(sans, 750, 50, 400, 400, this);
                box.draw(g);
                if (!inMenu) soul.draw(g);
                for (SansAttack bone : bones) bone.draw(g);
                drawHP(g);

            }
        };

        panel.setLayout(null);

        // Set up buttons
        fight = new JButton(new ImageIcon(getClass().getResource("/Resources/Fight1.png")));
        mercy = new JButton(new ImageIcon(getClass().getResource("/Resources/Mercy1.png")));

        int btnWidth = ((ImageIcon) fight.getIcon()).getIconWidth();
        int startX = (1920 - 2 * btnWidth - 20) / 2;
        fight.setBounds(startX, 850, btnWidth, fight.getIcon().getIconHeight());
        mercy.setBounds(startX + btnWidth + 20, 850, btnWidth, mercy.getIcon().getIconHeight());

        fight.addActionListener(e -> choose("Fight"));
        mercy.addActionListener(e -> choose("Mercy"));

        panel.add(fight);
        panel.add(mercy);

        // Set up keys
        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!inMenu) {
                    if (e.getKeyCode() == KeyEvent.VK_W){
                        up = true;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_S){
                        down = true;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_A){
                        left = true;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_D){
                        right = true;
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W){
                    up = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    down = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_A){
                    left = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D){
                    right = false;
                }
            }
        });
        panel.setFocusable(true);
        panel.requestFocus();

        // Start game loop
        new Timer(16, e -> {
            if (!inMenu) {
                move();
                updateBones();
                panel.repaint();
            }
        }).start();

        add(panel);
        setVisible(true);
    }

    private void move() {
        int x = soul.getX();
        int y = soul.getY();

        if (up) y -= 5;
        if (down) y += 5;
        if (left) x -= 5;
        if (right) x += 5;

        if (box.contains(x, y, soul.getSize())) {
            soul.setX(x);
            soul.setY(y);
        }
    }

    private void choose(String option) {
        if (attacking) return;  // Prevent actions while attacking

        if (option.equals("Fight")) {
            attacking = true;
            hideButtons();
            startAttack();
        } else if (option.equals("Mercy")) {
            // When Mercy button is clicked, show the victory screen immediately
            System.out.println("Mercy");

            // Hide the buttons and start the victory screen after a delay (15 seconds)
            Timer mercyTimer = new Timer(1500, e -> {  // Small delay for better transition
                new EndScreen(true);  // Trigger victory end screen (You Won)
                dispose();  // Close the BattleScreen frame
            });
            mercyTimer.setRepeats(false);  // Trigger only once
            mercyTimer.start();
        }
        panel.requestFocus();
    }



    private void startAttack() {

        if (phase >= 10) {
            attacking = false;
            showButtons();
            return;
        }

        phase++;
        System.out.println("Phase " + phase);

        bones.clear();  // Reset bones at the start of each attack phase
        final int[] waves = {phase};
        Timer waveTimer = new Timer(500, e -> {
            if (waves[0] > 0) {

                // Random y position within the box area
                int y = box.getY() + (int) (Math.random() * (box.getHeight() - 100));
                bones.add(new SansAttack(1920, y, 7));  // Add new attack bone
                waves[0]--;  // Decrement the wave counter

            }

            else {

                ((Timer) e.getSource()).stop();  // Stop the wave timer when no more waves are left

            }
        });
        waveTimer.start();  // Start the wave timer

        // After phase 10, end the game if bones are empty
        if (phase == 10) {
            Timer endGameTimer = new Timer(5000, e -> {

                if (bones.isEmpty()) {
                    endGame(true);  // End the game if no bones are left

                }
            });

            endGameTimer.setRepeats(false);  // Ensure the timer only runs once
            endGameTimer.start();  // Start the end game timer

        }
    }


    private void hideButtons() {

        fight.setVisible(false);
        mercy.setVisible(false);

    }

    private void showButtons() {

        fight.setVisible(true);
        mercy.setVisible(true);

    }

    private void endGame(boolean won) {

        inMenu = true;
        panel.removeKeyListener(panel.getKeyListeners()[0]);
        setVisible(false);
        new EndScreen(won);

    }

    private void updateBones() {
        for (int i = 0; i < bones.size(); i++) {

            SansAttack bone = bones.get(i);
            bone.update();

            if (bone.checkCollision(soul)) {

                hp -= 5;
                bones.remove(i);
                i--;

                if (hp <= 0) {
                    hp = 0;
                    System.out.println("Game Over");
                    endGame(false);
                }

                System.out.println("HP: " + hp);

            }

            else if (!bone.isActive()) {

                bones.remove(i);
                i--;

            }
        }

        if (bones.isEmpty() && attacking && phase < 10) {
            attacking = false;
            showButtons();

        }
    }

    private void drawHP(Graphics g) {
        int x = 810;
        int y = 805;

        g.setColor(Color.WHITE);
        g.drawRect(x - 2, y - 2, 304, 34);
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, (int) ((hp / 92.0) * 300), 30);
        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        String text = "Chara  Lv 19   HP: " + hp + " / 92";
        g.drawString(text, (1920 - g.getFontMetrics().stringWidth(text)) / 2, y - 10);

    }

}