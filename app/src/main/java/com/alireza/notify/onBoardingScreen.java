package com.alireza.notify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class onBoardingScreen extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout dots_layout;
    private SliderAdapter sliderAdapter;

    private TextView[] mDots;

    private TextView next_btn, prev_btn;
    private Button  get_started_btn;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        //hide action bar:
        getSupportActionBar().hide();

        next_btn = findViewById(R.id.next_btn);
        prev_btn = findViewById(R.id.prev_btn);
        get_started_btn = findViewById(R.id.get_started_btn);

        slideViewPager = findViewById(R.id.slide_view_pager);
        dots_layout = findViewById(R.id.dots_layout);

       sliderAdapter  =  new SliderAdapter(this);
       slideViewPager.setAdapter(sliderAdapter);

       addDotsIndicator(0);

       slideViewPager.addOnPageChangeListener(viewListener);

       next_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               slideViewPager.setCurrentItem(currentPage + 1 );

           }
       });


       prev_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               slideViewPager.setCurrentItem(currentPage - 1 );

           }
       });


       get_started_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               AlertDialog.Builder builder = new AlertDialog.Builder(onBoardingScreen.this);
               ViewGroup viewGroup = findViewById(android.R.id.content);
               View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.welcome_dialog, viewGroup, false);
               builder.setView(dialogView);
               AlertDialog alertDialog = builder.create();
               alertDialog.show();


               Handler handler = new Handler();
               handler.postDelayed(new Runnable() {
                   public void run() {
                       alertDialog.dismiss();

                       Intent intent=new Intent(onBoardingScreen.this,note_pad.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intent);
                       finish();
                       overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                   }
               }, 1000);

           }
       });

    }
    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        dots_layout.removeAllViews();

        for(int i = 0; i<mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(70);
            mDots[i].setTextColor(getResources().getColor(R.color.color_of_dots));

            dots_layout.addView(mDots[i]);

        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.color_dots_page_selected));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage = i;

            if (i==0){
                next_btn.setEnabled(true);
                prev_btn.setEnabled(false);
                get_started_btn.setEnabled(false);
                prev_btn.setVisibility(View.INVISIBLE);
                get_started_btn.setVisibility(View.INVISIBLE);
                next_btn.setVisibility(View.VISIBLE);

                next_btn.setText("Next");
                prev_btn.setText("");
                get_started_btn.setText("");


            }else if (i==mDots.length -1){


                next_btn.setEnabled(false);
                get_started_btn.setEnabled(true);
                prev_btn.setEnabled(true);
                prev_btn.setVisibility(View.VISIBLE);
                next_btn.setVisibility(View.INVISIBLE);
                get_started_btn.setVisibility(View.VISIBLE);

                get_started_btn.setText("Get started");
                prev_btn.setText("Back");

            }


            else {
                next_btn.setEnabled(true);
                prev_btn.setEnabled(true);
                get_started_btn.setEnabled(false);
                prev_btn.setVisibility(View.VISIBLE);
                next_btn.setVisibility(View.VISIBLE);
                get_started_btn.setVisibility(View.INVISIBLE);

                next_btn.setText("Next");
                prev_btn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
