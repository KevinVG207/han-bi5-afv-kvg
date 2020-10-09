import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProtUtils {

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

    public static int[] checkProteinValidity(String curProt) throws ValidityError {
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

    public static String listItemToSequence(String listItem){
        return listItem.split("\n")[1];
    }

    public static String listItemToHeader(String listItem){
        return listItem.split("\n")[0];
    }

    public static class ValidityError extends Exception {

        public ValidityError(String message){
            super(message);
        }

    }

}
