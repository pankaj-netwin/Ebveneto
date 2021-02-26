package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class MyProfileActivity extends BaseActivity
{

    LinearLayout llAddNewService;
    TextView tvNome, tvCognome, tvCof, tvDatadiNascita, tvIndirizzo, tvCitta, tvProvincia,
            tvInRegolaCon, tvCurrentlyEmplyoed;
    private LinearLayout llChangePassword;
    private LinearLayout llModificaDati;
    private TextView tvNomeOrRegion;
    private TextView tvCognomeOrPatrita;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_activty);
        initialise();
    }

    private void initialise()
    {
        llAddNewService = (LinearLayout) findViewById(R.id.llAddNewService);
        llChangePassword = (LinearLayout) findViewById(R.id.llChangePassword);
        llModificaDati = (LinearLayout) findViewById(R.id.llModificaDati);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvCognome = (TextView) findViewById(R.id.tvCognome);
        tvCof = (TextView) findViewById(R.id.tvCof);
        tvDatadiNascita = (TextView) findViewById(R.id.tvDatadiNascita);
        tvIndirizzo = (TextView) findViewById(R.id.tvIndirizzo);
        tvCitta = (TextView) findViewById(R.id.tvCitta);
        tvProvincia = (TextView) findViewById(R.id.tvProvincia);
        tvInRegolaCon = (TextView) findViewById(R.id.tvInRegolaCon);
        tvCurrentlyEmplyoed = (TextView) findViewById(R.id.tvCurrentlyEmplyoed);


        tvNomeOrRegion = (TextView) findViewById(R.id.tvNomeOrRegion);
        tvCognomeOrPatrita = (TextView) findViewById(R.id.tvCognomeOrPatrita);
      //  getUserData(MyProfileActivity.this);
        llAddNewService.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent my_profile_intent = new Intent(MyProfileActivity.this,
                        AddNewServicesActivity.class);
                my_profile_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(my_profile_intent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            }
        });

        llChangePassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent changePasswordIntent = new Intent(MyProfileActivity.this,
                        ChangePasswordActivity.class);
                changePasswordIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(changePasswordIntent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            }
        });

        llModificaDati.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intentEBVFCard = new Intent(MyProfileActivity.this, WebviewActivity.class);
                intentEBVFCard.putExtra("screen", "modifica_i_dati");
                startActivity(intentEBVFCard);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            }
        });

        if(TextUtils.equals(AppSession.getInstance().getTable(MyProfileActivity.this),"Aziende"))
        {
            tvNomeOrRegion.setText(getString(R.string.regione_sociale));
            tvCognomeOrPatrita.setText(getString(R.string.partita_iva));
            tvNome.setText(AppSession.getInstance().getCompanyName(MyProfileActivity.this));
            tvCognome.setText(AppSession.getInstance().getPiva(MyProfileActivity.this));
        }else {
            tvNomeOrRegion.setText(getString(R.string.name));
            tvCognomeOrPatrita.setText(getString(R.string.surname));
            tvNome.setText(AppSession.getInstance().getUserNome(MyProfileActivity.this));
            tvCognome.setText(AppSession.getInstance().getUserCognome(MyProfileActivity.this));
        }


        tvCof.setText(AppSession.getInstance().getCF(MyProfileActivity.this));
        tvDatadiNascita.setText(AppSession.getInstance().getDateNascita(MyProfileActivity.this));
        tvIndirizzo.setText(AppSession.getInstance().getVia(MyProfileActivity.this));
        tvCitta.setText(AppSession.getInstance().getCitta(MyProfileActivity.this));
        tvProvincia.setText(AppSession.getInstance().getProvincia(MyProfileActivity.this));
        tvInRegolaCon.setText(AppSession.getInstance().getEbv_versamenti(MyProfileActivity.this));
        tvCurrentlyEmplyoed.setText(AppSession.getInstance().getCompanyName(MyProfileActivity.this));
    }

    //Webservice implementation to get Profile data
    private void getUserData(Context context)
    {

        if (HttpHelper.isNetworkAvailable(context))
        {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getUserType(new TypedString(AppSession.getInstance().getEmail(MyProfileActivity.this)),
                    new TypedString(AppSession.getInstance().getPassword(MyProfileActivity.this)),
                    new TypedString("doLogin"),
                    new TypedString("1"),
                    new TypedString(AppSession.getInstance().getUserType(MyProfileActivity.this)),
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
                                    tvNome.setText(dataObject.optString("Nome"));
                                    tvCognome.setText(dataObject.optString("Cognome"));

                                    if (TextUtils.equals(dataObject.optString("cf"), "null"))
                                    {
                                        tvCof.setText("");
                                    } else
                                    {
                                        tvCof.setText(dataObject.optString("cf"));
                                    }

                                    if (TextUtils.equals(dataObject.optString("dtNascita"), "null"))
                                    {
                                        tvCof.setText("");
                                    } else
                                    {
                                        tvDatadiNascita.setText(dataObject.optString("dtNascita"));
                                    }


                                    if (!dataObject.optString("Via").equals("null"))
                                    {
                                        tvIndirizzo.setText(dataObject.optString("Via"));
                                    }

                                    if (!dataObject.optString("Cap").equals("null") && !dataObject.optString("Comune").equals("null") && !dataObject.optString("Frazione").equals("null"))
                                    {
                                        tvCitta.setText(dataObject.optString("Cap") + " " + dataObject.optString("Comune") + " " + dataObject.optString("Frazione"));
                                    }

                                    if (!dataObject.optString("Prov").equals("null"))
                                    {
                                        tvProvincia.setText(dataObject.optString("Prov"));
                                    }

                                    if (dataObject.optString("ebv_versamenti").equals("N"))
                                    {
                                        tvInRegolaCon.setText(getString(R.string.no));
                                    } else
                                    {
                                        tvInRegolaCon.setText(getString(R.string.yes));
                                    }

                                    JSONArray employment_array = dataObject.getJSONArray("employment");
                                    if (!employment_array.getString(0).equals(""))
                                    {
                                        tvCurrentlyEmplyoed.setText(employment_array.getString(0));
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
            CustomDialogManager.showOkDialog(MyProfileActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }
}
