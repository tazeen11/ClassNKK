package com.example.DBsqlite;

/**
 * Created by Tazeen on 28-07-2015.
 */
public class Inspector
{
    int _id;
    public String inspector_name;
    boolean inspect_selected = false;



    // Empty constructor
    public Inspector() {

    }

    // constructor
    public Inspector(int keyId, String name,  boolean inspect_selected)
    {
        super();
        this._id = keyId;
        this.inspector_name = name;
        this.inspect_selected = inspect_selected;

    }


    public boolean isInspect_selected() { return isInspect_selected();}

    public void setSelected(boolean selected) {
        this.inspect_selected = selected;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this.inspector_name;
    }

    // setting name
    public void setName(String name) {
        this.inspector_name = name;
    }

}
