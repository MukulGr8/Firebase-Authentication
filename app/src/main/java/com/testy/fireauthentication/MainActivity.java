package com.testy.fireauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    TextView txtName,txtEmail,txtPassword;
    EditText edtName,edtEmail,edtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        //If a user is signed in from first only then we will directly take him to the welcome.java activity using intent
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this,Welcome.class);
            startActivity(intent);
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = edtEmail.getText().toString();
                String userPass = edtPassword.getText().toString();
                signUpUser(userEmail,userPass);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = edtEmail.getText().toString();
                String userPass = edtPassword.getText().toString();
                signInUser(userEmail,userPass);
            }
        });

    }

    private void signUpUser(String userEmail,String userPassword){
        //When ever use sign up then this method will be called
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"You Are Successfuly Signed Up ",Toast.LENGTH_LONG).show();
                    specifyUserProflie();
                }
            }
        });
    }
    private void specifyUserProflie(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            //This is for setting the name of the user so that it can be later retrived to the welcome activity with the getter method.
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edtName.getText().toString()).build();
            //Updating the profile of the user
            firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                    }else{

                    }
                }
            });
        }
    }

    private void signInUser(String email,String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"There Was a Error",Toast.LENGTH_LONG).show();
                }else{
                    //if the user signed in successfully then show a toast and send them to a new welcome activity
                    Toast.makeText(getApplicationContext(),"Successfully Signed In",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,Welcome.class);
                    startActivity(intent);

                    //To close the current Activity
                    finish();
                }
            }
        });
    }
}
