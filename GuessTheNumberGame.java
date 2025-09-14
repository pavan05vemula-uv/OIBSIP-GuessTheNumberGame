import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessTheNumberGame extends JFrame implements ActionListener {
    // Components
    JLabel titleLabel, instructionLabel, feedbackLabel, scoreLabel, attemptLabel;
    JTextField inputField;
    JButton guessButton, nextRoundButton;

    // Game logic variables
    int targetNumber;
    int maxAttempts = 7;
    int currentAttempt = 0;
    int score = 0;
    int round = 1;

    public GuessTheNumberGame() {
        // Frame setup
        setTitle("🎮 Guess The Number - Advanced Game");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(250, 250, 250));

        // Fonts
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font feedbackFont = new Font("Segoe UI", Font.BOLD, 16);

        // Labels
        titleLabel = new JLabel("🔢 Guess The Number Game", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(0, 102, 204));

        instructionLabel = new JLabel("Guess a number between 1 and 100", JLabel.CENTER);
        instructionLabel.setFont(textFont);

        feedbackLabel = new JLabel(" ", JLabel.CENTER);
        feedbackLabel.setFont(feedbackFont);
        feedbackLabel.setForeground(new Color(0, 153, 76));

        attemptLabel = new JLabel("Attempts: 0 / " + maxAttempts, JLabel.CENTER);
        attemptLabel.setFont(textFont);

        scoreLabel = new JLabel("Score: 0 | Round: 1", JLabel.CENTER);
        scoreLabel.setFont(textFont);

        // Text field
        inputField = new JTextField(10);
        inputField.setFont(textFont);
        inputField.setHorizontalAlignment(JTextField.CENTER);

        // Buttons
        guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(0, 153, 76));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guessButton.setFocusPainted(false);
        guessButton.addActionListener(this);

        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setBackground(new Color(0, 102, 204));
        nextRoundButton.setForeground(Color.WHITE);
        nextRoundButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextRoundButton.setFocusPainted(false);
        nextRoundButton.addActionListener(e -> startNewRound());
        nextRoundButton.setVisible(false);

        // Layout
        setLayout(new GridLayout(8, 1, 10, 5));
        add(titleLabel);
        add(instructionLabel);
        add(inputField);
        add(guessButton);
        add(feedbackLabel);
        add(attemptLabel);
        add(scoreLabel);
        add(nextRoundButton);

        // Start the first round
        generateRandomNumber();

        setVisible(true);
    }

    // Generate a new random number between 1 and 100
    private void generateRandomNumber() {
        Random rand = new Random();
        targetNumber = rand.nextInt(100) + 1;
        currentAttempt = 0;
        attemptLabel.setText("Attempts: 0 / " + maxAttempts);
        feedbackLabel.setText(" ");
        inputField.setText("");
        inputField.setEnabled(true);
        guessButton.setEnabled(true);
        nextRoundButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid integer between 1 and 100!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (guess < 1 || guess > 100) {
            JOptionPane.showMessageDialog(this, "Number must be between 1 and 100!", "Invalid Range", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentAttempt++;
        attemptLabel.setText("Attempts: " + currentAttempt + " / " + maxAttempts);

        if (guess == targetNumber) {
            int points = (maxAttempts - currentAttempt + 1) * 10;
            score += points;
            feedbackLabel.setText("🎉 Correct! You earned " + points + " points!");
            inputField.setEnabled(false);
            guessButton.setEnabled(false);
            nextRoundButton.setVisible(true);
        } else if (guess < targetNumber) {
            feedbackLabel.setText("📉 Too low! Try again.");
        } else {
            feedbackLabel.setText("📈 Too high! Try again.");
        }

        if (currentAttempt >= maxAttempts && guess != targetNumber) {
            feedbackLabel.setText("❌ Out of attempts! Number was: " + targetNumber);
            inputField.setEnabled(false);
            guessButton.setEnabled(false);
            nextRoundButton.setVisible(true);
        }

        scoreLabel.setText("Score: " + score + " | Round: " + round);
    }

    private void startNewRound() {
        round++;
        generateRandomNumber();
        scoreLabel.setText("Score: " + score + " | Round: " + round);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuessTheNumberGame());
    }
}
