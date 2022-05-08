import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LinksSuggester {

    private List<Suggest> suggests;

    public LinksSuggester(File file) throws IOException, WrongLinksFormatException {

        suggests = new ArrayList<>();

        var configText = FileUtils.readFileToString(file); //FileUtils - дает возможность считывать текстовый файл
        String[] lines = configText.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split("\t");

            if (line.length != 3) {
                throw new WrongLinksFormatException(" Не правильный конфиг");

            }
            var keyWord = line[0];
            var title = line[1];
            var url = line[2];
            var suggest = new Suggest(keyWord, title, url);

            suggests.add(suggest);
        }


    }

    // Метод принимает текст, пробегается по всем рекомендациям из конфига и возвращает подходящие рекомендации.
    public List<Suggest> suggest(String text) {

        var resolt = new ArrayList<Suggest>();

        for (int i = 0; i < suggests.size(); i++) {
            var suggest = suggests.get(i);
            if (text.toLowerCase().contains(suggest.getKeyWord().toLowerCase())) {
                resolt.add(suggest);
            }
        }
        return resolt;

    }
}
