package com.example.tazeen.classnkk;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBsqlite.CustomList;
import com.example.DBsqlite.MyDbHelper;
import com.example.DBsqlite.Vessels;


public class Vessels_Tab extends Fragment
{
    int checked = 0;
    private ProgressDialog pDialog;
    String strSync_Time = null;
    String string_CompanyId;
    MyDbHelper dbhelper;
    ArrayAdapter<String> adapter;
    private Spinner spinner;
    private ListView vesselListView;
    SQLiteDatabase db;
    String strCompanyId;
    String selectedSpinnerItem;
    String str_CompanyId;
    String str_CompanyName ;
    MyDbHelper myDbHelper;
    Cursor cursor;
    Vessels vesselObj;
    String strSelectedItem_VesselName;
    String strSelelctedItem_VesselNotationNumber;
    ArrayList<Vessels> vesselArray = new ArrayList<Vessels>();
    private ArrayList<Vessels> vesselName_ArrayList = new ArrayList<Vessels>();
    String strACtiveStatus , strVessCompanyId , strVesselName , strVesseleId , strVesselsTypeId , VesselsTypeName , strClassCodeNumber , strIsFollowedVessels ="null";
    CustomList customListAdapter;
    @Override
        public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);

            dbhelper = new MyDbHelper(getActivity());
            vesselObj = new Vessels();
            cursor = null;
            //getSelectedItem();

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_vessels__tab, container, false);
            spinner = (Spinner) rootView.findViewById(R.id.spinner_CompanyList);
            setSpinnerContent();
            list_Intialize(rootView);
            return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("ListFragment", "onActivityCreated().");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");

    }


    private void setSpinnerContent()
    {


        Log.e("Get All Company List ", "");
        MyDbHelper mdb = new MyDbHelper(getActivity());
        List<String> lables = mdb.getAll_Company();

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spinner_style, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItem = spinner.getSelectedItem().toString().trim();

                System.out.println("selectedSpinnerItem & label" + selectedSpinnerItem);
                getCompanyId(selectedSpinnerItem);
                getVesselListName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



     private void getCompanyId(String selectedSpinnerItem)
	 {
		 try {
             myDbHelper = new MyDbHelper(getActivity());
		            db=myDbHelper.getReadableDatabase();

		             Cursor cursor = db.rawQuery("select Company_Id from Company where " + "Company_Name" + " = ?",
		                     new String[]{selectedSpinnerItem});

		            if(cursor!=null && cursor.moveToFirst())
		            {
                                string_CompanyId = cursor.getString(cursor.getColumnIndex("Company_Id"));
		                        System.out.println(" string_CompanyId =" + string_CompanyId);
                                Log.e(" string_CompanyId "," = "+ string_CompanyId);
		            }

		            cursor.close();
		            db.close();

		        }

		        catch (Exception e)
		        {
		            // TODO: handle exception
		            e.printStackTrace();
		        }
	   }

    public void getVesselListName()
    {

        myDbHelper = new MyDbHelper(getActivity());
        db=myDbHelper.getReadableDatabase();

        Log.e("string_CompanyId "," = "+string_CompanyId);
        Cursor mCursor=db.rawQuery("select * from VesselList where Vessel_companyId ='"+string_CompanyId+"'", null);
        vesselName_ArrayList.clear();
        if (mCursor.moveToFirst())
        {
            do
            {
                Vessels vessel = new Vessels();
                vessel.setVesselNotationnumber(mCursor.getString(mCursor.getColumnIndex("Notation")));
                vessel.setName(mCursor.getString(mCursor.getColumnIndex("Vessel_Name")));
                vesselName_ArrayList.add(vessel);
            } while (mCursor.moveToNext());
        }

        customListAdapter = new CustomList(getActivity(), R.layout.vessel_list_item, vesselName_ArrayList);
        vesselListView.setAdapter(customListAdapter);
        customListAdapter.notifyDataSetChanged();
        mCursor.close();
        db.close();
        System.out.print(vesselName_ArrayList);


    }



    private void list_Intialize(View view)
    {


        MyDbHelper mdb = new MyDbHelper(getActivity());
        List<Vessels> vesslesName = mdb.getAllVessels();
        for (Vessels vesselsList : vesslesName)
        {
            String log = "ID:" + vesselsList.getID() + " Name: " + vesselsList.getName();
            Log.e("Result: ", "" + log);
            vesselArray.add(vesselsList);
        }


        vesselListView = (ListView) view.findViewById(R.id.vessel_listView);
        customListAdapter = new CustomList(getActivity(), R.layout.vessel_list_item, vesselArray);
        vesselListView.setAdapter(customListAdapter);

        vesselListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)

            {
                LinearLayout ll = (LinearLayout) view;
                customListAdapter.setSelectedIndex(position);

                TextView txtVesselNotationNumber = (TextView)ll.findViewById(R.id.NotationNumber_txt);
                strSelelctedItem_VesselNotationNumber = txtVesselNotationNumber.getText().toString().trim();

                TextView txtVesselName = (TextView) ll.findViewById(R.id.vesselName_txt);
                strSelectedItem_VesselName = txtVesselName.getText().toString().trim();

                CheckBox checkBx = (CheckBox)ll.findViewById(R.id.checkBox_Item);
                if(checkBx.isChecked())
                {
                    checked = 1;
                    Log.e("checked: ", "" + checked);
                    Log.e("position", " = " + position + "=" + " strSelectedItem_VesselName  =" + strSelectedItem_VesselName + " & strSelelctedItem_VesselNotationNumber =" + strSelelctedItem_VesselNotationNumber);
                    Toast.makeText(getActivity(), "Checked is true: " + checkBx.getText() + " is " + checkBx.isChecked(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    checked = 0;
                    Log.e("checked: ", "" + checked);
                    Log.e("position", " = " + position + "=" + " strSelectedItem_VesselName  =" + strSelectedItem_VesselName + " & strSelelctedItem_VesselNotationNumber =" + strSelelctedItem_VesselNotationNumber);
                    Toast.makeText(getActivity(), "Checked is false: " + vesselObj.getName() + " is " + checkBx.isChecked(), Toast.LENGTH_LONG).show();

                }
            }
        });


        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.refreshTopDown);
        swipeRefreshLayout.setColorScheme(R.color.swype_1,
                R.color.swype_2,
                R.color.swype_3,
                R.color.swype_4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do nothing

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        RefreshContent();
                        spinner.setAdapter(adapter);
                        vesselListView.setAdapter(customListAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

    }

        public void RefreshContent()
        {
            if(dbhelper.isTitleExist(str_CompanyId) && dbhelper.isVesselId_Exist(strVesseleId))
            {
                //Upadte
                dbhelper.updatedetails(str_CompanyId, str_CompanyName);
                dbhelper.update_VesselsId(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                Log.e("Refresh Data updated in", "Company and Vessel Table !!");
            }
            else
            {

                //insert
                dbhelper.insertCompany(str_CompanyId,str_CompanyName);
                dbhelper.insert_VesselList(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                Log.e("Refresh Data inserted ", "Company and Vessel Table !!");
            }
        }


    /************************************************************************************************************************************************************************************/






    public class CustomList extends ArrayAdapter<Vessels>
    {
        Context context;
        int layoutResourceId;
        private int selectedIndex;
        private int selectedColor = Color.parseColor("#1b1b1b");
        ArrayList<Vessels> data=new ArrayList<Vessels>();

        MyDbHelper dbhelper;
        String str;

        public CustomList(Context context, int layoutResourceId, ArrayList<Vessels> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            selectedIndex = -1;
        }

        @Override
        public int getCount()
        {
            return data.size();
        }


        public void setSelectedIndex(int ind)
        {
            selectedIndex = ind;
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {
            View row = convertView;
            Holder holder = null;
            final Vessels vessel = (Vessels) this.getItem(pos);
            dbhelper = new MyDbHelper(getContext());
            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new Holder();
                holder.txtNotationNumber = (TextView)row.findViewById(R.id.NotationNumber_txt);
                holder.txtTitle = (TextView)row.findViewById(R.id.vesselName_txt);
                holder.check_View = (CheckBox)row.findViewById(R.id.checkBox_Item);

                row.setTag(holder);

                holder.check_View.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {

                        CheckBox cb = (CheckBox) v;
                        Vessels vessel_custAdpt = (Vessels) cb.getTag();
                        Toast.makeText(getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        vessel_custAdpt.setSelected(cb.isChecked());

                        Log.e("", "Item: " + pos);

                        if(cb.isChecked())
                        {
                            checked = 1;

                            String strIsFollow = "1";
                            Log.e("checked ","= " + strIsFollow);

                            String str_notationnumber = vessel.getVesselNotationnumber();
                            String strvesselname = vessel.getName();
                            Log.e("str_notationnumber ","= " + str_notationnumber);
                            Log.e("strvesselname ","= " + strvesselname);

                            dbhelper.updateCommentDetails(strvesselname, str_notationnumber, strIsFollow);
                            Log.e("If Part isFollowed ","Updated Succesfully !!!");



                        }
                        else
                        {
                            checked = 0;

                            String strIsFollow = "0";
                            Log.e("checked ","= " + strIsFollow);
                            String str_notationnumber = vessel.getVesselNotationnumber();
                            String strvesselname = vessel.getName();
                            Log.e("str_notationnumber ","= " + str_notationnumber);
                            Log.e("strvesselname ","= " + strvesselname);

                            dbhelper.updateCommentDetails(str_notationnumber, strvesselname, strIsFollow);
                            Log.e("Else Part isFollowed ","Updated Succesfully !!!");

                        }


                    }
                });
            }
            else
            {
                holder = (Holder)row.getTag();
            }
            Vessels vessel_custAdpt = data.get(pos);
            holder.txtNotationNumber.setText(vessel_custAdpt.getVesselNotationnumber());
            holder.txtTitle.setText(vessel_custAdpt.vessel_name);
            holder.check_View.setChecked(vessel_custAdpt.isSelected());
            holder.check_View.setTag(vessel_custAdpt);


            return row;
        }
         class Holder
        {

            TextView txtNotationNumber;
            TextView txtTitle;
            CheckBox check_View;
        }
    }

}













































/* private class GetCompanyDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


           pDialog = new ProgressDialog(getActivity());
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
           // getCompanyDeatails();
            //Get_Vessels_By_Type();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
          if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }
*/

  /*  public void getCompanyDeatails()
    {

        String strUrl_companyDeatls = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_Company/Sync_Time/2015-06-30%2021:01:29.263/Authentication_Token/C369CFF9-C265-4257-A8D3-022C2EC0D35B";


        Log.e("strUrl_companyDeatls ", " = " + strUrl_companyDeatls);

        InputStream inputStream = null;

        try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_companyDeatls));
                inputStream = httpResponse.getEntity().getContent();

                if(inputStream != null)
                    strResult = convertInputStreamToString(inputStream);
                else
                    strResult = "Did not work!";

        }

        catch (Exception e)
        {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        String jsonStr = strResult;
        Log.e("jsonStr ", " = " + jsonStr);



        if(jsonStr != null)
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

                     if(dbhelper.isTitleExist(str_CompanyId))
                     {
                        //Upadte
                         dbhelper.updatedetails(str_CompanyId, str_CompanyName);
                         Log.e("Data updated in ", "Company Table !!");
                     }
                     else
                     {

                        //insert
                         dbhelper.insertCompany(str_CompanyId,str_CompanyName);
                         Log.e("Data inserted in " , "Company Table !!");
                     }



                 }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }*/

/************************************************************************************************************************************************************************************/

   /* public void Get_Vessels_By_Type()
    {
        String strUrl_Vessels_By_Type = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_ALL_Vessels/Sync_Time/"+strSync_Time+"/Authentication_Token/"+str_Authentication_Token;
        Log.e("strUrl_Vessels_By_Type ", " = " + strUrl_Vessels_By_Type);

        InputStream inputStream = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_Vessels_By_Type));
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e("Get_Vessels_By_Type  "," = " + jsonStr);

            if(jsonStr != null)
            {
                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String jsonResult = jsonObj.toString().trim();
                    Log.e("jsonResult ", " = " + jsonResult);

                    JSONObject vesselList = jsonObj.getJSONObject("Get_ALL_VesselsResult");
                    Log.e("companyList ", " = " + vesselList.toString());

                    String strSync = vesselList.getString("Sync_Time");
                    Log.e("strSync ", " = " + strSync.toString());

                    JSONArray jarr = vesselList.getJSONArray("VesselList");
                    Log.e("jarr ", " = " + jarr.toString());

                    for (int j = 0; j < jarr.length(); j++)
                    {
                        JSONObject jobjVessels = jarr.getJSONObject(j);


                        strACtiveStatus = jobjVessels.getString("status");
                        strVessCompanyId = jobjVessels.getString("companyId");
                        strVesselName = jobjVessels.getString("Vessel_Name");
                        strVesseleId = jobjVessels.getString("Vessel_ID");
                        strVesselsTypeId = jobjVessels.getString("vesselTypeId");
                        VesselsTypeName = jobjVessels.getString("Vessel_Name");
                        strClassCodeNumber = jobjVessels.getString("Notation");

                        *//* Log.e("strACtiveStatus ", " = " + strACtiveStatus);
                        Log.e("strVessCompanyId ", " = " + strVessCompanyId);
                        Log.e("strVesselName ", " = " + strVesselName);
                        Log.e("strVesseleId ", " = " + strVesseleId);
                        Log.e("strVesselsTypeId ", " = " + strVesselsTypeId);
                        Log.e("VesselsTypeName ", " = " + VesselsTypeName);
                        Log.e("strClassCodeNumber ", " = " + strClassCodeNumber);
                        strIsFollowedVessels = jobjVessels.getString("");*//*


                        if(dbhelper.isVesselId_Exist(strVesseleId))
                        {
                            dbhelper.update_VesselsId(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                            Log.e("Data updated in ", "Table_VesselList Table !!");
                        }

                        else
                        {
                            dbhelper.insert_VesselList(strACtiveStatus, strVessCompanyId, strVesselName, strVesseleId, strVesselsTypeId, VesselsTypeName, strClassCodeNumber, strIsFollowedVessels);
                            Log.e("Data inserted in " , "Table_VesselList Table !!");
                        }

                    }

                }
                catch(JSONException je)
                {
                    je.printStackTrace();
                }
            }

        }

        catch (Exception e)
        {
            Log.d("InputStream", e.getLocalizedMessage());
        }
    }
*/
//http://103.24.4.60/CLASSNK1/MobileService.svc/Get_Vessels_By_Type/Sync_Time/2015-06-30%2021:01:29.263/Authentication_Token/C369CFF9-C265-4257-A8D3-022C2EC0D35B