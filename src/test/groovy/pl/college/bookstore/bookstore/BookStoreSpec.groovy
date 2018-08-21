package pl.college.bookstore.bookstore

import pl.college.bookstore.students.StudentsFacade
import spock.lang.Specification

class BookStoreSpec extends Specification {

    BookRepository bookRepository = Stub(BookRepository.class)
    StudentsFacade studentsFacade = Mock(StudentsFacade.class)
    BookStoreFacade bookStoreFacade

    def setup() {
        bookStoreFacade = new BookStoreFacade(studentsFacade, bookRepository)
    }

    def "Should rent a book"(){
        def bookId = 5L
        def studentId = 2L

        given: "Zakładając, że istnieje ksiazka o nazwie \"w pustyni i w puszczy\" o id 5"
        bookRepository.findBook(bookId) >> new Book()

        and: "I licencja studenta jest ważna"
        studentsFacade.checkIfStudentLicenseIsValid(studentId) >> true

        when: "Gdy Student chce wypożyczyć tą książke"
        boolean result = bookStoreFacade.rentABook(bookId, studentId)

        then: "Wtedy ksiazka powinna zostać wypozyczona"
        result
    }

    def "Nie wypozyczenie ksiazki przez niewazną legitymacje"(){
        def bookId = 5L
        def studentId = 2L

        given: "Zakładając, że istnieje ksiazka o nazwie \"w pustyni i w puszczy\" o id 5"
        bookRepository.findBook(bookId) >> new Book()

        and: "I licencja studenta nie jest ważna"
        studentsFacade.checkIfStudentLicenseIsValid(studentId) >> false

        when: "Gdy Student chce wypożyczyć tą książke"
        boolean result = bookStoreFacade.rentABook(bookId, studentId)

        then: "Wtedy ksiazka powinna zostać wypozyczona"
        !result
    }

    def "Po wypożyczeniu książka ma status wypożyczona"(){
        def bookId = 5L
        def studentId = 2L

        given: "Zakładając, że istnieje ksiazka o nazwie \"w pustyni i w puszczy\" o id 5"
        bookRepository.findBook(bookId) >> new Book()

        and: "I licencja studenta jest ważna"
        studentsFacade.checkIfStudentLicenseIsValid(studentId) >> true

        when: "Gdy Student chce wypożyczyć tą książke"
        boolean result = bookStoreFacade.rentABook(bookId, studentId)

        then: "Wtedy ksiazka powinna zmienić stan na wypożyczona"
        result
        bookRepository.findBook(bookId).isRented()

    }

    def "Moduł studentów będzie odpytany tylko raz dla ważnej legitymacji."(){
        def bookId = 5L
        def studentId = 2L

        given: "Zakładając, że istnieje ksiazka o nazwie \"w pustyni i w puszczy\" o id 5"
        bookRepository.findBook(bookId) >> new Book()

        and: "I licencja studenta jest ważna"
        studentsFacade.checkIfStudentLicenseIsValid(studentId) >> true

        when: "Gdy Student chce wypożyczyć tą książke"
        boolean result = bookStoreFacade.rentABook(bookId, studentId)

        then: "Wtedy moduł studentów powinien być zapytany tylko raz."
        1 * studentsFacade.checkIfStudentLicenseIsValid(_)

    }

    def "Moduł studentów będzie odpytany tylko raz dla nieważnej legitymacji."(){
        def bookId = 5L
        def studentId = 2L

        given: "Zakładając, że istnieje ksiazka o nazwie \"w pustyni i w puszczy\" o id 5"
        bookRepository.findBook(bookId) >> new Book()

        and: "I licencja studenta nie jest ważna"
        studentsFacade.checkIfStudentLicenseIsValid(studentId) >> false

        when: "Gdy Student chce wypożyczyć tą książke"
        boolean result = bookStoreFacade.rentABook(bookId, studentId)

        then: "Wtedy moduł studentów powinien być zapytany tylko raz."
        1 * studentsFacade.checkIfStudentLicenseIsValid(studentId)

    }

}