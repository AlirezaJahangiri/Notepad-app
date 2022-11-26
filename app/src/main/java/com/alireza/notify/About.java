package com.alireza.notify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    //introduce variables:
    private CardView cardView_app_info;
    private ImageView imageView;

private ImageView imageView_insta;
private ImageView imageView_telegram;
private Button follow_btn;
private Button subscribe_btn;
private  Button btn_download_telegram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //hide action bar:
        getSupportActionBar().hide();


        imageView_insta = findViewById(R.id.insta_btn);
        imageView_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set Custom Dialog for insta btn

                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_instagram_dark, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                follow_btn  = (Button) alertDialog.findViewById(R.id.buttonFollow);

                follow_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //intent_instagram

                        Uri uri = Uri.parse("http://instagram.com/_u/");

                        Intent i= new Intent(Intent.ACTION_VIEW,uri);

                        i.setPackage("com.instagram.android");

                        try {
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {

                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://instagram.com/_u/")));
                        }


                        alertDialog.dismiss();

                    }
                });

            }
        });

        imageView_telegram = findViewById(R.id.tel_btn);
        imageView_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //set Custom Dialog for telegram btn

                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_telegram_dark, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                subscribe_btn = alertDialog.findViewById(R.id.buttonsubscribe);
                subscribe_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //intent telegram channel

                        try {
                            Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                            telegramIntent.setData(Uri.parse("http://telegram.me/"));
                            telegramIntent.setPackage("org.telegram.messenger");
                            startActivity(telegramIntent);

                        } catch (Exception e) {

                            // show error message


                            //create Toast telegram not installed

                            final Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            View custom = getLayoutInflater().inflate(R.layout.custom_toast_telegram_not_installed, null);
                            toast.setView(custom);
                            toast.show();

                        }

                        alertDialog.dismiss();

                    }
                });
            }
        });

        imageView = findViewById(R.id.arrow_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(About.this,setting.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        cardView_app_info = findViewById(R.id.card_view_app_info);
        cardView_app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(About.this,app_info.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(About.this,setting.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    }

