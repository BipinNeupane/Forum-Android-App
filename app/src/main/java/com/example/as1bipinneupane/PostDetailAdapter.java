package com.example.as1bipinneupane;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.aware.IdentityChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.MyViewHolder> {

    private Context context;
    private ArrayList name_id, commenttext_id, comment_id, date_id;
    DBHelper db;


    public PostDetailAdapter(Context context, ArrayList name_id, ArrayList commenttext_id, ArrayList comment_id, ArrayList date_id, DBHelper db) {
        this.context = context;
        this.name_id = name_id;
        this.comment_id = comment_id;
        this.commenttext_id = commenttext_id;
        this.date_id = date_id;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        to show how data will be displayed
        View v = LayoutInflater.from(context).inflate(R.layout.commenttemplate, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.commenttext_id.setText(String.valueOf(commenttext_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));
        holder.comment_id.setText(String.valueOf(comment_id.get(position)));
        holder.isAdmin(holder);
        holder.onClickDeleteCommment();

    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, commenttext_id, comment_id, date_id;
        Button deleteCommentBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.txtCommentusername);
            commenttext_id = itemView.findViewById(R.id.txtComment);
            date_id = itemView.findViewById(R.id.txtCommentDate);
            comment_id = itemView.findViewById(R.id.txtCommentID);
            deleteCommentBtn = itemView.findViewById(R.id.deleteCmtBtn);
        }

        public void isAdmin(MyViewHolder holder) {
            SharedPreferences preferences = context.getSharedPreferences("userLogData", Context.MODE_PRIVATE);
            String isUserAdmin = preferences.getString("isAdmin", null);
            if (!isUserAdmin.equals("1")) {
                deleteCommentBtn.setVisibility(View.INVISIBLE);
            }
        }

        public void onClickDeleteCommment() {
            deleteCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Comment");
                    builder.setMessage("Are you sure you want to delete this comment?");

// Set the positive button
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            deleting from the database
                            String commentID = comment_id.getText().toString();
                            db.deleteComment(commentID);
                            Toast.makeText(context, "Comment deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

// Set the negative button
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when the user clicks on the No button
                        }
                    });

// Create and show the alert dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }


    }
}

