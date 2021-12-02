package Model;

import java.io.Serializable;

public class Square implements Serializable {
    private String name;

    public Square(String name){
        this.name = name;
    }

    public Square() {

    }

    public String getName() {
        return name;
    }
}
