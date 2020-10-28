package biotools.bioseq;

/**
 * DNA Class
 */
public class DNA extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.BLACK;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (curChar == 'A' || curChar == 'T'){
                return Color.YELLOW;
            } else {
                return Color.RED;
            }
        }
    }

    public float getGCperc(){
        // Not actually used
        return 0.0f;
    }
}
