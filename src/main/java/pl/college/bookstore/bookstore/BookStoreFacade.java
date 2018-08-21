package pl.college.bookstore.bookstore;

import org.springframework.stereotype.Service;
import pl.college.bookstore.payments.PaymentsFacade;
import pl.college.bookstore.payments.StudentWithUnpaidTransactions;
import pl.college.bookstore.students.StudentsFacade;

@Service
public class BookStoreFacade {

    private final StudentsFacade studentsFacade;
    private final BookRepository bookRepository;
    private final PaymentsFacade paymentsFacade;

    public BookStoreFacade(StudentsFacade studentsFacade, BookRepository bookRepository, PaymentsFacade paymentsFacade) {
        this.studentsFacade = studentsFacade;
        this.bookRepository = bookRepository;
        this.paymentsFacade = paymentsFacade;
    }

    public boolean rentABook(long bookId, long studentId){
        if(!studentsFacade.checkIfStudentLicenseIsValid(studentId)){
            return false;
        }
        try {
            paymentsFacade.validStudentPayments(studentId);
        } catch (StudentWithUnpaidTransactions studentWithUnpaidTransactions) {
            return false;
        }
        final Book book = bookRepository.findBook(bookId);
        book.markAsRented();
        bookRepository.update(book);
        return true;
    }
}
