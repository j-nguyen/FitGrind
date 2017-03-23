package ca.stclaircollege.fitgrind;

/**
 * Food class is used to retrieve items.
 */

public class Food {
    private String group;
    private String name;
    private int upc;
    private String ndbno;

    public Food(String group, String name, int upc, String ndbno) {
        this.group = group;
        this.name = name;
        this.upc = upc;
        this.ndbno = ndbno;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpc() {
        return upc;
    }

    public void setUpc(int upc) {
        this.upc = upc;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }
}
