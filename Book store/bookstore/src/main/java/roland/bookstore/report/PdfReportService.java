package roland.bookstore.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roland.bookstore.book.model.Book;

import java.io.IOException;
import java.util.List;

import static roland.bookstore.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService {
    @Override
    public String export(List<Book> books){
        try {
            PdfReportFactory.createPDF(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "PDF report exported";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
