import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class H2O {

    /**
     * Allows user to draw a H2O molecule at given coordinates, rotation and scale.
     * @throws InterruptedException
     */
    public static void run() throws InterruptedException {

        JOptionPane.showMessageDialog(null,"Verander interactief de getallen,\nof klik om de molecuul te verplaatsen.");

        // Setting up JFrame
        JFrame f = new JFrame();
        f.setLayout(new FlowLayout());
        f.setSize(500, 560);
        f.setResizable(false);

        // Add a JPanel canvas
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(480, 480));
        p.setBackground(Color.lightGray);

        f.add(p);

        //Labels and fields
        JLabel lab_x = new JLabel("x");
        JLabel lab_y = new JLabel("y");
        JLabel lab_rot = new JLabel("rotation (deg)");
        JLabel lab_scale = new JLabel("scale (factor)");

        JTextField field_x = new JTextField("240", 5);
        JTextField field_y = new JTextField("240", 5);
        JTextField field_rot = new JTextField("0", 5);
        JTextField field_scale = new JTextField("1.0", 5);

        JTextField[] fields = new JTextField[4];
        fields[0] = field_x;
        fields[1] = field_y;
        fields[2] = field_rot;
        fields[3] = field_scale;

        // Add the same DocumentListener to all fields.
        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {

                }

                public void removeUpdate(DocumentEvent e) {

                }

                public void insertUpdate(DocumentEvent e) {
                    try {
                        DrawMolecule(Integer.parseInt(field_x.getText()), Integer.parseInt(field_y.getText()), Float.parseFloat(field_rot.getText()), Float.parseFloat(field_scale.getText()), p.getGraphics(), p.getWidth(), p.getHeight());
                    } catch (NumberFormatException except) {
                        JOptionPane.showMessageDialog(null, "Please enter only numbers into the coordinate fields.");
                    }
                }

            });

        }

        // Button + ActionListener
        // Outdated: Molecule now automatically updates.
        /*
        JButton button = new JButton("Draw H2O");
        button.addActionListener(e -> {
            try {
                DrawMolecule(Integer.parseInt(field_x.getText()), Integer.parseInt(field_y.getText()), Float.parseFloat(field_rot.getText()), Float.parseFloat(field_scale.getText()), p.getGraphics(), p.getWidth(), p.getHeight());
            } catch (NumberFormatException except) {
                JOptionPane.showMessageDialog(null, "Please enter only numbers into the coordinate fields.");
            }
        });
        */

        //Add all to JPanel
        f.add(lab_x);
        f.add(field_x);
        f.add(lab_y);
        f.add(field_y);
        f.add(lab_rot);
        f.add(field_rot);
        f.add(lab_scale);
        f.add(field_scale);
        //f.add(button);


        // Add mouse listener
        p.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                // Move molecule to where mouse is clicked.
                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, p);
                field_x.setText(Integer.toString((int) point.getX()));
                field_y.setText(Integer.toString((int) point.getY()));
            }
        });

        //Create GUI
        f.setVisible(true);
        f.setLocationRelativeTo(null);

        // Wait a small amount of time, otherwise getGraphics() returns null(?)
        Thread.sleep(100);
        DrawMolecule(Integer.parseInt(field_x.getText()), Integer.parseInt(field_y.getText()), Float.parseFloat(field_rot.getText()), Float.parseFloat(field_scale.getText()), p.getGraphics(), p.getWidth(), p.getHeight());

    }

    /**
     * Draws a simplified dihydrogenmonoxide atom to a Graphics object.
     *
     * @param x           - x-coordinate of central oxygen.
     * @param y           - y-coordinate of central oxygen.
     * @param rot         - rotation in degrees from default.
     * @param scale       - scale. 1 = normal size.
     * @param g           - Graphics object to draw to.
     * @param panelWidth  - Width of the Graphics panel.
     * @param panelHeight - Height of the Graphics panel.
     */
    public static void DrawMolecule(int x, int y, float rot, float scale, Graphics g, int panelWidth, int panelHeight) {
        int rad_h = Math.round(26 * scale);
        int rad_o = Math.round(50 * scale);
        int offset_v = 70;
        int offset_h = 0;

        // Bereken de locatie van de H-atomen met hoek van 104.5 graden.
        double radians = Math.toRadians(rot);
        double angle_between = Math.toRadians(104.5);
        int new_offset_v = (int) Math.round((Math.sin(radians) * offset_h + Math.cos(radians) * offset_v) * scale);
        int new_offset_h = (int) Math.round((Math.cos(radians) * offset_h - Math.sin(radians) * offset_v) * scale);
        int new_offset_v2 = (int) Math.round(Math.sin(angle_between) * new_offset_h + Math.cos(angle_between) * new_offset_v);
        int new_offset_h2 = (int) Math.round(Math.cos(angle_between) * new_offset_h - Math.sin(angle_between) * new_offset_v);

        Color col_h = Color.blue;
        Color col_o = Color.red;
        Color col_connector = Color.black;
        Color col_bg = Color.lightGray;

        // Clear screen
        g.clearRect(0, 0, panelWidth, panelHeight);

        // Draw background
        g.setColor(col_bg);
        g.fillRect(0, 0, panelWidth, panelHeight);

        // Draw two connectors
        g.setColor(col_connector);
        g.drawLine(x, y, x + new_offset_h, y + new_offset_v);
        g.drawLine(x, y, x + new_offset_h2, y + new_offset_v2);

        // Draw O
        g.setColor(col_o);
        g.fillOval(x - (rad_o / 2), y - (rad_o / 2), rad_o, rad_o);

        // Draw two Hs
        g.setColor(col_h);
        g.fillOval(x + new_offset_h - (rad_h / 2), y + new_offset_v - (rad_h / 2), rad_h, rad_h);
        g.fillOval(x + new_offset_h2 - (rad_h / 2), y + new_offset_v2 - (rad_h / 2), rad_h, rad_h);

    }

}
