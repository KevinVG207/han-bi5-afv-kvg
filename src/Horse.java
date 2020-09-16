/**
 * Horse class
 * Holds variables for horses and allows them to move randomly with update().
 */

import java.awt.*;
import java.util.Random;

public class Horse {

    // Declaring variables
    private int index;
    private static int horseCount = 0;
    private int x = 0;
    private String name;
    private Color color;
    Random random = new Random();

    /**
     * Simple constructor.
     * @param name String - Name of horse.
     */
    Horse(String name){
        this.name = name;
        this.color = Color.red;
        this.index = horseCount++;
    }

    /**
     * More advanced constructor.
     * @param name String - Name of horse.
     * @param color Color - Color of horse.
     */
    Horse(String name, Color color){
        this.name = name;
        this.color = color;
        this.index = horseCount++;
    }

    public int getIndex() {
        return this.index;
    }

    public int getDistance() {
        return this.x;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    /**
     * update()
     * Moves horse forwards by 0~10 units.
     */
    public void update() {
        this.x += random.nextInt(11);
    }
}
