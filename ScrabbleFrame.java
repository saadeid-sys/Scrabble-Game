import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
/**
 * The ScrabbleFrame class initializes the scrabble board by building its different squares
 * and creating buttons for the user to use
 * @author Saad Eid
 */
public class ScrabbleFrame implements ScrabbleView, Runnable{
    public JFrame frame;
    public Bag letterBag;
    public Player p1, p2, currPlayer;
    public JPanel scoreBoard, tileBenchPanel, gameButtonPanel, gridPanel;
    public JLabel score1, score2, turn;
    public Square selectedLetter;
    public List<Square> squaresToSubmit;
    public ScrabbleModel board, tempBoard;
    public ScrabbleController scrabbleController;
    JButton undo = new JButton("Undo");
    public JButton[][] buttons;

    public ScrabbleFrame(){
        frame = new JFrame("Scrabble");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        letterBag = new Bag();
        scoreBoard = new JPanel();
        tileBenchPanel = new JPanel();
        gameButtonPanel = new JPanel();
        gridPanel = new JPanel(new GridLayout(15,15));
        buttons = new JButton[15][15];

        //variables to help with player input
        selectedLetter = new Square(-1, -1);
        squaresToSubmit = new LinkedList<Square>();
    }

    public void buildScorePanel(){
        scoreBoard.setLayout(new GridLayout(1, 3));
        score1 = new JLabel("\t\t\t\t"+ p1.getName() + "'s Score is " + p1.getScore() + " points");
        score2 = new JLabel(p2.getName() + "'s Score is " + p2.getScore() + " points");
        turn = new JLabel("\t\t\t\t\t  It's " + p1.getName() + "'s Turn");

        scoreBoard.add(score1);
        scoreBoard.add(turn);
        scoreBoard.add(score2);
    }

    public void buildTileBenchPanel(){
        currPlayer = (p1.getTurn()? p1 : p2);
        for (int i = 0; i < currPlayer.getBenchSize(); i++) {
            char c = currPlayer.getLetter(i);
            JButton b = new JButton(Character.toString(c));
            tileBenchPanel.add(b);
            b.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean blank = b.getText().equals("");
                    boolean selected = selectedLetter.hasLetter();
                    if (!blank && !selected) {
                        selectedLetter.setLetter(b.getText().charAt(0));
                        b.setText("");
                    }
                }
            });
        }
    }

    /**
     * Creates a game board for current state and temporary board for pre-submission state
     */
    public void createScrabbleModels(){
        try {
            board = new ScrabbleModel("wordlist.10000.txt", letterBag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            tempBoard = new ScrabbleModel("wordlist.10000.txt", letterBag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scrabbleController = new ScrabbleController(tempBoard, board, this);
    }
    public void run() {
        p1 = new Player(getUsername("Player 1"), letterBag.drawTiles(7), true);
        p2 = new Player("AI Player", letterBag.drawTiles(7), false);

        buildScorePanel();
        buildTileBenchPanel();
        createScrabbleModels();
        //buildGridPanel();

        Square[][] currBoard = tempBoard.getCurrentBoard();
        for (int row = 0; row < currBoard.length; row++) {
            for (int col = 0; col < currBoard[row].length; col++) {
                Square sq = currBoard[row][col];
                sq.addMouseListener(scrabbleController);
                sq.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if ((!sq.hasLetter()) && (selectedLetter.hasLetter())) {
                            sq.setLetter(selectedLetter.getLetter());
                            sq.repaint();
                            squaresToSubmit.add(sq);
                            selectedLetter.setLetter((char)-1);
                        }
                    }
                });
            }
        }

        //undo resets the tileRack to the player's bench
        //also resets the game board
        undo.setActionCommand("Undo");
        undo.addActionListener(scrabbleController);

        //player can opt to pass instead of submitting a move
        JButton pass = new JButton("Pass");
        pass.setActionCommand("Pass");
        pass.addActionListener(scrabbleController);

        //swap tiles, but give up your turn
        JButton swap = new JButton("Swap Tiles");
        swap.setActionCommand("Swap Tiles");
        swap.addActionListener(scrabbleController);

        //submit button
        JButton submit = new JButton("Submit");
        submit.setActionCommand("Submit");
        submit.addActionListener(scrabbleController);

        JButton checkTilesLeft = new JButton("Tiles Left");
        checkTilesLeft.setActionCommand("Tiles Left");
        checkTilesLeft.addActionListener(scrabbleController);

        //add all the buttons
        gameButtonPanel.add(undo);
        gameButtonPanel.add(submit);
        gameButtonPanel.add(pass);
        gameButtonPanel.add(swap);
        gameButtonPanel.add(checkTilesLeft);

        //add panels to frame
        frame.add(scoreBoard);
        frame.add(tempBoard);
        //frame.add(gridPanel);
        frame.add(tileBenchPanel);
        frame.add(gameButtonPanel);

        //top level stuff
        frame.validate();
        frame.setResizable(true);
        frame.setSize(670, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private void buildGridPanel() {
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                JButton b = new JButton(" ");
                b.setActionCommand(i + " " + j);
                buttons[i][j] = b;
                b.addActionListener(scrabbleController);
                gridPanel.add(b);
            }
        }
    }

    /**
     *
     * @param tempBoard - current state of board
     * @param actualBoard - board to reset to
     */
    void resetBoard(ScrabbleModel tempBoard, ScrabbleModel actualBoard) {
        Square[][] currBoard = tempBoard.getCurrentBoard();
        Square[][] oldBoard = actualBoard.getCurrentBoard();
        for (int row = 0; row < currBoard.length; row++) {
            for (int col = 0; col < currBoard[row].length; col++) {
                Square sq = currBoard[row][col];
                Square oldSq = oldBoard[row][col];
                sq.setLetter(oldSq.getLetter());
                sq.repaint();
            }
        }
    }


    private String getUsername(String player) {
        String tgt = JOptionPane.showInputDialog(null, player + ", Enter Your Name:");
        if (tgt==null) return getUsername(player);
        else return tgt;
    }

    @Override
    public void update(ScrabbleEvent e) {
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new ScrabbleFrame());
    }
}
