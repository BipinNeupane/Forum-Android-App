package com.example.as1bipinneupane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    EditText signFirstname, signLastname, signPhnumber, signEmail, signPassword, signRepass;
    CountryCodePicker signCountry;
    Button signCreateBtn;
    DBHelper Db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getByID();
        afterClick();

    }

    //    a function to get data from layout file
    private void getByID() {
        signFirstname = findViewById(R.id.signFirstname);
        signLastname = findViewById(R.id.signLastname);
        signPhnumber = findViewById(R.id.signPhnumber);
        signEmail = findViewById(R.id.signEmail);
        signPassword = findViewById(R.id.signPassword);
        signRepass = findViewById(R.id.signRepass);
        signCountry = findViewById(R.id.signCountry);
        signCreateBtn = findViewById(R.id.signCreateBtn);
    }

    //    creating another function which is done after
    private void afterClick() {
        signCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getting the input data from EditText
                String firstName = signFirstname.getText().toString();
                String lastName = signLastname.getText().toString();
                String phone = signPhnumber.getText().toString();
                String email = signEmail.getText().toString();
                String password = signPassword.getText().toString();
                String repass = signRepass.getText().toString();
                String country = signCountry.getSelectedCountryEnglishName();
                //        getting the current date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateRegistered = dateFormat.format(calendar.getTime());


//                if any fields are empty
                if (firstName.equals("") || lastName.equals("") || phone.equals("") || email.equals("") || password.equals("") || repass.equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

//              if password and re entered password do not match
                if (!password.equals(repass)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

//              check if email already exists
                Boolean checkEmail = Db.isEmailUnique(email);
                if (checkEmail) {
                    Toast.makeText(SignupActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

//              password and repass matched now using data insertion
                Boolean insert = Db.insertData(firstName, lastName, email, phone, password, country, dateRegistered, dateRegistered);
                if (insert) {
                    Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent toLoginActivity = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(toLoginActivity);
                } else {
                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

