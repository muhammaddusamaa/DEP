import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class QuizFrame extends JFrame {
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JButton nextButton;
    private JButton finishButton;
    private QuestionBank questionBank;
    private int currentQuestionIndex;
    private int score;
    private boolean questionAnswered;
    private Timer timer;
    private int timeLeft;
    private JLabel timerLabel;

    public QuizFrame() {
        setTitle("Quiz Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#f0f0f0"));

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.NORTH);

        timerLabel = new JLabel("Time left: 10 seconds");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        questionPanel.add(timerLabel, BorderLayout.SOUTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        optionButtons = new JButton[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            optionButtons[i].setBackground(Color.decode("#B1D4E0"));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setPreferredSize(new Dimension(200, 40));
            optionButtons[i].setBorder(BorderFactory.createLineBorder(Color.decode("#2980b9"), 2, true));
            optionButtons[i].setOpaque(true);
            optionButtons[i].setContentAreaFilled(true);
            optionButtons[i].setBorderPainted(true);
            optionButtons[i].addActionListener(new OptionButtonListener(i));
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        nextButton = new JButton("Next");
        finishButton = new JButton("Finish");
        customizeButton(nextButton);
        customizeButton(finishButton);
        controlPanel.add(nextButton);
        controlPanel.add(finishButton);
        add(controlPanel, BorderLayout.PAGE_END);

        questionBank = new QuestionBank();
        currentQuestionIndex = 0;
        loadNextQuestion();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!questionAnswered) {
                    loadNextQuestion();
                }
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ResultFrame resultFrame = new ResultFrame(score, questionBank.getTotalQuestions());
                resultFrame.setVisible(true);
            }
        });
    }

    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.decode("#B1D4E0"));
        button.setForeground(Color.decode("#2980b9"));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex >= questionBank.getTotalQuestions()) {
            dispose();
            ResultFrame resultFrame = new ResultFrame(score, questionBank.getTotalQuestions());
            resultFrame.setVisible(true);
            return;
        }

        Question currentQuestion = questionBank.getQuestion(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestionText());
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setEnabled(true);
        }

        currentQuestionIndex++;
        questionAnswered = false;
        startTimer();
    }

    private void startTimer() {
        timeLeft = 20;
        timerLabel.setText("Time left: " + timeLeft + " seconds");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    timer.cancel();
                    markQuestionAsZero();
                    loadNextQuestion();
                }
            }
        }, 1000, 1000);
    }

    private void markQuestionAsZero() {
        questionAnswered = true;
    }

    private class OptionButtonListener implements ActionListener {
        private int optionIndex;

        public OptionButtonListener(int optionIndex) {
            this.optionIndex = optionIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Question currentQuestion = questionBank.getQuestion(currentQuestionIndex - 1);
            if (currentQuestion.getCorrectOptionIndex() == optionIndex) {
                score++;
            }

            for (JButton button : optionButtons) {
                button.setEnabled(false);
            }

            questionAnswered = true;
            loadNextQuestion();
        }
    }
}
