import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private String username;
    private String bookIsbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    public Transaction(String transactionId, String username, String bookIsbn, 
                      LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate) {
        this.transactionId = transactionId;
        this.username = username;
        this.bookIsbn = bookIsbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    
    public String getTransactionId() { return transactionId; }
    public String getUsername() { return username; }
    public String getBookIsbn() { return bookIsbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    @Override
    public String toString() {
        String status = (returnDate != null) ? "Returned on " + returnDate : "Due on " + dueDate;
        return "Transaction ID: " + transactionId + 
               "\nUser: " + username + 
               "\nBook ISBN: " + bookIsbn + 
               "\nBorrowed on: " + borrowDate + 
               "\nStatus: " + status;
    }
    
    public String toCsvString() {
        String returnDateStr = (returnDate != null) ? returnDate.toString() : "null";
        return transactionId + "," + username + "," + bookIsbn + "," + 
               borrowDate + "," + dueDate + "," + returnDateStr;
    }
}