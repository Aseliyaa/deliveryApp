package by.ruban.deliveryapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import by.ruban.deliveryapp.Models.User;
import by.ruban.deliveryapp.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_sign);

        FirebaseAuth auth;
        FirebaseDatabase db;
        DatabaseReference users;

        AppCompatButton registerBtn = findViewById(R.id.registerBtn);

        EditText email = findViewById(R.id.editTextTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        EditText personName = findViewById(R.id.editTextPersonName);
        EditText repeatPassword = findViewById(R.id.editTextRepeatPassword);
        ConstraintLayout root = findViewById(R.id.sign_root);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(personName.getText().toString())){
                    Toast.makeText(SignUp.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(SignUp.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length() < 6){
                    Toast.makeText(SignUp.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!repeatPassword.getText().toString().equals(password.getText().toString()) && password.getText().toString().length() >= 6){
                    Toast.makeText(SignUp.this, "Repeat your password correctly", Toast.LENGTH_SHORT).show();
                    return;
                }

                Query emailQuery = users.orderByChild("email").equalTo(email.getText().toString());
                emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(SignUp.this, "This email has an account already!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SignUp.this, "Error. " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setName(personName.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setBirthday("");
                                user.setPhone("");

                                users.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SignUp.this, "User is successfully added!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUp.this, IntroActivity.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUp.this, "Error. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

            }
        });
    }
}