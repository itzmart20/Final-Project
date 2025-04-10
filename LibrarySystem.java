// File: LibrarySystem.java (Main class)
import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class LibrarySystem {
    private static List<User> users = new ArrayList<>();
    private static List<Book> books = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        loadData();
        createDefaultAdmin();
        showLoginScreen();
        saveData();
        scanner.close();
    }
    
    private static void loadData() {
        loadUsers();
        loadBooks();
        loadTransactions();
    }
    
    private static void createDefaultAdmin() {
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", "Default Admin", "Admin"));
            System.out.println("Default admin created. Username: admin, Password: admin123");
        }
    }
    
    private static void showLoginScreen() {
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
                System.out.println("1. Login");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                
                int choice = getIntInput();
                switch (choice) {
                    case 1: login(); break;
                    case 0: running = false; break;
                    default: System.out.println("Invalid option.");
                }
            } else {
                showMainMenu();
            }
        }
    }
    
    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful. Welcome, " + user.getName() + " (" + user.getRole() + ")!");
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }
    
    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
    
    private static void showMainMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("Logged in as: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        
        switch (currentUser.getRole()) {
            case "Admin": showAdminMenu(); break;
            case "Librarian": showLibrarianMenu(); break;
            case "Reader": showReaderMenu(); break;
        }
    }
    
    private static void showAdminMenu() {
        System.out.println("\n----- ADMIN MENU -----");
        System.out.println("1. Add user | 2. Remove user | 3. List users");
        System.out.println("4. View books | 9. Logout | 0. Exit");
        System.out.print("Choice: ");
        
        switch (getIntInput()) {
            case 1: addUser(); break;
            case 2: removeUser(); break;
            case 3: listAllUsers(); break;
            case 4: listAllBooks(); break;
            case 9: logout(); break;
            case 0: saveData(); System.exit(0);
            default: System.out.println("Invalid option.");
        }
    }
    
    private static void showLibrarianMenu() {
        System.out.println("\n----- LIBRARIAN MENU -----");
        System.out.println("1. Add book | 2. Remove book | 3. List books");
        System.out.println("4. View transactions | 9. Logout | 0. Exit");
        System.out.print("Choice: ");
        
        switch (getIntInput()) {
            case 1: addBook(); break;
            case 2: removeBook(); break;
            case 3: listAllBooks(); break;
            case 4: viewAllTransactions(); break;
            case 9: logout(); break;
            case 0: saveData(); System.exit(0);
            default: System.out.println("Invalid option.");
        }
    }
    
    private static void showReaderMenu() {
        System.out.println("\n----- READER MENU -----");
        System.out.println("1. View books | 2. Borrow book | 3. Return book");
        System.out.println("4. My borrowed books | 9. Logout | 0. Exit");
        System.out.print("Choice: ");
        
        switch (getIntInput()) {
            case 1: listAllBooks(); break;
            case 2: borrowBook(); break;
            case 3: returnBook(); break;
            case 4: viewMyBorrowedBooks(); break;
            case 9: logout(); break;
            case 0: saveData(); System.exit(0);
            default: System.out.println("Invalid option.");
        }
    }
    
    private static void addUser() {
        System.out.println("\n----- ADD USER -----");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        
        System.out.println("Select role: 1. Admin | 2. Librarian | 3. Reader");
        System.out.print("Choice: ");
        int roleChoice = getIntInput();
        
        String role;
        switch (roleChoice) {
            case 1: role = "Admin"; break;
            case 2: role = "Librarian"; break;
            case 3: role = "Reader"; break;
            default: System.out.println("Invalid role."); return;
        }
        
        users.add(new User(username, password, name, role));
        System.out.println("User added successfully.");
        saveUsers();
    }
    
    private static void removeUser() {
        System.out.println("\n----- REMOVE USER -----");
        System.out.print("Username to remove: ");
        String username = scanner.nextLine();
        
        if (username.equals(currentUser.getUsername())) {
            System.out.println("You cannot remove your own account.");
            return;
        }
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                System.out.println("User removed successfully.");
                saveUsers();
                return;
            }
        }
        System.out.println("User not found.");
    }
    
    private static void listAllUsers() {
        System.out.println("\n----- ALL USERS -----");
        for (User user : users) {
            System.out.println(user);
        }
    }
    
    private static void addBook() {
        System.out.println("\n----- ADD BOOK -----");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        
        System.out.println("Book type: 1. Printed Book | 2. E-Book");
        System.out.print("Choice: ");
        int typeChoice = getIntInput();
        
        Book newBook;
        if (typeChoice == 1) {
            System.out.print("Number of pages: ");
            int pages = getIntInput();
            newBook = new Book(title, author, genre, isbn, "PrintedBook", pages, null);
        } else if (typeChoice == 2) {
            System.out.print("File format: ");
            String format = scanner.nextLine();
            newBook = new Book(title, author, genre, isbn, "Ebook", 0, format);
        } else {
            System.out.println("Invalid book type.");
            return;
        }
        
        books.add(newBook);
        System.out.println("Book added successfully.");
        saveBooks();
    }
    
    private static void removeBook() {
        System.out.println("\n----- REMOVE BOOK -----");
        System.out.print("ISBN of book to remove: ");
        String isbn = scanner.nextLine();
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getIsbn().equals(isbn)) {
                if (!book.isAvailable() && !book.getType().equals("Ebook")) {
                    System.out.println("Book is currently borrowed and cannot be removed.");
                    return;
                }
                books.remove(i);
                System.out.println("Book removed successfully.");
                saveBooks();
                return;
            }
        }
        System.out.println("Book not found.");
    }
    
    private static void listAllBooks() {
        System.out.println("\n----- ALL BOOKS -----");
        for (Book book : books) {
            System.out.println(book);
            System.out.println();
        }
    }
    
    private static void borrowBook() {
        if (!currentUser.getRole().equals("Reader")) {
            System.out.println("Only readers can borrow books.");
            return;
        }
        
        System.out.println("\n----- BORROW BOOK -----");
        System.out.print("ISBN of book to borrow: ");
        String isbn = scanner.nextLine();
        
        Book bookToBorrow = null;
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                bookToBorrow = book;
                break;
            }
        }
        
        if (bookToBorrow == null) {
            System.out.println("Book not found.");
            return;
        }
        
        if (bookToBorrow.getType().equals("Ebook")) {
            System.out.println("E-books cannot be borrowed. They are always available.");
            return;
        }
        
        if (!bookToBorrow.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return;
        }
        
        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(14);
        
        Transaction transaction = new Transaction(generateTransactionId(), 
            currentUser.getUsername(), bookToBorrow.getIsbn(), now, dueDate, null);
        
        transactions.add(transaction);
        bookToBorrow.setAvailable(false);
        bookToBorrow.setDueDate(dueDate);
        
        System.out.println("Book borrowed successfully. Due date: " + dueDate);
        saveTransactions();
        saveBooks();
    }
    
    private static String generateTransactionId() {
        return "T" + (transactions.size() + 1);
    }
    
    private static void returnBook() {
        if (!currentUser.getRole().equals("Reader")) {
            System.out.println("Only readers can return books.");
            return;
        }
        
        System.out.println("\n----- RETURN BOOK -----");
        
        // Show borrowed books
        boolean hasBorrowedBooks = false;
        for (Transaction t : transactions) {
            if (t.getUsername().equals(currentUser.getUsername()) && t.getReturnDate() == null) {
                for (Book b : books) {
                    if (b.getIsbn().equals(t.getBookIsbn())) {
                        System.out.println(b);
                        System.out.println("Due Date: " + t.getDueDate());
                        System.out.println();
                        hasBorrowedBooks = true;
                    }
                }
            }
        }
        
        if (!hasBorrowedBooks) {
            System.out.println("You have no books to return.");
            return;
        }
        
        System.out.print("ISBN of book to return: ");
        String isbn = scanner.nextLine();
        
        Book bookToReturn = null;
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                bookToReturn = book;
                break;
            }
        }
        
        if (bookToReturn == null) {
            System.out.println("Book not found.");
            return;
        }
        
        Transaction transactionToUpdate = null;
        for (Transaction t : transactions) {
            if (t.getBookIsbn().equals(isbn) && 
                t.getUsername().equals(currentUser.getUsername()) && 
                t.getReturnDate() == null) {
                transactionToUpdate = t;
                break;
            }
        }
        
        if (transactionToUpdate == null) {
            System.out.println("You did not borrow this book.");
            return;
        }
        
        LocalDate now = LocalDate.now();
        transactionToUpdate.setReturnDate(now);
        bookToReturn.setAvailable(true);
        bookToReturn.setDueDate(null);
        
        System.out.println("Book returned successfully.");
        saveTransactions();
        saveBooks();
    }
    
    private static void viewMyBorrowedBooks() {
        System.out.println("\n----- MY BORROWED BOOKS -----");
        boolean hasBorrowedBooks = false;
        
        for (Transaction t : transactions) {
            if (t.getUsername().equals(currentUser.getUsername()) && t.getReturnDate() == null) {
                for (Book b : books) {
                    if (b.getIsbn().equals(t.getBookIsbn())) {
                        System.out.println(b);
                        System.out.println("Due Date: " + t.getDueDate());
                        System.out.println();
                        hasBorrowedBooks = true;
                    }
                }
            }
        }
        
        if (!hasBorrowedBooks) {
            System.out.println("You have no borrowed books.");
        }
    }
    
    private static void viewAllTransactions() {
        System.out.println("\n----- ALL TRANSACTIONS -----");
        for (Transaction t : transactions) {
            System.out.println(t);
            System.out.println();
        }
    }
    
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // File I/O methods
    private static void saveData() {
        saveUsers();
        saveBooks();
        saveTransactions();
    }
    
    private static void loadUsers() {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing users data found. Starting with empty list.");
        }
    }
    
    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                writer.println(user.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Error saving users data: " + e.getMessage());
        }
    }
    
    private static void loadBooks() {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    String isbn = parts[3];
                    String type = parts[4];
                    boolean available = Boolean.parseBoolean(parts[5]);
                    LocalDate dueDate = parts[6].equals("null") ? null : LocalDate.parse(parts[6]);
                    
                    if (type.equals("PrintedBook") && parts.length >= 8) {
                        int pages = Integer.parseInt(parts[7]);
                        books.add(new Book(title, author, genre, isbn, type, pages, null, available, dueDate));
                    } else if (type.equals("Ebook") && parts.length >= 8) {
                        String format = parts[7];
                        books.add(new Book(title, author, genre, isbn, type, 0, format, available, dueDate));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No existing books data found. Starting with empty list.");
        }
    }
    
    private static void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.println(book.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books data: " + e.getMessage());
        }
    }
    
    private static void loadTransactions() {
        transactions.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String username = parts[1];
                    String isbn = parts[2];
                    LocalDate borrowDate = LocalDate.parse(parts[3]);
                    LocalDate dueDate = LocalDate.parse(parts[4]);
                    LocalDate returnDate = parts[5].equals("null") ? null : LocalDate.parse(parts[5]);
                    
                    transactions.add(new Transaction(id, username, isbn, borrowDate, dueDate, returnDate));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing transactions data found. Starting with empty list.");
        }
    }
    
    private static void saveTransactions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt"))) {
            for (Transaction transaction : transactions) {
                writer.println(transaction.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions data: " + e.getMessage());
        }
    }
}

// File: User.java


// File: Book.java


// File: Transaction.java
