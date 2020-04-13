package com.cs421g29;

public class Book {
    public int id;
    public String title;
    public String author;
    public String description;
    public double price;
    public int pageCount;
    public double rating;

    public Book(int id, String title, String author, String description, double price, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.pageCount = pageCount;
    }

    public Book(int id, String title, String author, String description, double price, int pageCount, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.pageCount = pageCount;
        this.rating = rating;
    }
}
