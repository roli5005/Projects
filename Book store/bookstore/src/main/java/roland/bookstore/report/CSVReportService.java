package roland.bookstore.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roland.bookstore.book.model.Book;

import java.io.FileNotFoundException;
import java.util.List;

import static roland.bookstore.report.ReportType.CSV;


@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {
    @Override
    public String export(List<Book> books){
        try {
            CsvReportFactory.createReport(books);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "CSV report exported";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
