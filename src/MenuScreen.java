import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JFrame {

    private Image titleImage;


    public MenuScreen() {

        setTitle("Sans Fight - Menu");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load Undertale title image
        titleImage = new ImageIcon(getClass().getResource("/Resources/Undertale.png")).getImage();

        // Create panel with black background
        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight()); // Full black background

                // Draw title image at the top center
                int titleWidth = 1800;
                int titleHeight = 600;
                int titleX = (getWidth() - titleWidth) / 2;
                int titleY = -100;
                g.drawImage(titleImage, titleX, titleY, titleWidth, titleHeight, this);

            }

        };

        panel.setLayout(null);

        // Load "Fight" button image
        JButton startButton = new JButton(new ImageIcon(getClass().getResource("/Resources/Fight.jpg")));

        // Get button size from image
        ImageIcon buttonIcon = (ImageIcon) startButton.getIcon();
        int buttonWidth = buttonIcon.getIconWidth();
        int buttonHeight = buttonIcon.getIconHeight();

        // Set button position dynamically
        int buttonX = (1920 - buttonWidth) / 2;
        int buttonY = 600; // Below the title

        startButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Close menu
                new BattleScreen(); // Open battle screen

            }
        });

        panel.add(startButton);
        add(panel);
        setVisible(true);

    }
}
