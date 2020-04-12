package com.cs421g29;

import java.util.Date;

public class Order {
    public int oid;
    public Date creatingdate;
    public String paymentmethod;
    public String status;
    public String email;
    public String shippingaddress;
    public float rate;

    public Order(int oid, Date creatingdate, String paymentmethod, String status, String email, String shippingaddress, float rate) {
        this.oid = oid;
        this.creatingdate = creatingdate;
        this.paymentmethod = paymentmethod;
        this.status = status;
        this.email = email;
        this.shippingaddress = shippingaddress;
        this.rate = rate;
    }
}
