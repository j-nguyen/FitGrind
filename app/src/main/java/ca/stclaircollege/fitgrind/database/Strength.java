package ca.stclaircollege.fitgrind.database;

/**
 * Created by johnnynguyen on 2017-04-02.
 */

public class Strength {
    private long id;
    private String name;
    private int set;
    private int reptitions;
    private double weight;

    public Strength(long id, String name, int set, int reptitions, double weight) {
        this.id = id;
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
