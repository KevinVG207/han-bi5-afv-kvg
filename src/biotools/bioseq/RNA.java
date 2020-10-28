package biotools.bioseq;

/**
 * RNA Class
 */
public class RNA extends Sequence {
    @Override
    public Color getColor(int position) {
        Color curColor = Color.BLACK;
        if (position > sequence.length()){
            return curColor;
        } else {
            char curChar = sequence.charAt(position);
            if (curChar == 'A') {
                return Color.BLUE;
            } else if(curChar == 'U'){
                return Color.YELLOW;
            } else {
                return Color.RED;
            }
        }
    }
}
