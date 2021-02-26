package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.MenuHome;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.ebveneto.widgets.RateThisApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, DialogListener {

    TextView tvWelcomeText;
    Button btnVerifyData,btnAddNewServices;
    private FirebaseAnalytics mFirebaseAnalytics;
    ArrayList<MenuHome> menuList;
    private LinearLayout llLeft;
    private LinearLayout llRight;
    private String idSend="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initialise();
        appUpdateAvailble(WelcomeActivity.this);
    }
    public void appUpateDialog(final float minVersionNamePlayStore) {
        final Dialog dialog = new Dialog(WelcomeActivity.this);
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
                AppSession.getInstance().saveSkipFlag(WelcomeActivity.this, "true");
                AppSession.getInstance().saveSkipVersion(WelcomeActivity.this, String.valueOf(minVersionNamePlayStore));
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
            CustomDialogManager.showOkDialog(WelcomeActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }


    private void initialise() {
        tvWelcomeText = (TextView)findViewById(R.id.tvWelcomeText);
        btnVerifyData = (Button)findViewById(R.id.btnVerifyData);
        btnVerifyData.setOnClickListener(this);
        btnAddNewServices = (Button)findViewById(R.id.btnAddNewServices);
        btnAddNewServices.setOnClickListener(this);

        llLeft = (LinearLayout) findViewById(R.id.llLeft);
        llRight = (LinearLayout) findViewById(R.id.llRight);
        
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        if(TextUtils.equals(AppSession.getInstance().getTable(WelcomeActivity.this),"Dipendenti")){
         //   tvWelcomeText.setText("Benvenuto"+" " + AppSession.getInstance().getUserNome(WelcomeActivity.this) +" "+ AppSession.getInstance().getUserCognome(WelcomeActivity.this) +" "+"nell'app di Ente Bilaterale Veneto F.V.G. per richiedere un servizio");
         //   "Benvenuto"+" " + AppSession.getInstance().getUserNome(WelcomeActivity.this) +" "+ AppSession.getInstance().getUserCognome(WelcomeActivity.this) +" "+"attualmente impiegato in"+" " + AppSession.getInstance().getCompanyName(WelcomeActivity.this);
            String sourceString ="Benvenuto"+" " + "<b>" + AppSession.getInstance().getUserNome(WelcomeActivity.this) +" "+ AppSession.getInstance().getUserCognome(WelcomeActivity.this) + "</b> " +" "+"attualmente impiegato in"+" " + "<b>" + AppSession.getInstance().getCompanyName(WelcomeActivity.this) + "</b> ";
            tvWelcomeText.setText(Html.fromHtml(sourceString));
            menuList.add(new MenuHome("VERIFICA I TUOI DATI",R.mipmap.profile,false ,R.color.colorFaintYellow,R.color.colorHomeTitleBlue));//CHECK YOUR DATA
            menuList.add(new MenuHome("RICHIEDI UN NUOVO SERVIZIO",R.mipmap.nuova,false, R.color.colorPrimary,R.color.colorHomeTitleBlue));  //REQUEST A NEW SERVICE
            menuList.add(new MenuHome("Servizi Richiesti",R.mipmap.services,false, R.color.colorPrimary,R.color.colorHomeTitleBlue));//Requested services
            menuList.add(new MenuHome("EBVF Card",R.mipmap.evbf_card_orang,false, R.color.colorFaintOrange,R.color.colorOrangeTitle));//
            menuList.add(new MenuHome("Esci",R.mipmap.logout_home,false,  R.color.colorPrimary,R.color.colorHomeTitleBlue));//Log out
        }else if(TextUtils.equals(AppSession.getInstance().getTable(WelcomeActivity.this),"Aziende")){
            //  String sourceString ="Benvenuto"+" " + "<b>" + AppSession.getInstance().getCompanyName(WelcomeActivity.this)+ "</b> "  +" nell'app di Ente Bilaterale Veneto F.V.G. per richiedere un servizio";
            String sourceString = "";
            if(TextUtils.equals(AppSession.getInstance().getFIELD_LEGALE_RAPPRESENTANTE(WelcomeActivity.this),"")){
                   sourceString ="Benvenuto"+" " + "<b>" + AppSession.getInstance().getCompanyName(WelcomeActivity.this)+ "</b> "  +" nell'app di Ente Bilaterale Veneto F.V.G.";

            }else {
                String companyName =  AppSession.getInstance().getCompanyName(WelcomeActivity.this);
                String legalAdvisor = AppSession.getInstance().getFIELD_LEGALE_RAPPRESENTANTE(WelcomeActivity.this);

                sourceString ="Benvenuto"+" " + "<b>" + companyName+ "</b> "+" legale rappresentante "+ "<b>" + legalAdvisor+ "</b> "  +" nell'app di Ente Bilaterale Veneto F.V.G.";
            }
            tvWelcomeText.setText(Html.fromHtml(sourceString));
        //    tvWelcomeText.setText("Benvenuto"+" " + AppSession.getInstance().getCompanyName(WelcomeActivity.this) + " nell'app di Ente Bilaterale Veneto F.V.G. per richiedere un servizio");

            menuList.add(new MenuHome("VERIFICA I TUOI DATI",R.mipmap.profile_pink,false, R.color.colorDarkPink,R.color.colorFirstTitle));
            menuList.add(new MenuHome("RICHIEDI UN NUOVO SERVIZIO",R.mipmap.nuova,false,R.color.colorPrimary,R.color.colorHomeTitleBlue));
            menuList.add(new MenuHome("Servizi Richiesti",R.mipmap.services,false ,R.color.colorPrimary,R.color.colorHomeTitleBlue));
            menuList.add(new MenuHome("Dipendenti",R.mipmap.group_people,false ,R.color.colorFaintYellow,R.color.colorHomeTitleBlue));
            menuList.add(new MenuHome("Qualifiche Dipendenti",R.mipmap.id_card,false, R.color.colorDarkYellow,R.color.colorHomeTitleBlue));//Employee Qualifications
            menuList.add(new MenuHome("Sedi",R.mipmap.sedi_grey,false, R.color.colorFaintGraySedi,R.color.colorSediTitle));//Locations

            menuList.add(new MenuHome("Versamenti",R.mipmap.euro_green,false, R.color.colorFaintGreen,R.color.colorGreenTitle));//Payments

            menuList.add(new MenuHome("EBVF Card",R.mipmap.evbf_card_orang,false, R.color.colorFaintOrange,R.color.colorOrangeTitle));
            menuList.add(new MenuHome("Esci",R.mipmap.logout_home,false, R.color.colorPrimary,R.color.colorHomeTitleBlue));
            //    menuList.add(new MenuHome("Invia SMS gratis",R.mipmap.sms,false));
        }
        
        setHomeMenu();
        if(!AppSession.getInstance().getLoginPolicyStatus(WelcomeActivity.this)) //if policy not accept
        {
            policyStatus();//check whether user agree the private policy
        }

       // AppRater.app_launched(this);
        RateThisApp.onCreate(this);
        RateThisApp.Config config = new RateThisApp.Config(7, 2);
                // Monitor launch times and interval from installation
        RateThisApp.init(config);
        // If the condition is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
        //RateThisApp.showRateDialog(this);

    }

    private void setHomeMenu()
    {
        for (int i = 0; i < menuList.size(); i++)
        {
            final View homeView =  View.inflate(WelcomeActivity.this, R.layout.item_home, null);
            TextView tvHome = (TextView) homeView.findViewById(R.id.tvHome);
            tvHome.setText(menuList.get(i).getTitle());
            tvHome.setCompoundDrawablesWithIntrinsicBounds(0, menuList.get(i).getImageRes(), 0, 0);
            tvHome.setId(i);
            tvHome.setBackgroundColor(WelcomeActivity.this.getResources().getColor(menuList.get(i).getBgColor()));
            tvHome.setTextColor(WelcomeActivity.this.getResources().getColor(menuList.get(i).getTextColor()));

            homeView.setId(i);
            if (i % 2 == 0)
            {
                llLeft.addView(homeView);

            } else
            {
                llRight.addView(homeView);
            }

            homeView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(TextUtils.equals(AppSession.getInstance().getTable(WelcomeActivity.this),"Dipendenti")){   //Dipendenti login
                        switch (homeView.getId()){
                            case 0:
                                Intent my_profile_intent = new Intent(WelcomeActivity.this, MyProfileActivity.class);
                                startActivity(my_profile_intent);
                                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                break;

                            case 1:
                                Intent add_new_services_intent = new Intent(WelcomeActivity.this, AddNewServicesActivity.class);
                                startActivity(add_new_services_intent);
                                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                break;

                            case 2:
                                Intent requestedServiceIntent = new Intent(WelcomeActivity.this, RequestedServicesActivity.class);
                                startActivity(requestedServiceIntent);
                                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                break;

                            case 3:
                                //EBVF Card
                                Intent intentEBVFCard = new Intent(WelcomeActivity.this, WebviewActivity.class);
                                intentEBVFCard.putExtra("screen", "EBVF Card");
                                startActivity(intentEBVFCard);
                                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                break;

                            case 4:
                                logout();
                                break;
                        }

                    }else if(TextUtils.equals(AppSession.getInstance().getTable(WelcomeActivity.this),"Aziende")){    //Aziende login
                       switch (homeView.getId()){
                           case 0:
                               Intent my_profile_intent = new Intent(WelcomeActivity.this, MyProfileActivity.class);
                               startActivity(my_profile_intent);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                               break;

                           case 1:
                               Intent add_new_services_intent = new Intent(WelcomeActivity.this, AddNewServicesActivity.class);
                               startActivity(add_new_services_intent);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                               break;

                           case 2:
                               Intent requestedServiceIntent = new Intent(WelcomeActivity.this, RequestedServicesActivity.class);
                               startActivity(requestedServiceIntent);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                               break;

                           case 3:
                               Intent intentDipendenti = new Intent(WelcomeActivity.this, WebviewActivity.class);
                               intentDipendenti.putExtra("screen", "Dipendenti");
                               startActivity(intentDipendenti);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);

                               break;

                           case 4:
                               Intent intentAboutUs = new Intent(WelcomeActivity.this, WebviewActivity.class);
                               intentAboutUs.putExtra("screen", "Qualifiche Dipendenti");
                               startActivity(intentAboutUs);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                              /* CustomDialogManager.showOkDialog(WelcomeActivity.this, getString(R.string.this_section_is_under_construction), new DialogListener()
                               {
                                   @Override
                                   public void onButtonClicked(int type)
                                   {
                                   }
                               });*/
                               break;

                           case 5:
                               Intent sediIntent = new Intent(WelcomeActivity.this, SediActivity.class);
                               startActivity(sediIntent);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                               break;

                           case 6:
                               Intent versametiIntent = new Intent(WelcomeActivity.this, VersametiActivity.class);
                               startActivity(versametiIntent);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                               break;

                           case 7:
                                //EBVF Card
                               Intent intentEBVFCard = new Intent(WelcomeActivity.this, WebviewActivity.class);
                               intentEBVFCard.putExtra("screen", "EBVF Card");
                               startActivity(intentEBVFCard);
                               overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                             /*  CustomDialogManager.showOkDialog(WelcomeActivity.this, getString(R.string.this_section_is_under_construction), new DialogListener()
                               {
                                   @Override
                                   public void onButtonClicked(int type)
                                   {
                                   }
                               });*/
                               break;


                           case 8:
                               logout();
                               break;

                       }
                    }
                }
            });
           /* final CheckBox finalCbWorkType = cbWorkType;
            cbWorkType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                {
                    if (TextUtils.equals(AppDataHolder.getSession(getActivity()).getUserType(), "1"))
                    {
                        if (b)
                        {
                            setCheckboxLogic(finalCbWorkType.getId());
                        }
                    }
                }
            });*/
        }
    }

    private void logout()
    {
        CustomDialogManager.showOkCancelDialog(WelcomeActivity.this, getString(R.string.logout_message), getString(R.string.yes), getString(R.string.no), new DialogListener() {
            @Override
            public void onButtonClicked(int type) {
                if (Dialog.BUTTON_POSITIVE == type) {
                    AppSession.getInstance().saveLoginStatus(WelcomeActivity.this, false);
                    AppSession.getInstance().savePolicyStatus(WelcomeActivity.this,false);
                    AppSession.getInstance().clearRateThisAppSharedPreference(WelcomeActivity.this);
                   // AppSession.getInstance().clearSharedPreference(WelcomeActivity.this);
                    Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    finishAffinity();
                    startActivity(login_intent);
                } else {
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVerifyData:
                Intent my_profile_intent = new Intent(WelcomeActivity.this, MyProfileActivity.class);
                startActivity(my_profile_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                break;

            case R.id.btnAddNewServices:
                Intent add_new_services_intent = new Intent(WelcomeActivity.this, AddNewServicesActivity.class);
                startActivity(add_new_services_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                break;
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

    public void policyStatus()
    {
        if(!AppSession.getInstance().getPolicyStatus(WelcomeActivity.this))
        {
            CustomDialogManager.showPrivacyPolicyDialog(WelcomeActivity.this,HttpHelper.PRIVACY_POLICY_URL,2,this);
        }
    }


    @Override
    public void onButtonClicked(int type)
    {
        if(Dialog.BUTTON_POSITIVE==type) //if privacy policy accept then save in session
        {

            validatePolicy();

        }else
        {
            AppSession.getInstance().saveLoginStatus(WelcomeActivity.this, false);
            AppSession.getInstance().savePolicyStatus(WelcomeActivity.this,false);
            Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            finishAffinity();
            startActivity(login_intent);
        }
    }

    private void validatePolicy()
    {
        if (HttpHelper.isNetworkAvailable(WelcomeActivity.this))
        {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(WelcomeActivity.this);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.updateUserConsent(new TypedString(AppSession.getInstance().getTable(WelcomeActivity.this)),
                    new TypedString(AppSession.getInstance().getUserId(WelcomeActivity.this)),
                    new TypedString("updateUserConsent"),
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
                                    Helper.Log("URL", "response : ==>  " + jsonObject);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONObject dataObject = jsondataObject.getJSONObject("data");
                                  if(dataObject.optString("PRIVACY_POLICY").equals("1"))
                                  {
                                      //Toast.makeText(WelcomeActivity.this, "true", Toast.LENGTH_SHORT).show();
                                      AppSession.getInstance().savePolicyStatus(WelcomeActivity.this,true);
                                  }else
                                  {
                                      //Toast.makeText(WelcomeActivity.this, "false", Toast.LENGTH_SHORT).show();
                                  }

                                } else
                                {
                                   /* CustomDialogManager.showOkDialog(MyProfileActivity.this, jsonObject.optString("message"), new DialogListener() {
                                        @Override
                                        public void onButtonClicked(int type) {

                                        }
                                    });*/
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
            CustomDialogManager.showOkDialog(WelcomeActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }
}
