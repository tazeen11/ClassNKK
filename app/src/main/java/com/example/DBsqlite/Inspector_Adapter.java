package com.example.DBsqlite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tazeen.classnkk.R;

import java.util.ArrayList;

/**
 * Created by Tazeen on 28-07-2015.
 */
public class Inspector_Adapter{}
 /*extends ArrayAdapter<Inspector>
{
    Context context;
    int layoutResourceId;

    ArrayList<Inspector> data=new ArrayList<Inspector>();



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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new Holder();
            holder.txtTitle = (TextView)row.findViewById(R.id.inspectortName_txt);
            holder.checkImageView = (ImageView)row.findViewById(R.id.imageView_Checkable_Inspector);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }
        Inspector adpt = data.get(position);
        holder.txtTitle.setText(adpt.inspector_name);


        return row;
    }
    static class Holder
    {

        TextView txtTitle;
        ImageView checkImageView;
    }
}

*/