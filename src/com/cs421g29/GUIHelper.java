package com.cs421g29;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUIHelper {
	
	
	
	// Let user page books in groups of 10
    public static int bookOffset = 0;
    public static final int pageSize = 10;

    // Store user's connection to database
    public static Connection conn;

    // Create scanner to check for user input
    public static Scanner inputScanner = new Scanner(System.in);

    
   
    public static String printBooksString (ArrayList<Book> bookList) {
		String result = "";
		 for (Book book : bookList) {
	            result+= String.format( "* [ID #%d] \"%s\" by %s ($%.2f, %d pages)",
	                            book.id, book.title, book.author, book.price, book.pageCount) + System.lineSeparator();
	     }
		return result;
	}
    
    
    
    
    public static String display() {
    	String result = "";
    	
    	try {
    	conn = DatabaseController.openConnection();
    	
    	if (conn != null) {
    		ArrayList<Book> displayedBooks = DatabaseController.getBooks(conn, pageSize, bookOffset);
    		
    		 // Display the books to the user
    		
    		result += "Here are some of our books:" + System.lineSeparator();
    		result += printBooksString(displayedBooks);
    		DatabaseController.closeConnection(conn);
    	}else {
    		result = "Can't connect to comp421.cs.mcgill.ca";
    		return result;
    	}
    	
    	}catch (SQLException e) {
		
            System.out.println(e.getMessage());
        } 
    	return result;
    }
    
    
    
    
    public static String next() {
    	String result = "";

    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		int numberOfBooks = DatabaseController.getNumberOfBooks(conn);
        		
        		if (bookOffset - pageSize >= 0) {
                    bookOffset -= pageSize;
                } else {
                    System.out.println("Already at start of book catalog, can't view previous books (try viewing next " + pageSize + " books).");
                }
            	
        		ArrayList<Book> displayedBooks = DatabaseController.getBooks(conn, pageSize, bookOffset);
        		
        		 // Display the books to the user
        		result += "===============================" + System.lineSeparator();
        		result += "Here are some of our books:" + System.lineSeparator();
        		result += printBooksString(displayedBooks);
        	}	
        	
        	
        	DatabaseController.closeConnection(conn);
        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	
    	return result;
    }
    
    
    
    public static String prev() {
    	String result = "";

    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		int numberOfBooks = DatabaseController.getNumberOfBooks(conn);
        		
            	if (bookOffset + pageSize < numberOfBooks) {
                    bookOffset += pageSize;
            	} else {
                    System.out.println("Already at end of book catalog, can't view more books (try viewing previous " + pageSize + " books).");
            	}
            	
        		ArrayList<Book> displayedBooks = DatabaseController.getBooks(conn, pageSize, bookOffset);
        		
        		 // Display the books to the user
        		result += "===============================" + System.lineSeparator();
        		result += "Here are some of our books:" + System.lineSeparator();
        		result += printBooksString(displayedBooks);
        		DatabaseController.closeConnection(conn);
        	}else {
        		result = "Can't connect to comp421.cs.mcgill.ca";
        		return result;
        	}
        	
        	
        	
        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	
    	return result;
    }
    
    
    
    
    public static String option1(String email) {
    	String result = "";
    	

    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		
        		// Check if email they entered is valid
                if (!DatabaseController.existsUserWithEmail(conn, email)) {
                 
                    result += "No user with that email exists"+ System.lineSeparator();
                    return result; 
                }
                
                //valid
                // Get books associated with this account
                ArrayList<Book> books = DatabaseController.getBooksInUserShoppingCart(conn, email);
             
                result += "Books in this user's shopping cart:" + System.lineSeparator();
                if (books.size() > 0) {
                	result += printBooksString(books);
                    //printBooks(books);
                } else {
                	result += "This user has no book in their shopping cart." + System.lineSeparator();
                }

                DatabaseController.closeConnection(conn);
        	}else {
        		result = "Can't connect to comp421.cs.mcgill.ca";
        		return result;
        	}
        	
        	
        	
        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	return result;
    }
    
    
    
    
    
    public static String option2(String bookid) {
    	String result = "";
    	
    	int id ;
    	
    	try {
	    	id = Integer.parseInt(bookid);
    	}catch(Exception e) {
    		result += "Non-valid Number";
    		return result;
    	}
    	
    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		
        		// Check if id they entered is valid
                if (!DatabaseController.existsBookWithId(conn, id)) {
                	result += "No book exists with that id"+ System.lineSeparator();
                    return result; 
                }
                
                
                //valid id
                // Get that book and its average rating
                Book book = DatabaseController.getBookOfIdWithAvgRating(conn, id);
                
                if (book.rating != 0) {
                	result += String.format("Book #%d : \"%s\" \nAuthor: %s \nAverage Rating: %.2f / 5 stars",
                                    book.id, book.title, book.author, book.rating) + System.lineSeparator();
                	
                } else {
                	result += String.format("Book #%d \"%s\" by %s has no ratings yet",
                            book.id, book.title, book.author, book.rating) + System.lineSeparator();
  
                }
        		
                DatabaseController.closeConnection(conn);
        	}else {
        		result = "Can't connect to comp421.cs.mcgill.ca";
        		return result;
        	}
        	
        	
        	
        	
        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	return result;
    }
    
    
    
    public static String option3(String bidString, String numString) {
    	String result = "";
    	int id ;
    	int newStock ;
    	
    	try {
	    	id = Integer.parseInt(bidString);
	    	newStock = Integer.parseInt(numString);
    	}catch(Exception e) {
    		result += "Non-valid Number";
    		return result;
    	}
    	
    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		
        		// Check if id they entered is valid
                if (!DatabaseController.existsBookWithId(conn, id)) {
                	result += "No book exists with that id"+ System.lineSeparator();
                	return result; 
                }
                
                if (newStock < 0) {
                	result += "Non-valid Number"+ System.lineSeparator();
                	return result; 
                }
                
                // Update that book's stock
                DatabaseController.updateBookStock(conn, id, newStock);

                // Print results
                result += "Book stock updated. There are now " + newStock + " copies."+ System.lineSeparator();
	
                DatabaseController.closeConnection(conn);
        	}else {
        		result = "Can't connect to comp421.cs.mcgill.ca"+ System.lineSeparator();;
        		return result;
        	}
        	

        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	return result;
    }
    
    
    public static String option4(String email, String bidIn, String numIn) {
    	String result = "";
    	int id ;
    	int rating;
    	
    	
    	try {
	    	id = Integer.parseInt(bidIn);
	    	rating = Integer.parseInt(numIn);
    	}catch(Exception e) {
    		result += "Non-valid Number";
    		return result;
    	}
    	

    	
    	try {
        	conn = DatabaseController.openConnection();
        	

        	if (conn != null) {
        		
        		// Check if id they entered is valid
                if (!DatabaseController.existsBookWithId(conn, id)) {
                	result += "No book exists with that id"+ System.lineSeparator();
                	return result; 
                }
                
                // Check if email they entered is valid
                if (!DatabaseController.existsUserWithEmail(conn, email)) { 
                    result += "No user with that email exists"+ System.lineSeparator();
                    return result; 
                }
                
                if (rating <0 || rating > 5) {
                	result += "That's not a valid rating score (enter \"1\", \"2\", \"3\", \"4\", or \"5\" but without the \"\")."+ System.lineSeparator();
                    return result; 
          
                }
                
                // Perform rating of book
                DatabaseController.addRatingToBook(conn, id, email, rating);

                // Print results
                result += "Review added." + System.lineSeparator();
               
                
	
                DatabaseController.closeConnection(conn);
        	}else {
        		result = "Can't connect to comp421.cs.mcgill.ca"+ System.lineSeparator();;
        		return result;
        	}
        	

        }catch (SQLException e) {
    		
            System.out.println(e.getMessage());
        } 
    	
    	return result;
    }
    
    
    
    

	

}
