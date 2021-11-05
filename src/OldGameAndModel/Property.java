package OldGameAndModel;

/**
 * The OldGameAndModel.Property class: A Model.OldGameAndModel.Square on the board that can be bought by a OldGameAndModel.Player
 * Date: October 17, 2021
 * @author Ibrahim Said, 101158275
 * @version 1.0
 */

public class Property extends Square {
    private String name;
    private String color;

    private int value;

    // class constructor
    public Property(String name, String color, int value){
        super();
        this.name = name;
        this.value = value;
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Accessor for the name of the OldGameAndModel.Property
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor for the value of the OldGameAndModel.Property
     * @return value;
     */
    public int getValue() {
        return value;
    }

    /**
     * Accessor for the color of the OldGameAndModel.Property
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the OldGameAndModel.Property with parameter color
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the name of the OldGameAndModel.Property with parameter name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value of the OldGameAndModel.Property with parameter value
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }


}

