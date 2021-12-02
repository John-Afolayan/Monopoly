package Model;

import java.io.Serializable;

public class House implements Serializable {
    private String name;
    private int price;
    private String color;

    public House(String name, int price, String color) {
        this.price = price;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
