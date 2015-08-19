package com.example.DBsqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tazeen on 20-07-2015.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ClassNKK";
    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase db;
    /**
     * *****************************************************************************************************
     */
    public static final String Table_NAME = "UserDetails";
    public static final String COL_ID = "User_ID";
    public static final String COL_AuthenticationToken = "Authentication_Token";
    public static final String COL_Email = "Email";
    public static final String COL_Name = "Name";
    public static final String COL_Password = "Password";
    public static final String COL_Phone = "Phone";
    public static final String COL_Role = "Role";
    public static final String COL_Status = "Status";
    public static final String COL_User_ID = "UserID";
    public static final String COL_Username = "Username";


    /**
     * *****************************************************************************************************
     */
    public static final String Table_COMPANY = "Company";
    public static final String COL_CompanyId = "Co_ID";
    public static final String COL_Company_Id = "Company_Id";
    public static final String COL_CompanyName = "Company_Name";


    /**
     * *****************************************************************************************************
     */


    public static final String Table_VesselList = "VesselList";
    public static final String COL_VESSL_ID = "vesselid";
    public static final String COL_ACtiveStatus = "status";
    public static final String COL_VessCompanyId = "Vessel_companyId";
    public static final String COL_VesselName = "Vessel_Name";
    public static final String COL_VesseleId = "Vessel_ID";
    public static final String COL_VesselsTypeId = "vesselTypeId";
    public static final String COL_VesselsTypeName = "VesselsTypeName";
    public static final String COL_ClassCodeNumber = "Notation";
    public static final String COL_IsFollowedVessels = "sFollowedVessels";

    /**
     * *****************************************************************************************************
     */

    public static final String Table_Inspector_List = "Inspector";
    public static final String COL_InspectID = "InspectID";
    public static final String COL_IsFollowFlag = "FollowFlag";
    public static final String COL_Inspector_Id = "Inspector_Id";
    public static final String COL_Inspector_name = "Inspector_name";
    public static final String COL_InspectorRole = "Role";

    /**
     * ******************************************************************************************************
     */
    public static final String Table_AllPost_Table = "ALL_Post";
    public static final String COL_AllPost_ID = "ALL_ACT_ID";
    public static final String COL_AllPost_PostActivityId = "ActivityId";
    public static final String COL_AllPost_Description = "Description";
    public static final String COL_AllPost_Title = "Title";
    public static final String COL_AllPost_Status = "Status";
    public static final String COL_AllPost_Type = "Type";
    public static final String COL_AllPost_UserId = "UserId";
    public static final String COL_AllPost_VesselId = "VesselId";
    public static final String COL_AllPost_ActionDate = "ActionDate";
    public static final String COL_AllPost_StarFlag = "StarFlag";
    public static final String COL_AllPost_DownLoadStatus = "DownLoad_Status";

    /**
     * ******************************************************************************************************
     */
    public static final String Table_DESCRIPTION = "ALLPost_Description";
    public static final String COL_desc_id = "descid";
    public static final String COL_inspectorId = "inspectorID";
    public static final String Col_Activityid = "ActivityId";
    public static final String COL_description = "description";
    public static final String COL_initLetter = "initLetter";
    public static final String COL_Title = "title";


    /**
     * ******************************************************************************************************
     */

    public static final String Table_ActivityObjectList = "ActivityObjectList";
    public static final String COL_activityobjlistID = "objID";
    public static final String COL_activityId = "activityId";
    public static final String COL_activityObjId = "activityObjId";
    public static final String COL_imageaudioPath = "imageaudioPath";
    public static final String COL_objectId = "objectId";
    public static final String COL_userId = "userId";
    public static final String COL_ActivityObject_DownLoadStatus = "DownLoad_Status";


    public MyDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // TODO Auto-generated constructor stub
    }

    /**
     * *****************************************************************************************************
     */
    @Override
    public void onCreate(SQLiteDatabase db) {


        String strTableUserDetails = "CREATE TABLE IF NOT EXISTS " + Table_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_AuthenticationToken + " TEXT, "
                + COL_Email + " TEXT, "
                + COL_Name + " TEXT, "
                + COL_Password + " TEXT, "
                + COL_Phone + " TEXT, "
                + COL_Role + " TEXT, "
                + COL_Status + " TEXT, "
                + COL_User_ID + " TEXT, "
                + COL_Username + " TEXT" + ");";


        String strTableCompany = "CREATE TABLE IF NOT EXISTS " + Table_COMPANY + " ("
                + COL_CompanyId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_Company_Id + " TEXT, "
                + COL_CompanyName + " TEXT" + ");";


        String strTableVesselsList = "CREATE TABLE IF NOT EXISTS " + Table_VesselList + " ("
                + COL_VESSL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ACtiveStatus + " TEXT, "
                + COL_VessCompanyId + " TEXT, "
                + COL_VesselName + " TEXT, "
                + COL_VesseleId + " TEXT, "
                + COL_VesselsTypeId + " TEXT, "
                + COL_VesselsTypeName + " TEXT, "
                + COL_ClassCodeNumber + " TEXT, "
                + COL_IsFollowedVessels + " TEXT" + ");";


        String strTableInspectorList = "CREATE TABLE IF NOT EXISTS " + Table_Inspector_List + " ("
                + COL_InspectID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_IsFollowFlag + " TEXT, "
                + COL_Inspector_Id + " TEXT, "
                + COL_Inspector_name + " TEXT, "
                + COL_InspectorRole + " TEXT" + ");";


        String strTable_AllPostDetails = "CREATE TABLE IF NOT EXISTS " + Table_AllPost_Table + " ("
                + COL_AllPost_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_AllPost_PostActivityId + " TEXT, "
                + COL_AllPost_Description + " TEXT, "
                + COL_AllPost_Title + " TEXT, "
                + COL_AllPost_Status + " TEXT, "
                + COL_AllPost_Type + " TEXT, "
                + COL_AllPost_UserId + " TEXT, "
                + COL_AllPost_VesselId + " TEXT, "
                + COL_AllPost_ActionDate + " TEXT, "
                + COL_AllPost_StarFlag + " TEXT, "
                + COL_AllPost_DownLoadStatus + " TEXT" + ");";

        String strTableDescriptionAllPost = "CREATE TABLE IF NOT EXISTS " + Table_DESCRIPTION + " ("
                + COL_desc_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_inspectorId + " TEXT, "
                + Col_Activityid + " TEXT, "
                + COL_description + " TEXT, "
                + COL_initLetter + " TEXT, "
                + COL_Title + " TEXT" + ");";


        String strTable_ActivityObjectList = "CREATE TABLE IF NOT EXISTS " + Table_ActivityObjectList + " ("
                + COL_activityobjlistID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_activityId + " TEXT, "
                + COL_activityObjId + " TEXT, "
                + COL_imageaudioPath + " TEXT, "
                + COL_objectId + " TEXT, "
                + COL_userId + " TEXT, "
                + COL_ActivityObject_DownLoadStatus + " TEXT" + ");";


        db.execSQL(strTableUserDetails);
        db.execSQL(strTableCompany);
        db.execSQL(strTableVesselsList);
        db.execSQL(strTableInspectorList);
        db.execSQL(strTable_AllPostDetails);
        db.execSQL(strTableDescriptionAllPost);
        db.execSQL(strTable_ActivityObjectList);
    }

    /**
     * *****************************************************************************************************
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + Table_VesselList);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Inspector_List);
        db.execSQL("DROP TABLE IF EXISTS " + Table_AllPost_Table);
        db.execSQL("DROP TABLE IF EXISTS " + Table_DESCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + Table_ActivityObjectList);
        onCreate(db);
    }

    public void update_ActivityObjectList(String activityId,
                                          String stractivityObjId,
                                          String strimageaudioPath,
                                          String strobjectId,
                                          String struserId,
                                          String strDownLoadStatus) {
        // TODO Auto-generated method stub

        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();

        args.put(COL_activityId, activityId);
        args.put(COL_activityObjId, stractivityObjId);
        args.put(COL_imageaudioPath, strimageaudioPath);
        args.put(COL_objectId, strobjectId);
        args.put(COL_userId, struserId);
        args.put(COL_ActivityObject_DownLoadStatus, strDownLoadStatus);
        db1.update(Table_ActivityObjectList, args, "imageaudioPath" + "= ?", new String[]{strimageaudioPath});
        args.clear();
        //Log.e("isFollowed list Update","");
    }

    public void update_DownLoadStatus(String StrimageName , String strDownLoadStatus) {
        // TODO Auto-generated method stub

        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_ActivityObject_DownLoadStatus, strDownLoadStatus);
        db1.update(Table_ActivityObjectList, args, "imageaudioPath" + "= ?", new String[]{StrimageName});
        args.clear();

    }


    public void updateIbspectorNameList(String strInspectorId ,String strInspectornameName , String strIsFolloweFlage) {
        // TODO Auto-generated method stub
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_Inspector_name, strInspectornameName);
        args.put(COL_IsFollowFlag, strIsFolloweFlage);
        db1.update(Table_Inspector_List, args, "Inspector_Id" + "= ?", new String[]{strInspectorId});
        args.clear();
       // Log.e("isFollowed list Update","");
    }


    public void updateCommentDetails(String strVesselName , String strVesselNotationNumber, String strIsFollowed) {
        // TODO Auto-generated method stub
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_ClassCodeNumber, strVesselNotationNumber);
        args.put(COL_IsFollowedVessels, strIsFollowed);
        db1.update(Table_VesselList, args, "Vessel_Name" + "= ?", new String[]{strVesselName});
        args.clear();
      //  Log.e("isFollowed list Update","");
    }

    public void insert_ActivityObjectList(String activityId,
                                          String stractivityObjId,
                                          String strimageaudioPath,
                                          String strobjectId,
                                          String struserId,
                                          String strDownLoadStatus) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_activityId, activityId);
        values.put(COL_activityObjId, stractivityObjId);
        values.put(COL_imageaudioPath, strimageaudioPath);
        values.put(COL_objectId, strobjectId);
        values.put(COL_userId, struserId);
        values.put(COL_ActivityObject_DownLoadStatus, strDownLoadStatus);

        db.insert(Table_ActivityObjectList, null, values);
        db.close();
    }




    public boolean isActivityObjectList_Exist(String str_imageaudioPath) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_ActivityObjectList, new String[]{COL_imageaudioPath}, COL_imageaudioPath + "=?",

                new String[]{String.valueOf(str_imageaudioPath)}, null, null, null, null);

        if (cursor != null) {

            if (cursor.getCount() == 0) {
             //   Log.e("Row is  Not  ", " Exist in Company Table in MyDbHelper");

                cursor.close();
                db.close();
                return false;

            } else {

               // Log.e("Row is   already ", " Exist in Company Table in MyDbHelper ");
                cursor.close();
                db.close();
                return true;
            }
        }
        return false;
    }

    public void insertDescriptions(String strInspectorId , String ActivityId, String strDescriptions, String strInitLetters, String strTitle) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_inspectorId,strInspectorId);
        values.put(Col_Activityid , ActivityId);
        values.put(COL_description, strDescriptions);
        values.put(COL_initLetter, strInitLetters);
        values.put(COL_Title, strTitle);

        db.insert(Table_DESCRIPTION, null, values);
        db.close();
    }



    public List<All_Post> getAllDescriptions() {
        List<All_Post> allPostDescList = new ArrayList<All_Post>();

        String selectQuery = "select * from  " + Table_DESCRIPTION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                All_Post allPostDesc = new All_Post();

                allPostDesc.setID(cursor.getInt(0));
                allPostDesc.setStrActivityId(cursor.getString(2));
                allPostDesc.setName(cursor.getString(3));
                allPostDesc.setInitialLetter(cursor.getString(4));
                allPostDesc.setStrDesc(cursor.getString(5));

                allPostDescList.add(allPostDesc);
            } while (cursor.moveToNext());
        }

        db.close();

        return allPostDescList;

    }
    /**
     * *****************************************************************************************************
     */
    public void insertUserDetails(String strAuthentication_Token, String strEmail, String strName, String strPassword, String strPhone, String strRole, String strStatus, String strUserId, String strUserName) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AuthenticationToken, strAuthentication_Token);
        values.put(COL_Email, strEmail);
        values.put(COL_Name, strName);
        values.put(COL_Password, strPassword);
        values.put(COL_Phone, strPhone);
        values.put(COL_Role, strRole);
        values.put(COL_Status, strStatus);
        values.put(COL_User_ID, strUserId);
        values.put(COL_Username, strUserName);

        db.insert(Table_NAME, null, values);
        db.close();
    }

    /**
     * *****************************************************************************************************
     */


    public void insert_AllPost(String strAllPost_strPostActivityId,
                               String strAllPost_strRemark,
                               String strAllPost_strName,
                               String strAllPost_strStatus,
                               String strAllPost_strType,
                               String strAllPost_userId,
                               String strAllPost_strObjectId,
                               String strAllPost_strActiondate,
                               String strAllPost_strStarFlag,
                               String strDownLoadStatus) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_AllPost_PostActivityId, strAllPost_strPostActivityId);
        values.put(COL_AllPost_Description, strAllPost_strRemark);
        values.put(COL_AllPost_Title, strAllPost_strName);
        values.put(COL_AllPost_Status, strAllPost_strStatus);
        values.put(COL_AllPost_Type, strAllPost_strType);
        values.put(COL_AllPost_UserId, strAllPost_userId);
        values.put(COL_AllPost_VesselId, strAllPost_strObjectId);
        values.put(COL_AllPost_ActionDate, strAllPost_strActiondate);
        values.put(COL_AllPost_StarFlag, strAllPost_strStarFlag);
        values.put(COL_AllPost_DownLoadStatus ,strDownLoadStatus );

        db.insert(Table_AllPost_Table, null, values);
        db.close();
    }

    /**
     * *****************************************************************************************************
     */


    public void insertCompany(String strCompanyId, String strCompanyName) {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_Company_Id, strCompanyId);
        values.put(COL_CompanyName, strCompanyName);
        db1.insert(Table_COMPANY, null, values);
        db1.close();
    }


    /*********************************************************************************************************/
    /**
     * Getting all company name to list of labels
     */

    public List<String> getAll_Company() {
        List<String> labels = new ArrayList<String>();

        String selectQuery = "select * from  " + Table_COMPANY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;
    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
            super.close();
        }
    }

    /**
     * *****************************************************************************************************
     */
    public boolean updatedetails(String str_CompanyId, String str_CompanyName) {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_Company_Id, str_CompanyId);
        args.put(COL_CompanyName, str_CompanyName);
        int i = db1.update(Table_COMPANY, args, COL_Company_Id + "=" + str_CompanyId, null);
        return i > 0;
    }


    public void insert_VesselList(String strACtiveStatus, String strVessCompanyId, String strVesselName, String strVesseleId, String strVesselsTypeId, String VesselsTypeName, String strClassCodeNumber, String strIsFollowedVessels) {
        //Log.e("Insert In MyDbHelper", "!!!");
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ACtiveStatus, strACtiveStatus);
        values.put(COL_VessCompanyId, strVessCompanyId);
        values.put(COL_VesselName, strVesselName);
        values.put(COL_VesseleId, strVesseleId);
        values.put(COL_VesselsTypeId, strVesselsTypeId);
        values.put(COL_VesselsTypeName, VesselsTypeName);
        values.put(COL_ClassCodeNumber, strClassCodeNumber);
        values.put(COL_IsFollowedVessels, strIsFollowedVessels);

        db.insert(Table_VesselList, null, values);
        db.close();
    }


    public List<Vessels> getAllVessels() {
        List<Vessels> contactList = new ArrayList<Vessels>();

        String selectQuery = "select * from  " + Table_VesselList;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Vessels contact = new Vessels();

                contact.setName(cursor.getString(3));
                contact.setVesselNotationnumber(cursor.getString(7));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();

        return contactList;

    }


    public boolean isTitleExist(String str_Company_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_COMPANY, new String[]{COL_Company_Id}, COL_Company_Id + "=?",

                new String[]{String.valueOf(str_Company_id)}, null, null, null, null);

        if (cursor != null) {

            if (cursor.getCount() == 0) {
               // Log.e("Row is  Not  ", " Exist in Company Table in MyDbHelper");

                cursor.close();
                db.close();
                return false;

            } else {

             //   Log.e("Row is   already ", " Exist in Company Table in MyDbHelper ");
                cursor.close();
                db.close();
                return true;
            }
        }
        return false;
    }


    public boolean isVesselId_Exist(String str_Vessel_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_VesselList, new String[]{COL_VesseleId}, COL_VesseleId + "=?",

                new String[]{String.valueOf(str_Vessel_id)}, null, null, null, null);

        if (cursor != null) {

            if (cursor.getCount() == 0) {
               // Log.e("Row is  Not  ", " Exist in Table_VesselList Table in MyDbHelper");

                cursor.close();
                db.close();
                return false;

            } else {

              //  Log.e("Row is   already ", " Exist in Table_VesselList Table in MyDbHelper ");
                cursor.close();
                db.close();
                return true;
            }
        }
        return false;
    }


    public boolean update_VesselsId(String strACtiveStatus,
                                    String strVessCompanyId,
                                    String strVesselName,
                                    String strVesseleId,
                                    String strVesselsTypeId,
                                    String VesselsTypeName,
                                    String strClassCodeNumber,
                                    String strIsFollowedVessels) {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_ACtiveStatus, strACtiveStatus);
        args.put(COL_VessCompanyId, strVessCompanyId);
        args.put(COL_VesselName, strVesselName);
        args.put(COL_VesseleId, strVesseleId);
        args.put(COL_VesselsTypeId, strVesselsTypeId);
        args.put(COL_VesselsTypeName, VesselsTypeName);
        args.put(COL_ClassCodeNumber, strClassCodeNumber);
        args.put(COL_IsFollowedVessels, strIsFollowedVessels);


        int i = db1.update(Table_VesselList, args, COL_VesseleId + "=" + strVesseleId, null);
        return i > 0;
    }


    public void insertInspectorList(String strInspector_Id, String strInspector_name, String strRole) {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_Inspector_Id, strInspector_Id);
        values.put(COL_Inspector_name, strInspector_name);
        values.put(COL_InspectorRole, strRole);
        db1.insert(Table_Inspector_List, null, values);
        db1.close();
    }


    public List<Inspector> getAllInspectorName() {
        List<Inspector> inspectList = new ArrayList<Inspector>();

        String selectQuery = "select * from  " + Table_Inspector_List;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Inspector inspect = new Inspector();


                inspect.setName(cursor.getString(3));

                inspectList.add(inspect);
            } while (cursor.moveToNext());
        }

        db.close();

        return inspectList;

    }

    public boolean isInspector_id_Exist(String str_Inspector_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_Inspector_List, new String[]{COL_Inspector_Id}, COL_Inspector_Id + "=?",

                new String[]{String.valueOf(str_Inspector_id)}, null, null, null, null);

        if (cursor != null) {

            if (cursor.getCount() == 0) {
             //   Log.e("Row is  Not  ", " Exist in Table_VesselList Table in MyDbHelper");

                cursor.close();
                db.close();
                return false;

            } else {

               // Log.e("Row is   already ", " Exist in Table_VesselList Table in MyDbHelper ");
                cursor.close();
                db.close();
                return true;
            }
        }
        return false;
    }


    public boolean update_Inspector_Id(String strIsFollowFlag, String strInspector_Id, String strInspector_name, String strRole) {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_IsFollowFlag, strIsFollowFlag);
        args.put(COL_Inspector_Id, strInspector_Id);
        args.put(COL_Inspector_name, strInspector_name);
        args.put(COL_InspectorRole, strRole);


        int i = db1.update(Table_Inspector_List, args, COL_Inspector_Id + "=" + strInspector_Id, null);
        return i > 0;
    }


    public boolean isAllPostId_Exist(String str_Inspector_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_AllPost_Table, new String[]{COL_AllPost_PostActivityId}, COL_AllPost_PostActivityId + "=?",

                new String[]{String.valueOf(str_Inspector_id)}, null, null, null, null);

        if (cursor != null) {

            if (cursor.getCount() == 0) {
             //   Log.e("Row is  Not  ", " Exist in Table_VesselList Table in MyDbHelper");

                cursor.close();
                db.close();
                return false;

            } else {

              //  Log.e("Row is   already ", " Exist in Table_VesselList Table in MyDbHelper ");
                cursor.close();
                db.close();
                return true;
            }
        }
        return false;
    }


    public boolean update_AllPost(String strActivityId,
                                  String strRemark,
                                  String strName,
                                  String strStatus,
                                  String strType,
                                  String strUserId,
                                  String strObjectId,
                                  String strActiondate,
                                  String strStarFlag,
                                  String strDownLoadStatus)
    {
        SQLiteDatabase db1 = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(COL_AllPost_PostActivityId, strActivityId);
        args.put(COL_AllPost_Description, strRemark);
        args.put(COL_AllPost_Title, strName);
        args.put(COL_AllPost_Status, strStatus);
        args.put(COL_AllPost_Type, strType);
        args.put(COL_AllPost_UserId, strUserId);
        args.put(COL_AllPost_VesselId, strObjectId);
        args.put(COL_AllPost_ActionDate, strActiondate);
        args.put(COL_AllPost_StarFlag, strStarFlag);
        args.put(COL_AllPost_DownLoadStatus , strDownLoadStatus);


        int i = db1.update(Table_AllPost_Table, args, COL_AllPost_PostActivityId + "=" + strActivityId, null);
        return i > 0;
    }



}



