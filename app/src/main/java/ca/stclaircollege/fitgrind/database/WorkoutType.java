package ca.stclaircollege.fitgrind.database;

/**
 * Created by johnnynguyen on 2017-04-02.
 */

public abstract class WorkoutType {
    protected long id;
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
