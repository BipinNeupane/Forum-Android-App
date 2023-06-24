package com.example.as1bipinneupane;

import android.content.Context;
import android.content.DialogInterface;
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

public class AdminControlAdapter extends RecyclerView.Adapter<AdminControlAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> firstName,lastName,email,userID;
    DBHelper db;

//    creating a constructor
    public AdminControlAdapter(Context context, ArrayList<String> firstName, ArrayList<String> lastName, ArrayList<String> email,ArrayList<String> userID,DBHelper db){
        this.context = context;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userID = userID;
        this.db = db;
    }

    public AdminControlAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        to show how data will be displayed
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlist, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminControlAdapter.MyViewHolder holder, int position) {
        holder.mtxtfirstName.setText(String.valueOf(firstName.get(position)));
        holder.mtxtlastName.setText(String.valueOf(lastName.get(position)));
        holder.mtxtEmail.setText(String.valueOf(email.get(position)));
        holder.mtxtuserID.setText(String.valueOf(userID.get(position)));
        holder.deleteUser();

    }

    @Override
    public int getItemCount() {
        return firstName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mtxtfirstName,mtxtlastName,mtxtEmail,mtxtuserID;
        Button deleteUserBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtxtfirstName = itemView.findViewById(R.id.txtFirstName);
            mtxtlastName = itemView.findViewById(R.id.txtLastName);
            mtxtEmail = itemView.findViewById(R.id.txtEmailAdd);
            mtxtuserID = itemView.findViewById(R.id.txtUserID);
            deleteUserBtn = itemView.findViewById(R.id.deleteUserBtn);
        }

        public void deleteUser(){
            deleteUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete User");
                    builder.setMessage("Are you sure you want to delete this user?");

// Set the positive button
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            deleting from the database
                            String userID = mtxtuserID.getText().toString();
                            db.deleteUser(userID);
                            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
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
