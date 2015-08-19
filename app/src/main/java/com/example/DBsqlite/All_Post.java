package com.example.DBsqlite;

/**
 * Created by Tazeen on 30-07-2015.
 */
public class All_Post
{
    int _id;

    String post_name;
    String strActivityId;
    String initialLetter;
    String strDesc;
    byte[] _image;



    // Empty constructor
    public All_Post() {

    }
    // constructor
    public All_Post(byte[] image) {
             this._image = image;

    }


    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }


    // constructor
    public All_Post(int keyId, String strActivityId, String name, String strInit, String strDesc) {
        this._id = keyId;
        this.post_name = name;
        this.initialLetter = strInit;
        this.strDesc = strDesc;
        this.strActivityId = strActivityId;


    }

    // constructor
    public All_Post(String name ,String strInit, String strDesc ) {
        this.post_name = name;
        this.initialLetter = strInit;
        this.strDesc =  strDesc;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    public String getStrActivityId()
    {
        return this.strActivityId;
    }

    public void setStrActivityId(String strActivityId)
    {
         this.strActivityId = strActivityId;
    }
    // getting name
    public String getName() {
        return this.post_name;
    }

    // setting name
    public void setName(String name) {
        this.post_name = name;
    }

    public String getInitName()
    {
        return this.initialLetter;
    }

    public void setInitialLetter(String strInitLette)
    {
        this.initialLetter = strInitLette;
    }

    public String getStrDesc()
    {
        return this.strDesc;
    }

    public void setStrDesc(String strDesc)
    {
        this.strDesc = strDesc;
    }
}
