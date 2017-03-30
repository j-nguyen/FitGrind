package ca.stclaircollege.fitgrind.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ndbno")
    @Expose
    private String ndbno;
    @SerializedName("ds")
    @Expose
    private String ds;

    public final static Parcelable.Creator<Item> CREATOR = new Creator<Item>() {

        public Item createFromParcel(Parcel in) {
            Item instance = new Item();
            instance.offset = ((int) in.readValue((Integer.class.getClassLoader())));
            instance.group = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.ndbno = ((String) in.readValue((String.class.getClassLoader())));
            instance.ds = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Item[] newArray(int size) {
            return (new Item[size]);
        }

    }
            ;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
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

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(offset);
        dest.writeValue(group);
        dest.writeValue(name);
        dest.writeValue(ndbno);
        dest.writeValue(ds);
    }

    public int describeContents() {
        return 0;
    }

}