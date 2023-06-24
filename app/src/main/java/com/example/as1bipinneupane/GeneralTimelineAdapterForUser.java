package com.example.as1bipinneupane;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GeneralTimelineAdapterForUser extends RecyclerView.Adapter<GeneralTimelineAdapterForUser.MyViewHolder> {

    private Context context;
    private ArrayList<String> user_id, forum_id, date_id,post_id;
    DBHelper db;

    public GeneralTimelineAdapterForUser(Context context, ArrayList user_id, ArrayList forum_id, ArrayList date_id,ArrayList post_id,DBHelper db) {
        this.context = context;
        this.user_id = user_id;
        this.forum_id = forum_id;
        this.date_id = date_id;
        this.post_id = post_id;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        to show how data will be displayed
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posttemplateuser, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.isAdmin(holder);
        holder.user_id.setText(String.valueOf(user_id.get(position)));
        holder.forum_id.setText(String.valueOf(forum_id.get(position)));
        holder.date_id.setText(String.valueOf(date_id.get(position)));
        holder.hideID(holder);
        holder.post_id.setText(String.valueOf(post_id.get(position)));
        holder.setClickListener(holder);
        holder.onClickDeleteBtn(holder);
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_id, forum_id, date_id, post_id;
        Button deleteBtn, commentBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.txtUserName);
            forum_id = itemView.findViewById(R.id.txtUserPost);
            date_id = itemView.findViewById(R.id.txtUserPostDate);
            post_id = itemView.findViewById(R.id.txtpostID);
            commentBtn = itemView.findViewById(R.id.commentBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }

        @Override
        public void onClick(View v) {

        }

        public void setClickListener(MyViewHolder holder) {
            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the values of the clicked item
                    String username = user_id.getText().toString();
                    String post = forum_id.getText().toString();
                    String date = date_id.getText().toString();
                    String postID = post_id.getText().toString();
                    // Do something with the values, such as start a new activity
                    Intent intent = new Intent(context, PostDetail.class);
                    intent.putExtra("name", username);
                    intent.putExtra("post",post);
                    intent.putExtra("date",date);
                    intent.putExtra("postID",postID);
                    context.startActivity(intent);

                }
            });
        }

        public void isAdmin(MyViewHolder holder) {
            SharedPreferences preferences = context.getSharedPreferences("userLogData", Context.MODE_PRIVATE);
            String isUserAdmin = preferences.getString("isAdmin", null);
            if (!isUserAdmin.equals("1")) {
                deleteBtn.setVisibility(View.INVISIBLE);
//                hide the id too
            }
        }

        public void hideID(MyViewHolder holder){
            post_id.setVisibility(View.INVISIBLE);
        }

        public void onClickDeleteBtn(MyViewHolder holder){
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Post");
                    builder.setMessage("Are you sure you want to delete this post?");

// Set the positive button
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            deleting from the database
                            String postID = post_id.getText().toString();
                            db.deletePost(postID);
                            Toast.makeText(context, "Post deleted", Toast.LENGTH_SHORT).show();
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

