package com.example.DBsqlite;

/**
 * Created by Tazeen on 27-07-2015.
 */
public class Vessels
{
    int _id;
    public  String vessel_name;
    public String vesselNotationnumber;
    boolean selected = false;



    // Empty constructor
    public Vessels() {

    }

    // constructor
    public Vessels(int keyId, String vesselNotationnumber, String name, boolean selected)
    {
        super();
        this._id = keyId;
        this.vesselNotationnumber = vesselNotationnumber;
        this.vessel_name = name;
        this.selected = selected;

    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    // constructor
    public Vessels(String name) {
        this.vessel_name = name;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    public String getVesselNotationnumber(){ return  this.vesselNotationnumber;}

    public  void setVesselNotationnumber(String strVesselNotationNumber){ this.vesselNotationnumber = strVesselNotationNumber; }

    // getting name
    public String getName() {
        return this.vessel_name;
    }

    // setting name
    public void setName(String name) {
        this.vessel_name = name;
    }


}

//http://www.androidhub4you.com/2012/09/hello-friends-today-i-am-going-to-share.html