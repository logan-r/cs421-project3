package com.cs421g29;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;




public class Main {
    // Let user page books in groups of 10
    public static int bookOffset = 0;
    public static final int pageSize = 10;

    // Store user's connection to database
    public static Connection conn;

    // Create scanner to check for user input
    public static Scanner inputScanner = new Scanner(System.in);

    /**
     * Prints a list of books to the console
     */
    public static void printBooks(ArrayList<Book> bookList) {
        for (Book book : bookList) {
            System.out.println(
                    String.format(
                            "* [ID #%d] \"%s\" by %s ($%.2f, %d in stock, %d pages)",
                            book.id, book.title, book.author, book.price, book.stock, book.pageCount
                    )
            );
        }
    }

    /**
     * Prompt the user for a command to execute from the main menu and then do so
     * @return whether or not user has decided to keep running program (false = quit)
     */
    public static boolean getCommand() throws SQLException {
        // Let the user pick a command
        System.out.println("Pick one of the following commands:");
        System.out.println("1. View previous 10 books");
        System.out.println("2. View next 10 books");
        System.out.println("3. Lookup user by email and view their shopping cart");
        System.out.println("4. Add rating of book from user");
        System.out.println("5. View book's average rating");
        System.out.println("6. Update book's stock");
        System.out.println("7. Quit");

        int numberOfBooks = DatabaseController.getNumberOfBooks(conn);

        while (true) {
            System.out.println("");
            System.out.print("Pick a command number (e.g. 1): ");
            String choice = inputScanner.nextLine();

            // Did user pick previous 10 books?
            if (choice.equals("1")) {
                if (bookOffset - pageSize >= 0) {
                    bookOffset -= pageSize;
                    break;
                } else {
                    System.out.println("Already at start of book catalog, can't view previous books (try viewing next " + pageSize + " books).");
                }
            }

            // Show user next 10 books
            else if (choice.equals("2")) {
                if (bookOffset + pageSize < numberOfBooks) {
                    bookOffset += pageSize;
                    break;
                } else {
                    System.out.println("Already at end of book catalog, can't view more books (try viewing previous " + pageSize + " books).");
                }
            }

            // Shows book in a user's shopping cart
            else if (choice.equals("3")) {
                // Get 3 sample emails from database
                ArrayList<String> sample = DatabaseController.getUserEmails(conn, 3);

                // Ask for user emails
                String email;
                System.out.println("");
                System.out.println("VIEW USER'S SHOPPING CART");
                System.out.println("=========================");
                while (true) {
                    System.out.println("");
                    System.out.println("What the email of the user who's cart you want to view?");
                    System.out.println("(Some emails from our database: " + String.join(", ", sample) + ")");
                    System.out.print("Email: ");
                    email = inputScanner.nextLine();

                    // Check if email they entered is valid
                    if (DatabaseController.existsUserWithEmail(conn, email)) {
                        break;
                    } else {
                        System.out.println("");
                        System.out.println("No user with that email exists");
                    }
                }

                // Get books associated with this account
                ArrayList<Book> books = DatabaseController.getBooksInUserShoppingCart(conn, email);
                System.out.println("");
                System.out.println("Books in this user's shopping cart:");
                if (books.size() > 0) {
                    printBooks(books);
                } else {
                    System.out.println("This user has no book in their shopping cart.");
                }
                System.out.println("");
                System.out.println("");
                System.out.println("Press enter to continue...");
                inputScanner.nextLine();
                break;
            }

            // Look up average rating for book
            else if (choice.equals("5")) {
                int id;
                while (true) {
                    System.out.println("");
                    System.out.println("What's the id # of the book you want to view the average rating for? ");
                    try {
                        id = inputScanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("That's not a valid number.");
                        inputScanner.next();
                        continue;
                    }

                    // Check if id they entered is valid
                    if (DatabaseController.existsBookWithId(conn, id)) {
                        break;
                    } else {
                        System.out.println("");
                        System.out.println("No book exists with that id");
                    }
                }

                // Get that book and its average rating
                Book book = DatabaseController.getBookOfIdWithAvgRating(conn, id);
                System.out.println("");
                if (book.rating != 0) {
                    System.out.println(
                            String.format(
                                    "Book #%d \"%s\" by %s has an average rating of %.2f stars out of 5",
                                    book.id, book.title, book.author, book.rating
                            )
                    );
                } else {
                    System.out.println(
                            String.format(
                                    "Book #%d \"%s\" by %s has no ratings yet",
                                    book.id, book.title, book.author, book.rating
                            )
                    );
                }
                System.out.println("");
                System.out.println("");
                System.out.println("Press enter to continue...");
                inputScanner.nextLine();
                inputScanner.nextLine();
                break;
            }

            // Update a books stock
            else if (choice.equals("6")) {
                int id;
                while (true) {
                    System.out.println("");
                    System.out.println("What's the id # of the book whose stock you want to update? ");
                    try {
                        id = inputScanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("That's not a valid number.");
                        inputScanner.next();
                        continue;
                    }

                    // Check if id they entered is valid
                    if (DatabaseController.existsBookWithId(conn, id)) {
                        break;
                    } else {
                        System.out.println("");
                        System.out.println("No book exists with that id");
                    }
                }

                int newStock;
                while (true) {
                    System.out.println("");
                    System.out.println("How many copies of that book do we now have in stock? ");
                    try {
                        newStock = inputScanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("That's not a valid number.");
                        inputScanner.next();
                        continue;
                    }

                    if (newStock >= 0) {
                        break;
                    }
                    System.out.println("That's not a valid number.");
                }

                // Update that book's stock
                DatabaseController.updateBookStock(conn, id, newStock);

                // Print results
                System.out.println("Book stock update. There are now " + newStock + " copies.");
                System.out.println("");
                System.out.println("");
                System.out.println("Press enter to continue...");
                inputScanner.nextLine();
                break;
            }

            // User decided to quit
            else if (choice.equals("7")) {
                return false; // Return false if user has exited program
            } else {
                System.out.println("Invalid command. Enter a number like \"1\" (but without the \"\").");
            }
        }

        // Return true if user hasn't exited program
        return true;
    }

    public static void main(String[] args) throws SQLException {
        // Open connection to database
        conn = DatabaseController.openConnection();
        if (conn != null) {
            while (true) {
                // Fetch the page of books the user can currently see
                ArrayList<Book> displayedBooks = DatabaseController.getBooks(conn, pageSize, bookOffset);

                // Display the books to the user
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("===============================");
                System.out.println("Welcome to a bookstore program.");
                System.out.println("");
                System.out.println("Here are some of our books:");
                printBooks(displayedBooks);
                System.out.println("");
                System.out.println("");

                // Check the user's input
                boolean keepRunning = getCommand();
                if (!keepRunning) {
                    break;
                }
            }

            DatabaseController.closeConnection(conn);
        }
    }
}
