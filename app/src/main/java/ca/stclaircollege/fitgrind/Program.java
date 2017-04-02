package ca.stclaircollege.fitgrind;

/**
 * Created by Allan on 4/1/2017.
 */

public class Program {
    private String name;
    private String description;
    private String length;

    public Program(String name, String description, String length){
        this.name = name;
        this.description = description;
        this.length = length;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String toString() {
        return getName();
    }
}
