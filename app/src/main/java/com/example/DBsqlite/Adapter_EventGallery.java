package com.example.DBsqlite;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.tazeen.classnkk.R;

/**
 * Created by Tazeen on 08-08-2015.
 */
/*
public class Adapter_EventGallery extends BaseAdapter
{
    private static final String ERRORTAG = "ERROR_CHECKING --> ";

    //Component variables
    private Context adapterContext;



   // private Integer[] images;
   Integer[] imageIDs = {
           R.drawable.img_placeholder,
           R.drawable.img_placeholder,
           R.drawable.img_placeholder,
           R.drawable.img_placeholder,
           R.drawable.img_placeholder,
           R.drawable.img_placeholder,
           R.drawable.img_placeholder
   };


      */
/*  public Adapter_EventGallery(Context passedContext, Integer[] images) {
        adapterContext = passedContext;
        this.imageIDs = images;
    }
*//*


    public Adapter_EventGallery(Context c)
    {
        adapterContext = c;
        // sets a grey background; wraps around the images
        //TypedArray a =ob(R.styleable.MyGallery);
       // itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
       // a.recycle();
    }

    public int getCount()
    {
        Log.i(ERRORTAG, "Length: " + imageIDs.length);
        return imageIDs.length;
    }


    public Object getItem(int position)
    {
        return position;
    }


    public long getItemId(int position)
    {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.i(ERRORTAG, "EventGallery Position: " + position);

        ImageView galleryImage = new ImageView(adapterContext);

        galleryImage.setImageResource(imageIDs[position]);
        galleryImage.setLayoutParams(new Gallery.LayoutParams(400, 400));
        galleryImage.setPadding(5,5,5,5);
        galleryImage.setScaleType(ImageView.ScaleType.FIT_XY);
        //galleryImage.setBackgroundResource(mGalleryItemBackground);

        return galleryImage;
    }
}
*/
