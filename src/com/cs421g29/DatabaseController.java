package com.cs421g29;

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
                    "SELECT * from USERORDER"
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
            java.sql.ResultSet results = statement.executeQuery(
                    "SELECT * from USERORDER"
            );
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = openConnection();
        ArrayList<Order> allOrders = getAllOrders(conn);
        for (Order o : allOrders) {
            System.out.println("order: " + o.oid + " " + o.shippingaddress);
        }


        // Creating a table
        /*try {
            String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
            System.out.println (createSQL ) ;
            statement.executeUpdate (createSQL ) ;
            System.out.println ("DONE");
        }catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }

        // Inserting Data into the table
        try {
            String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) " ;
            System.out.println ( insertSQL ) ;
            statement.executeUpdate ( insertSQL ) ;
            System.out.println ( "DONE" ) ;

            insertSQL = "INSERT INTO " + tableName + " VALUES ( 2 , \'Vera\' ) " ;
            System.out.println ( insertSQL ) ;
            statement.executeUpdate ( insertSQL ) ;
            System.out.println ( "DONE" ) ;
            insertSQL = "INSERT INTO " + tableName + " VALUES ( 3 , \'Franca\' ) " ;
            System.out.println ( insertSQL ) ;
            statement.executeUpdate ( insertSQL ) ;
            System.out.println ( "DONE" ) ;

        } catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.gsetSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }*/

        // Querying a table
        /*try {
            String querySQL = "SELECT aid, name from author";
            System.out.println (querySQL) ;
            java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
            while ( rs.next ( ) ) {
                int id = rs.getInt ( 1 ) ;
                String name = rs.getString (2);
                System.out.println ("aid:  " + id);
                System.out.println ("name:  " + name);
            }
            System.out.println ("DONE");
        } catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }*/

        //Updating a table
        /*try {
            String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
            System.out.println(updateSQL);
            statement.executeUpdate(updateSQL);
            System.out.println("DONE");

            // Dropping a table
            String dropSQL = "DROP TABLE " + tableName;
            System.out.println ( dropSQL ) ;
            statement.executeUpdate ( dropSQL ) ;
            System.out.println ("DONE");
        } catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }*/
    }
}