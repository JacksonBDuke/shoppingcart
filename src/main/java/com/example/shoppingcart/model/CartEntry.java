package com.example.shoppingcart.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CartEntry {

    @Id
    private long PID;
    private int quantity;

    public long getPID() {
        return PID;
    }

    public void setPID(long PID) {
        this.PID = PID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
