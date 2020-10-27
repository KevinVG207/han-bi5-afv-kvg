package biotools.bioseq;

import java.awt.*;

/**
 * Peptide class
 */
public class Peptide extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.black;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (    curChar == 'R' ||
                    curChar == 'K'){
                return Color.blue;
            } else if ( curChar == 'D' ||
                        curChar == 'E') {
                return Color.red;
            } else {
                return Color.gray;
            }
        }
    }
}
