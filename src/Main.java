import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        /**
         * Main method. Allows user to choose the exercise to show.
         */

        System.out.println("Hello World!");

        Scanner scanner = new Scanner(System.in);
        String inputVar = "";
        boolean exiting = false;

        while (!exiting) {
            System.out.println("(Typ q om af te sluiten.)");
            System.out.print("Voer afvinkopdr nummer in (bv. 1-3 / 2-1a):\t");
            inputVar = scanner.next().toLowerCase();
            switch (inputVar) {
                case "1-3":
                    System.out.println("Starting opdracht 1-3...");
                    TextField();
                    break;
                case "1-6":
                    System.out.println("Starting opdracht 1-6...");
                    HelloWorld();
                    break;
                case "2":
                    System.out.println("Starting opdracht 2...");
                    System.out.println("WARNING: Help popup may appear behind other windows!");
                    H2O.run();
                    break;
                case "3":
                    System.out.println("Starting opdracht 3...");
                    System.out.println("WARNING: Play mode popup may appear behind other windows!");
                    TicTacToe.run();
                    break;
                case "4":
                    System.out.println("Starting opdracht 4...");
                    HorseRace.run();
                    break;
                case "5":
                    System.out.println("Starting opdracht 5...");
                    TextPredictDialog TP = new TextPredictDialog();
                    TP.run();
                    break;
                case "q":
                case "quit":
                case "stop":
                case "exit":
                    exiting = true;
                    break;
                default:
                    System.out.println("Fout: Nummer niet gevonden.");
            }

        }
        System.out.println("Exiting...");

    }

    public static void TextField() {
        /**
         * Creates JFrame applet with label, textbox.
         * Minimizing is disabled.
         */


        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setResizable(false);

        f.getContentPane().setBackground(Color.cyan);

        f.addWindowListener(getWindowAdapter(f));


        ImageIcon ico = new ImageIcon("_assets/han_logo.png");
        f.setIconImage(ico.getImage());

        JLabel l = new JLabel("Here's a label");
        l.setForeground(Color.red);
        f.add(l);

        JTextField field = new JTextField("Text in the field", 20);
        field.setForeground(Color.red);
        f.add(field);

        f.setSize(640, 360);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

    }

    /*  Disable minimize action
        Source: http://www.java2s.com/Tutorials/Java/Swing_How_to/JFrame/Disable_JFrame_minimize_button.htm
     */
    private static WindowAdapter getWindowAdapter(JFrame frame) {
        /**
         * Overrides minimize action of JFrame.
         */
        return new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent we) {
                frame.setState(JFrame.NORMAL);
                JOptionPane.showMessageDialog(frame, "Minimizig disabled.");
            }
        };
    }

    public static void HelloWorld() {
        /**
         * Creates 'Hello World' dialog box.
         */
        System.out.println("Hello World!");
        JOptionPane.showMessageDialog(null, "Hello World!");
    }

}
