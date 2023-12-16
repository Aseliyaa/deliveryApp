package by.ruban.deliveryapp.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.ruban.deliveryapp.R;

public class Profile extends AppCompatActivity {
    TextView phone;
    TextView userName;
    TextView birthday;
    TextView email;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phone = findViewById(R.id.phone);
        userName = findViewById(R.id.userName);
        birthday = findViewById(R.id.birthdayText);
        email = findViewById(R.id.emailText);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        ConstraintLayout root = findViewById(R.id.profile_root);
        setInfo(userRef);
        btnManager(root);
        hideCursor();
    }

    private void hideCursor() {
        View view = findViewById(R.id.hide);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.clearFocus();
                return true;
            }
        });
    }
    private void btnManager(ConstraintLayout root) {
        Button logOutBtn = findViewById(R.id.logOutButton);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout settingsBtn = findViewById(R.id.settingsBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,CartActivity.class));
            }
        });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, IntroActivity.class));
            }
        });

        EditText phone = findViewById(R.id.phone);
        String regex = "\\+375\\d{9}";
        Pattern pattern = Pattern.compile(regex);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPhone = phone.getText().toString();
                phone.setText(currentPhone);
                phone.setSelection(currentPhone.length());
                phone.setFocusableInTouchMode(true);
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newPhone = phone.getText().toString();
                    Matcher matcher = pattern.matcher(newPhone);
                    if (matcher.matches()) {
                        userRef.child("phone").setValue(newPhone);
                    } else {
                        Snackbar.make(root, "Incorrect number!", Snackbar.LENGTH_SHORT).show();
                        userRef.child("phone").setValue("");
                        phone.setText("");
                    }
                }
            }
        });

        EditText birthdayText = findViewById(R.id.birthdayText);

        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Profile.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String birthdate = String.format(Locale.getDefault(), "%02d.%02d.%04d", dayOfMonth, month + 1, year);
                                birthdayText.setText(birthdate);
                                userRef.child("birthday").setValue(birthdate);
                            }
                        },
                        year,
                        month,
                        dayOfMonth);
                datePickerDialog.show();
            }
        });
        birthdayText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newBirthdate = birthdayText.getText().toString();
                    userRef.child("birthday").setValue(newBirthdate);
                }
            }
        });
    }

    private void setInfo(DatabaseReference userRef) {
        userRef.child("name").get().addOnSuccessListener(dataSnapshot -> {
            String nameStr = dataSnapshot.getValue(String.class);
            userName.setText(nameStr);
        }).addOnFailureListener(e -> {
            userName.setText("");
        });

        userRef.child("email").get().addOnSuccessListener(dataSnapshot -> {
            String emailStr = dataSnapshot.getValue(String.class);
            email.setText(emailStr);
        }).addOnFailureListener(e -> {
            email.setText("");
        });

        userRef.child("phone").get().addOnSuccessListener(dataSnapshot -> {
            String phoneStr = dataSnapshot.getValue(String.class);
            phone.setText(phoneStr);
        }).addOnFailureListener(e -> {
            phone.setText("");
        });

        userRef.child("birthday").get().addOnSuccessListener(dataSnapshot -> {
            String birthdayStr = dataSnapshot.getValue(String.class);
            birthday.setText(birthdayStr);
        }).addOnFailureListener(e -> {
            birthday.setText("");
        });


    }
}