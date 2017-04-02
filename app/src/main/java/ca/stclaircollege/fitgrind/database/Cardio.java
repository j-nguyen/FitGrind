package ca.stclaircollege.fitgrind.database;

/**
 * Created by johnnynguyen on 2017-04-02.
 */

public class Cardio {
    private long id;
    private String name;
    private double time;

    public Cardio(long id, String name, double time) {
        this.id = id;
        this.name = name;
        this.time = time;
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

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
