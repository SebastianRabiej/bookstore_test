package pl.college.bookstore.bookstore;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class BookRepository {

    Map<Long,Book> inMemoryDb = new HashMap<>();

    public Book findBook(long bookId) {
        return inMemoryDb.get(bookId);
    }

    public void update(Book book) {
        //ze względu, że jest to pseudo implementacji w pamięci, nie musimy robić update.
        //Referencja załatwia wszystko.
    }
}
