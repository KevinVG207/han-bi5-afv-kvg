package biotools.bioseq;

import java.awt.*;

/**
 * Deze abstracte class bevat een sequentie met bijbehorende functies.
 */
public abstract class Sequence {
    String sequence = "";


    public String getSeq(){
        return sequence;
    }

    public void setSeq(String sequence){
        this.sequence = sequence;
    }

    public abstract Color getColor(int position);

    public int getLength(){
        return sequence.length();
    }


}
