package biotools.bioseq;

/**
 * Deze abstracte class bevat een sequentie met bijbehorende functies.
 */
public abstract class Sequence {
    String sequence = "";

    public enum Color {
        RED,
        YELLOW,
        BLUE,
        BLACK,
        GRAY
    }


    public String getSeq(){
        return sequence;
    }

    public void setSeq(String sequence){
        this.sequence = sequence;
    }

    /**
     * Geeft de kleur van een character in de string op de opgegeven positie.
     * @param position int - Positie in de string (0-based)
     * @return Color - De kleur van de positie voor het visualiseren.
     */
    public abstract Color getColor(int position);

    public int getLength(){
        return sequence.length();
    }


}
