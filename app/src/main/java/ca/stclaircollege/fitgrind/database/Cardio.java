package ca.stclaircollege.fitgrind.database;

/**
 * Created by johnnynguyen on 2017-04-02.
 */

public class Cardio extends WorkoutType {
    private long cardioId;
    private double time;

    public Cardio(long cardioId, String name, double time) {
        this.name = name;
        this.cardioId = cardioId;
        this.time = time;
    }

    public Cardio(long id, String name, long cardioId, double time) {
        this.id = id;
        this.name = name;
        this.cardioId = cardioId;
        this.time = time;
    }

    public long getCardioId() {
        return cardioId;
    }

    public void setCardioId(long cardioId) {
        this.cardioId = cardioId;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
