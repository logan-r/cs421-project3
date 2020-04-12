package com.cs421g29;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    /**
     * Prints a list of books to the console
     */
    public static void printBooks(ArrayList<Book> bookList) {
        for (Book book : bookList) {
            System.out.println(
                    String.format(
                            "* [ID #%d] \"%s\" by %s ($%.2f, %d page)",
                            book.id, book.title, book.author, book.price, book.pageCount
                    )
            );
        }

    }

    public static void main(String[] args) throws SQLException {
        // Open connection to database
        Connection conn = DatabaseController.openConnection();
        if (conn != null) {
            // Let user page books in groups of 10
            int bookOffset = 0;
            final int pageSize = 10;

            // Create scanner to check for user input
            Scanner inputScanner = new Scanner(System.in);

            while (true) {
                // Fetch the page of books the user can currently see
                ArrayList<Book> displayedBooks = DatabaseController.getBooks(conn, pageSize, bookOffset);

                // Display the books to the user
                System.out.println("");
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

                // Let the user pick a command
                System.out.println("Pick one of the following commands:");
                System.out.println("1. View next 10 books");
                System.out.println("2. View last 10 books (disabled)");
                System.out.println("3. View user's shopping cart");
                System.out.println("4. Add book to user's shopping cart");
                System.out.println("5. Make user add rating to book");
                System.out.println("6. View book's average rating");
                System.out.println("7. View author ranked by their books' average ratings");
                System.out.println("");
                System.out.print("Pick a command number (e.g. 1): ");
                String choice = inputScanner.nextLine();
                System.out.println(choice);

                // Did user pick next 10 books?


                break;
            }

            DatabaseController.closeConnection(conn);
        }
    }
}
