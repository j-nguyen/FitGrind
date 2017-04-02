package ca.stclaircollege.fitgrind.database;

/**
 * Created by Allan on 4/1/2017.
 */

public class Routine {
    private String name;
    private String description;

    public Routine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return getName();
    }
}
