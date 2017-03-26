package ca.stclaircollege.fitgrind;

/**
 * Food class is used to retrieve items.
 */

public class Food {
    private String group;
    private String name;
    private String ndbno;

    public Food(String group, String name, String ndbno) {
        this.group = group;
        // only showcase the Name of the food, not the UPC #. That isn't important to the user.
        if (name.indexOf(", UPC") != -1) {
            this.name = name.substring(0, name.indexOf("UPC"));
        } else {
            this.name = name;
        }
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

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }
}
