package com.alireza.notify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;

public class Edit_note extends AppCompatActivity {

    // creating variables for our edit text
    private EditText titleEdt, descriptionEdt;

    // creating a strings for storing
    // our values from edittext fields.
    private String title, description;
    private long id;
    private TextView updatenote_btn;
    private Realm realm;
    private TextView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //hide action bar:
        getSupportActionBar().hide();

        back_btn = findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Edit_note.this,note_pad.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });


        // initializing our edittext and buttons
        realm = Realm.getDefaultInstance();
        titleEdt = findViewById(R.id.titleinput_edit);
        descriptionEdt = findViewById(R.id.descriptioninput_edit);
        updatenote_btn = findViewById(R.id.edit_note_btn);

        // on below line we are getting data which is passed from intent.
        title = getIntent().getStringExtra("title");
        description= getIntent().getStringExtra("description");
        id = getIntent().getLongExtra("id", 0);

        // on below line we are setting data in our edit test fields.
        titleEdt.setText(title);
        descriptionEdt.setText(description);

        // adding on click listener for update button.
        updatenote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting data from edittext fields.
                String title = titleEdt.getText().toString();
                String description = descriptionEdt.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(description)) {

                    SweetAlertDialog error_dc = new SweetAlertDialog(Edit_note.this,SweetAlertDialog.ERROR_TYPE);
                    error_dc.setTitleText("Error");
                    error_dc.setContentText("Please enter description.");
                    error_dc.setConfirmText("OK");
                    error_dc.show();
                    TextView text = (TextView) error_dc.findViewById(R.id.title_text);
                    text.setTextColor(Color.WHITE);

                    TextView content = (TextView) error_dc.findViewById(R.id.content_text);
                    content.setTextColor(Color.WHITE);
                }
                else {

                    // on below line we are getting data from our modal where
                    // the id of the course equals to which we passed previously.
                    final DataModal modal = realm.where(DataModal.class).equalTo("id", id).findFirst();
                    update_note(modal, title, description);

                    // on below line we are displaying a toast message when course is updated.
                  SweetAlertDialog update_not =  new  SweetAlertDialog (Edit_note.this,SweetAlertDialog.PROGRESS_TYPE);
                    update_not.setTitleText("Editing note ...");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        public void run() {
                            // 1.7 second close the dialog
                            update_not.dismiss();
                            Intent intent = new Intent(Edit_note.this,note_pad.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                            //create Toast (Note Updated)
                           final Toast toast = new Toast(getApplicationContext());
                            toast.setDuration(Toast.LENGTH_SHORT);
                            View custom = getLayoutInflater().inflate(R.layout.custom_toast_sucsses_note_updated,null);
                            toast.setView(custom);
                            toast.show();

                        }}, 1000);

                    update_not.show();
                    TextView text = (TextView) update_not.findViewById(R.id.title_text);
                    text.setTextColor(Color.WHITE);
                }


            }
        });

        TextView deletebtn = findViewById(R.id.delete_note);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog deleteDialog = new SweetAlertDialog(Edit_note.this, SweetAlertDialog.WARNING_TYPE);
                deleteDialog.setTitleText("Delete Note");
                deleteDialog.setContentText("Are you sure you want to delete this note?");
                deleteDialog.setCancelText("YES! DELETE");
                deleteDialog.setConfirmText("CANCEL");
                deleteDialog.show();
                TextView texttitle = (TextView) deleteDialog.findViewById(R.id.title_text);
                texttitle.setTextColor(Color.WHITE);

                TextView textcontent = (TextView) deleteDialog.findViewById(R.id.content_text);
                textcontent.setTextColor(Color.WHITE);


                deleteDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        deleteCourse(id);
                sweetAlertDialog
                .setTitleText("Deleted")
                        .showCancelButton(false)
                .setContentText("Note has been deleted.")
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sweetAlertDialog.findViewById(R.id.confirm_button).setVisibility(View.GONE);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){
                            public void run() {
                              // 1.7 second close the dialog
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(Edit_note.this,note_pad.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            }}, 1000);

                    }
                }).show();

                TextView texttitle2 = (TextView) deleteDialog.findViewById(R.id.title_text);
                texttitle2.setTextColor(Color.WHITE);

                TextView textcontent2 = (TextView) deleteDialog.findViewById(R.id.content_text);
                textcontent2.setTextColor(Color.WHITE);

            }
        });
    }


    private void update_note (DataModal modal, String title, String description) {

        // on below line we are calling
        // a method to execute a transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // on below line we are setting data to our modal class
                // which we get from our edit text fields.
                modal.setTitle(title);
                modal.setDescription(description);

                // inside on execute method we are calling a method to copy
                // and update to real m database from our modal class.
                realm.copyToRealmOrUpdate(modal);
            }
        });
    }
    // deleteCourse() function
    private void deleteCourse(long id) {
        // on below line we are finding data from our modal class by comparing it with the course id.
        DataModal modal = realm.where(DataModal.class).equalTo("id", id).findFirst();
        // on below line we are executing a realm transaction.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // on below line we are calling a method for deleting this course
                modal.deleteFromRealm();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Edit_note.this,note_pad.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

