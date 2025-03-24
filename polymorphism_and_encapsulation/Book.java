// Subclass Book (Polymorphism - Overriding)

class Book extends LibraryItem {

    private final int pages;

    public Book(String title, String author, int publicationYear, int pages) {
        super(title, author, publicationYear);
        this.pages = pages;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book - Title: " + getTitle() + ", Author: " + getAuthor() + ", Year: " + getPublicationYear() + ", Pages: " + pages);
    }
}
