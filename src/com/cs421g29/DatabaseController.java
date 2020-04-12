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
     * Gets a list of every order in the database
     * @param conn  a connection to the database (see this.openConnection)
     * @return an array list of every order or null if there was an error
     * @throws SQLException
     */
    public static ArrayList<Order> getAllOrders(Connection conn) throws SQLException {
        // Make a new sql statement
        Statement statement = conn.createStatement();

        // Make arraylist to put results into
        ArrayList<Order> allOrders = new ArrayList<Order>();

        // Attempt to execute the query
        try {
            java.sql.ResultSet results = statement.executeQuery(
                    "SELECT * FROM userorder"
            );

            // Convert every returned tuple to an order object and add list
            while (results.next()) {
                allOrders.add(
                    new Order(
                            results.getInt("oid"),
                            results.getDate("creatingdate"),
                            results.getString("paymentmethod"),
                            results.getString("status"),
                            results.getString("email"),
                            results.getString("shippingaddress"),
                            results.getFloat("rate")
                    )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all orders. Error code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            statement.close();
            return null;
        }

        // Return list of all orders
        statement.close();
        return allOrders;
    }

    /**
     *
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

    public static void main(String[] args) throws SQLException {
        Connection conn = openConnection();
        if (conn != null) {
            ArrayList<Order> allOrders = getAllOrders(conn);
            /*for (Order o : allOrders) {
                System.out.println("order: " + o.oid + " " + o.shippingaddress);
            }*/

            addOrder(conn, new Order(
                    222,
                    new Date(),
                    "Credit Card: XXXX-XXXX-XXXX-1234",
                    "Processing",
                    "Ali.Zemlak94@yahoo.ca",
                    "A fake address",
                    (float) 20.22
            ));
            closeConnection(conn);
        }
    }
}