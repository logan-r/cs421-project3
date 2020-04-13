package com.cs421g29;

import java.util.Date;

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseController {
    static final String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";

    /**
     * Open a new connection to our project's database
     * that statements can be run on
     * @return the connection or null if there was an error
     */
    public static Connection openConnection() {
        // Register progresql driver
        try {
            DriverManager.registerDriver(new org.postgresql.Driver()) ;
        } catch (Exception cnfe) {
            System.out.println("Can't find postgresql driver, please make sure you've added it to the classpath.");
        }

        // Attempt to open connection to database
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "cs421g29", "Group20nine");
        } catch (Exception e) {
            System.out.println("Can't connect to comp421.cs.mcgill.ca");
            return null;
        }

        return conn;
    }

    /**
     * Closes a connection to the database
     * @param conn  the connection to close
     */
    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    /**
     * Gets a certain number of books from the database (offset by a certain about for pagation purposes)
     * @param conn  a connection to the database (see this.openConnection)
     * @param limit  the number of books to fetch
     * @param offset   the number of books to skip before fetching
     * @return an array list of books or null if there was an error
     * @throws SQLException
     */
    public static ArrayList<Book> getBooks(Connection conn, int limit, int offset) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Make arraylist to put books into
        ArrayList<Book> allBooks = new ArrayList<>();

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery(
                    "select book.bid, book.title, author.name, book.description, book.price, book.stockcount, book.pagecount from writtenby join " +
                            "author on writtenby.aid = author.aid join book on writtenby.bid = book.bid order by book.bid limit " + limit + " offset " + offset
            );

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                allBooks.add(
                        new Book(
                                results.getInt("bid"),
                                results.getString("title"),
                                results.getString("name"),
                                results.getString("description"),
                                results.getDouble("price"),
                                results.getInt("pagecount"),
                                results.getInt("stockcount")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all books
        statement.close();
        return allBooks;
    }

    /**
     * Gets book of specific id with its average rating
     * @param conn  a connection to the database (see this.openConnection)
     * @param id  the id of the book to fetch
     * @return a book object (or null if couldn't find)
     * @throws SQLException
     */
    public static Book getBookOfIdWithAvgRating(Connection conn, int id) throws SQLException {
        // Make a new sql statement
        PreparedStatement statement = conn.prepareStatement(
                "select book.bid, book.title, author.name, book.description, book.price, book.pagecount, book.stockcount, coalesce(avg_score.average, 0) average from writtenby join " +
                        "author on writtenby.aid = author.aid join book on writtenby.bid = book.bid " +
                        "left join (SELECT bid, AVG(score) average FROM rating GROUP BY bid) avg_score on book.bid = avg_score.bid " +
                        "where book.bid = ?"
        );
        statement.setInt(1, id);

        // The book to return
        Book book = null;

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery();

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                book = new Book(
                        results.getInt("bid"),
                        results.getString("title"),
                        results.getString("name"),
                        results.getString("description"),
                        results.getDouble("price"),
                        results.getInt("pagecount"),
                        results.getInt("stockcount"),
                        results.getDouble("average")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching book of specific id with rating. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all books
        statement.close();
        return book;
    }

    /**
     * Gets the number of books in the database
     * @param conn  a connection to the database (see this.openConnection)
     * @return the number of book records in databse
     * @throws SQLException
     */
    public static int getNumberOfBooks(Connection conn) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        int count = 0; // Number of books

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery(
                    "select count(*) from writtenby"
            );

            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching number of books. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return 0;
        }

        // Return list of all books
        statement.close();
        return count;
    }

    /**
     * Gets all the books in a specific user's shopping cart
     * @param conn  a connection to the database (see this.openConnection)
     * @param email  the email of the user from whose shopping cart to fetch the books
     * @return an array list of books or null if there was an error
     * @throws SQLException
     */
    public static ArrayList<Book> getBooksInUserShoppingCart(Connection conn, String email) throws SQLException {
        // Make a new sql statement
        PreparedStatement statement = conn.prepareStatement(
                "select book.bid, book.title, author.name, book.description, book.price, book.pagecount, book.stockcount from " +
                        "useraccount join shoppingcart on useraccount.email = shoppingcart.email join cartincludes on " +
                        "shoppingcart.sc_id = cartincludes.sc_id join book on book.bid = cartincludes.bid " +
                        "join writtenby on writtenby.bid = book.bid join author on author.aid = writtenby.aid " +
                        "where useraccount.email = ?;"
        );
        statement.setString(1, email);

        // Make arraylist to put books into
        ArrayList<Book> allBooks = new ArrayList<>();

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery();

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                allBooks.add(
                        new Book(
                                results.getInt("bid"),
                                results.getString("title"),
                                results.getString("name"),
                                results.getString("description"),
                                results.getDouble("price"),
                                results.getInt("pagecount"),
                                results.getInt("stockcount")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all books
        statement.close();
        return allBooks;
    }

    /**
     * Check if a book with a certain email id in our database
     * @param conn  a connection to the database (see this.openConnection)
     * @param id  the id to check if exists
     * @return true if book exists, else false
     * @throws SQLException
     */
    public static boolean existsBookWithId(Connection conn, int id) throws SQLException {
        // Make a new sql statement
        PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM writtenby WHERE bid = ?");
        statement.setInt(1, id);

        // Record the number of users that match said email
        int count = 0;

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery();

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching book with specific id. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return false;
        }

        // Return list of all orders
        statement.close();
        return count > 0;
    }

    /**
     * Updates the stock of a specific book
     * @param conn  a connection to the database (see this.openConnection)
     * @param id  the id of the book to update
     * @param stock  the new number of copies we possess
     * @throws SQLException
     */
    public static void updateBookStock(Connection conn, int id, int stock) throws SQLException {
        // Make a new sql statement
        PreparedStatement statement = conn.prepareStatement("update book set stockcount = ? where bid = ?");
        statement.setInt(1, stock);
        statement.setInt(2, id);

        // Attempt to execute the query
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating book stock. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
        }

        // Return list of all orders
        statement.close();
    }

    /** Get a list of every user in database
     * @param conn  Connection to database
     * @return list of users
     * @throws SQLException
     */
    public static ArrayList<String> getUserEmails(Connection conn) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Make arraylist to put results into
        ArrayList<String> allEmails = new ArrayList<>();

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery(
                    "SELECT email FROM useraccount"
            );

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                allEmails.add(results.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all emails. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all orders
        statement.close();
        return allEmails;
    }

    /**
     * Gets a list of every user email in the database
     * @param conn  a connection to the database (see this.openConnection)
     * @return an array list of every email or null if there was an error
     * @throws SQLException
     */
    public static ArrayList<String> getUserEmails(Connection conn, int limit) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Make arraylist to put results into
        ArrayList<String> allEmails = new ArrayList<>();

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery(
                    "SELECT email FROM useraccount limit " + limit
            );

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                allEmails.add(results.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all emails. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all orders
        statement.close();
        return allEmails;
    }

    /**
     * Check if a user with a certain email exist in our database
     * @param conn  a connection to the database (see this.openConnection)
     * @param email  the email to check if exists
     * @return true if user exists, else false
     * @throws SQLException
     */
    public static boolean existsUserWithEmail(Connection conn, String email) throws SQLException {
        // Make a new sql statement
        PreparedStatement statement = conn.prepareStatement("SELECT count(*) FROM useraccount WHERE email = ?");
        statement.setString(1, email);

        // Record the number of users that match said email
        int count = 0;

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery();

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user with specific email. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return false;
        }

        // Return list of all orders
        statement.close();
        return count > 0;
    }

    /**
     * Add an order to the database
     * @param conn  a connection to the database
     * @param order  the order object to add to the database
     * @return true if successfully added, false otherwise
     * @throws SQLException
     */
    public static boolean addOrder(Connection conn, Order order) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Attempt to execute the query
        try {
            String sql = String.format(
                    "INSERT INTO userorder VALUES (%d, '%s', '%s', '%s', '%s', '%s', %.2f)",
                    order.oid, "2020-01-01", order.paymentmethod, order.status,
                    order.email, order.shippingaddress, order.rate
            );
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (e.getSQLState() == "23503") {
                System.out.println("Error creating order. No valid account exists for user email.");
            } else {
                System.out.println("Error fetching updating orders. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            }
            statement.close();
            return false;
        }

        // Return true if updated
        statement.close();
        return true;
    }

    /**
     * Update an order associate with a specific id in the database
     * @param conn  a connection to the database
     * @param order  the order object to add to the database
     * @return true if successfully added, false otherwise
     * @throws SQLException
     */
    public static boolean updateOrder(Connection conn, Order order) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Attempt to execute the query
        try {
            String sql = String.format(
                    "INSERT INTO userorder VALUES (%d, '%s', '%s', '%s', '%s', '%s', %.2f)",
                    order.oid, "2020-01-01", order.paymentmethod, order.status,
                    order.email, order.shippingaddress, order.rate
            );
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (e.getSQLState().toString() == "23503") {
                System.out.println("Error creating order. No valid account exists for user email.");
            } else {
                System.out.println("Error fetching updating orders. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            }
            statement.close();
            return false;
        }

        // Return true if updated
        statement.close();
        return true;
    }
}