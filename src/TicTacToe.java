import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * This class has all the functions needed to play a game of Tic Tac Toe.
 * Either with 2 players or VS the computer.
 */
public class TicTacToe {

    // Setting up variables
    private static int turn = 1; // Even: X    Uneven: O
    private static JButton[] buttons = new JButton[9];
    private static final int NONE = 0;
    private static final int WIN_X = 1;
    private static final int WIN_O = 2;
    private static final int TIE = 3;
    private static final Font buttonFont = new Font("Arial", Font.BOLD, 35);

    private static JList selectPlayer = new JList(new String[] {"2 Players", "VS Computer"});
    private static boolean cpu = false;

    /**
     * run()
     * Call this method to start the game.
     * It asks the player to select 2 Player or CPU mode.
     */
    public static void run() {
        while(selectPlayer.getSelectedIndices().length < 1) {
            JOptionPane.showMessageDialog(null, selectPlayer, "Select your play mode!", JOptionPane.PLAIN_MESSAGE);
        }
        if (selectPlayer.getSelectedIndices()[0] == 1){
            cpu = true;
        }
        create_JFrame();
    }

    /**
     * create_JFrame()
     * Sets up the game window.
     */
    private static void create_JFrame() {
        // Setting up JFrame
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setSize(300, 350);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panesHolder = new JPanel();
        JPanel infoPane = new JPanel();
        JPanel playPane = new JPanel();
        JPanel bottomPane = new JPanel();
        JPanel buttonPane = new JPanel();

        infoPane.setLayout(new FlowLayout());
        playPane.setLayout(new GridLayout(3,3));
        bottomPane.setLayout(new FlowLayout());
        buttonPane.setLayout(new FlowLayout());

        JLabel topLabel = new JLabel("Tic Tac Toe");
        infoPane.add(topLabel);

        JLabel infoLabel = new JLabel("Turn: 1. Player O is playing.");
        bottomPane.add(infoLabel);

        // Create 9 buttons and add action listeners.
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(64,64));
            buttons[i].setFont(buttonFont);
            buttons[i].addActionListener(e ->
                    on_click_action(Arrays.asList(buttons).indexOf(e.getSource()), infoLabel));
            playPane.add(buttons[i]);
        }

        JButton restartButton = new JButton("Reset");
        restartButton.addActionListener(e ->
                restartGame(f, infoLabel));
        buttonPane.add(restartButton);

        panesHolder.setLayout(new BoxLayout(panesHolder, BoxLayout.Y_AXIS));
        panesHolder.add(infoPane);
        panesHolder.add(playPane);
        panesHolder.add(bottomPane);
        panesHolder.add(buttonPane);


        f.add(panesHolder);

        f.setVisible(true);
        f.setLocationRelativeTo(null);


    }

    /**
     * This function gets called when player wants to mark off a field.
     * Handles most of the game logic.
     * @param buttonIndex int - The index of the button clicked [0..8]
     * @param infoLabel JLabel - The JLabel object to change the text.
     */
    private static void on_click_action(int buttonIndex, JLabel infoLabel){
        //System.out.println(buttonIndex);

        // Checks if click is valid; daubs the field and sets next player.
        if(buttons[buttonIndex].isEnabled()){
            String curPlayer = "X";
            if (turn % 2 != 0){
                curPlayer = "O";
            }
            String nextPlayer = "";
            // Button not yet clicked

            if (turn % 2 == 0) {
                // EVEN     X
                buttons[buttonIndex].setText("X");
                nextPlayer = "O";
            }else{
                // UNEVEN   O
                buttons[buttonIndex].setText("O");
                nextPlayer = "X";
            }

            buttons[buttonIndex].setEnabled(false);

            // Determine if game is won/tied
            int status = determine_status(curPlayer);
            if (status != NONE) {
                if (status == TIE) {
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                } else if (status == WIN_X) {
                    JOptionPane.showMessageDialog(null, "Player X wins!");
                } else if (status == WIN_O) {
                    JOptionPane.showMessageDialog(null, "Player O wins!");
                }
                for(int i = 0; i < buttons.length; i++){
                    buttons[i].setEnabled(false);
                }
                return;
            }

            // Progess turn
            turn++;
            infoLabel.setText("Turn: " + turn + ". Player " + nextPlayer + " is playing.");

            // Do CPU turn
            if (cpu && nextPlayer == "X"){
                // CPU LOGIC
                on_click_action(cpu_do_move(), infoLabel);
                // This could potentially recurse infinitely!!
            }
        }

    }

    /**
     * Checks the board to see if any player has won or the game is tied.
     * @param curPlayer String - The player who made the most recent move.
     * @return int - The current condition of the board. NONE, WIN_X, WIN_O, TIE
     */
    private static int determine_status(String curPlayer){
        int status = NONE;

        // Check horizontal wins
        for(int i = 0; i < 9; i+=3){
            if( buttons[i].getText() == curPlayer &&
                buttons[1+i].getText() == curPlayer &&
                buttons[2+i].getText() == curPlayer) {
                if (curPlayer == "X") {
                    return WIN_X;
                }else{
                    return WIN_O;
                }
            }
        }

        // Check vertical wins
        for(int i = 0; i < 3; i++){
            if( buttons[i].getText() == curPlayer &&
                buttons[3+i].getText() == curPlayer &&
                buttons[6+i].getText() == curPlayer){
                if (curPlayer == "X") {
                    return WIN_X;
                }else{
                    return WIN_O;
                }
            }
        }

        // Diagonals
        if(         buttons[0].getText() == curPlayer &&
                    buttons[4].getText() == curPlayer &&
                    buttons[8].getText() == curPlayer){
            if (curPlayer == "X") {
                return WIN_X;
            }else{
                return WIN_O;
            }
        }

        else if(    buttons[2].getText() == curPlayer &&
                    buttons[4].getText() == curPlayer &&
                    buttons[6].getText() == curPlayer){
            if (curPlayer == "X") {
                return WIN_X;
            }else{
                return WIN_O;
            }
        }

        // Tie check
        else if(turn >= 9){
            return TIE;
        }

        return status;
    }

    /**
     * My horrendous CPU logic.
     * Chooses best* move for CPU to take.
     *
     *
     * *may not actually be best move.
     * @return
     */
    private static int cpu_do_move(){
        int move = 0;

        // Always go for center if possible
        if(buttons[4].getText() != "X" && buttons[4].isEnabled()){
            return 4;
        }


        /*
        This part checks for all buttons, to see if they're pressable
        and then does some crazy for-loops to check 9 possible offsets from that button
        to determine if there's a O there (CPU logic will try to block player1 from winning).
        If it does, give that placement +1 score. If the same offset again is another O,
        then player1 has (not always) two fields in a row. These must be blocked at all cost,
        so gives that spot a high score.
        Button with the highest score will be where the CPU plays.
        (With same scores, first button to be checked gets priority.)
        This algorithm doesn't take the grid into consideration so I've added another if-statement
        that catches some of the exceptions.
         */
        int highestScore = -1;
        for(int i = 0; i < buttons.length; i++){
            // Find scores
            if (buttons[i].isEnabled()){
                int curScore = 0;
                for (int j = -4; j <= 4; j++){
                    if(i+j >= 0 && i+j <= 8 && buttons[i+j].getText() == "O"){ // Checks offset x1
                        // Valid spot to check && is already picked by player
                        curScore++;
                        if(i+j+j >= 0 && i+j+j <= 8 && buttons[i+j+j].getText() == "O"){ // Checks offset x2
                            // Two O in a row! Must block!

                            // My crappy algorithm doesn't know that it's in a grid; catch exceptions.
                            if(!(   i == 2 && j == 1 ||
                                    i == 5 && j == 1 ||
                                    i == 7 && j == -1 ||
                                    i == 1 && j == 1)){
                                curScore += 50;
                            }
                        }
                    }
                }
                if (curScore > highestScore){
                    highestScore = curScore;
                    move = i;
                }
            }

        }

        return move;
    }


    /**
     * restartGame()
     * Restarts the game when button is pressed.
     * @param frame JFrame - The frame of the program.
     * @param infoLabel JLabel - Info label to change.
     */
    private static void restartGame(JFrame frame, JLabel infoLabel){
        frame.setVisible(false);
        for(JButton curButton : buttons){
            curButton.setText("");
            curButton.setEnabled(true);
        }

        turn = 1;

        infoLabel.setText("Turn: 1. Player O is playing.");

        frame.setVisible(true);

    }

}
