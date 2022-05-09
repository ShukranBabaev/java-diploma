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
import java.util.HashSet;


public class Main {
    public static void main(String[] args) throws Exception {
        // System.out.println();
        // создаём конфиг
        LinksSuggester linksSuggester = new LinksSuggester(new File("data/config"));

        String convPath = "data/converted/";

        var dir = new File("data/pdfs");
        for (var fileIn : dir.listFiles()) {

            var fileOut = new File(convPath + fileIn.getName());


            //  var fileIn = new File("data/pdfs/Проба.pdf");
            //   var fileOut = new File("data/converted/Проба.pdf");


            var pdfReader = new PdfReader(fileIn);
            var pdfWriter = new PdfWriter(fileOut);

            var doc = new PdfDocument(pdfReader, pdfWriter);

            int numberOfPages = doc.getNumberOfPages();

            var recomendasionsSet = new HashSet<Suggest>(); // создано множество рекомендаций. Это множество содержит рекомендации добавленные в ПДФ
            for (int i = 1; i <= numberOfPages; i++) {

                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var recomendasions = linksSuggester.suggest(text); // получаем список рекомендаций к странице

                if (recomendasions.size() > 0 && !recomendasionsSet.containsAll(recomendasions)) { //Если список полученных рекомендаций не пуст и множество ранее добавленных рекомендаций не содержит все рекомендации для страницы, тогда мы добавляем новую страницу
                    var newPage = doc.addNewPage(i + 1);
                    numberOfPages = numberOfPages + 1;
                    i = i + 1;

                    var rect = new Rectangle(newPage.getPageSize()).moveRight(10).moveDown(10); //Создали прямоугольник в новой странице
                    Canvas canvas = new Canvas(newPage, rect); //Создали холст - добавляем туда новую страницу и прямоугольник
                    Paragraph paragraph = new Paragraph("Suggestions:\n");
                    paragraph.setFontSize(25); // указали размер шрифта
// сюда вставтье логика добавления нужных ссылок

                    for (int j = 0; j < recomendasions.size(); j++) {

                        if (!recomendasionsSet.contains(recomendasions.get(j))) { // если множество ранее добавленных рекомендаций не содержит j рекомендаию, тогда мы добавляем j рекомендацию в пдф файл
                            PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
                            PdfAction action = PdfAction.createURI(recomendasions.get(j).getUrl()); //Создали ссылку
                            annotation.setAction(action); //Добавили ссылку
                            Link link = new Link(recomendasions.get(j).getTitle(), annotation); //Текст который дает нам возможность перейти по ссылке
                            paragraph.add(link.setUnderline()); // Добавляем ссылку в параграф
                            paragraph.add("\n"); //Добавляем новую строчку
                        }


                    }

                    canvas.add(paragraph); //Параграф добавляем в холст
                    canvas.close();


                }
                recomendasionsSet.addAll(recomendasions);
            }


            doc.close();
        }

        // перебираем пдфки в data/pdfs

        // для каждой пдфки создаём новую в data/converted

        // перебираем страницы pdf

        // если в странице есть неиспользованные ключевые слова, создаём новую страницу за ней

        // вставляем туда рекомендуемые ссылки из конфига
    }
}
