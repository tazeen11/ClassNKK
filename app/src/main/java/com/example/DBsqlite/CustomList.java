package com.example.DBsqlite;

/**
 * Created by Tazeen on 27-07-2015.
 */
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tazeen.classnkk.R;

public class CustomList
{}

/*extends ArrayAdapter<Vessels>
{
    Context context;
    int layoutResourceId;
    private int selectedIndex;
    private int selectedColor = Color.parseColor("#1b1b1b");
    ArrayList<Vessels> data=new ArrayList<Vessels>();
    int checked = 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;
        dbhelper = new MyDbHelper(getContext());
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new Holder();
            holder.txtTitle = (TextView)row.findViewById(R.id.vesselName_txt);
            str = holder.txtTitle.getText().toString().trim();
            holder.check_View = (CheckBox)row.findViewById(R.id.checkBox_Item);

            row.setTag(holder);

            holder.check_View.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Vessels vessel_custAdpt = (Vessels) cb.getTag();
                    Toast.makeText(getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    vessel_custAdpt.setSelected(cb.isChecked());
                    if(cb.isChecked())
                    {
                        checked = 1;
                       // dbhelper.update_isFollowed();
                        Log.e("str infront Check box :", "" + str);
                        Toast.makeText(getContext(), "Checked is true: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        checked = 0;
                    }

                    //db.execSQL("UPDATE DB_TABLE SET YOUR_COLUMN='newValue' WHERE id=6 ");
                }
            });
        }
        else
        {
            holder = (Holder)row.getTag();
        }
        Vessels vessel_custAdpt = data.get(position);
        holder.txtTitle.setText(vessel_custAdpt.vessel_name);
        holder.check_View.setChecked(vessel_custAdpt.isSelected());
        holder.check_View.setTag(vessel_custAdpt);


        return row;
    }
    static class Holder
    {

        TextView txtTitle;
        CheckBox check_View;
    }
}*/



/*

if(selectedIndex!= -1 && position == selectedIndex)
        {
        // holder.checkImageView.

        holder.checkImageView.setImageResource(R.drawable.small_check);
        // holder.checkImageView.isEnabled(View.);
        }



// holder.checkImageView.setText("" + (position + 1) + " " + data.get(position).getTestText());
//convert byte to bitmap take from contact class
      */
/*  byte[] outImage=picture._image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);*/
