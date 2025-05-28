import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private TicTacToeGame game = new TicTacToeGame();
    private boolean vsAI = true;
    private boolean iaDificil = false;
    private int scoreX = 0, scoreO = 0;
    private JLabel scoreLabel = new JLabel("X: 0 | O: 0");
    private JLabel infoLabel = new JLabel("Vez do Jogador X");

    public TicTacToeGUI() {
        setTitle("Jogo da Velha");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(440, 520);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        // Painel superior com placar e informações
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        scoreLabel.setForeground(new Color(30, 144, 255));
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        infoLabel.setForeground(Color.DARK_GRAY);
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.add(scoreLabel);
        topPanel.add(infoLabel);

        // Painel do tabuleiro
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(220, 230, 241));
        initializeButtons(boardPanel);

        // Painel inferior com botões de ação
        JPanel bottomPanel = new JPanel();
        JButton resetButton = new JButton("Reiniciar");
        resetButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        resetButton.addActionListener(_e -> resetGame());
        JButton modeButton = new JButton("Trocar Modo");
        modeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        modeButton.addActionListener(_e -> chooseMode());
        JButton diffButton = new JButton("Dificuldade IA");
        diffButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        diffButton.addActionListener(e -> chooseDifficulty());
        bottomPanel.add(resetButton);
        bottomPanel.add(modeButton);
        bottomPanel.add(diffButton);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        chooseMode();
        setVisible(true);
    }

    private void initializeButtons(JPanel panel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.WHITE);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(_e -> handleMove(row, col));
                panel.add(buttons[i][j]);
            }
        }
    }

    private void handleMove(int row, int col) {
        if (!game.makeMove(row, col)) return;
        updateBoard();
        playSound("/click.wav");
        animateButton(buttons[row][col]);
        if (game.checkWin()) {
            showEndMessage("Jogador " + game.getCurrentPlayer() + " venceu!");
            return;
        }
        if (game.isDraw()) {
            showEndMessage("Empate!");
            return;
        }
        game.switchPlayer();
        infoLabel.setText("Vez do Jogador " + game.getCurrentPlayer());
        if (vsAI && game.getCurrentPlayer() == 'O') {
            makeAIMove();
        }
    }

    private void makeAIMove() {
        Timer timer = new Timer(400, _e -> {
            int[] move = iaDificil ? game.getAIMoveMinimax() : game.getAIMoveRandom();
            if (move != null) {
                game.makeMove(move[0], move[1]);
                updateBoard();
                playSound("/click.wav");
                animateButton(buttons[move[0]][move[1]]);
                if (game.checkWin()) {
                    showEndMessage("Computador venceu!");
                } else if (game.isDraw()) {
                    showEndMessage("Empate!");
                } else {
                    game.switchPlayer();
                    infoLabel.setText("Vez do Jogador " + game.getCurrentPlayer());
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(board[i][j] == ' ' ? "" : String.valueOf(board[i][j]));
                if (board[i][j] == 'X') {
                    buttons[i][j].setForeground(Color.BLACK);
                } else if (board[i][j] == 'O') {
                    buttons[i][j].setForeground(Color.RED);
                } else {
                    buttons[i][j].setForeground(Color.BLACK);
                }
            }
        }
    }

    private void resetGame() {
        game.reset();
        updateBoard();
        infoLabel.setText("Vez do Jogador " + game.getCurrentPlayer());
    }

    private void showEndMessage(String message) {
        playSound("/win.wav");
        if (message.contains("X")) scoreX++;
        if (message.contains("O") || message.contains("Computador")) scoreO++;
        updateScore();
        JOptionPane.showMessageDialog(this, message);
        resetGame();
    }

    private void updateScore() {
        scoreLabel.setText("X: " + scoreX + " | O: " + scoreO);
    }

    // Animação simples: botão pisca rapidamente
    private void animateButton(JButton button) {
        Color original = button.getBackground();
        Timer timer = new Timer(80, null);
        timer.addActionListener(new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent e) {
                if (count % 2 == 0) button.setBackground(Color.YELLOW);
                else button.setBackground(original);
                count++;
                if (count > 3) timer.stop();
            }
        });
        timer.start();
    }

    // Sons: arquivos .wav devem estar em /resources e no classpath
    private void playSound(String filename) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(filename);
            if (audioSrc == null) return;
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            // Ignorar erros de som
        }
    }

    // Escolher modo de jogo
    private void chooseMode() {
        String[] options = {"Contra Humano", "Contra Computador"};
        int mode = JOptionPane.showOptionDialog(this, "Escolha o modo de jogo:",
            "Modo de Jogo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[1]);
        vsAI = (mode == 1);
        resetGame();
    }

    // Escolher dificuldade da IA
    private void chooseDifficulty() {
        String[] options = {"Fácil (Aleatório)", "Difícil (Minimax)"};
        int diff = JOptionPane.showOptionDialog(this, "Escolha a dificuldade da IA:",
            "Dificuldade IA", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        iaDificil = (diff == 1);
        resetGame();
    }

    public static void main(String[] args) {
        // Para sons, coloque click.wav e win.wav em /resources e adicione ao classpath
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
