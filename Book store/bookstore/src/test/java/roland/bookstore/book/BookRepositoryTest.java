package roland.bookstore.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roland.bookstore.book.model.Book;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getBooksOutOfStock() {
        Book book1 = Book.builder()
                .author("test")
                .genre("test")
                .price(34.56)
                .title("test")
                .quantity(0)
                .id(1L).build();
        Book book2 = Book.builder()
                .author("test")
                .genre("test")
                .price(44.56)
                .title("test")
                .quantity(3)
                .id(2L).build();
        List<Book> books = new ArrayList<>();
        books.add(book1); books.add(book2);

        bookRepository.saveAll(books);
        List<Book> outOdStockBooks = bookRepository.getBooksOutOfStock();

        Assertions.assertEquals(outOdStockBooks.size(),1);

    }
}
