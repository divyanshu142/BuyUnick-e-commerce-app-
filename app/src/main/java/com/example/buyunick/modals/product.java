package com.example.buyunick.modals;

public class product {
    private String name, mages, status;
    private double price,discount;
    private int stoke, id;

    public product(String name, String mages, String status, double price, double discount, int stoke, int id) {
        this.name = name;
        this.mages = mages;
        this.status = status;
        this.price = price;
        this.discount = discount;
        this.stoke = stoke;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMages() {
        return mages;
    }

    public void setMages(String mages) {
        this.mages = mages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {return (int) price; }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getStoke() {
        return stoke;
    }

    public void setStoke(int stoke) {
        this.stoke = stoke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
