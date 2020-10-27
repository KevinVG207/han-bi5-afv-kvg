package biotools.bioseq;

import java.awt.*;

/**
 * RNA Class
 */
public class RNA extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.black;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (curChar == 'A') {
                return Color.blue;
            } else if(curChar == 'U'){
                return Color.yellow;
            } else {
                return Color.red;
            }
        }
    }
}
