package ca.stclaircollege.fitgrind.database;

/**
 * Progress class dedicated for the viewpager in creating your 'weekly' or 'monthly' progress
 *
 */

public class Progress {
    private long id;
    private String resource;

    public Progress(long id, String resource) {
        this.id = id;
        this.resource = resource;
    }

    public Progress(String resource) {
        this.resource = resource;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
