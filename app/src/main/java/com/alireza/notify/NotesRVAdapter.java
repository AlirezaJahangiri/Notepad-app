package com.alireza.notify;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.ViewHolder> {

    // variable for our array list and context
    private List<DataModal> dataModalArrayList;
    private Context context;
    private Realm realm;
    private Long id;

    public NotesRVAdapter(List<DataModal> dataModalArrayList, Context context) {
        this.dataModalArrayList = dataModalArrayList;
        this.context = context;
    }

    public void filterList(ArrayList<DataModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRVAdapter.ViewHolder holder, int position) {
        DataModal modal = dataModalArrayList.get(position);
        holder.titleTV.setText(modal.getTitle());
        holder.descriptionTV.setText(modal.getDescription());
        holder.timeTV.setText(modal.getTime());

        // adding on click listener for item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are creating a new intent.
                Intent i = new Intent(context, Edit_note.class);

                // on below line we are passing all the data to new activity.
                i.putExtra("title", modal.getTitle());
                i.putExtra("description", modal.getDescription());
                i.putExtra("id", modal.getId());
                // on below line we are starting a new activity.
                context.startActivity(i);
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//               showDialog();
//                return false;
//            }
//        });
//    }
//
//    public void showDialog() {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.bottom_sheet_layout);
//        dialog.show();
//
//         TextView deletOption = dialog.findViewById(R.id.delete_option);
//         TextView editOption = dialog.findViewById(R.id.edit_option);
//        TextView shareOption = dialog.findViewById(R.id.share_option);
//
//      shareOption.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Intent sendIntent = new Intent();
//              sendIntent.setAction(Intent.ACTION_SEND);
//              sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//              sendIntent.setType("text/plain");
//
//              Intent shareIntent = Intent.createChooser(sendIntent, null);
//              context.getApplicationContext().startActivity(shareIntent);
//          }
//      });


    }



    @Override
    public int getItemCount() {
        return dataModalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private TextView titleTV, descriptionTV, timeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            titleTV = itemView.findViewById(R.id.titleoutput);
            descriptionTV = itemView.findViewById(R.id.descriptionoutput);
            timeTV = itemView.findViewById(R.id.timeoutput);
        }
    }

}

