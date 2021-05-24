package roland.bookstore.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import roland.bookstore.TestCreationFactory;
import roland.bookstore.book.model.Book;

import java.util.List;

import static roland.bookstore.report.ReportType.CSV;
import static roland.bookstore.report.ReportType.PDF;
@SpringBootTest
@Service
class ReportServiceTest {
    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void export() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("CSV report exported", csvReportService.export(books));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("PDF report exported", pdfReportService.export(books));
    }
}
