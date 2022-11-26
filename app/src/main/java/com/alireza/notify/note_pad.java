package com.alireza.notify;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;

public class note_pad extends AppCompatActivity {

    List<DataModal> dataModals;

    // creating variables for realm,
    // recycler view, adapter and our list.
    private Realm realm;
    private RecyclerView notesRV;
    private NotesRVAdapter notesRVAdapter;
    private ImageView add_note_btn;
    private ImageView setting_btn;
    private ActionBar actionBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);




        //hide the title of action bar
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        add_note_btn = findViewById(R.id.addnewnotebtn);
        add_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(note_pad.this, Add_note.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });

        setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(note_pad.this, setting.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });



        // on below lines we are initializing our variables.
        notesRV = findViewById(R.id.recyclerview);
        realm = Realm.getDefaultInstance();
        dataModals = new ArrayList<>();

        // calling a method to load
        // our recycler view with data.
        prepareRecyclerView();
    }


    private void prepareRecyclerView() {
        // on below line we are getting data from realm database in our list.
        dataModals = realm.where(DataModal.class).findAll();
        // on below line we are adding our list to our adapter class.
        notesRVAdapter = new NotesRVAdapter(dataModals, this);
        // on below line we are setting layout manager to our recycler view.
        notesRV.setLayoutManager(new LinearLayoutManager(this));
        // at last we are setting adapter to our recycler view.
        notesRV.setAdapter(notesRVAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }


    public void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DataModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DataModal item :dataModals) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
                // if the item is matched we are
            } else  if (item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.

            //create Toast (no data found ...)
            final Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            View custom = getLayoutInflater().inflate(R.layout.custom_toast_no_data_found,null);
            toast.setView(custom);
            toast.show();

        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            notesRVAdapter.filterList(filteredlist);
        }
    }

    //Handle on back pressed event

    @Override
    public void onBackPressed() {

            SweetAlertDialog exit = new SweetAlertDialog(note_pad.this,SweetAlertDialog.WARNING_TYPE);
            exit.setTitleText("Confirm Exit");
            exit.setContentText("Are you sure you want to exit?");
            exit.setConfirmText("CANCEL");
            exit.setCancelText("EXIT");
            exit.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    //exit from app
                    finishAffinity();
                }
            });
            exit.show();
            // change the text color of alert dialog

        TextView text = (TextView) exit.findViewById(R.id.title_text);
        text.setTextColor(Color.WHITE);

        TextView content = (TextView) exit.findViewById(R.id.content_text);
        content.setTextColor(Color.WHITE);

        }
    }





