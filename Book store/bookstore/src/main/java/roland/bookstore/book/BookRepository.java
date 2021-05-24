package roland.bookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roland.bookstore.book.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT book from Book book where book.quantity = 0")
    List<Book> getBooksOutOfStock();
}
