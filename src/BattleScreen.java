import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattleScreen extends JFrame {
    private Rectangle battleBox;
    private Soul me;
    private JPanel battlePanel;
    private boolean up, down, left, right;
    private boolean menuActive = true;

    private JButton fightButton, actButton, itemButton, mercyButton;

    // Sans attack system
    private List<SansAttack> attacks = new ArrayList<>();
    private long lastAttackTime = 0;
    private int attackInterval = 1000; // Time between attacks in ms

    public BattleScreen() {
        setTitle("Battle Box");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define battle box
        me = new Soul(700, 450, "red", 40, 40);

        // Panel for the battle
        battlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Draw battle box
                g.setColor(Color.WHITE);
                g.drawRect(battleBox.x, battleBox.y, battleBox.width, battleBox.height);

                // Draw soul
                me.draw(g);

                // Draw all attacks
                for (SansAttack attack : attacks) {
                    attack.draw(g);
                }
            }
        };
        battlePanel.setFocusable(true);
        battlePanel.requestFocusInWindow();
        add(battlePanel);

        setupKeyBindings();

        // Menu buttons
        fightButton = createButton("Fight", 660, 650);
        actButton = createButton("Act", 860, 650);
        itemButton = createButton("Item", 1060, 650);
        mercyButton = createButton("Mercy", 1260, 650);

        battlePanel.setLayout(null);
        battlePanel.add(fightButton);
        battlePanel.add(actButton);
        battlePanel.add(itemButton);
        battlePanel.add(mercyButton);

        // Game loop timer
        Timer timer = new Timer(16, e -> {
            if (!menuActive) moveSoul();

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAttackTime > attackInterval) {
                spawnAttack();
                lastAttackTime = currentTime;
            }

            updateAttacks();
            battlePanel.repaint();
        });
        timer.start();

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 160, 60);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setRolloverEnabled(false); // disable hover effect
        return button;
    }

    private void setupKeyBindings() {
        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed UP"), "upPressed");
        battlePanel.getActionMap().put("upPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { up = true; }
        });
        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "upReleased");
        battlePanel.getActionMap().put("upReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { up = false; }
        });

        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed DOWN"), "downPressed");
        battlePanel.getActionMap().put("downPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { down = true; }
        });
        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "downReleased");
        battlePanel.getActionMap().put("downReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { down = false; }
        });

        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed LEFT"), "leftPressed");
        battlePanel.getActionMap().put("leftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { left = true; }
        });
        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");
        battlePanel.getActionMap().put("leftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { left = false; }
        });

        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed RIGHT"), "rightPressed");
        battlePanel.getActionMap().put("rightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { right = true; }
        });
        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased");
        battlePanel.getActionMap().put("rightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { right = false; }
        });

        battlePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
        battlePanel.getActionMap().put("enterPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                menuActive = !menuActive;
            }
        });
    }

    private void moveSoul() {
        if (up) me.move(0, -5);
        if (down) me.move(0, 5);
        if (left) me.move(-5, 0);
        if (right) me.move(5, 0);
    }

    private void spawnAttack() {
        int attackY = battleBox.y + (int) (Math.random() * (battleBox.height - 20));
        attacks.add(new SansAttack(1920, attackY, 40, 20, 6));
    }

    private void updateAttacks() {
        Iterator<SansAttack> it = attacks.iterator();
        while (it.hasNext()) {
            SansAttack attack = it.next();
            attack.update();
            if (attack.checkCollision(me)) {
                takeDamage(5); // Soul hit
                it.remove();
            } else if (!attack.isActive()) {
                it.remove();
            }
        }
    }

    private void takeDamage(int amount) {
        System.out.println("Ouch! Took " + amount + " damage!");
        // You can add health here if you want to track HP
    }

    public static void main(String[] args) {
        new BattleScreen();
    }
}
