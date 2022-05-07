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

        //   var dir = new File("data/pdfs");
        //   for (var fileIn : dir.listFiles()) {
        //       System.out.println(fileIn);
        //   }


        var fileIn = new File("data/pdfs/Проба.pdf");
        var fileOut = new File("data/converted/Проба.pdf");


        var pdfReader = new PdfReader(fileIn);
        var pdfWriter = new PdfWriter(fileOut);

        var doc = new PdfDocument(pdfReader, pdfWriter);

        int numberOfPages = doc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            var page = doc.getPage(i);
            var text = PdfTextExtractor.getTextFromPage(page);
         //   System.out.println(text);
        }

        var newPage = doc.addNewPage(2);

        doc.close();

        // перебираем пдфки в data/pdfs

        // для каждой пдфки создаём новую в data/converted

        // перебираем страницы pdf

        // если в странице есть неиспользованные ключевые слова, создаём новую страницу за ней

        // вставляем туда рекомендуемые ссылки из конфига
    }
}
