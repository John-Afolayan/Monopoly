package Model;

import java.io.Serializable;

public class Utility extends Square implements Serializable {

    private String name;
    private int value;

    public Utility(String name, int value ){
        super();
        this.name = name;
        this.value = value;
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
}
