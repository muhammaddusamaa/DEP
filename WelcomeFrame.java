import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("Welcome to the Quiz!");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#f0f0f0"));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#4A90E2"));
        JLabel headerLabel = new JLabel("Welcome to the Quiz!");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                QuizFrame quizFrame = new QuizFrame();
                quizFrame.setVisible(true);
            }
        });
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WelcomeFrame welcomeScreen = new WelcomeFrame();
                welcomeScreen.setVisible(true);
            }
        });
    }
}
