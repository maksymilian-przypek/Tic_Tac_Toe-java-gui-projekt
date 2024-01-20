import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GameBoard extends JFrame
{
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button21;
    private JButton button22;
    private JButton button23;
    private JButton button31;
    private JButton button32;
    private JButton button33;
    private JButton buttonExit;
    private JPanel JMainPanel;
    private JPanel gamePanel;
    private JButton buttonReset;
    private boolean isPlayer1Turn = true;
    private boolean gameComputer;
    private static char[][] gameBoard = new char[3][3];
    private final JButton[] buttonBoard = new JButton[]{button11, button12, button13, button21, button22, button23, button31, button32, button33};
    public static final ArrayList<Object[]> player = new ArrayList<>();

    public GameBoard(boolean gameComputer)
    {
        super("Tic-Tac-Toe");
        this.gameComputer = gameComputer;
        this.setContentPane(this.JMainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        resetBoard();
        initializeButtons();
        actionListener();
    }

    private void initializeButtons()
    {
        int i = 0;
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                buttonBoard[i].putClientProperty("id", "" + row + col);
                i++;
            }
        }
    }

    private void actionListener()
    {
        buttonExit.addActionListener(e -> System.exit(0));
        buttonReset.addActionListener(e -> resetBoard());

        ActionListener listener = e ->
        {
            JButton pressedButton = (JButton) e.getSource();
            String buttonId = (String) pressedButton.getClientProperty("id");
            int row = Integer.parseInt(buttonId.substring(0, 1));
            int col = Integer.parseInt(buttonId.substring(1, 2));

            if (gameBoard[row][col] == ' ')
            {
                if (gameComputer)
                {
                    makeMove(pressedButton, row, col, 'X');
                    if (checkWin() || !empty()) return;
                    makeComputerMove();
                    checkWin();
                }
                else
                {
                    char currentPlayerMark = isPlayer1Turn ? 'X' : 'O';
                    makeMove(pressedButton, row, col, currentPlayerMark);
                    isPlayer1Turn = !isPlayer1Turn;
                    checkWin();
                }
            }
        };

        for (JButton button : buttonBoard)
        {
            button.addActionListener(listener);
        }
    }

    private void makeMove(JButton button, int row, int col, char playerMark)
    {
        button.setText(String.valueOf(playerMark));
        button.setEnabled(false);
        gameBoard[row][col] = playerMark;
    }

    private void makeComputerMove()
    {
        int[] bestMove = findBestMove();
        int row = bestMove[0];
        int col = bestMove[1];
        JButton button = buttonBoard[row * 3 + col];
        makeMove(button, row, col, 'O');
    }

    private int[] findBestMove()
    {
        int bestVal = -1000;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (gameBoard[i][j] == ' ')
                {
                    gameBoard[i][j] = 'O';
                    int moveVal = minimax(0, false);
                    gameBoard[i][j] = ' ';

                    if (moveVal > bestVal)
                    {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int depth, boolean isMax)
    {
        int score = evaluate();

        if (score != 0) return score;
        if (!empty()) return 0;

        if (isMax)
        {
            int best = -1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (gameBoard[i][j] == ' ')
                    {
                        gameBoard[i][j] = 'O';
                        best = Math.max(best, minimax(depth + 1, false));
                        gameBoard[i][j] = ' ';
                    }
                }
            }
            return best;
        }
        else
        {
            int best = 1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (gameBoard[i][j] == ' ')
                    {
                        gameBoard[i][j] = 'X';
                        best = Math.min(best, minimax(depth + 1, true));
                        gameBoard[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }

    private int evaluate()
    {
        for (int i = 0; i < 3; i++)
        {
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2])
            {
                if (gameBoard[i][0] == 'O') return +10;
                else if (gameBoard[i][0] == 'X') return -10;
            }

            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i])
            {
                if (gameBoard[0][i] == 'O') return +10;
                else if (gameBoard[0][i] == 'X') return -10;
            }
        }

        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2])
        {
            if (gameBoard[0][0] == 'O') return +10;
            else if (gameBoard[0][0] == 'X') return -10;
        }

        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0])
        {
            if (gameBoard[0][2] == 'O') return +10;
            else if (gameBoard[0][2] == 'X') return -10;
        }

        return 0;
    }

    private boolean checkWin()
    {
        String info = "";
        Date date = new Date();
        int score = evaluate();
        if (score == 10)
        {
            if (gameComputer)
            {
                JOptionPane.showMessageDialog(this, "Computer wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                info = date.toString() + " - computer wins";
                fileWriteInfo(info);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Player 2 wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                info = date.toString() + " - player 2 wins {" + player.get(1)[0] + ", " + player.get(1)[1] + ", lat " + player.get(1)[3] + "}";
                fileWriteInfo(info);
            }
            resetBoard();
            return true;
        }
        else if (score == -10)
        {
            if (gameComputer)
            {
                JOptionPane.showMessageDialog(this, "Player wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                info = date.toString() + " - player wins {" + player.get(0)[0] + ", " + player.get(0)[1] + ", lat " + player.get(0)[3] + "}";
                fileWriteInfo(info);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Player 1 wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                info = date.toString() + " - player 1 wins {" + player.get(0)[0] + ", " + player.get(0)[1] + ", lat " + player.get(0)[3] + "}";
                fileWriteInfo(info);
            }
            resetBoard();
            return true;
        }
        else if (!empty())
        {
            JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            info = date.toString() + " - draw";
            fileWriteInfo(info);
            resetBoard();
            return true;
        }
        return false;
    }

    private void fileWriteInfo(String info)
    {
        try
        {
            FileWriter fileReader = new FileWriter("src\\info.txt", true);
            fileReader.write(info + "\n");
            fileReader.close();
        }
        catch (IOException e) { System.out.println("Error"); }
    }

    private boolean empty()
    {
        for (char[] row : gameBoard)
        {
            for (char cell : row)
            {
                if (cell == ' ')
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetBoard()
    {
        isPlayer1Turn = true;
        gameBoard = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        for (JButton button : buttonBoard)
        {
            button.setEnabled(true);
            button.setText("");
        }
    }

    public static void main(String[] args)
    {
        new GameBoard(true);  // Set to 'false' for a two-player game
    }
}
