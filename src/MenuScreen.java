import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuScreen extends JFrame {

    private Image title;

    public MenuScreen() {
        setSize(1920, 1080);

        // Load title image
        title = new ImageIcon(getClass().getResource("/Resources/Undertale.png")).getImage();

        // Create panel
        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 1920, 1080);
                g.drawImage(title, 60, -100, 1800, 600, this);

            }
        };

        panel.setLayout(null);

        // Create button
        JButton start = new JButton(new ImageIcon(getClass().getResource("/Resources/Fight.jpg")));
        ImageIcon icon = (ImageIcon) start.getIcon();
        start.setBounds((1920 - icon.getIconWidth()) / 2, 600, icon.getIconWidth(), icon.getIconHeight());

        // Button action
        start.addActionListener(e -> {

            setVisible(false);
            new BattleScreen();

        });

        panel.add(start);
        add(panel);
        setVisible(true);

    }
}