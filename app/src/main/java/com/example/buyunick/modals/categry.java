package com.example.buyunick.modals;

public class categry {

    private String name, icon, colour, brife;
    private int id;

    public categry(String name, String icon, String colour, String brife, int id) {
        this.name = name;
        this.icon = icon;
        this.colour = colour;
        this.brife = brife;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBrife() {
        return brife;
    }

    public void setBrife(String brife) {
        this.brife = brife;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
