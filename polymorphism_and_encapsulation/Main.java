// Main Class

public class Main {

    public static void main(String[] args) {
        LibraryItem item = new LibraryItem("General Item", "Unknown", 2000);
        item.displayInfo();
        item.borrowItem();
        item.borrowItem("Alice");

        System.out.println();

        Book book = new Book("The Java Handbook", "John Doe", 2015, 450);
        book.displayInfo();
        book.borrowItem("Bob");

        System.out.println();

        Magazine magazine = new Magazine("Tech Monthly", "Jane Smith", 2023, 10);
        magazine.displayInfo();
        magazine.borrowItem();
    }
}
