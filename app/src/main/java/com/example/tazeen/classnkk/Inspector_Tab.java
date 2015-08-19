package com.example.tazeen.classnkk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBsqlite.CustomList;
import com.example.DBsqlite.Inspector;
import com.example.DBsqlite.Inspector_Adapter;
import com.example.DBsqlite.MyDbHelper;
import com.example.DBsqlite.Vessels;

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

public class Inspector_Tab extends Fragment
{
    String strResult;
    int checked;
    private ListView Inspector_ListView;
    private ProgressDialog pDialog;
    Inspector_Adapter inspector_Adapter;
    ArrayList<Inspector> InspectorArray = new ArrayList<Inspector>();
    MyDbHelper dbhelper;
    String strCheckItem;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dbhelper = new MyDbHelper(getActivity());
       // GetInspector_Details getCompany = new GetInspector_Details();
        //getCompany.execute();
    }

    /************************************************************************************************************************************************************************************/
    private class GetInspector_Details extends AsyncTask<Void, Void, Void> {

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
          //  Get_InspectorUserList();
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



    /****************************************************************************************************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_inspector__tab, container, false);
        list_Intialize(rootView);
        return rootView;
    }



    private void list_Intialize(View view) {

        MyDbHelper mdb = new MyDbHelper(getActivity());
        List<Inspector> inspectorList = mdb.getAllInspectorName();
        for(Inspector inspectorNameList : inspectorList)
        {
            InspectorArray.add(inspectorNameList);
        }

        inspector_Adapter = new Inspector_Adapter(getActivity(), R.layout.inspector_list_item, InspectorArray);
        Inspector_ListView = (ListView)view.findViewById(R.id.inspectore_Listview);
        Inspector_ListView.setAdapter(inspector_Adapter);


    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    public class Inspector_Adapter  extends ArrayAdapter<Inspector>
    {
        Context context;
        int layoutResourceId;
        ArrayList<Inspector> data=new ArrayList<Inspector>();
        MyDbHelper dbhelper;


        public Inspector_Adapter(Context context, int layoutResourceId, ArrayList<Inspector> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;

        }

        @Override
        public int getCount()
        {
            return data.size();
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Holder holder = null;

            final Inspector inspect = (Inspector) this.getItem(position);
            dbhelper = new MyDbHelper(getContext());
            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new Holder();
                holder.txtTitle = (TextView)row.findViewById(R.id.inspectortName_txt);
                holder.inspector_CheckBox = (CheckBox)row.findViewById(R.id.inspector_CheckBox);

                row.setTag(holder);

                holder.inspector_CheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       CheckBox checkbox = (CheckBox)v;


                        if(checkbox.isChecked())
                        {
                            checked = 1;

                            String strIsFollowFlag = "1";
                            Log.e("checked  ","strIsFollowFlag = " + strIsFollowFlag);
                            String strInspectorname = inspect.getName();
                            Log.e("strInspectorname", "= " + strInspectorname);

                            getCompanyId(strInspectorname);
                            dbhelper.updateIbspectorNameList(strCheckItem, strInspectorname, strIsFollowFlag);
                            Log.e("If Part isFollowed ", "Updated Succesfully !!!");

                        }
                        else
                        {
                            checked = 0;
                            String strIsFollowFlag = "0";
                            Log.e("checked  ","strIsFollowFlag = " + strIsFollowFlag);
                            String strInspectorname = inspect.getName();
                            Log.e("strInspectorname", "= " + strInspectorname);

                            getCompanyId(strInspectorname);
                            dbhelper.updateIbspectorNameList(strCheckItem , strInspectorname , strIsFollowFlag);
                            Log.e("If Part isFollowed ","Updated Succesfully !!!");
                        }
                    }
                });
            }
            else
            {
                holder = (Holder)row.getTag();
            }
            Inspector adpt = data.get(position);
            holder.txtTitle.setText(adpt.inspector_name);


            return row;
        }
         class Holder
        {

            TextView txtTitle;
            CheckBox inspector_CheckBox;
        }
    }


    private void getCompanyId(String selectedCheckItem)
    {
        try {
            dbhelper = new MyDbHelper(getActivity());
            SQLiteDatabase db=dbhelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select Inspector_Id from Inspector where " + "Inspector_name" + " = ?",
                    new String[]{selectedCheckItem});

            if(cursor!=null && cursor.moveToFirst())
            {
                strCheckItem = cursor.getString(cursor.getColumnIndex("Inspector_Id"));
                System.out.println(" strCheckItem =" + strCheckItem);
                Log.e(" string_CompanyId "," = "+ strCheckItem);
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


}


































  /* public void Get_InspectorUserList()
    {
        String strUrl_GetALlUserList = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_User_List/Sync_Time/null/Authentication_Token/4B505408-EAE7-4AF3-B0D4-3F7F11A2B131";
        Log.e("Inspector List", " = " + strUrl_GetALlUserList);

        InputStream inputStream = null;

        try
        {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(strUrl_GetALlUserList));
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                strResult = convertInputStreamToString(inputStream);
            else
                strResult = "Did not work!";

            String jsonStr = strResult;
            Log.e(" Get_User_List "," = " + jsonStr);

            if(jsonStr != null)
            {
                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String jsonResult = jsonObj.toString().trim();
                    Log.e("jsonResult ", " = " + jsonResult);

                    JSONObject vesselList = jsonObj.getJSONObject("Get_User_ListResult");
                    Log.e("companyList ", " = " + vesselList.toString());

                    String strSync = vesselList.getString("Sync_Time");
                    Log.e("strSync ", " = " + strSync.toString());

                    JSONArray jarr = vesselList.getJSONArray("UserList");
                    Log.e("jarr ", " = " + jarr.toString());

                    for (int j = 0; j < jarr.length(); j++)
                    {
                        JSONObject jobInspectors = jarr.getJSONObject(j);

                        strInspector_Id = jobInspectors.getString("Inspector_Id");
                        strInspector_name = jobInspectors.getString("Inspector_name");
                        strRole = jobInspectors.getString("Role");

                        Log.e("strInspector_Id ", " = " + strInspector_Id);
                        Log.e("strInspector_name ", " = " + strInspector_name);
                        Log.e("strRole ", " = " + strRole);


                        if(dbhelper.isInspector_id_Exist(strInspector_Id))
                        {
                            //Upadte
                            dbhelper.update_Inspector_Id(strInspector_Id, strInspector_name,strRole );
                            Log.e("Data updated in ", "Company Table !!");
                        }
                        else
                        {

                            //insert
                            dbhelper.insertInspectorList(strInspector_Id, strInspector_name, strRole);
                            Log.e("Inspector Userdata save", " Sucesfully !!! = ");
                        }



                    }
                }
                catch (JSONException jes)
                {
                    jes.printStackTrace();
                }
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
*/