package pl.college.bookstore.bookstore;

import org.springframework.stereotype.Service;
import pl.college.bookstore.students.StudentsFacade;

@Service
public class BookStoreFacade {

    private final StudentsFacade studentsFacade;
    private final BookRepository bookRepository;

    public BookStoreFacade(StudentsFacade studentsFacade, BookRepository bookRepository) {
        this.studentsFacade = studentsFacade;
        this.bookRepository = bookRepository;
    }

    public boolean rentABook(long bookId, long studentId){
        if(!(studentsFacade.checkIfStudentLicenseIsValid(studentId))){
            return false;
        }
        final Book book = bookRepository.findBook(bookId);
        book.markAsRented();
        bookRepository.update(book);
        return true;
    }
}
