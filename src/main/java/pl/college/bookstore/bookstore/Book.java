package pl.college.bookstore.bookstore;

class Book {
    private String title;
    private boolean isRented;

    public void markAsRented() {
        isRented = true;
    }

    public boolean isRented(){
        return isRented;
    }
}
