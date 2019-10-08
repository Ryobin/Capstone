package capstone.sda.com.literatures.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import capstone.sda.com.literatures.MainActivity;
import capstone.sda.com.literatures.R;
import capstone.sda.com.literatures.Utils.SessionHandler;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;

    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        logo = findViewById(R.id.imageView);
        session = new SessionHandler(getApplicationContext());

            final Intent intent = new Intent(this, MainActivity.class);

            Thread timer = new Thread() {
                public void run() {

                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        startActivity(intent);
                        finish();
                    }
                }
            };

            timer.start();

    }

/*    protected void onStart() {
        super.onStart();

        if(session.isLoggedIn()){
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }*/
/*
        if(session.isLoggedIn()){
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);

        }
        */

 //       FirebaseUser user = mAuth.getCurrentUser();

   //     if(user != null) {
            //user is already connected  so we need to redirect him to home page
        //}
    }

//}
