package com.alireza.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class app_info extends AppCompatActivity {

    //introduce variables
    private ImageView imageView_arrow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        //hide action bar
        getSupportActionBar().hide();

        imageView_arrow_btn = findViewById(R.id.arrow_btn);
        imageView_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(app_info.this, About.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(app_info.this, About.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
