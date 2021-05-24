package roland.bookstore.book;

import org.mapstruct.Mapper;
import roland.bookstore.book.model.Book;
import roland.bookstore.book.model.dto.BookDTO;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);

    Book fromDto(BookDTO bookDTO);
}
