package biotools.bioapp;

import biotools.bioseq.DNA;
import biotools.bioseq.Peptide;
import biotools.bioseq.RNA;
import biotools.bioseq.Sequence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Deze class bevat de sequence visualizer GUI.
 * Laat een gebruiker een fasta bestand selecteren en visualizeren.
 */
public class SeqVis {

    private Sequence seqClass;
    private JLabel label1;
    private JTextField textField1;
    private JButton browseButton;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private JButton visButton;
    private JPanel visPanel;

    enum Type {
        DNA,
        RNA,
        PEPTIDE
    }

    private static final char[] VALID_AA = new char[]{
            'A',
            'R',
            'N',
            'D',
            'C',
            'E',
            'Q',
            'G',
            'H',
            'I',
            'L',
            'K',
            'M',
            'F',
            'P',
            'S',
            'T',
            'W',
            'Y',
            'V'
    };

    /**
     * GUI Class, handelt de ActionListeners.
     */
    public SeqVis() {
        visPanel.setBackground(Color.black);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On browse
                JFileChooser jfc = new JFileChooser();
                int returnVal = jfc.showOpenDialog(mainPanel);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    textField1.setText(jfc.getSelectedFile().getPath());
                }
            }
        });
        visButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //On visualize
                //Load sequence
                String curSeq = loadSeq();

                try{
                    switch(checkValidity(curSeq)){
                        case DNA:
                            seqClass = new DNA();
                            break;
                        case RNA:
                            seqClass = new RNA();
                            break;
                        case PEPTIDE:
                            seqClass = new Peptide();
                            break;
                    }
                    seqClass.setSeq(curSeq);
                    textArea1.setText(curSeq);
                    visualize();
                } catch (NoValidSeq ex){
                    textArea1.setText(ex.getMessage());
                    visPanel.getGraphics().setColor(Color.black);
                    visPanel.getGraphics().fillRect(0,0,visPanel.getWidth(),visPanel.getHeight());
                }
            }
        });
    }

    /**
     * Deze class laadt een tekstbestand in.
     * @return String - Tekst uit een textbestand.
     */
    private String loadSeq(){
        String curSeq = "";
        try (BufferedReader br = new BufferedReader(new FileReader(textField1.getText()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(">")){
                    curSeq += line;
                }
            }
        }catch (IOException e){
            textArea1.setText("File not found");
        }
        return curSeq;
    }

    /**
     * Deze class bepaalt wat voor sequentie een String is.
     * @param sequence String - Input string
     * @return Type - Het type sequentie
     * @throws NoValidSeq
     */
    Type checkValidity(String sequence) throws NoValidSeq {
        int a = 0;
        int t = 0;
        int g = 0;
        int c = 0;
        int u = 0;
        int amountAA = 0;
        int len = sequence.length();

        // Check all characters
        for(int i = 0; i < len; i++){
            char curChar = sequence.charAt(i);
            for(char charLoop : VALID_AA){
                if(curChar == charLoop){
                    amountAA++;
                }
            }
            switch(curChar){
                case 'A':
                    a++;
                    break;
                case 'T':
                    t++;
                    break;
                case 'G':
                    g++;
                    break;
                case 'C':
                    c++;
                    break;
                case 'U':
                    u++;
                    break;
            }
        }

        if(a+t+g+c == len) {     // DNA
            return Type.DNA;
        }else if(a+u+g+c == len) {    // RNA
            return Type.RNA;
        }else if(amountAA == len) {      //Peptide
            return Type.PEPTIDE;
        }else {
            throw new NoValidSeq("Not a Valid Sequence!");
        }
    }

    public static class NoValidSeq extends Exception {
        public NoValidSeq(String errorMessage){
            super(errorMessage);
        }
    }

    /**
     * Tekent de visualizatie op het scherm.
     */
    private void visualize(){
        int len = seqClass.getLength();
        int panelWidth = visPanel.getWidth();
        int panelHeight = visPanel.getHeight();
        int charLength = Math.round(panelWidth / (float)len);
        Graphics g = visPanel.getGraphics();

        g.clearRect(0,0,panelWidth,panelHeight);

        for(int i = 0; i < len; i++){
            g.setColor(seqClass.getColor(i));
            g.fillRect(i * charLength, 0, charLength, panelHeight);
        }
    }

    /**
     * Zet de JFrame op.
     */
    public static void run(){
        JFrame frame = new JFrame("SeqVis");
        frame.setSize(1280,720);
        frame.setResizable(false);
        frame.setContentPane(new SeqVis().mainPanel);
        frame.getContentPane().setSize(1280,720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
