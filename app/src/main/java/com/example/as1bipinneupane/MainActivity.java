package com.example.as1bipinneupane;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassowrd;
    Button btnLogin;
    TextView signup;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getById();
        afterSubmit();

        signup = findViewById(R.id.txtSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getById() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassowrd = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void afterSubmit() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassowrd.getText().toString();

//                if email and password field are empty
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

//                authentication
                boolean loginAuthenticate = db.checkEmailPassword(email, password);

//                if email and password do not match
                if (!loginAuthenticate) {
                    Toast.makeText(MainActivity.this, "No users found", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent toGeneralTimeline = new Intent(MainActivity.this, FragmentBinder.class);
                    startActivity(toGeneralTimeline);
                    finish();


//                getting the logged in user data and storing it to shared pref
                    Cursor cursor = db.getUserDataByEmail(email);
                    if (cursor.moveToFirst()) {
                        int columnIndexofFirstname = cursor.getColumnIndex("firstName");
                        int columnIndexoflastName = cursor.getColumnIndex("lastName");
                        int columnIndexofCountry = cursor.getColumnIndex("country");
                        int columnIndexofPhone = cursor.getColumnIndex("phone");
                        int columnIndexofEmail = cursor.getColumnIndex("email");
                        int columnIndexofAdmin = cursor.getColumnIndex("isAdmin");
                        int columnIndexofDateUpdated = cursor.getColumnIndex("dateUpdated");
                        Log.d("columnIndex", "The column Index is :" + columnIndexofEmail);
                        String loggedFirstName = cursor.getString(columnIndexofFirstname);
                        String loggedLastName = cursor.getString(columnIndexoflastName);
                        String loggedCountry = cursor.getString(columnIndexofCountry);
                        String loggedPhone = cursor.getString(columnIndexofPhone);
                        String loggedEmail = cursor.getString(columnIndexofEmail);
                        String isAdmin = cursor.getString(columnIndexofAdmin);
                        String dateUpdated = cursor.getString(columnIndexofDateUpdated);
                        Log.d("EmailID", "Email is : " + loggedEmail);
//                        getting the user id
                        String userID = db.getUserIdByEmail(loggedEmail);


                        SharedPreferences preferences = getSharedPreferences("userLogData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userID", userID);
                        editor.putString("isAdmin", isAdmin);
                        editor.putString("firstName", loggedFirstName);
                        editor.putString("lastName", loggedLastName);
                        editor.putString("country", loggedCountry);
                        editor.putString("phone", loggedPhone);
                        editor.putString("email", loggedEmail);
                        editor.putString("dateUpdated",dateUpdated);
                        editor.commit();
                    }
                }
            }
        });
    }
}