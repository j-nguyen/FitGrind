package ca.stclaircollege.fitgrind;

/**
 * DAO Type class, that catches all of the id, pics and what not from the sqlite table
 */
public class Weight {
    // we will be using long over id for capacity
    private long id;
    private long timeId;
    private double weight;

    // Add in multiple constrcutors for use

    public Weight(long id, long timeId, double weight) {
        this.id = id;
        this.timeId = timeId;
        this.weight = weight;
    }

    public Weight(double weight, long id) {
        this.weight = weight;
        this.id = id;
    }

    public Weight(double weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimeId() {
        return timeId;
    }

    public void setTimeId(long timeId) {
        this.timeId = timeId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
