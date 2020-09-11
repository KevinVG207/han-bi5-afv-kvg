import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                    H2O_viewer();
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


    public static void H2O_viewer() throws InterruptedException {
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setSize(500, 640);
        f.setResizable(false);

        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(480,480));
        p.setBackground(Color.lightGray);

        f.add(p);

        JLabel lab_x = new JLabel("x");
        JLabel lab_y = new JLabel("y");

        JTextField field_x = new JTextField("240", 5);
        JTextField field_y = new JTextField("240", 5);

        JButton button = new JButton("Draw H2O");

        button.addActionListener(e -> {
            try {
                DrawMolecule(Integer.parseInt(field_x.getText()), Integer.parseInt(field_y.getText()), p.getGraphics(), p.getWidth(), p.getHeight());
            }
            catch (NumberFormatException except){
                JOptionPane.showMessageDialog(null, "Please enter only numbers into the coordinate fields.");
            }
        });


        f.add(lab_x);
        f.add(field_x);
        f.add(lab_y);
        f.add(field_y);
        f.add(button);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        Thread.sleep(100);
        DrawMolecule(240,240,p.getGraphics(),p.getWidth(),p.getHeight());

        //g.clearRect (0, 0, p.getWidth(), p.getHeight());



    }

    public static void DrawMolecule(int x, int y, Graphics g, int panelWidth, int panelHeight){
        int rad_h = 26;
        int rad_o = 50;
        int offset_v = 50;
        int offset_h = 70;
        Color col_h = Color.blue;
        Color col_o = Color.red;
        Color col_connector = Color.black;
        Color col_bg = Color.lightGray;

        // Clear screen
        g.clearRect (0,0,panelWidth,panelHeight);

        // Draw background
        g.setColor(col_bg);
        g.fillRect(0,0,panelWidth,panelHeight);

        // Draw two connectors
        g.setColor(col_connector);
        g.drawLine(x, y, x+offset_h,y+offset_v);
        g.drawLine(x,y,x-offset_h,y+offset_v);

        // Draw O
        g.setColor(col_o);
        g.fillOval(x-(rad_o/2), y-(rad_o/2), rad_o, rad_o);

        // Draw two Hs
        g.setColor(col_h);
        g.fillOval(x-offset_h-(rad_h/2), y+offset_v-(rad_h/2), rad_h, rad_h);
        g.fillOval(x+offset_h-(rad_h/2), y+offset_v-(rad_h/2), rad_h, rad_h);

    }

}
