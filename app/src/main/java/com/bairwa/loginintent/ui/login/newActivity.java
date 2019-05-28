package com.bairwa.loginintent.ui.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bairwa.loginintent.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class newActivity extends AppCompatActivity {
Button btn;
FirebaseAuth mAuth;
ImageView profile;
TextView mName;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onStart() {
        super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        btn=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        profile=findViewById(R.id.imageView);
        mName=findViewById(R.id.textView);
        TextView emailsent=findViewById(R.id.textView2);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(newActivity.this, LoginActivity.class));
                }

            }
        };

        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userName=user.getDisplayName();
        mName.setText(userName);
        Glide.with(newActivity.this).load(user.getPhotoUrl()).into(profile);
        if (user.isEmailVerified()){
            emailsent.setText("Email is verified");
            emailsent.setClickable(false);

        }else{
            emailsent.setText("(click Here for email verify)");
        }
        emailsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sendEmailVerification();
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(newActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
