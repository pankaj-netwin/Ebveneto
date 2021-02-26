package com.ebveneto.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class UserSelectionActivity extends Activity implements View.OnClickListener {
    LinearLayout btnEmployees;
    LinearLayout btnCompanies;
    Button btnConsultant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);
        initialise();
        appUpdateAvailble(UserSelectionActivity.this);

    }

    public void appUpateDialog(final float minVersionNamePlayStore) {
        final Dialog dialog = new Dialog(UserSelectionActivity.this);
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
                AppSession.getInstance().saveSkipFlag(UserSelectionActivity.this, "true");
                AppSession.getInstance().saveSkipVersion(UserSelectionActivity.this, String.valueOf(minVersionNamePlayStore));
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
                                            Log.d("***MSG", "1");
                                        }
                                    } else {
                                        appUpateDialog(minVersionNamePlayStore);
                                        Log.d("***MSG", "2");
                                    }
                                } else {
                                    Log.d("***MSG", "3");
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
            CustomDialogManager.showOkDialog(UserSelectionActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }

    private void initialise() {
        btnEmployees = (LinearLayout) findViewById(R.id.btnEmployees);
        btnEmployees.setOnClickListener(this);

        btnCompanies = (LinearLayout) findViewById(R.id.btnCompanies);
        btnCompanies.setOnClickListener(this);

        /*btnConsultant = (Button) findViewById(R.id.btnConsultant);
        btnConsultant.setOnClickListener(this);*/
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEmployees:
                sendUserDetails(UserSelectionActivity.this, AppSession.getInstance().getEmail(UserSelectionActivity.this), AppSession.getInstance().getPassword(UserSelectionActivity.this), "Dipendenti");
                break;

            case R.id.btnCompanies:
                sendUserDetails(UserSelectionActivity.this, AppSession.getInstance().getEmail(UserSelectionActivity.this), AppSession.getInstance().getPassword(UserSelectionActivity.this), "Aziende");
                break;

          /*  case R.id.btnConsultant:
                sendUserDetails(UserSelectionActivity.this, AppSession.getInstance().getEmail(UserSelectionActivity.this), AppSession.getInstance().getPassword(UserSelectionActivity.this), "Cdl");
                break;*/
        }
    }

    //Webservice implementation to send user type
    private void sendUserDetails(final Context context, final String email, final String password, final String userType) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getUserType(new TypedString(email),
                    new TypedString(password),
                    new TypedString("doLogin"),
                    new TypedString("1"),
                    new TypedString(userType),
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    Helper.Log("URL", "response : ==>  " + jsonObject);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONObject dataObject = jsondataObject.getJSONObject("data");
                                    AppSession.getInstance().saveUserType(UserSelectionActivity.this, userType);
                                    AppSession.getInstance().saveBudget(UserSelectionActivity.this, dataObject.optString("budgetDisp"));
                                    AppSession.getInstance().saveSomma(UserSelectionActivity.this, dataObject.optString("sommaRichieste"));

                                    switch (userType) {
                                       /* case "Cdl":
                                            AppSession.getInstance().saveUserId(UserSelectionActivity.this, dataObject.optString("idCdL"));
                                            AppSession.getInstance().saveUserNome(UserSelectionActivity.this, dataObject.optString("Ragione"));
                                            AppSession.getInstance().saveTable(UserSelectionActivity.this, dataObject.optString("_tabella_"));
                                            break;*/
                                        case "Aziende":
                                            AppSession.getInstance().saveUserId(UserSelectionActivity.this, dataObject.optString("idAZ"));
                                            AppSession.getInstance().saveUserNome(UserSelectionActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(UserSelectionActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveTable(UserSelectionActivity.this, dataObject.optString("_tabella_"));
                                            AppSession.getInstance().saveCompanyName(UserSelectionActivity.this, dataObject.optString("Ragione"));
                                            AppSession.getInstance().savePiva(UserSelectionActivity.this, dataObject.optString("piva"));
                                            if (TextUtils.equals(dataObject.optString("legalerap"), "null")) {
                                                AppSession.getInstance().saveFIELD_LEGALE_RAPPRESENTANTE(UserSelectionActivity.this, "");

                                            } else {
                                                AppSession.getInstance().saveFIELD_LEGALE_RAPPRESENTANTE(UserSelectionActivity.this, dataObject.optString("legalerap"));
                                            }
                                            break;
                                        case "Dipendenti":

                                            AppSession.getInstance().saveUserNome(UserSelectionActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(UserSelectionActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveUserId(UserSelectionActivity.this, dataObject.optString("idDIP"));
                                            AppSession.getInstance().saveTable(UserSelectionActivity.this, dataObject.optString("_tabella_"));
                                            AppSession.getInstance().saveCompanyName(UserSelectionActivity.this, dataObject.optString("updUSER"));
                                            break;
                                        default:
                                            AppSession.getInstance().saveUserNome(UserSelectionActivity.this, dataObject.optString("Nome"));
                                            AppSession.getInstance().saveUserCognome(UserSelectionActivity.this, dataObject.optString("Cognome"));
                                            AppSession.getInstance().saveUserId(UserSelectionActivity.this, dataObject.optString("idDIP"));
                                            AppSession.getInstance().saveTable(UserSelectionActivity.this, dataObject.optString("_tabella_"));
                                            break;
                                    }

                                    if (TextUtils.equals(dataObject.optString("cf"), "null")) {
                                        AppSession.getInstance().saveCF(UserSelectionActivity.this, "");
                                    } else {
                                        AppSession.getInstance().saveCF(UserSelectionActivity.this, dataObject.optString("cf"));
                                    }

                                    if (TextUtils.equals(dataObject.optString("dtNascita"), "null")) {
                                        AppSession.getInstance().saveDateNascita(UserSelectionActivity.this, "");
                                    } else {
                                        AppSession.getInstance().saveDateNascita(UserSelectionActivity.this, dataObject.optString("dtNascita"));
                                    }


                                    if (!dataObject.optString("Via").equals("null")) {
                                        AppSession.getInstance().saveVia(UserSelectionActivity.this, dataObject.optString("Via"));
                                    }

                                    if (!dataObject.optString("Cap").equals("null") && !dataObject.optString("Comune").equals("null") && !dataObject.optString("Frazione").equals("null")) {
                                        String citta = dataObject.optString("Cap") + " " + dataObject.optString("Comune") + " " + dataObject.optString("Frazione");
                                        AppSession.getInstance().saveCitta(UserSelectionActivity.this, citta);

                                    }

                                    if (!dataObject.optString("Prov").equals("null")) {
                                        AppSession.getInstance().saveProvincia(UserSelectionActivity.this, dataObject.optString("Prov"));
                                    }

                                    if (dataObject.optString("ebv_versamenti").equals("N")) {
                                        AppSession.getInstance().saveEbv_versamenti(UserSelectionActivity.this, getString(R.string.no));
                                    } else {
                                        AppSession.getInstance().saveEbv_versamenti(UserSelectionActivity.this, getString(R.string.yes));
                                    }


                                    Intent my_profile_intent = new Intent(UserSelectionActivity.this, WelcomeActivity.class);
                                    startActivity(my_profile_intent);
                                    overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);

                                } else {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(UserSelectionActivity.this, jsonObject.optString("message"), new DialogListener() {
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
                        }
                    });
        } else {
            CustomDialogManager.showOkDialog(UserSelectionActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }
}
