import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;



public class Main {
    public static void main(String[] args) throws Exception {
       // System.out.println();
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
            var recomendasions = linksSuggester.suggest(text);

            if (recomendasions.size() > 0) {
                var newPage = doc.addNewPage(i + 1);

                var rect = new Rectangle(newPage.getPageSize()).moveRight(10).moveDown(10);
                Canvas canvas = new Canvas(newPage, rect);
                Paragraph paragraph = new Paragraph("Suggestions:\n");
                paragraph.setFontSize(25);
// сюда вставтье логика добавления нужных ссылок
                canvas.add(paragraph);

                PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
                PdfAction action = PdfAction.createURI(recomendasions.get(0).getUrl());
                annotation.setAction(action);
                Link link = new Link(recomendasions.get(0).getTitle(), annotation);
                paragraph.add(link.setUnderline());
                paragraph.add("\n");

            }
        }



        doc.close();

        // перебираем пдфки в data/pdfs

        // для каждой пдфки создаём новую в data/converted

        // перебираем страницы pdf

        // если в странице есть неиспользованные ключевые слова, создаём новую страницу за ней

        // вставляем туда рекомендуемые ссылки из конфига
    }
}
