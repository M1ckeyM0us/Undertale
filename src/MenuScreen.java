import javax.swing.*;
import java.awt.*;

public class MenuScreen extends JFrame {
    public MenuScreen() {
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        // Load top image (Undertale logo)
        ImageIcon topImageIcon = new ImageIcon(getClass().getResource("/Resources/Undertale.png"));
        JLabel topImageLabel = new JLabel(topImageIcon);
        topImageLabel.setBounds(0, 50, 1920, 300); // Adjust if needed

        // Load button image (Fight button)
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource("/Resources/Fight.jpg"));
        JButton playButton = new JButton(buttonIcon);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);

        // Center the button
        int buttonWidth = buttonIcon.getIconWidth();
        int buttonHeight = buttonIcon.getIconHeight();
        int buttonX = (1920 - buttonWidth) / 2;
        int buttonY = 500;

        playButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        // Start BattleScreen when button is clicked
        playButton.addActionListener(e -> {
            dispose(); // Close menu
            new BattleScreen(); // Open battle screen
        });

        add(topImageLabel);
        add(playButton);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuScreen();
    }
}
