package roland.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roland.bookstore.book.model.Book;
import roland.bookstore.book.model.dto.BookDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book newbook = findById(book.getId());
        newbook.setAuthor(book.getAuthor());
        newbook.setGenre(book.getGenre());
        newbook.setTitle(book.getTitle());
        newbook.setPrice(book.getPrice());
        newbook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(newbook)
        );
    }

    public void delete(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.delete(book.get());
    }

    public List<Book> booksOutOfstock(){
        return bookRepository.getBooksOutOfStock();
    }



}
