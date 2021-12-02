package Model;

import java.io.Serializable;

public class Hotel implements Serializable {
    private String name;
    private String color;
    private int price;

    public Hotel(String name, int price , String color){
        this.color = color;
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
