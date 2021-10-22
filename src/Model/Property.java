package Model;

/**
 * The Property class: A Model.Square on the board that can be bought by a Player
 * Date: October 17, 2021
 * @author Ibrahim Said, 101158275
 * @version 1.0
 */

public class Property extends Square {
    private String name;
    private String color;

    private int value;

    private Player player;

    // class constructor
    public Property(String name, String color, int value){
        super(name);
        this.value = value;
        this.color = color;
    }

    /**
     * Accessor for the name of the Property
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor for the value of the Property
     * @return value;
     */
    public int getValue() {
        return value;
    }

    /**
     * Accessor for the color of the Property
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the Property with parameter color
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the name of the Property with parameter name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value of the Property with parameter value
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets a Player to the Property
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the player associated with the Property
     * @return player;
     */
    public Player getPlayer() {
        return player;
    }
}
