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
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import by.ruban.deliveryapp.R;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        FirebaseAuth auth;
        ConstraintLayout root = findViewById(R.id.login_root);

        auth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.editTextTextEmail);
        EditText password = findViewById(R.id.editTextTextPassword);

        AppCompatButton loginBtn = findViewById(R.id.logInBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(LogIn.this, "Enter your emai", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(LogIn.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.fetchSignInMethodsForEmail(emailStr)
                        .addOnSuccessListener(new OnSuccessListener<SignInMethodQueryResult>() {
                            @Override
                            public void onSuccess(SignInMethodQueryResult signInMethodQueryResult) {
                                List<String> signInMethods = signInMethodQueryResult.getSignInMethods();
                                if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {

                                    auth.signInWithEmailAndPassword(emailStr, passwordStr)
                                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make(root, "Authorization failure. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(LogIn.this, "User does not exist. Please sign up first.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogIn.this, "Failed to check if user exists. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}