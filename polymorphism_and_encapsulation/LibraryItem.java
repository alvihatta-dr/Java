// Superclass (Encapsulation & Polymorphism)

class LibraryItem {

    private final String title;
    private final String author;
    private final int publicationYear;

    public LibraryItem(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void displayInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", Year: " + publicationYear);
    }

    // Overloaded method
    public void borrowItem() {
        System.out.println("The item \"" + title + "\" has been borrowed.");
    }

    public void borrowItem(String borrowerName) {
        System.out.println(borrowerName + " has borrowed the item \"" + title + "\".");
    }
}
