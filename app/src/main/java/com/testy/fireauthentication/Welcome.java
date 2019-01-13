package com.testy.fireauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity {

    Button btnSignOut;
    TextView txtWelcome;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnSignOut = findViewById(R.id.btnSignOut);
        txtWelcome = findViewById(R.id.txtWelcome);

        //Always get a instance of the firebaseauth to initialize it.
        mAuth = FirebaseAuth.getInstance();

        //Cross Check if the current user is null or it is a user, if null then send him to the MainActivity.class
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(Welcome.this,MainActivity.class);
            startActivity(intent);

            //To close current Activity
            finish();
        }
        else{
            //Else show him a message that he is successfully signed in
            txtWelcome.setText("How are you "+mAuth.getCurrentUser().getDisplayName());

        }

        //Sign out the user and send him to the MainActivity.java file using the intent
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(Welcome.this,MainActivity.class);
                startActivity(intent);

                //To close current Activity
                finish();
            }
        });
    }
}
