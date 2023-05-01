import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class TicTacToe extends Frame {
    Button newGame;
    Label winner;
    Label[] squares = new Label[9];
    Label[] squareBG = new Label[9];
    String currentPlayer = "X";
    static int width = 400, height = 400;
    boolean gameOver = false;

    TicTacToe() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        setFont(new Font("Times", Font.PLAIN, 12));
        setLayout(null);
        Font currentFont = this.getFont();

        Label title = new Label("Tic-Tac-Toe", Label.CENTER);
        title.setFont(new Font(currentFont.getFontName(), Font.BOLD, 30));
        title.setBounds(0, 60, width, 40);
        add(title);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label l = new Label("", Label.CENTER);
                l.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        if (gameOver)
                            return;
                        if (!l.getText().equals(""))
                            return;
                        l.setText(currentPlayer);
                        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                        if (!checkWinner().equals("")) {
                            gameOver = true;
                            winner.setText("Winner: " + checkWinner());
                            return;
                        }
                        opponentMove();
                    }
                });
                l.setFont(new Font(currentFont.getFontName(), Font.BOLD, 30));
                l.setBounds(j * 50 + 123, i * 50 + 120, 50, 50);
                l.setCursor(new Cursor(Cursor.HAND_CURSOR));
                squares[i * 3 + j] = l;
                add(l);

                Label bg = new Label("");
                bg.setBackground(Color.BLACK);
                Rectangle b = l.getBounds();
                bg.setBounds(b.x - 2, b.y - 2, b.width + 4, b.height + 4);
                squareBG[i * 3 + j] = bg;
                add(bg);
            }
        }

        newGame = new Button("New Game");
        newGame.addActionListener(e -> {
            for (Label l : squares) {
                l.setText("");
                l.setBackground(Color.WHITE);
            }
            winner.setText("");
            gameOver = false;
            currentPlayer = "X";
        });
        newGame.setIgnoreRepaint(true);
        newGame.setBounds(161, 300, 75, 30);
        add(newGame);

        winner = new Label("", Label.CENTER);
        winner.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 20));
        winner.setBounds(0, height - 50, width, 30);
        add(winner);
    }

    public String checkWinner() {
        int[][] combinations = new int[][] {
                new int[] { 0, 1, 2 },
                new int[] { 3, 4, 5 },
                new int[] { 6, 7, 8 },
                new int[] { 0, 3, 6 },
                new int[] { 1, 4, 7 },
                new int[] { 2, 5, 8 },
                new int[] { 0, 4, 8 },
                new int[] { 2, 4, 6 }
        };

        for (int[] c : combinations) {
            if (getTextOf(c[0]).equals(""))
                continue;
            if (getTextOf(c[0]).equals(getTextOf(c[1])) && getTextOf(c[0]).equals(getTextOf(c[2]))) {
                for (int index : c)
                    getLabel(index).setBackground(new Color(0, 255, 0));
                return getTextOf(c[0]);
            }
        }
        return "";
    }

    public String getTextOf(int index) {
        return getLabel(index).getText();
    }

    public Label getLabel(int index) {
        return squares[index];
    }

    public void opponentMove() {
        Random rand = new Random();
        while (true) {
            int index = rand.nextInt(9);
            if (!getTextOf(index).equals(""))
                continue;
            getLabel(index).setText(currentPlayer);
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            if (!checkWinner().equals("")) {
                gameOver = true;
                winner.setText("Winner: " + checkWinner());
                return;
            }
            break;
        }
    }

    public static void main(String[] args) {
        TicTacToe ttt = new TicTacToe();
        ttt.setSize(width, height);
        ttt.setResizable(false);
        ttt.setLocationRelativeTo(null);
        ttt.setTitle("TicTacToe");
        ttt.setVisible(true);
    }
}