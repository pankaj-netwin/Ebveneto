package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePasswordActivity extends BaseActivity
{

    private EditText etExistingPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private LinearLayout llChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initialization();
    }

    private void initialization()
    {
        etExistingPassword = (EditText) findViewById(R.id.etExistingPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        llChangePassword = (LinearLayout) findViewById(R.id.llChangePassword);
        llChangePassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate();
            }
        });

    }

    private void validate() {
        if (TextUtils.isEmpty(etExistingPassword.getText().toString())) {
            CustomDialogManager.showOkDialog(ChangePasswordActivity.this, getString(R.string.please_enter_existing_password), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etExistingPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
            CustomDialogManager.showOkDialog(ChangePasswordActivity.this, getString(R.string.please_enter_new_password), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
            CustomDialogManager.showOkDialog(ChangePasswordActivity.this, getString(R.string.please_enter_confirm_password), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etConfirmPassword.requestFocus();
            return;
        }
        if (!TextUtils.equals(etNewPassword.getText().toString(), etConfirmPassword.getText().toString())) {
            CustomDialogManager.showOkDialog(ChangePasswordActivity.this, getString(R.string.please_enter_new_password), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etConfirmPassword.requestFocus();
            return;
        }
        changePassword(ChangePasswordActivity.this);

    }

    //Webservice implementation for Login
    private void changePassword(Context context)
    {

        if (HttpHelper.isNetworkAvailable(context))
        {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL_VERSAMENTI)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.changePassword("changePassword",
                    AppSession.getInstance().getTable(ChangePasswordActivity.this),
                    etExistingPassword.getText().toString(),
                    etNewPassword.getText().toString(),
                    etConfirmPassword.getText().toString(),
                    AppSession.getInstance().getUserId(ChangePasswordActivity.this),//user id
                    new Callback<JsonObject>()
                    {
                        @Override
                        public void success(JsonObject responseObject, Response response)
                        {
                            mDialog.dismiss();
                            try
                            {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true"))
                                {
                                    CustomDialogManager.showOkDialog(ChangePasswordActivity.this, jsonObject.optString("message"), new DialogListener()
                                    {
                                        @Override
                                        public void onButtonClicked(int type)
                                        {
                                            etExistingPassword.setText("");
                                            etNewPassword.setText("");
                                            etConfirmPassword.setText("");
                                        }
                                    });


                                } else
                                {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(ChangePasswordActivity.this, jsonObject.optString("message"), new DialogListener()
                                    {
                                        @Override
                                        public void onButtonClicked(int type)
                                        {
                                            etExistingPassword.setText("");
                                            etNewPassword.setText("");
                                            etConfirmPassword.setText("");
                                        }
                                    });

                                }

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error)
                        {
                            mDialog.dismiss();
                        }
                    });
        } else
        {
            CustomDialogManager.showOkDialog(ChangePasswordActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }
}
