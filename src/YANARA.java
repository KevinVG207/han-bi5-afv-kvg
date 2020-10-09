import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class YANARA {

    private static JFrame f;
    private static String curFilePath = "";

    private static void onBrowse(JTextField textField){
        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showOpenDialog(f);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            textField.setText(jfc.getSelectedFile().getName());
            curFilePath = jfc.getSelectedFile().getPath();
        }
    }

    private static void onAnalyze(JTextArea textArea, JPanel drawArea){
        int totLen = 0;
        int totPolar = 0;
        int totNonPolar = 0;
        ArrayList<String> fastaList = ProtUtils.fastaToList(curFilePath);
        ArrayList<int[]> seqData = new ArrayList<>();
        textArea.setText("");
        try {
            for (int i = 0; i < fastaList.size(); i++) {
                String curProt = fastaList.get(i);
                String curSeq = ProtUtils.listItemToSequence(curProt);
                String curHead = ProtUtils.listItemToHeader(curProt);
                try {
                    seqData.add(ProtUtils.checkProteinValidity(curSeq));
                } catch (ProtUtils.ValidityError e) {
                    textArea.setText(textArea.getText() + "Problematic sequence:\t" + curHead + "\n" + e.getMessage() + ". This sequence is ignored.\n");
                }

                for (int[] curData : seqData) {
                    totLen += curData[0];
                    totPolar += curData[1];
                    totNonPolar += curData[2];
                }

            }

            float percPolar = (float) totPolar / totLen * 100;
            float percNonPolar = (float) totNonPolar / totLen * 100;

            textArea.setText(textArea.getText() + "Total animo acids:\t" + totLen +
                    "\nTotal polar aa:\t\t" + totPolar + "\t(" + percPolar + "%)" +
                    "\nTotal non-polar aa:\t" + totNonPolar + "\t(" + percNonPolar + "%)\n");

            drawPercentages(drawArea, totLen, totPolar, totNonPolar);
        }
        catch (ArrayIndexOutOfBoundsException e){
            textArea.setText("Error reading file.\nMake sure input file is .fasta format.");
        }
    }

    private static void drawPercentages(JPanel drawArea, int totLen, int totPolar, int totNonPolar){
        Graphics g = drawArea.getGraphics();
        int panelWidth = drawArea.getWidth();
        int panelHeight = drawArea.getHeight();

        int margin = 10;
        int barHeight = Math.round((panelHeight - 3 * (float) margin) / 2);
        int calcWidth = panelWidth - 2*margin;

        int bar1Width = Math.round(calcWidth / (float) totLen * totPolar);
        int bar2Width = Math.round(calcWidth / (float) totLen * totNonPolar);

        float perc1 = totPolar / (float) totLen * 100;
        float perc2 = totNonPolar / (float) totLen * 100;

        g.clearRect(0,0,panelWidth,panelHeight);

        g.setColor(Color.lightGray);
        g.fillRect(0,0,panelWidth,panelHeight);

        g.setColor(Color.red);
        g.fillRect(margin,margin,bar1Width,barHeight);
        g.setColor(Color.white);
        g.drawString(Float.toString(perc1),margin*2,margin+20);

        g.setColor(Color.blue);
        g.fillRect(margin,margin+barHeight+margin,bar2Width,barHeight);
        g.setColor(Color.white);
        g.drawString(Float.toString(perc2),margin*2,margin+barHeight+margin+20);

    }

    public static void run(){
        // JFrame layout via JVider

        f = new JFrame("YANARA");
        f.setSize(512,512);
        f.setResizable(false);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel pnPanel0 = new JPanel();
        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        pnPanel0.setLayout( gbPanel0 );

        JTextField tfFile = new JTextField(20);
        gbcPanel0.gridx = 4;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 10;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( tfFile, gbcPanel0 );
        pnPanel0.add( tfFile );

        JLabel lbFile = new JLabel( "File:"  );
        gbcPanel0.gridx = 0;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 4;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( lbFile, gbcPanel0 );
        pnPanel0.add( lbFile );

        JButton btBrowse = new JButton( "Browse..."  );
        gbcPanel0.gridx = 14;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 2;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btBrowse, gbcPanel0 );
        pnPanel0.add( btBrowse );

        JButton btAnalyze = new JButton( "Analyze"  );
        gbcPanel0.gridx = 16;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 3;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btAnalyze, gbcPanel0 );
        pnPanel0.add( btAnalyze );

        JLabel lbInfo = new JLabel( "Info:"  );
        gbcPanel0.gridx = 0;
        gbcPanel0.gridy = 4;
        gbcPanel0.gridwidth = 4;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( lbInfo, gbcPanel0 );
        pnPanel0.add( lbInfo );

        JTextArea taInfo = new JTextArea();
        Dimension dimInfo = new Dimension(400,200);
        taInfo.setPreferredSize(dimInfo);
        taInfo.setMinimumSize(dimInfo);
        taInfo.setMaximumSize(dimInfo);
        gbcPanel0.gridx = 4;
        gbcPanel0.gridy = 4;
        gbcPanel0.gridwidth = 14;
        gbcPanel0.gridheight = 8;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( taInfo, gbcPanel0 );
        pnPanel0.add( taInfo );

        JPanel pnDrawPanel = new JPanel();
        Dimension dimDrawPanel = new Dimension(400,100);
        pnDrawPanel.setPreferredSize(dimDrawPanel);
        pnDrawPanel.setMinimumSize(dimDrawPanel);
        pnDrawPanel.setMaximumSize(dimDrawPanel);
        pnDrawPanel.setBackground(Color.lightGray);
        GridBagLayout gbDrawPanel = new GridBagLayout();
        GridBagConstraints gbcDrawPanel = new GridBagConstraints();
        pnDrawPanel.setLayout( gbDrawPanel );
        gbcPanel0.gridx = 4;
        gbcPanel0.gridy = 13;
        gbcPanel0.gridwidth = 14;
        gbcPanel0.gridheight = 6;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( pnDrawPanel, gbcPanel0 );
        pnPanel0.add( pnDrawPanel );

        JLabel lbPerc = new JLabel( "Percentage:"  );
        gbcPanel0.gridx = 0;
        gbcPanel0.gridy = 13;
        gbcPanel0.gridwidth = 4;
        gbcPanel0.gridheight = 2;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 1;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( lbPerc, gbcPanel0 );
        pnPanel0.add( lbPerc );

        pnPanel0.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        btBrowse.addActionListener( e -> {
            onBrowse(tfFile);
        });

        btAnalyze.addActionListener( e -> {
            onAnalyze(taInfo, pnDrawPanel);
        });

        f.setContentPane( pnPanel0 );
        f.pack();


        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

}
