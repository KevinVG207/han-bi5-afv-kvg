package biotools.bioseq;

/**
 * Peptide class
 */
public class Peptide extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.BLACK;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (    curChar == 'R' ||
                    curChar == 'K'){
                return Color.BLUE;
            } else if ( curChar == 'D' ||
                        curChar == 'E') {
                return Color.RED;
            } else {
                return Color.GRAY;
            }
        }
    }
}
