package roland.bookstore.report;

import roland.bookstore.book.model.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvReportFactory {
    public static File createReport(List<Book> books) throws FileNotFoundException {
        File csvOutputFile = new File("Books_Out_Of_Stock.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("\tOUT OF STOCK");
            pw.println("\tID\tTITLE\tAUTHOR\tGENRE\tPRICE");
            books.stream()
                    .map(CsvReportFactory::convertToCSV)
                    .forEach(pw::println);
            pw.close();
            pw.flush();
        }
        return csvOutputFile;
    }
    public static String convertToCSV(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t"+book.getId());
        sb.append("\t"+book.getTitle());
        sb.append("\t"+book.getAuthor());
        sb.append("\t"+book.getGenre());
        sb.append("\t"+book.getPrice());

        return sb.toString();
    }
}
