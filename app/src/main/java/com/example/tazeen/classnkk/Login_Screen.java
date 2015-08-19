package com.example.tazeen.classnkk;

import android.app.Activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;import android.app.ProgressDialog;import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.telephony.TelephonyManager;
import android.content.Context;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.text.TextUtils;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.DBsqlite.All_Post;
import com.example.DBsqlite.MyDbHelper;


public class Login_Screen extends Activity {
    //IMEI( International Mobile Equipment Identity )
    String str_UserName, str_Password, strIMEI;
    EditText edittxtUserName, editTextPassWord;
    Button btnLogin;
    private ProgressDialog pDialog;
    String result;
    MyDbHelper dbhelper;
    String strAuthentication_Token;
    String strEmail;
    String inspectorName = "";
    String inVesselName = "";
    String strPassword;
    String strPhone;
    String str_UserId;
    String strUserName;
    String strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels = "null";
    protected String strActivityId, strRemark, strName, strStatus, strType, strUserId, strObjectId, strActiondate, strStarFlag;
    String strFollowFlag,strInspector_Id, strInspector_name, strRole;
    String  str_imgList_imageaudioPath,str_imgList_activityId,str_imgList_activityObjId,str_imgList_objectId,str_imgList_userId;
    String str_CompanyId;
    String str_CompanyName;
    String strResult, str_Authentication_Token;
    String strSync_Time = null;
    ProgressBar prgs;

