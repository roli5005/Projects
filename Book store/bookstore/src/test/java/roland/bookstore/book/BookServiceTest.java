package roland.bookstore.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roland.bookstore.TestCreationFactory;
import roland.bookstore.book.model.Book;
import roland.bookstore.book.model.dto.BookDTO;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }
    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(all.size(),books.size());
    }

    @Test
    void create() {
        BookDTO bookDTO = BookDTO.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .build();
        Book book = Book.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .build();
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO newBook = bookService.create(bookDTO);
        Assertions.assertNotNull(newBook);

    }

    @Test
    void edit() {
        BookDTO bookDTO = BookDTO.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .id(1L)
                .build();
        Book book = Book.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .id(1L)
                .build();
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO updatedBook = bookService.edit(bookDTO);
        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO).getId());
        Assertions.assertNotNull(updatedBook);
    }

    @Test
    void delete() {
        BookDTO bookDTO = BookDTO.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .id(1L)
                .build();
        Book book = Book.builder()
                .author("test")
                .genre("test")
                .title("test")
                .price(37)
                .quantity(23)
                .id(1L)
                .build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.delete(bookDTO.getId());
    }
}
