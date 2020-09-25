import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextPredictor {

    private static List<String> sentences = null;

    public TextPredictor(String filepath) {
        try {
            sentences = Files.readAllLines(Path.of(filepath), StandardCharsets.UTF_8);

            for(int i = 0; i < sentences.size(); i++){
                String temp = sentences.get(i);
                String[] split = temp.split("\t");
                sentences.set(i, split[2]);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[] predict(String text, int amount) throws NoMatchException {

        String[] out = new String[amount];

        int curNum = 0;

        if(!text.equals("")) {

            for (String curSentence : sentences) {
                if (curNum >= amount) {
                    break;
                } else if (curSentence.toLowerCase().startsWith(text.toLowerCase())) {
                    out[curNum] = curSentence;
                    curNum++;
                }
            }
        } else {
            throw new NoMatchException("Text field is empty.");
        }

        if (curNum == 0){
            throw new NoMatchException("No results.");
        }


        return out;

    }

    public static class NoMatchException extends Exception {

        public NoMatchException(String message){
            super(message);
        }

    }
}
