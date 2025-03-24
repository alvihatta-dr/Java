// Subclass Magazine (Polymorphism - Overriding)

class Magazine extends LibraryItem {

    private final int issueNumber;

    public Magazine(String title, String author, int publicationYear, int issueNumber) {
        super(title, author, publicationYear);
        this.issueNumber = issueNumber;
    }

    @Override
    public void displayInfo() {
        System.out.println("Magazine - Title: " + getTitle() + ", Author: " + getAuthor() + ", Year: " + getPublicationYear() + ", Issue: " + issueNumber);
    }
}
