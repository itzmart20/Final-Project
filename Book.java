import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private String type; // "PrintedBook" or "Ebook"
    private int pages; // For PrintedBook
    private String fileFormat; // For Ebook
    private boolean available;
    private LocalDate dueDate;
    
    // Constructor for new books
    public Book(String title, String author, String genre, String isbn, String type, int pages, String fileFormat) {
        this(title, author, genre, isbn, type, pages, fileFormat, true, null);
    }
    
    // Full constructor
    public Book(String title, String author, String genre, String isbn, String type, 
                int pages, String fileFormat, boolean available, LocalDate dueDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.type = type;
        this.pages = pages;
        this.fileFormat = fileFormat;
        this.available = type.equals("Ebook") ? true : available;
        this.dueDate = dueDate;
    }
    
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getIsbn() { return isbn; }
    public String getType() { return type; }
    
    public boolean isAvailable() {
        return type.equals("Ebook") ? true : available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public LocalDate getDueDate() { return dueDate; }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title)
          .append("\nAuthor: ").append(author)
          .append("\nGenre: ").append(genre)
          .append("\nISBN: ").append(isbn)
          .append("\nType: ").append(type);
          
        if (type.equals("PrintedBook")) {
            sb.append("\nPages: ").append(pages);
            sb.append("\nStatus: ").append(available ? "Available" : "Borrowed (Due: " + dueDate + ")");
        } else {
            sb.append("\nFile Format: ").append(fileFormat);
            sb.append("\nNote: E-books are always available");
        }
        
        return sb.toString();
    }
    
    public String toCsvString() {
        String dueDateStr = (dueDate != null) ? dueDate.toString() : "null";
        String additionalInfo = (type.equals("PrintedBook")) ? String.valueOf(pages) : fileFormat;
        return title + "," + author + "," + genre + "," + isbn + "," + 
               type + "," + available + "," + dueDateStr + "," + additionalInfo;
    }
}