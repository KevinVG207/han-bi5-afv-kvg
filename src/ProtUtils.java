import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides utility functions to deal with FASTA amino acid sequences (proteins).
 */
public class ProtUtils {

    // Protein single letter codes.
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
     * checkProteinPolarity()
     * Counts the amount of polar and non-polar amino acids in a given protein sequence String.
     * If not a valid protein, a ValidityError will be thrown.
     * @param curProt String - Protein amino acid String.
     * @return int[] - 0: Total length of protein; 1: Amount of polar aa; 2: Amount of non-polar aa.
     * @throws ValidityError - Thrown when input String is not a valid aa sequence.
     */
    public static int[] checkProteinPolarity(String curProt) throws ValidityError {
        int[] out = new int[3];
        int polar = 0;
        int nonPolar = 0;

        for(int i = 0; i < curProt.length(); i++){
            char curAA = curProt.charAt(i);

            if (    curAA == 'R' ||
                    curAA == 'N' ||
                    curAA == 'D' ||
                    curAA == 'C' ||
                    curAA == 'Q' ||
                    curAA == 'E' ||
                    curAA == 'H' ||
                    curAA == 'K' ||
                    curAA == 'S' ||
                    curAA == 'T' ||
                    curAA == 'Y'){
                polar++;
            }else{
                nonPolar++;
            }

            boolean charFound = false;
            for(char curChar : VALID_AA){
                if(curChar == curAA){
                    charFound = true;
                }
            }
            if (!charFound){
                throw new ValidityError("Unknown amino acid at index " + i);
            }
        }

        out[0] = curProt.length();
        out[1] = polar;
        out[2] = nonPolar;
        return out;
    }

    /**
     * fastaToList()
     * Reads a fasta file and returns the sequences in an ArrayList<String>
     * @param filePath String - Path to fasta file.
     * @return ArrayList<String> - ArrayList with Fasta sequences as Strings with header. Format: HEADER\nSEQUENCE
     */
    public static ArrayList<String> fastaToList(String filePath){
        ArrayList<String> output = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String curSeq = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")){
                    if (curSeq != ""){
                        output.add(curSeq);
                    }
                    curSeq = line + "\n";
                }
                else {
                    curSeq += line;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return output;
    }

    /**
     * listItemToSequence()
     * Returns sequence of fasta string with format HEADER\nSEQUENCE
     * @param listItem String - fasta string.
     * @return String - Fasta sequence
     */
    public static String listItemToSequence(String listItem){
        return listItem.split("\n")[1];
    }

    /**
     * listItemToHeader()
     * Returns header of fasta string with format HEADER\nSEQUENCE
     * @param listItem String - fasta string.
     * @return String - Fasta header
     */
    public static String listItemToHeader(String listItem){
        return listItem.split("\n")[0];
    }

    /**
     * ValidityError Exception
     * Custom exception. Thrown on invalid protein sequence.
     */
    public static class ValidityError extends Exception {

        public ValidityError(String message){
            super(message);
        }

    }

}
