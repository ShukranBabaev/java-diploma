import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        // создаём конфиг
        LinksSuggester linksSuggester = new LinksSuggester(new File("data/config"));

        var dir = new File("data/pdfs");
        for (var fileIn : dir.listFiles()) {
            System.out.println(fileIn);
        }


        var fileIn = new File("data/pdfs/Проба.pdf");
        var fileOut = new File("data/pdfs/Проба.pdf");
        var doc = new PdfDocument(new PdfReader(new FileInputStream(fileIn)), new PdfWriter(new FileOutputStream(fileOut)));
        var page1 = doc.getPage(1);
        var text = PdfTextExtractor.getTextFromPage(page1);
        System.out.println(text);

        // перебираем пдфки в data/pdfs

        // для каждой пдфки создаём новую в data/converted

        // перебираем страницы pdf

        // если в странице есть неиспользованные ключевые слова, создаём новую страницу за ней

        // вставляем туда рекомендуемые ссылки из конфига
    }
}
