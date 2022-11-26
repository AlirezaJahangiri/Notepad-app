package com.alireza.notify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;

public class Add_note extends AppCompatActivity {

    // creating variables for our edit text
    private EditText titleEdt, descriptionEdt, timeEdt;
    private Realm realm;
    private TextView back_btn;

    // creating a strings for storing
    // our values from edittext fields.
    private String title, description, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        //hide action bar:
        getSupportActionBar().hide();


        back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_note.this,note_pad.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        // initializing our edittext and buttons
        realm = Realm.getDefaultInstance();
        titleEdt = findViewById(R.id.titleinput);
        descriptionEdt = findViewById(R.id.descriptioninput);
        timeEdt = findViewById(R.id.timeinput);

        // creating variable for button
        TextView save_Btn = findViewById(R.id.savebtn);
        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                title = titleEdt.getText().toString();
                time = timeEdt.getText().toString();
                String description = descriptionEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(description)){

                    SweetAlertDialog error_dc = new SweetAlertDialog(Add_note.this,SweetAlertDialog.ERROR_TYPE);
                    error_dc.setTitleText("Error");
                    error_dc.setContentText("Please enter description");
                    error_dc.setConfirmText("OK");
                    error_dc.show();

                    //change the text color of alert dialog

                    TextView text = (TextView) error_dc.findViewById(R.id.title_text);
                    text.setTextColor(Color.WHITE);

                    TextView content = (TextView) error_dc.findViewById(R.id.content_text);
                    content.setTextColor(Color.WHITE);


                } else {
                    // calling method to add data to Realm database..

                    //set progress sweet alert dialog
                    SweetAlertDialog save_note = new SweetAlertDialog(Add_note.this,SweetAlertDialog.PROGRESS_TYPE);
                    save_note.setTitleText("Saving Note ...");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        public void run() {
                            addDataToDatabase(title, description, time);
                            titleEdt.setText("");
                            descriptionEdt.setText("");
                            timeEdt.setText("");
                            Intent intent = new Intent(Add_note.this,note_pad.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);



                            //create Toast (Note Saved)

                            final Toast toast =  new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            View custom_view = getLayoutInflater().inflate(R.layout.custom_toast_sucsses_note_saved,null);
                            toast.setView(custom_view);
                            toast.show();

                        }},1000 );

                    save_note.show();

                    //change the text color of alert dialog
                    TextView text = (TextView) save_note.findViewById(R.id.title_text);
                    text.setTextColor(Color.WHITE);
                }
            }
        });
    }

    private void addDataToDatabase(String title, String description, String time) {

        // on below line we are creating
        // a variable for our modal class.
        DataModal modal = new DataModal();

        // on below line we are getting id for the course which we are storing.
        Number id = realm.where(DataModal.class).max("id");

        // on below line we are
        // creating a variable for our id.
        long nextId;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
        }
        // on below line we are setting the
        // data entered by user in our modal class.
        modal.setId(nextId);
        modal.setTitle(title);
        modal.setDescription(description);
        modal.setTime(time);


        // on below line we are calling a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // inside on execute method we are calling a method
                // to copy to real m database from our modal class.
                realm.copyToRealm(modal);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Add_note.this,note_pad.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}