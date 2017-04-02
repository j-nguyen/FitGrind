package ca.stclaircollege.fitgrind.database;

/**
 * Created by Allan on 4/1/2017.
 */

public class Routine {
    private long id;
    private String name;
    private String description;

    public Routine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Routine(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