    public static String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);
        //ProgressTask task = new ProgressTask();
        SharedPreferences settings = getSharedPreferences(Login_Screen.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("hasLoggedIn", true);
        editor.commit();

        dbhelper = new MyDbHelper(this);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        strIMEI = telephonyManager.getDeviceId();

        init();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName = edittxtUserName.getText().toString();

                if (TextUtils.isEmpty(strUserName)) {
                    edittxtUserName.setError("UserName can not be empty . ");

                }

                strPassword = editTextPassWord.getText().toString().trim();
                if (TextUtils.isEmpty(strPassword)) {
                    editTextPassWord.setError("Password can not be empty . ");

                } else {
                    Log.e("click In login page", "!!!");
                    new GetLoginDetails().execute();
                    new GetDetails().execute();

                }

            }
        });
    }


    /**
     * ********************************************************************************************************************************************************************************
     */
    private class GetLoginDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
               }

        @Override
        protected Void doInBackground(Void... arg0) {
            getLoginDetails();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            getSelectedItem();
        }
    }


    private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Login_Screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            getCompanyDeatails();
            Get_Vessels_By_Type();
            Get_InspectorUserList();
            getAllActivityList();
            getAllImagePath_List();

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            Intent i = new Intent(Login_Screen.this, AllPosts_Page.class);
            startActivity(i);

        }

    }

    /**
     * ************************************************************************************************************************************************************
     */

    public void getLoginDetails() {

        String strUrl = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_UserDetails/user_name/"+strUserName+"/user_password/"+strPassword+"/DeviceID/"+strIMEI;
        Log.e("strUrl ", " = " + strUrl);
        InputStream inputStream = null;

        try {


            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String jsonResult = jsonObj.toString().trim();
                Log.e("jsonResult ", " = " + jsonResult);

                JSONArray jsonArray = jsonObj.getJSONArray("Get_UserDetailsResult");
                String strJsonArray = jsonArray.toString();
                Log.e("strJsonArray ", " = " + strJsonArray);


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jobjLoginDetails = jsonArray.getJSONObject(i);

                    strAuthentication_Token = jobjLoginDetails.getString("Authentication_Token");
                    strEmail = jobjLoginDetails.getString("Email");
                    strName = jobjLoginDetails.getString("Name");
                    strPassword = jobjLoginDetails.getString("Password");
                    strPhone = jobjLoginDetails.getString("Phone");
                    strRole = jobjLoginDetails.getString("Role");
                    strStatus = jobjLoginDetails.getString("Status");
                    strUserId = jobjLoginDetails.getString("User_ID");
                    strUserName = jobjLoginDetails.getString("User_Name");

                    Log.e("strAuthenticationToken ", " = " + strAuthentication_Token);
                    Log.e("strEmail ", " = " + strEmail);
                    Log.e("strName ", " = " + strName);
                    Log.e("strPassword ", " = " + strPassword);
                    Log.e("strPhone ", " = " + strPhone);
                    Log.e("strRole ", " = " + strRole);
                    Log.e("strStatus ", " = " + strStatus);
                    Log.e("strUserId ", " = " + strUserId);
                    Log.e("strUserName ", " = " + strUserName);

                    dbhelper.insertUserDetails(strAuthentication_Token, strEmail, strName, strPassword, strPhone, strRole, strStatus, strUserId, strUserName);
                    Log.e("Data Inserted ", " SuccesFully !!!!! = ");


                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
    }

    /**
     * ********************************************************************************************************************************************************************************
     */

    public void getSelectedItem() {
        MyDbHelper mDbHelper = new MyDbHelper(this);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
        String query = "select * from UserDetails ";
        Log.e("query ", " = " + query);

        Cursor cursor = mDb.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                str_Authentication_Token = cursor.getString(cursor.getColumnIndex("Authentication_Token"));
                str_UserId = cursor.getString(cursor.getColumnIndex("User_ID"));

                Log.e("*********************", " *************************** ");
                Log.e("Authentication_Token", " in getSelectedItem in vessel tab= " + str_Authentication_Token);

            } while (cursor.moveToNext());
        }
        cursor.close();
        mDb.close();
    }
    /**
     * ********************************************************************************************************************************************************************************
     */

    public void getCompanyDeatails() {

        String strUrl_companyDeatls = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_Company/Sync_Time/"+strSync_Time+"/Authentication_Token/"+str_Authentication_Token;


        Log.e("strUrl_companyDeatls ", " = " + strUrl_companyDeatls);

        InputStream inputStream = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_companyDeatls));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        String jsonStr = strResult;
        Log.e("jsonStr ", " = " + jsonStr);


        if (jsonStr != null)
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String jsonResult = jsonObj.toString().trim();
                Log.e("jsonResult ", " = " + jsonResult);


                JSONObject companyList = jsonObj.getJSONObject("Get_CompanyResult");
                Log.e("companyList ", " = " + companyList.toString());

                JSONArray jarr = companyList.getJSONArray("CompanylList");
                Log.e("jarr ", " = " + jarr.toString());


                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject jobCompanyDetails = jarr.getJSONObject(i);

                    str_CompanyId = jobCompanyDetails.getString("Company_ID");
                    str_CompanyName = jobCompanyDetails.getString("Company_Name");

                    Log.e("str_CompanyId ", " = " + str_CompanyId);
                    Log.e("str_CompanyName ", " = " + str_CompanyName);

                    if (dbhelper.isTitleExist(str_CompanyId)) {
                        //Upadte
                        dbhelper.updatedetails(str_CompanyId, str_CompanyName);
                        Log.e("Data updated in ", "Company Table !!");
                    } else {

                        //insert
                        dbhelper.insertCompany(str_CompanyId, str_CompanyName);
                        Log.e("Data inserted in ", "Company Table !!");
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


    }

    /**
     * ********************************************************************************************************************************************************************************
     */

    public void Get_Vessels_By_Type() {
        String strUrl_Vessels_By_Type = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_ALL_Vessels/Sync_Time/" + strSync_Time + "/Authentication_Token/" + str_Authentication_Token;
        Log.e("strUrl_Vessels_By_Type ", " = " + strUrl_Vessels_By_Type);

        InputStream inputStream = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_Vessels_By_Type));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e("Get_Vessels_By_Type  ", " = " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String jsonResult = jsonObj.toString().trim();
                    Log.e("jsonResult ", " = " + jsonResult);

                    JSONObject vesselList = jsonObj.getJSONObject("Get_ALL_VesselsResult");
                    Log.e("companyList ", " = " + vesselList.toString());

                    String strSync = vesselList.getString("Sync_Time");
                    Log.e("strSync ", " = " + strSync.toString());

                    JSONArray jarr = vesselList.getJSONArray("VesselList");
                    Log.e("jarr ", " = " + jarr.toString());

                    for (int j = 0; j < jarr.length(); j++) {
                        JSONObject jobjVessels = jarr.getJSONObject(j);


                        strACtiveStatus = jobjVessels.getString("status");
                        strVessCompanyId = jobjVessels.getString("companyId");
                        strVesselName = jobjVessels.getString("Vessel_Name");
                        strVesseleId = jobjVessels.getString("Vessel_ID");
                        strVesselsTypeId = jobjVessels.getString("vesselTypeId");
                        VesselsTypeName = jobjVessels.getString("Vessel_Name");
                        strClassCodeNumber = jobjVessels.getString("Notation");
                        strIsFollowedVessels = jobjVessels.getString("FollowFlag");


                        if (dbhelper.isVesselId_Exist(strVesseleId)) {
                            dbhelper.update_VesselsId(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                            Log.e("Data updated in ", "Table_VesselList Table !!");
                        } else {
                            dbhelper.insert_VesselList(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                            Log.e("Data inserted in ", "Table_VesselList Table !!");
                        }

                    }

                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }

    /**
     * ************************************************************************************************************************************************************
     */

    public void Get_InspectorUserList() {
        //String strUrl_GetALlUserList = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_User_List/Sync_Time/" + strSync_Time + "/Authentication_Token/" + str_Authentication_Token;
        String strUrl_GetUserList = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_User_List/userID/"+str_UserId+"/Sync_Time/"+strSync_Time+"/Authentication_Token/" + str_Authentication_Token;
        Log.e("Inspector List", " = " + strUrl_GetUserList);

        InputStream inputStream = null;

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_GetUserList));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e(" Get_User_List ", " = " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String jsonResult = jsonObj.toString();
                    Log.e("jsonResult ", " = " + jsonResult);

                    JSONObject vesselList = jsonObj.getJSONObject("Get_User_ListResult");
                    Log.e("companyList ", " = " + vesselList.toString());

                    String strSync = vesselList.getString("Sync_Time");
                    Log.e("strSync ", " = " + strSync.toString());

                    JSONArray jarr = vesselList.getJSONArray("UserList");
                    Log.e("jarr ", " = " + jarr.toString());

                    for (int j = 0; j < jarr.length(); j++) {
                        JSONObject jobInspectors = jarr.getJSONObject(j);

                        strFollowFlag = jobInspectors.getString("FollowFlag");
                        strInspector_Id = jobInspectors.getString("Inspector_Id");
                        strInspector_name = jobInspectors.getString("Inspector_name");
                        strRole = jobInspectors.getString("Role");

                        Log.e("strFollowFlag ", " = " + strFollowFlag);
                        Log.e("strInspector_Id ", " = " + strInspector_Id);
                        Log.e("strInspector_name ", " = " + strInspector_name);
                        Log.e("strRole ", " = " + strRole);


                        if (dbhelper.isInspector_id_Exist(strInspector_Id)) {
                            //Upadte
                            dbhelper.update_Inspector_Id(strFollowFlag,strInspector_Id, strInspector_name, strRole);
                            Log.e("Data updated in ", "Company Table !!");
                        } else {

                            //insert
                            dbhelper.insertInspectorList(strInspector_Id, strInspector_name, strRole);
                            Log.e("Inspector Userdata save", " Sucesfully !!! = ");
                        }


                    }
                } catch (JSONException jes) {
                    jes.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ************************************************************************************************************************************************************
     */

    public void getAllActivityList() {
        String strUrl_GetAll_Activity = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_ALL_ActivityList/userID/"+str_UserId+"/Sync_Time/"+strSync_Time+"/Authentication_Token/" + str_Authentication_Token;
        Log.e("strUrl_GetAll_Activity ", " = " + strUrl_GetAll_Activity);

        InputStream inputStream = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_GetAll_Activity));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e("Get_Vessels_By_Type  ", " = " + jsonStr);

            if (jsonStr != null) {

                try {
                    String[] myString = {};
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String jsonResult = jsonObj.toString().trim();
                    Log.e("jsonResult ", " = " + jsonResult);

                    JSONObject getAllActivity_List = jsonObj.getJSONObject("Get_ALL_ActivityListResult");
                    Log.e("getAllActivity_List ", " = " + getAllActivity_List.toString());

                    String strSync = getAllActivity_List.getString("Sync_Time");
                    Log.e("strSync ", " = " + strSync.toString());

                    JSONArray jarr = getAllActivity_List.getJSONArray("ActivityList");
                    Log.e("jarr ", " = " + jarr.toString());
                    SQLiteDatabase mDb = dbhelper.getWritableDatabase();
                    for (int j = 0; j < jarr.length(); j++) {
                        JSONObject jobjVessels = jarr.getJSONObject(j);

                        strActivityId = jobjVessels.getString("activityId");
                        strRemark = jobjVessels.getString("remark");
                        strName = jobjVessels.getString("name");
                        strStatus = jobjVessels.getString("status");
                        strType = jobjVessels.getString("type");
                        strUserId = jobjVessels.getString("userId");
                        strObjectId = jobjVessels.getString("objectId");
                        strActiondate = jobjVessels.getString("actionDate");
                        strStarFlag = jobjVessels.getString("StarFlag");
                        String strDownLoadStatus = "0";

                        if (dbhelper.isAllPostId_Exist(strActivityId))
                        {
                            //Upadte
                            Log.e("*******************", "*******************");
                            dbhelper.update_AllPost(strActivityId, strRemark, strName, strStatus, strType, strUserId, strObjectId, strActiondate, strStarFlag, strDownLoadStatus);
                            Log.e("Data updated in ", "Company Table !!");
                            Log.e("*******************", "*******************");
                        }
                          else
                        {

                            Log.e("*******************", "*******************");
                            dbhelper.insert_AllPost(strActivityId, strRemark, strName, strStatus, strType, strUserId, strObjectId, strActiondate, strStarFlag, strDownLoadStatus);
                            Log.e("Dats save in ", "AllPost Succesively !!!!");
                            Log.e("*******************", "*******************");

                        }

                        Log.e("*******After Else******", "*******************");
                        getInspectorName(strActivityId,strUserId, strObjectId);

                    }

                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * ************************************************************************************************************************************************************
     */

    public String getInspectorName(String strActivityId, String strInspectorId, String strVesselId) {
        Cursor cursor1 = null, cursor2 = null;

        String strInitLetter = "initial";
        SQLiteDatabase mDb = dbhelper.getWritableDatabase();
        try {

            cursor1 = mDb.rawQuery("select Inspector_name from Inspector where Inspector_Id=?", new String[]{strInspectorId + ""});
            cursor2 = mDb.rawQuery("select Vessel_Name from VesselList where Vessel_ID=?", new String[]{strVesselId + ""});

            if (cursor1.getCount() > 0 && cursor2.getCount() > 0) {

                if (cursor1.moveToFirst() && cursor2.moveToFirst())

                    inspectorName = cursor1.getString(cursor1.getColumnIndex("Inspector_name"));
                inVesselName = cursor2.getString(cursor2.getColumnIndex("Vessel_Name"));
                String strMyDescription = inspectorName + "@" + inVesselName;

                Log.e(" inspectorName ", " = " + inspectorName);
                Log.e(" inVesselName ", " = " + inVesselName);
                Log.e(" strMyDescription ", " = " + strMyDescription);

                dbhelper.insertDescriptions(strInspectorId , strActivityId ,strMyDescription, strInitLetter, strRemark);
                Log.e(" insert Descriptions  ", "Succesfully  = " + strMyDescription);
            }

            return inspectorName + "" + inVesselName;

        } finally {

            cursor1.close();
            cursor2.close();
        }

    }

    /**
     * ************************************************************************************************************************************************************
     */

   public void getAllImagePath_List() {
        String strUrl_GetAll_Activity = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_ALL_ActivityList/userID/"+str_UserId+"/Sync_Time/"+strSync_Time+"/Authentication_Token/"+str_Authentication_Token;
        Log.e("strUrl_GetAll_Activity ", " str_imgList_imageaudioPath = " + strUrl_GetAll_Activity);
        InputStream inputStream = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_GetAll_Activity));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e("Get_Vessels_By_Type  ", " = " + jsonStr);

            try {

                JSONObject jsonObj = new JSONObject(jsonStr);
                String jsonResult = jsonObj.toString().trim();
                Log.e("jsonResult ", " = " + jsonResult);

                JSONObject getAllActivity_List = jsonObj.getJSONObject("Get_ALL_ActivityListResult");
                Log.e("getAllActivity_List ", " = " + getAllActivity_List.toString());

                String strSync = getAllActivity_List.getString("Sync_Time");
                Log.e("strSync ", " = " + strSync.toString());

                JSONArray jarr = getAllActivity_List.getJSONArray("ActivityObjectList");
                Log.e("jarr ", " = " + jarr.toString());

                SQLiteDatabase mDb = dbhelper.getWritableDatabase();

                BufferedOutputStream bos;
                for (int j = 0; j < jarr.length(); j++)
                {
                    JSONObject jobjVessels = jarr.getJSONObject(j);

                    str_imgList_activityId = jobjVessels.getString("activityId");
                    str_imgList_activityObjId = jobjVessels.getString("activityObjId");
                    str_imgList_imageaudioPath = jobjVessels.getString("imageaudioPath");
                    Log.e("str_imgList"," _imageaudioPath = " + str_imgList_imageaudioPath);
                    str_imgList_objectId = jobjVessels.getString("objectId");
                    str_imgList_userId = jobjVessels.getString("userId");
                    String strImageDownLoadStatus = "0";



                  if( dbhelper.isActivityObjectList_Exist(str_imgList_imageaudioPath))
                    {
                        //update
                        dbhelper.update_ActivityObjectList(str_imgList_activityId , str_imgList_activityObjId ,str_imgList_imageaudioPath ,str_imgList_objectId , str_imgList_userId, strImageDownLoadStatus);
                        Log.e("update ", " ActivityObjectList succesfully !!");
                    }
                    else
                    {
                        //insert
                        dbhelper.insert_ActivityObjectList(str_imgList_activityId , str_imgList_activityObjId ,str_imgList_imageaudioPath ,str_imgList_objectId , str_imgList_userId, strImageDownLoadStatus);
                        Log.e("insert " , " ActivityObjectList succesfully !!" );
                    }


                }

            }

            catch (JSONException je)
            {
                je.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * ************************************************************************************************************************************************************
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    /**
     * ************************************************************************************************************************************************************
     */
    public void init() {
        edittxtUserName = (EditText) findViewById(R.id.editText_UserName);
        editTextPassWord = (EditText) findViewById(R.id.editText_Password);
        btnLogin = (Button) findViewById(R.id.button_Login);
       // prgs = (ProgressBar)findViewById(R.id.progressBaar_LoginProg);
    }




}
