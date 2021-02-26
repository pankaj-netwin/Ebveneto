package com.ebveneto.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.utils.AppSession;
import com.ebveneto.widgets.CustomDialogManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Asmita on 03-01-2017.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.container);
        viewGroup.removeAllViews();
        View view = getLayoutInflater().inflate(layoutResID, null);
        viewGroup.addView(view);
        initialise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_services:
                Intent add_new_services_intent = new Intent(BaseActivity.this, AddNewServicesActivity.class);
                startActivity(add_new_services_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                break;

            case R.id.my_profile :
                Intent my_profile_intent = new Intent(BaseActivity.this, MyProfileActivity.class);
                startActivity(my_profile_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                break;

            case R.id.logout:
               CustomDialogManager.showOkCancelDialog(BaseActivity.this, getString(R.string.logout_message), getString(R.string.yes), getString(R.string.no), new DialogListener() {
                   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                   @Override
                   public void onButtonClicked(int type) {
                       if (Dialog.BUTTON_POSITIVE == type) {
                           AppSession.getInstance().saveLoginStatus(BaseActivity.this, false);
                           Intent login_intent = new Intent(BaseActivity.this, LoginActivity.class);
                           finishAffinity();
                           startActivity(login_intent);
                       } else {
                       }

                   }
               });
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialise() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View view) {

    }
}
