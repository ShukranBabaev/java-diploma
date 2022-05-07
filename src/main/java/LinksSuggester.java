import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LinksSuggester {

    private List<Suggest> suggests;

    public LinksSuggester(File file) throws IOException, WrongLinksFormatException {

        suggests = new ArrayList<>();

        var text = FileUtils.readFileToString(file);
        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++){
            String[] line = lines[i].split("\t");

            if (line.length != 3){
                throw new WrongLinksFormatException(" Не правильный конфиг");

            }
            var keyWord = line[0];
            var title = line[1];
            var url = line[2];
            var suggest = new Suggest(keyWord, title, url);

            suggests.add(suggest);
            System.out.println(suggest);
        }


    }

    public List<Suggest> suggest(String text) {
        return null;
    }
}
