package com.example.as1bipinneupane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateProfile extends AppCompatActivity {

    private EditText mfirstname,mlastname,mcountry,mphone,memail;
    private Button saveProfileBtn;
    DBHelper db = new DBHelper(this);
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        preferences = getSharedPreferences("userLogData", Context.MODE_PRIVATE);
        getByID();
        setText(mfirstname,mlastname,mcountry,mphone,memail);
        afterSavebtnClick();

    }


    private void getByID(){
        mfirstname = findViewById(R.id.edtFirstName);
        mlastname = findViewById(R.id.edtLastName);
        mcountry = findViewById(R.id.edtCountry);
        mphone = findViewById(R.id.edtPhonenumber);
        memail = findViewById(R.id.edtProfileEmail);
        saveProfileBtn = findViewById(R.id.saveChangesBtn);
    }

    private void setText(TextView firstname,TextView lastname,TextView country,TextView phone,TextView email){
        firstname.setText(preferences.getString("firstName",null));
        lastname.setText(preferences.getString("lastName",null));
        country.setText(preferences.getString("country",null));
        phone.setText(preferences.getString("phone",null));
        email.setText(preferences.getString("email",null));
    }


    private void afterSavebtnClick(){
    saveProfileBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //getiing inputs after edit
            String firstName = mfirstname.getText().toString();
            String lastName = mlastname.getText().toString();
            String country = mcountry.getText().toString();
            String phone = mphone.getText().toString();
            String email = memail.getText().toString();

            //        getting the current date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateUpdated = dateFormat.format(calendar.getTime());


//            updating the value in sharedpreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firstName", firstName);
            editor.putString("lastName", lastName);
            editor.putString("country", country);
            editor.putString("phone", phone);
            editor.putString("email", email);
            editor.putString("dateUpdated", dateUpdated);
            editor.commit();



//          update data
            String loggedEmail = preferences.getString("email",null);
            String userID = db.getUserIdByEmail(loggedEmail);
            Log.d("userID", "user id is : " + userID);
            db.updateUserData(userID,firstName,lastName,email,phone,country,dateUpdated);
            Toast.makeText(UpdateProfile.this, "Data updated", Toast.LENGTH_SHORT).show();


        }
    });
    }



}