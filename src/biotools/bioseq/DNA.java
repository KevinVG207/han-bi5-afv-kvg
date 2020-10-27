package biotools.bioseq;

import java.awt.*;

/**
 * DNA Class
 */
public class DNA extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.black;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (curChar == 'A' || curChar == 'T'){
                return Color.yellow;
            } else {
                return Color.red;
            }
        }
    }

    public float getGCperc(){
        // Not actually used
        return 0.0f;
    }
}
