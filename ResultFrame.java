import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultFrame extends JFrame {

    public ResultFrame(int score, int totalQuestions) {
        // Set up the frame
        setTitle("Quiz Result");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set a modern background color
        getContentPane().setBackground(Color.decode("#f0f0f0")); // Light gray background

        // Result Panel
        JPanel resultPanel = new JPanel();
        JLabel resultLabel = new JLabel("Your score is: " + score + "/" + totalQuestions);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultPanel.add(resultLabel);
        add(resultPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}