package ca.stclaircollege.fitgrind.database;

/**
 * Created by johnnynguyen on 2017-04-02.
 */

public class Strength extends WorkoutType {
    private long strengthId;
    private int set;
    private int reptitions;
    private double weight;

    public Strength(long strengthId, String name, int set, int reptitions, double weight) {
        this.strengthId = strengthId;
        this.name = name;
        this.set = set;
        this.reptitions = reptitions;
        this.weight = weight;
    }

    public Strength(long id, String name, long strengthId, int set, int reptitions, double weight) {
        this.id = id;
        this.strengthId = strengthId;
        this.name = name;
        this.set = set;
        this.reptitions = reptitions;
        this.weight = weight;
    }

    public Strength(String name, int set, int reptitions, double weight) {
        this.name = name;
        this.set = set;
        this.reptitions = reptitions;
        this.weight = weight;
    }

    public long getStrengthId() {
        return strengthId;
    }

    public void setStrengthId(long strengthId) {
        this.strengthId = strengthId;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getReptitions() {
        return reptitions;
    }

    public void setReptitions(int reptitions) {
        this.reptitions = reptitions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
