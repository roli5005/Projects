package roland.bookstore.report;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import roland.bookstore.book.model.Book;

import java.io.IOException;
import java.util.List;

public class PdfReportFactory {
    public static void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin,
                                 String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = 550f;
        final float tableHeight = rowHeight * rows;
        final float cellMargin=1f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx,y,nextx,y-tableHeight);
            if(i==1)nextx += 250f;
            else nextx += 75f;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,10);

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(text);
                contentStream.endText();
                if(j==1)textx += 250f +cellMargin;
                else textx += 75f +cellMargin;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }
    public static void createPDF(List<Book> books) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage( page );

        PDPageContentStream contentStream =
                new PDPageContentStream(doc, page);
        String[][] tableBooks = new String[books.size()+1][5];
        String[] columnNames = new String[5];

        columnNames[0] = "ID";
        columnNames[1] = "TITLE";
        columnNames[2] = "AUTHOR";
        columnNames[3] = "GENRE";
        columnNames[4] = "PRICE";
        tableBooks[0] = columnNames;
        int i = 0;
        for (Book b:books
             ) {
            i++;
            String[] row = new String[5];
            row[0] = String.valueOf(b.getId());
            row[1] = b.getTitle();
            row[2] = b.getAuthor();
            row[3] = b.getGenre();
            row[4] = String.valueOf(b.getPrice());
            tableBooks[i] = row;

        }

        drawTable(page, contentStream, 700, 20, tableBooks);
        contentStream.close();
        doc.save("BooksOutOfStock.pdf" );
    }

}
