import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Card Counter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            
            // Use CardLayout to switch between Menu and Game
            CardLayout cl = new CardLayout();
            JPanel mainPanel = new JPanel(cl);
            
            MenuPanel menu = new MenuPanel(mainPanel, cl);
            mainPanel.add(menu, "MENU");
            
            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}