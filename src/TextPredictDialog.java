/**
 * TextPredictDialog
 *
 * This class contains code to set up the GUI for text prediction.
 * Contains a JTextField and JList to show results.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class TextPredictDialog {

    // Declare/init global variables
    private final TextPredictor predictor = new TextPredictor("_assets/sentences_short.csv");
    private final JTextField textField = new JTextField("",40);
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private SwingWorker<String[], Void> curWorker = null;

    /**
     * run()
     *
     * Sets up JFrame with JTextField (global init) and JList.
     * TextField has DocumentListener to update while typing.
     */
    public void run() {
        JFrame f = new JFrame("Text Prediction");
        f.setSize(512,256);
        f.setResizable(false);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());

        JLabel label = new JLabel("Text Prediction");

        textField.setMaximumSize(textField.getPreferredSize());
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                predict();
            }

            public void removeUpdate(DocumentEvent e) {
                predict();
            }

            public void insertUpdate(DocumentEvent e) {
                predict();
            }

        });

        JList<String> predList = new JList<>(listModel);
        predList.setPreferredSize(new Dimension(450,128));
        predList.setMaximumSize(predList.getPreferredSize());

        p.add(label);
        p.add(textField);
        p.add(predList);

        p.setBorder(new EmptyBorder(10,10,10,10));

        f.add(p);

        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

    /**
     * predict()
     *
     * Use SwingWorker to predict text in different threads.
     */
    private void predict() {

        // Cancel previous worker if new one is being made.
        if(curWorker != null){
            curWorker.cancel(true);
            curWorker = null;
        }

        // SwingWorker does text prediction in background.
        SwingWorker<String[], Void> worker = new SwingWorker<>(){
            @Override
            protected String[] doInBackground() throws Exception {
                try {
                    return predictor.predict(textField.getText(), 7);
                } catch(TextPredictor.NoMatchException e){
                    System.out.println(e.getMessage());
                    return new String[]{""};
                }
            }

            @Override
            protected void done() {
                if(!isCancelled()) {
                    // Worker done and not cancelled:
                    // Update result list.
                    try {
                        String[] newArray = get();
                        listModel.clear();
                        for (String arrayEntry : newArray) {
                            listModel.addElement(arrayEntry);
                        }
                        cancel(true);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        curWorker = worker;
        curWorker.execute();
    }
}
