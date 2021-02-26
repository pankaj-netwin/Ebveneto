package com.ebveneto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.ebveneto.R;
import com.ebveneto.utils.AppSession;
import com.ebveneto.widgets.Helper;

/**
 * Created by Asmita on 03-01-2017.
 */

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSplash();
                Helper.copyAssets(SplashActivity.this);
                Helper.copyReadmeFile(SplashActivity.this);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void startSplash() {
       if (AppSession.getInstance().getLoginStatus(SplashActivity.this)){
            if(AppSession.getInstance().getTable(SplashActivity.this).equals("Dipendenti")){
                Intent welcome_intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(welcome_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            }
           else {
                Intent login_intent = new Intent(SplashActivity.this, UserSelectionActivity.class);
                startActivity(login_intent);
            }

        }else {
            Intent login_intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(login_intent);
        }
    }
}
