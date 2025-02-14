import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JFrame {
    public MenuScreen() {
        setTitle("Sans Fight - Menu");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute positioning (manual coordinates)
        getContentPane().setBackground(Color.BLACK); // Black background like Undertale

        // Load top image (Undertale logo)
        ImageIcon topImageIcon = new ImageIcon(getClass().getResource("/Resources/Undertale.png"));
        JLabel topImageLabel = new JLabel(topImageIcon);
        topImageLabel.setBounds(0, 50, 1920, 300); // Adjust position & size if needed

        // Load button image (Fight button)
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource("/Resources/Fight.jpg"));
        JButton playButton = new JButton(buttonIcon);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);

        // Position button in the center
        int buttonWidth = buttonIcon.getIconWidth();
        int buttonHeight = buttonIcon.getIconHeight();
        int buttonX = (1920 - buttonWidth) / 2;
        int buttonY = 500;

        playButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Button action (start game)
        playButton.addActionListener(e -> {
            dispose();
            System.out.println("Game Started!");
        });

        // Add elements
        add(topImageLabel);
        add(playButton);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuScreen();
    }
}
