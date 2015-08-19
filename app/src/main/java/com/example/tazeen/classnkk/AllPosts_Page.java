package com.example.tazeen.classnkk;

//http://www.coderanch.com/t/612017/Android/Mobile/AsyncTask-show-progress-bar

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.DBsqlite.All_Post;
import com.example.DBsqlite.MyDbHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AllPosts_Page extends Activity {
    String strDownLoadStatus;
    int activityObj_Id;
    ListView listView;
    String[] mStringArray;
    MyDbHelper dbhelper;
    ImageView imgLeftmenu;
    File newFolder;
    ImageView imgSearch;
    ImageView imgAdd;
    Integer[] imageIDs;
    String png_Pattern = ".png";
    String mp3_Pattern = ".mp3";
    String str_Authentication_Token, str_UserId;
    String strDescription;
    String imageName;
    ArrayList<All_Post> descArray = new ArrayList<All_Post>();
    int intCount;
    String[] storedImage_FinalPath;
    List<String> labels;
    String str_activityid;
    String str_activityobjid;
    String str_objectid;
    String str_userid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_post);
        CreateFile();
        dbhelper = new MyDbHelper(this);
        init();
        getSelectedItem();
        allPostDescListInit();


        imgLeftmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AllPosts_Page.this, Filter_Page.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        imgLeftmenu = (ImageView) findViewById(R.id.imgLeftMenu);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
    }

    public void allPostDescListInit() {
        List<All_Post> allDesc = dbhelper.getAllDescriptions();
        for (All_Post all_Post : allDesc) {
            descArray.add(all_Post);
        }

        listView = (ListView) findViewById(R.id.listview_AllPost);
        MyListAdapter adapter = new MyListAdapter(this, R.layout.all_post_row, descArray);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


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

    public void CreateFile() {
        try {
            // Check for SD Card
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                        .show();
            } else {
                // Locate the image folder in your SD Card
                newFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "SDImages");
                // Create a new folder if no folder named SDImageTutorial exist
                newFolder.mkdirs();
            }
        } catch (Exception ex) {
            System.out.println("ex: " + ex);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyListAdapter extends ArrayAdapter<All_Post> {
        Context context;
        int layoutResourceId;

        ArrayList<All_Post> data = new ArrayList<All_Post>();

        public MyListAdapter(Context context, int layoutResourceId, ArrayList<All_Post> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Holder holder = null;
            if (row == null) {

                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new Holder();
                holder.txt_Descid = (TextView) row.findViewById(R.id.textDescid);
                holder.txtTitle = (TextView) row.findViewById(R.id.txt_Desc);
                holder.txtViewActivityId = (TextView) row.findViewById(R.id.txtActivityId);
                holder.txtInitialLetter = (TextView) row.findViewById(R.id.txt_InitialLetter);
                holder.txtRemark = (TextView) row.findViewById(R.id.textRemark);
                holder.imgBtn_Rework = (ImageView) row.findViewById(R.id.imgButtonRework);
                holder.imgBtn_FollowUp = (ImageView) row.findViewById(R.id.imgButtonFoloowUp);
                holder.imgBtn_RateEquipMent = (ImageView) row.findViewById(R.id.imgButtonRateEquipment);
                holder.imgBtn_Camera = (ImageView) row.findViewById(R.id.imgButtonCamera);
                holder.imgBtn_Audio = (ImageView) row.findViewById(R.id.imgButtonAudio);
                holder.imgBtn_FollowInspector = (ImageView) row.findViewById(R.id.imgButtonFollowInspector);
                holder.galleryView = (Gallery) row.findViewById(R.id.gallery_JsonImg);

                row.setTag(holder);

            } else {
                holder = (Holder) row.getTag();
            }

            All_Post all_Post = data.get(position);
            holder.txt_Descid.setText(Integer.toString(all_Post.getID()));
            String strTextDescId = holder.txt_Descid.getText().toString().trim();
            Log.e("strTextDescId ", " = " + strTextDescId);

            getActivityId(strTextDescId);
            getActivityObjDetails(strTextDescId);
            getActivityObjectId_Count();
            getActivityObjectID_Max();
            GetDetails getImageDetails = new GetDetails();
            getImageDetails.execute();

            dbhelper = new MyDbHelper(AllPosts_Page.this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from ActivityObjectList where activityId ='" + strDescription + "'", null);

            if (cursor.moveToFirst())
            {
                do  {   labels.add(cursor.getString(cursor.getColumnIndex("imageaudioPath"))); } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            mStringArray = new String[labels.size()];
            mStringArray = labels.toArray(mStringArray);


            storedImage_FinalPath = new String[mStringArray.length];
            for (int m = 0; m < mStringArray.length; m++)
            {
                Log.e("******************", "************************");
                String firstValue = mStringArray[m];
                Log.e("Image is ", "firstValue = " + firstValue);

                String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                String path = baseDir + "/SDImages/" + firstValue;

                storedImage_FinalPath[m] = path;
                Log.e("Image is ", "storedImage_FinalPath = " + storedImage_FinalPath[m]);
                Log.e("******************", "************************");
            }

            ImageAdapter adapter = new ImageAdapter(context, storedImage_FinalPath);
            holder.galleryView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            holder.txtViewActivityId.setText(all_Post.getStrActivityId());
            holder.txtTitle.setText(all_Post.getName());
            String strTxtTitle = holder.txtTitle.getText().toString().trim();
            Log.e("strTxtTitle ", " = " + strTxtTitle);
            holder.txtInitialLetter.setText(all_Post.getInitName());
            holder.txtRemark.setText(all_Post.getStrDesc());



            //OnClickListener for images in the gallery
            holder.galleryView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();

                }
            });

            //OnClickListener for Button in the ListItem
            holder.imgBtn_Rework.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "Rework" + " = ", Toast.LENGTH_SHORT).show();
                }
            });

            //OnClickListener for camera button in the List
            holder.imgBtn_Camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "Camera" + " = ", Toast.LENGTH_SHORT).show();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 0);

                  /*  activityObj_Id++;
                    String intoString = Integer.toString(activityObj_Id);

                    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                    String dateformat = s.format(new Date());
                    Log.e("format"," and activityObj_Id = "+ dateformat +" & "+ activityObj_Id);

                    String imageCamera_Path = activityObj_Id +"_"+ dateformat;
                    String downloadstatus = "1";
                    Log.e("imageCamera_Path", "= " + imageCamera_Path);

                    dbhelper.insert_ActivityObjectList(strDescription,intoString,imageCamera_Path ,str_objectid,  str_userid,downloadstatus);*/
                }
            });

            //OnClick listener for Audio Button in the List
            holder.imgBtn_Audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "Audio" + " = ", Toast.LENGTH_SHORT).show();

                }
            });
            return row;
        }

        class Holder {
            TextView txt_Descid;
            TextView txtViewActivityId;
            TextView txtTitle;
            TextView txtInitialLetter;
            TextView txtRemark;
            Gallery galleryView;

            ImageView imgBtn_Rework;
            ImageView imgBtn_FollowUp;
            ImageView imgBtn_RateEquipMent;
            ImageView imgBtn_Camera;
            ImageView imgBtn_Audio;
            ImageView imgBtn_FollowInspector;

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        Log.e("onActivityResult bp "," = " + bp);
       // iv.setImageBitmap(bp);

        activityObj_Id++;
        String intoString = Integer.toString(activityObj_Id);

        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String dateformat = s.format(new Date());
        Log.e("format", " and activityObj_Id = " + dateformat + " & " + activityObj_Id);

        String imageCamera_Path = activityObj_Id +"_"+ dateformat;
        String downloadstatus = "1";
        Log.e("imageCamera_Path", "= " + imageCamera_Path);

        dbhelper.insert_ActivityObjectList(strDescription, intoString, imageCamera_Path, str_objectid, str_userid, downloadstatus);
        Log.e("Data Save","in onActivityResult Succesfully !!!!");
    }

    private void getActivityId(String descriptionID) {
        try {
            dbhelper = new MyDbHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("select activityId from ALLPost_Description where " + "descid" + " = ?",
                    new String[]{descriptionID});

            if (cursor != null) {

                cursor.moveToFirst();

               strDescription = cursor.getString(0);


                Log.e(" Primery key ", "activityId in getActivityId = " + strDescription);

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void getActivityObjectId_Count() {
        try {
            dbhelper = new MyDbHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor cursor2 = db.rawQuery("select count(activityObjId) from ActivityObjectList where " + "activityId" + " = ?", new String[]{strDescription});

            if (cursor2 != null) {

                cursor2.moveToFirst();

                String strCount_ActivityObjectId = cursor2.getString(0);
                intCount = Integer.parseInt(strCount_ActivityObjectId);
                Log.e("intCount", "in getActivityObjectId = " + intCount);


                imageIDs = new Integer[intCount];
                int imageLength = imageIDs.length;
                Log.e("imageLength ", " = " + imageLength);
                for (int i = 0; i < imageIDs.length; i++) {
                    Log.e("imageLength ", " = " + imageLength);
                    imageIDs[i] = R.drawable.img_placeholder;
                }

            }

            cursor2.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void getActivityObjDetails(String strid) {
        dbhelper = new MyDbHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor3 = db.rawQuery("select * from ActivityObjectList where objID " + "= ? ", new String[]{strid});
        if (cursor3.moveToFirst())
        {
            do
            {
                 str_activityid = cursor3.getString(cursor3.getColumnIndex("activityId"));
                 str_activityobjid = cursor3.getString(cursor3.getColumnIndex("activityObjId"));
                 str_objectid = cursor3.getString(cursor3.getColumnIndex("objectId"));
                 str_userid = cursor3.getString(cursor3.getColumnIndex("userId"));

                Log.e("ActivityObjectList ", " in Each Item of List= " +str_activityid +" # "+str_activityobjid+" # "+str_objectid+" # "+str_userid);



            } while (cursor3.moveToNext());
        }
            cursor3.close();
            db.close();


        }

        public List<String> getAll_ImageAudioPath () {
            labels = new ArrayList<String>();

            dbhelper = new MyDbHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from ActivityObjectList where activityId ='" + strDescription + "'", null);

            if (cursor.moveToFirst()) {
                do {

                    imageName = cursor.getString(cursor.getColumnIndex("imageaudioPath"));
                    strDownLoadStatus = cursor.getString(cursor.getColumnIndex("DownLoad_Status"));
                    Log.e("imageName ", "  = " + imageName + " & strDownLoadStatus is " + strDownLoadStatus);

                    if (strDownLoadStatus.equalsIgnoreCase("0")) {
                        if (imageName.endsWith(png_Pattern)) {
                            String str_ImageUrl = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + imageName;
                            Log.e("str_ImageUrl ", " = " + str_ImageUrl);
                            download_PngFile(str_ImageUrl);

                            Log.e("Png Downloaded ", "Completed !!!!!");
                            strDownLoadStatus = "1";/**/
                            dbhelper.update_DownLoadStatus(imageName, strDownLoadStatus);
                        }

                        if (imageName.endsWith(mp3_Pattern)) {
                            String str_Mp3Url = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + imageName;
                            Log.e("str_ImageUrl ", " = " + str_Mp3Url);
                            download_Mp3File(str_Mp3Url);

                            Log.e("Png Downloaded ", "Completed !!!!!");
                            strDownLoadStatus = "1";
                            dbhelper.update_DownLoadStatus(imageName, strDownLoadStatus);
                        }
                    }

             /*   else if (strDownLoadStatus.equalsIgnoreCase("1"))
                {
                    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String path = baseDir + "/SDImages/" + imageName;
                    imageNamelist.add(path);


                }*/
                } while (cursor.moveToNext());

            }

            cursor.close();
            db.close();


            return labels;
        }

    public void getActivityObjectID_Max()
    {
        try {
            dbhelper = new MyDbHelper(this);
            SQLiteDatabase db = dbhelper.getReadableDatabase();


            Cursor cursor2 = db.rawQuery("select MAX(activityObjId) from ActivityObjectList where " + "activityId" + " = ?", new String[]{strDescription});
            if (cursor2 != null)
            {
                cursor2.moveToFirst();
                String strMax_ActivityObjectId = cursor2.getString(0);

                activityObj_Id = 0;
                try
                {
                    activityObj_Id = Integer.parseInt(strMax_ActivityObjectId);
                    Log.e("MAX activityObj_Id "," = " + activityObj_Id);
                }
                catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
            }

            cursor2.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    void download_PngFile(String fileUrl) {
        Log.e("In download_PngFile ", " str_imgList_imageaudioPath = " + imageName);
        Bitmap imagenObtenida = null;
        try {
            URL ImgUrl = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) ImgUrl.openConnection();
            conn.connect();
            imagenObtenida = BitmapFactory.decodeStream(conn.getInputStream());
            Log.e("imagenObtenida", " = " + imagenObtenida);

            String fotoname = imageName;
            File file = new File(newFolder, fotoname);
            if (file.exists()) file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                imagenObtenida.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
                Toast.makeText(getApplicationContext(), "Saved to your folder", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void download_Mp3File(String fileUrl) {
        URL mp3_url = null;
        InputStream inputStream = null;
        int bufferLength = 0;
        byte[] buffer = new byte[1024];
        int downloadedSize = 0;
        Log.e("In download_Mp3File ", "= " + imageName);

        try {
            mp3_url = new URL(fileUrl);
        } catch (MalformedURLException mfe) {
            mfe.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) mp3_url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        File file = new File(newFolder, System.currentTimeMillis() + imageName);
        FileOutputStream fileOutput = null;

        try {
            fileOutput = new FileOutputStream(file);


            inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            Log.e("totalSize ", " = " + totalSize);
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            while ((bufferLength = inputStream.read(buffer)) > 0)
            {

                fileOutput.write(buffer, 0, bufferLength);
                //bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutput1);
                downloadedSize += bufferLength;
                Log.e("downloadedSize", " = " + downloadedSize);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // close the output stream when done
        try {
            fileOutput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;

        public ImageAdapter(Context c, String[] storedImage_FinalPath1 ) {
            context = c;
            mStringArray = storedImage_FinalPath1;
            TypedArray a = obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.LinearLayoutCompat_android_baselineAligned, 0);
            a.recycle();

        }

        public int getCount()
        {
            return  mStringArray.length;
        }

        public Object getItem(int position)
        {
            return position;
        }
        public long getItemId(int position) {
            return position;
        } // returns the ID of an item

        public View getView(final int position, View convertView, ViewGroup parent) // returns an ImageView view
        {
            View  galleryView = convertView;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ViewHolder holder;

            if (galleryView == null)
            {
                holder = new ViewHolder();
                galleryView = inflater.inflate(R.layout.gallery_item, null);
                holder.image = (ImageView)galleryView.findViewById(R.id.imgvw11);
                holder.progressBar = (ProgressBar)galleryView.findViewById(R.id.progressBaar_Image);
                holder.image.setBackgroundResource(itemBackground);

                galleryView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) galleryView.getTag();
                ((ImageView)convertView).setImageBitmap(null);

            }
            holder.progressBar.setVisibility(View.VISIBLE);

           new AsyncTask<ViewHolder, Void, Bitmap>()
           {
                private ViewHolder v;

                @Override
                protected Bitmap doInBackground(ViewHolder... params)
                {
                    v = params[0];
                    Bitmap bitmap = BitmapFactory.decodeFile(mStringArray[position]);
                    Log.e("bitmap ", " = " + bitmap);

                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap result)
                {
                    super.onPostExecute(result);
                    v.progressBar.setVisibility(View.GONE);

                    try
                    {
                        Log.e("_______________________", "________________________");
                            String storedImage = mStringArray[position];
                            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                            String path = baseDir + "/SDImages/" + storedImage;

                            if(path.endsWith(mp3_Pattern))
                            {
                                v.image.setImageResource(R.drawable.audio_control);
                                Log.e("path", " = " + path);
                                Log.e("storedImage path in ", "onPostExecute in if condition= " + path);
                            }
                            else
                            {
                                v.image.setImageBitmap(result);
                                Log.e("storedImage path in ", "onPostExecute in Else condition= " + path);
                            }

                        Log.e("_______________________", "________________________");

                    }
                    catch(Exception uae)
                    {  uae.printStackTrace();  }
                }
            }.execute(holder);


            return galleryView;
        }

        class ViewHolder {
            ImageView image;
            ProgressBar progressBar;

        }
    }

    private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            getAll_ImageAudioPath();
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
      }
    }

}






































    /*public void getAllImagePath_List() {
        String strUrl_GetAll_Activity = "http://103.24.4.60/CLASSNK1/MobileService.svc/Get_ALL_ActivityList/userID/" + str_UserId + "/Sync_Time/" + strSync_Time + "/Authentication_Token/" + str_Authentication_Token;
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



                for (int j = 0; j < jarr.length(); j++) {
                    JSONObject jobjVessels = jarr.getJSONObject(j);

                    str_imgList_imageaudioPath = jobjVessels.getString("imageaudioPath");

                    Log.e("", "" + str_imgList_imageaudioPath);

                    if (str_imgList_imageaudioPath.endsWith(png_Pattern))
                    {
                        String str_ImageUrl = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + str_imgList_imageaudioPath;
                        Log.e("str_ImageUrl ", " = " + str_ImageUrl);
                        download_PngFile(str_ImageUrl);
                        Log.e("Png Downloaded ", "Completed !!!!!");
                    }
                    if (str_imgList_imageaudioPath.endsWith(mp3_Pattern))
                    {
                        String str_Mp3Url = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + str_imgList_imageaudioPath;
                        Log.e("str_ImageUrl ", " = " + str_Mp3Url);
                        download_Mp3File(str_Mp3Url);
                        Log.e("Png Downloaded ", "Completed !!!!!");
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/

    /*private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllPosts_Page.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                //img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(AllPosts_Page.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
*/






/*    /* if (newFolder.isDirectory()) {
                listFile = newFolder.listFiles();

                filepath = new String[listFile.length];
                for (int i = 0; i < listFile.length; i++) {
                    filepath[i] = listFile[i].getAbsolutePath();

                }

storedImage_FinalPath = new String[mStringArray.length];
        for (int m = 0; m < filepath.length; m++) {
        String firstValue = filepath[m];
        // Log.e("Image is ","firstValue = " + firstValue);

        for (int n = 0; n < mStringArray.length; n++) {
        String SecondValue = mStringArray[n];
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = baseDir + "/SDImageTutorial/" + SecondValue;
        // Log.e("Image is ","Secondvalue = " + path);

        if (firstValue.equalsIgnoreCase(path)) {
        Log.e("******************", "************************");
        Log.e("Image is ", "Equal = ");
        storedImage_FinalPath[n] = path;
        Log.e("Image is ", "storedImage_FinalPath = " + storedImage_FinalPath[n]);
        Log.e("******************", "************************");

        } else {
        Log.e("Image is ", "Not Equal");
        }
        }
        }
        */












/* /*   Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.audio_control);
        StringTokenizer tokens = new StringTokenizer(imageName, ".");
        String foto_mp31 = tokens.nextToken();
        String foto_mp32 = tokens.nextToken();
          Log.e("foto_mp31"," = "+ foto_mp31);
        Log.e("foto_mp32"," = "+ foto_mp32);
           File file1 = new File(newFolder, foto_mp31+".png");
            fileOutput1 = new FileOutputStream(file1);
*/










  /* img_array = imageNamelist.toArray(new String[imageNamelist.size()]);
        for(int x = 0 ; x < imageNamelist.size(); x++)
        {
            String strImgname = imageNamelist.get(x);
            img_array[x] = strImgname;
            Log.e(" xyz ", " = " + img_array[x] );        }*/

        /*imageAdapter = new ImageAdapter(adapterContext, position);
 _imageAdapter.setList(adapterWallList.get(position).getImages());
 holder.imagesGallery.setAdapter(_imageAdapter);
 holder.imagesGallery.setTag(position);

//Set list method\

public void setList(ArrayList<UImage> YOUR ARRAY) {
        this.images = listImage;
    }
*/
/**/
      /*  int strLabelSize = labels.size();
        Log.e("Value of strLabelSize ", " = " + strLabelSize);

        for (int i = 0; i < labels.size(); i++)
        {
            Log.e("Value of element ", " = " + i + " & " + labels.get(i));
            strLablepath = labels.get(i);
            Log.e("Value of strLablepath ", " = " + strLablepath);

            mStringArray = new String[labels.size()];
            mStringArray = labels.toArray(mStringArray);

            if (strLablepath.endsWith(png_Pattern)) {
                String str_ImageUrl = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + strLablepath;
                Log.e("str_ImageUrl ", " = " + str_ImageUrl);
                download_PngFile(str_ImageUrl);
                Log.e("Png Downloaded ", "Completed !!!!!");
            }

            if (strLablepath.endsWith(mp3_Pattern)) {
                String str_Mp3Url = "http://103.24.4.60/CLASSNK1/MobileService.svc/DownloadFile/FileName/" + strLablepath;
                Log.e("str_ImageUrl ", " = " + str_Mp3Url);
                download_Mp3File(str_Mp3Url);
                Log.e("Png Downloaded ", "Completed !!!!!");
            }


        }*/





