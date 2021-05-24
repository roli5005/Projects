package roland.bookstore.report;

import roland.bookstore.book.model.Book;

import java.util.List;

public interface ReportService {
    String export(List<Book> books);

    ReportType getType();
}
