package Model;

import java.io.Serializable;

public class Railroad extends Square implements Serializable {

    private String name;
    private int value;
    private int rent;

    public Railroad(String name, int value){
        super();
        this.name = name;
        this.value = value;
        this.rent = 25;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRent() {
        return rent;
    }
}
