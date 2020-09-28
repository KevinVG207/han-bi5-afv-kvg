/**
 * HorseRace class
 *
 * Creates a simulated horse race with graphical display.
 */

import javax.swing.*;
import java.awt.*;

public class HorseRace {

    // Set up global variables
    private static final int FINISH = 1000;
    private static final Color BACKGROUND = Color.lightGray;
    private static boolean finished = false;

    /**
     * run()
     * Creates Horse objects and launches the JFrame.
     */
    public static void run(){

        Horse[] horses = new Horse[]{
                new Horse("Horse 1", Color.RED),
                new Horse("Horse 2", Color.BLUE),
                new Horse("Horse 3", Color.GREEN),
                new Horse("Horse 4", Color.ORANGE),
                new Horse("Horse 5", Color.PINK),
                new Horse("Horse 6", Color.GRAY),
                new Horse("Horse 7", Color.YELLOW),
                new Horse("Horse 8", Color.CYAN),
                new Horse("Horse 9", Color.MAGENTA),
                new Horse("Horse 10", Color.DARK_GRAY),
        };

        makeJFrame(horses);

    }

    /**
     * doUpdate()
     * Updates all Horse objects on a delay of 20ms.
     * Checks if a horse has finished the race.
     *
     * @param horses Horse[] - Array of horse objects.
     * @param drawPanel JPanel - Reference to the panel to draw on.
     * @param button JButton - Button to disable once clicked.
     */
    private static void doUpdate(Horse[] horses, JPanel drawPanel, JButton button, JFrame frame){
        button.setEnabled(false);
        String finalHorseName = "";
        while (!finished) {
            for (int i = 0; i < horses.length; i++) {
                Horse curHorse = horses[i];
                // Update each horse
                curHorse.update();
                if (curHorse.getDistance() > 1000) {
                    finished = true;
                    finalHorseName = curHorse.getName();
                    break;
                }
            }

            drawRace(horses, drawPanel);
            pause(20); // 50FPS
        }

        JOptionPane.showMessageDialog(frame, finalHorseName + " has won the race!\nCongratulations!");
    }

    /**
     * drawRace()
     * Draws all of the horse info graphics on a JPanel.
     *
     * @param horses Horse[] - Array of Horse objects.
     * @param drawPanel JPanel - The panel to draw on.
     */
    private static void drawRace(Horse[] horses, JPanel drawPanel) {
        int panelWidth = drawPanel.getWidth();
        int panelHeight = drawPanel.getHeight();
        Graphics g = drawPanel.getGraphics();
        int topOffsetX = 128;
        int topOffsetY = 16;
        int barHeight = 16;
        int spacing = 20;
        float drawFieldRatio = 1.00f;
        int finishWidth = 2;
        int finishDistance = Math.round((panelWidth - (topOffsetX * 2) ) * drawFieldRatio);
        Color finishColor = Color.black;
        Color textColor = Color.black;
        int textOffsetY = 12;
        int textOffsetX = 10;

        // Clear screen
        g.clearRect(0, 0, panelWidth, panelHeight);

        // Draw background
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, panelWidth, panelHeight);

        for (Horse curHorse : horses) {
            // Draw each horse distance
            int x = topOffsetX;
            int y = topOffsetY + curHorse.getIndex() * (barHeight + spacing);
            int height = barHeight;
            int width = Math.round((finishDistance - topOffsetX) * (curHorse.getDistance() / (float) FINISH));
            g.setColor(curHorse.getColor());
            g.fillRect(x, y, width, height);

            g.setColor(textColor);
            g.drawString(curHorse.getName(), textOffsetX, y + textOffsetY);
            g.drawString(Integer.toString(curHorse.getDistance()), finishDistance + textOffsetX, y + textOffsetY);
        }

        // Draw finish line
        g.setColor(finishColor);
        g.fillRect(finishDistance, topOffsetY, finishWidth, horses.length * (barHeight + spacing) - spacing);

    }

    /**
     * pause()
     * Pauses thread by specified amount of milliseconds.
     *
     * @param ms int - Milliseconds ot pause.
     */
    private static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }

    /**
     * makeJFrame()
     * Sets up JFrame for the graphics.
     * Sets up button to be clicked.
     *
     * @param horses Horse[] - Array of Horse objects.
     */
    private static void makeJFrame(Horse[] horses) {
        JFrame f = new JFrame("Horse Race Simulator");
        f.setLayout(new FlowLayout());
        f.setSize(1280, 500);
        f.setResizable(false);


        JPanel panelHolder = new JPanel();
        JPanel playPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        playPanel.setPreferredSize(new Dimension(1200, 400));
        playPanel.setBackground(BACKGROUND);
        bottomPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start the race!");
        startButton.addActionListener(e -> doUpdate(horses, playPanel, startButton, f));
        bottomPanel.add(startButton);

        panelHolder.setLayout(new BoxLayout(panelHolder, BoxLayout.Y_AXIS));
        panelHolder.add(playPanel);
        panelHolder.add(bottomPanel);

        f.add(panelHolder);

        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
