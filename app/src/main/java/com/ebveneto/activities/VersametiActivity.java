package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ebveneto.R;
import com.ebveneto.adapters.VersametiAdapter;
import com.ebveneto.interfaces.ClickListener;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.Versameti;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VersametiActivity extends BaseActivity
{
    ArrayList<Versameti> versametiList;    
    VersametiAdapter versametiAdapter;
    private ListView lvVersameti;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versameti);
        initialization();
    }

    private void initialization()
    {
        if(versametiList == null){
            versametiList = new ArrayList<>();
        }
        lvVersameti = (ListView) findViewById(R.id.lvVersameti);
        requestServices(VersametiActivity.this);
        versametiAdapter = new VersametiAdapter(VersametiActivity.this, R.layout.list_item_versameti, versametiList, new ClickListener()
        {
            @Override
            public void onButtonClicked(int position)
            {

               /* CustomDialogManager.showOkDialog(VersametiActivity.this, getString(R.string.this_section_is_under_construction), new DialogListener()
                {
                    @Override
                    public void onButtonClicked(int type)
                    {
                    }
                });*/
                Versameti versameti = versametiList.get(position);

                Intent versametiIntent = new Intent(VersametiActivity.this, EditVersametiActivity.class);
                versametiIntent.putExtra("Versameti", versameti);
                startActivity(versametiIntent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);

            }
        });
        lvVersameti.setAdapter(versametiAdapter);

    }

    private void requestServices(Context context) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getVersameti("getVersionList",
                    AppSession.getInstance().getTable(VersametiActivity.this),//table name
                    AppSession.getInstance().getUserId(VersametiActivity.this),//user id
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    Helper.Log("URL", "response : ==>  " + jsonObject);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONArray dataArray = jsondataObject.optJSONArray("data");

                                    for (int i = 0; i < dataArray.length(); i++)
                                    {
                                        JSONObject versametiObject = dataArray.getJSONObject(i);
                                        Versameti versameti = new Versameti();
                                        versameti.setAnnoCompetenza(versametiObject.getString("AnnoCompetenza"));
                                        versameti.setIdAZ(versametiObject.optString("idAZ"));
                                        versameti.setAzNome(versametiObject.optString("AzNome"));
                                        versameti.setCf(versametiObject.optString("cf"));
                                        versameti.setPiva(versametiObject.optString("piva"));
                                        versameti.setEbv_versamenti(versametiObject.optString("ebv_versamenti"));
                                        versameti.setINPS(versametiObject.optString("INPS"));
                                        versameti.setJAN(versametiObject.optString("GEN"));
                                        versameti.setFEB(versametiObject.optString("FEB"));
                                        versameti.setMAR(versametiObject.optString("MAR"));
                                        versameti.setAPR(versametiObject.optString("APR"));
                                        versameti.setMAY(versametiObject.optString("MAG"));
                                        versameti.setJUN(versametiObject.optString("GIU"));
                                        versameti.setJUL(versametiObject.optString("LUG"));
                                        versameti.setAUG(versametiObject.optString("AGO"));
                                        versameti.setSEP(versametiObject.optString("SET"));
                                        versameti.setOCT(versametiObject.optString("OTT"));
                                        versameti.setNOV(versametiObject.optString("NOV"));
                                        versameti.setDEC(versametiObject.optString("DIC"));
                                        
                                        versametiList.add(versameti);
                                    }
                                //    Collections.reverse(versametiList);
                                    versametiAdapter.notifyDataSetChanged();
                                    

                                } else {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(VersametiActivity.this, jsonObject.optString("message"), new DialogListener() {
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
            CustomDialogManager.showOkDialog(VersametiActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }
}
