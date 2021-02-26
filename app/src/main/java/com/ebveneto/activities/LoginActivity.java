package com.ebveneto.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.Location;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class LoginActivity extends Activity implements View.OnClickListener {

    Button btnLogin, btnRegister;
    EditText etEmail, etPassword;
    CheckBox privacyCheckBox;
    TextView tvForgotPassword,tvPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();

        appUpdateAvailble(LoginActivity.this);
    }
    public void appUpateDialog(final float minVersionNamePlayStore) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.custom_app_update_dialog);
        dialog.setCancelable(true);

        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        TextView txtUpdateDesc = (TextView) dialog.findViewById(R.id.txtUpdateDesc);
        TextView txtSkipVersion = (TextView) dialog.findViewById(R.id.txtSkipVersion);

        txtUpdateDesc.setText("E' disponibile l'aggiornamento "+minVersionNamePlayStore+" per il download.\n" +
                "Scaricando l'ultimo aggiornamento otterrai le ultime funzionalità, miglioramenti e correzioni di bug");

        Button btnSkip = (Button) dialog.findViewById(R.id.btnSkip);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
        // if button is clicked, close the custom dialog
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txtSkipVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSession.getInstance().saveSkipFlag(LoginActivity.this, "true");
                AppSession.getInstance().saveSkipVersion(LoginActivity.this, String.valueOf(minVersionNamePlayStore));
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.ebveneto"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    private void appUpdateAvailble(Context context) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://www.ebvenetofvg.it/web_services")
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.updateAvailable(
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                JSONObject ebvenetoData = jsonData.getJSONObject("com.ebveneto");
                                float minVersionNamePlayStore = Float.parseFloat((ebvenetoData.getString("minVersionName")));
//                                float minVersionNamePlayStore = (float) 2.5;

                                float minVersionNameLocal = 0;
                                try {
                                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                    minVersionNameLocal = Float.parseFloat((pInfo.versionName));

                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }

                                if (minVersionNamePlayStore > minVersionNameLocal) {
                                    Log.d("***MSG", +minVersionNamePlayStore + "" + minVersionNameLocal);
                                    String SkipFlag = AppSession.getInstance().getSkipFlag(getApplicationContext());
                                    float SkipVersion = Float.parseFloat(AppSession.getInstance().getSkipVersion(getApplicationContext()));
                                    if (SkipFlag.equals("true")) {
                                        if (minVersionNamePlayStore == SkipVersion) {
                                            mDialog.dismiss();
                                        } else {
                                            appUpateDialog(minVersionNamePlayStore);
                                            Log.d("***MSG", "****no update availble");
                                        }
                                    } else {
                                        appUpateDialog(minVersionNamePlayStore);
                                        Log.d("***MSG", "****no update availble");
                                    }
                                } else {
                                    Log.d("***MSG", "****no update availble");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mDialog.dismiss();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            mDialog.dismiss();
                        }
                    });
        } else {
            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }

    private void initialise() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        privacyCheckBox = (CheckBox) findViewById(R.id.privacyCheckBox);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);
        tvPrivacyPolicy = (TextView) findViewById(R.id.tvPrivacyPolicy);
        tvPrivacyPolicy.setOnClickListener(this);
        etEmail.setText(AppSession.getInstance().getEmail(LoginActivity.this));
        etPassword.setText(AppSession.getInstance().getPassword(LoginActivity.this));
       // etEmail.setText("m.palazzo@ebveneto.it");
        //etEmail.setText("serena.ruzzene@confve.it");
      //  etPassword.setText("Ar!cuFUrerVo");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                validate();
                break;

            case R.id.btnRegister:
                String url = "http://www.ebveneto.it/a_ITA_183_1.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.tvPrivacyPolicy:
                showPrivacyPolicyDialog(this);
                break;
        }
    }

    private void validate() {

        //Added by sanket on 13July2018
        if (!privacyCheckBox.isChecked())
        {
            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.privacy_not_provided), new DialogListener() {
            @Override
            public void onButtonClicked(int type) {

            }
        });
            privacyCheckBox.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.please_enter_email), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.please_enter_password), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etPassword.requestFocus();
            return;
        }
        doLogin(LoginActivity.this, etEmail.getText().toString(), etPassword.getText().toString());
    }
    //Webservice implementation for Login
    private void doLogin(Context context, final String email, final String password) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.doLogin(new TypedString(email),
                    new TypedString(password),
                    new TypedString("doLogin"),
                    new TypedString("1"),
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    Helper.Log("URL", "response : ==>  " + jsonObject);
                                    AppSession.getInstance().saveEmail(LoginActivity.this, email);
                                    AppSession.getInstance().savePasword(LoginActivity.this, password);
                                    AppSession.getInstance().saveLoginStatus(LoginActivity.this, true);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONObject dataObject = jsondataObject.getJSONObject("data");

                                    AppSession.getInstance().saveUserNome(LoginActivity.this, dataObject.optString("Nome"));
                                    AppSession.getInstance().saveUserCognome(LoginActivity.this, dataObject.optString("Cognome"));

                                    AppSession.getInstance().saveUserId(LoginActivity.this, dataObject.optString("idDIP"));
                                    AppSession.getInstance().saveTable(LoginActivity.this, dataObject.optString("_tabella_"));
                                    AppSession.getInstance().saveBudget(LoginActivity.this, dataObject.optString("budgetDisp"));
                                    AppSession.getInstance().saveSomma(LoginActivity.this, dataObject.optString("sommaRichieste"));

                                    switch (dataObject.optString("_tabella_")) {
                                       /* case "Cdl":
                                            AppSession.getInstance().saveUserId(UserSelectionActivity.this, dataObject.optString("idCdL"));
                                            AppSession.getInstance().saveUserNome(UserSelectionActivity.this, dataObject.optString("Ragione"));
                                            AppSession.getInstance().saveTable(UserSelectionActivity.this, dataObject.optString("_tabella_"));
                                            break;*/
                                            case "Aziende":
                                            AppSession.getInstance().saveUserId(LoginActivity.this, dataObject.optString("idAZ"));
                                            AppSession.getInstance().saveUserNome(LoginActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(LoginActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveTable(LoginActivity.this, dataObject.optString("_tabella_"));
                                            AppSession.getInstance().saveCompanyName(LoginActivity.this, dataObject.optString("Ragione"));
                                            AppSession.getInstance().savePiva(LoginActivity.this, dataObject.optString("piva"));
                                            if(TextUtils.equals(dataObject.optString("legalerap"),"null")){
                                                AppSession.getInstance().saveFIELD_LEGALE_RAPPRESENTANTE(LoginActivity.this, "");

                                            }else {
                                                AppSession.getInstance().saveFIELD_LEGALE_RAPPRESENTANTE(LoginActivity.this, dataObject.optString("legalerap"));
                                            }
                                            break;
                                        case "Dipendenti":
                                            AppSession.getInstance().saveUserNome(LoginActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(LoginActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveUserId(LoginActivity.this, dataObject.optString("idDIP"));
                                            AppSession.getInstance().saveTable(LoginActivity.this, dataObject.optString("_tabella_"));
                                            break;
                                        default:
                                            AppSession.getInstance().saveUserNome(LoginActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(LoginActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveUserId(LoginActivity.this, dataObject.optString("idDIP"));
                                            AppSession.getInstance().saveTable(LoginActivity.this, dataObject.optString("_tabella_"));
                                            break;
                                    }

                                    if(TextUtils.equals(dataObject.optString("cf"),"null")){
                                        AppSession.getInstance().saveCF(LoginActivity.this, "");
                                    }else{
                                        AppSession.getInstance().saveCF(LoginActivity.this, dataObject.optString("cf"));
                                    }

                                    if(TextUtils.equals(dataObject.optString("dtNascita"),"null")){
                                        AppSession.getInstance().saveDateNascita(LoginActivity.this, "");
                                    }else{
                                        AppSession.getInstance().saveDateNascita(LoginActivity.this, dataObject.optString("dtNascita"));
                                    }


                                    if (!dataObject.optString("Via").equals("null")) {
                                        AppSession.getInstance().saveVia(LoginActivity.this, dataObject.optString("Via"));
                                    }

                                    if (!dataObject.optString("Cap").equals("null") &&!dataObject.optString("Comune").equals("null") && !dataObject.optString("Frazione").equals("null")) {
                                        String citta =  dataObject.optString("Cap") + " " + dataObject.optString("Comune") + " " + dataObject.optString("Frazione");
                                        AppSession.getInstance().saveCitta(LoginActivity.this, citta);

                                    }

                                    if (!dataObject.optString("Prov").equals("null")) {
                                        AppSession.getInstance().saveProvincia(LoginActivity.this, dataObject.optString("Prov"));
                                    }

                                    if (dataObject.optString("ebv_versamenti").equals("N")) {
                                        AppSession.getInstance().saveEbv_versamenti(LoginActivity.this, getString(R.string.no));
                                    } else {
                                        AppSession.getInstance().saveEbv_versamenti(LoginActivity.this, getString(R.string.yes));
                                    }

                                    if (jsonObject.optString("is_multiple").equals("true")) {
                                        AppSession.getInstance().saveLoginPolicyStatus(LoginActivity.this,true); //terms and condition accept by user so we have to set true
                                        Intent my_profile_intent = new Intent(LoginActivity.this, UserSelectionActivity.class);
                                        startActivity(my_profile_intent);
                                        overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                    } else {
                                     //
                                        if(TextUtils.equals(AppSession.getInstance().getCompanyName(LoginActivity.this),"")){
                                            JSONArray empArray = dataObject.optJSONArray("employment");
                                            String company = "";
                                           company = empArray.optString(0);
                                            AppSession.getInstance().saveCompanyName(LoginActivity.this, company);

                                        }else {

                                        }
                                        AppSession.getInstance().saveLoginPolicyStatus(LoginActivity.this,true);//terms and condition accept by user so we have to set true
                                        Intent my_profile_intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                                        startActivity(my_profile_intent);
                                        overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                    }

                                } else {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(LoginActivity.this, jsonObject.optString("message"), new DialogListener() {
                                        @Override
                                        public void onButtonClicked(int type) {

                                        }
                                    });

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            mDialog.dismiss();
                            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.oops), new DialogListener() {
                                @Override
                                public void onButtonClicked(int type) {

                                }
                            });
                        }
                    });
        } else {
            CustomDialogManager.showOkDialog(LoginActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        CustomDialogManager.showOkCancelDialog(this, getString(R.string.do_you_want_to_exit_application), getString(R.string.yes), getString(R.string.no), new DialogListener() {
            @Override
            public void onButtonClicked(int type) {
                if (Dialog.BUTTON_POSITIVE == type) {
                    finishAffinity();
                }
            }
        });
    }

    public static void showPrivacyPolicyDialog(final Context context) {

        final Dialog[] mDialog = new Dialog[1];
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog privacyPolicyalog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_privacy_policy, null);
        build.setView(view);
        privacyPolicyalog = build.create();
        privacyPolicyalog.setCancelable(false);
        //privacyPolicyalog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final WebView wvPrivacyPolicy = (WebView) view.findViewById(R.id.wvPrivacyPolicy);
        wvPrivacyPolicy.getSettings().setBuiltInZoomControls(true);
        wvPrivacyPolicy.setVerticalScrollBarEnabled(false);
        wvPrivacyPolicy.setHorizontalScrollBarEnabled(false);
        wvPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
        //To show a progress dialog while webpage is loading
        wvPrivacyPolicy.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
                mDialog[0] = new ProgressDialog(context);
                mDialog[0].setTitle("Accesso");
                mDialog[0].setCancelable(true);
                mDialog[0].show();
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO hide your progress image
                super.onPageFinished(view, url);
                mDialog[0].dismiss();
            }
        });
        wvPrivacyPolicy.loadUrl(HttpHelper.PRIVACY_POLICY_URL);
        final Button btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                privacyPolicyalog.dismiss();//close the dialog

            }

        });

        privacyPolicyalog.show();
    }
}
