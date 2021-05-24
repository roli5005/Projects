package roland.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import roland.bookstore.book.model.dto.BookDTO;
import roland.bookstore.report.ReportServiceFactory;
import roland.bookstore.report.ReportType;

import java.util.List;

import static roland.bookstore.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final ReportServiceFactory reportServiceFactory;
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) {
        reportServiceFactory.getReportService(type).export(bookService.booksOutOfstock());
    }
}
