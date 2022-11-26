package com.alireza.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Array
    public int[] slide_images = {

            R.drawable.accessdrawble,
            R.drawable.uidrawble,
            R.drawable.wastedrawble

    };



    public String[] slide_headings = {

            "Simple and accessible",
            "Super ui design",
            "Stop wasting your time"

    };


    public String [] slide_descriptions = {

            "simply you can save, edit and delete your notes and easily access to your data.",
                    "A minimal design with a lot of animations that transfer good feelings when use this app.",
                    "Let's write your first note and save it for ever. "

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view,  Object object) {
        return view == (RelativeLayout)object ;
    }


    @Override
    public Object instantiateItem( ViewGroup container, int position) {


        layoutInflater  = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_activity,container,false);

        ImageView SlideImageView  = (ImageView) view.findViewById(R.id.slide_image);
        TextView SlideHeading  = (TextView) view.findViewById(R.id.slide_heading);
        TextView SlideDescription  = (TextView) view.findViewById(R.id.slide_desc);

        SlideImageView.setImageResource(slide_images[position]);
        SlideHeading.setText(slide_headings[position]);
        SlideDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {

        container.removeView((RelativeLayout)object);
    }
}
