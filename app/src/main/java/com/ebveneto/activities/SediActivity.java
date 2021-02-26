package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.adapters.SediAdapter;
import com.ebveneto.interfaces.ClickListener;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.Sedi;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.ebveneto.widgets.StaticListView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SediActivity extends BaseActivity
{
    ArrayList<Sedi> sediList;
    private TextView tvSedeAddress;
    private StaticListView lvAddress;
    SediAdapter sediAdapter;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedi);
        initialization();
    }

    private void initialization()
    {
        if(sediList == null){
            sediList = new ArrayList<>();
        }
        tvSedeAddress = (TextView) findViewById(R.id.tvSedeAddress);
        lvAddress = (StaticListView) findViewById(R.id.lvAddess);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        getSedi(SediActivity.this);
        sediAdapter = new SediAdapter(SediActivity.this, R.layout.list_tem_sedi, sediList, new ClickListener()
        {
            @Override
            public void onButtonClicked(int position)
            {
                Sedi sedi = sediList.get(position);
                Intent sediIntent = new Intent(SediActivity.this, EditSediActivity.class);
                sediIntent.putExtra("sedi", sedi);
                startActivity(sediIntent);
                overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                /*CustomDialogManager.showOkDialog(SediActivity.this, getString(R.string.this_section_is_under_construction), new DialogListener()
                {
                    @Override
                    public void onButtonClicked(int type)
                    {
                    }
                });*/
            }
        });

        lvAddress.setAdapter(sediAdapter);

    }

    private void getSedi(Context context) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getSedi("getSeatList",
                    AppSession.getInstance().getTable(SediActivity.this),//table name
                    AppSession.getInstance().getUserId(SediActivity.this),//user id
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    tvEmpty.setVisibility(View.GONE);
                                    lvAddress.setVisibility(View.VISIBLE);
                                    Helper.Log("URL", "response : ==>  " + jsonObject);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONObject dataObj = jsondataObject.optJSONObject("data");

                                    JSONObject reg_officeObj = dataObj.optJSONObject("Reg_office");

                                    String via = reg_officeObj.optString("Via");
                                    String comune = reg_officeObj.optString("Comune");
                                    String prov = reg_officeObj.optString("Prov");
                                    String cap = reg_officeObj.optString("Cap");

                                    ArrayList<String> addressList = new ArrayList<String>();
                                    if(TextUtils.equals(via,"null")){
                                      via = "";
                                    }else {
                                        addressList.add(via) ;
                                    }

                                    if(TextUtils.equals(comune,"null")){
                                        comune = "";
                                    }else {
                                        addressList.add(comune) ;
                                    }
                                    if(TextUtils.equals(prov,"null")){
                                        prov = "";
                                    }else {
                                        addressList.add(prov) ;
                                    }
                                    if(TextUtils.equals(cap,"null")){
                                        cap = "";
                                    }else {
                                        addressList.add(cap) ;
                                    }

                                    String address = "";
                                    for (int i = 0; i < addressList.size(); i++)
                                    {
                                       if(i==0){
                                           address = addressList.get(0);
                                       }else {
                                           address =  address + ", " + addressList.get(i);
                                       }
                                    }
                                    tvSedeAddress.setText(address);
                                  //   tvSedeAddress.setText(via+", "+comune+", "+", "+prov+", "+cap+".");

                                    JSONArray dataArray = dataObj.optJSONArray("Branches");
                                    for (int i = 0; i < dataArray.length(); i++)
                                    {
                                        JSONObject sediObject = dataArray.getJSONObject(i);
                                        Sedi sedi = new Sedi();

                                        sedi.setIdSede(sediObject.optString("idSede"));
                                        sedi.setIdAZ(sediObject.optString("idAZ"));
                                        sedi.setNome(sediObject.optString("Nome"));
                                        sedi.setVia(sediObject.optString("Via"));
                                        sedi.setCap(sediObject.optString("Cap"));
                                        sedi.setComune(sediObject.optString("Comune"));

                                        sedi.setFrazione(sediObject.optString("Frazione"));
                                        sedi.setProv(sediObject.optString("Prov"));
                                        sedi.setTipo(sediObject.optString("Tipo"));
                                        sedi.setUpdDATA(sediObject.optString("updDATA"));
                                        sedi.setUpdUSER(sediObject.optString("updUSER"));
                                        sedi.setUpdPC(sediObject.optString("updPC"));
                                        sedi.setFonte(sediObject.optString("fonte"));

                                        sediList.add(sedi);
                                    }

                                    sediAdapter.notifyDataSetChanged();


                                } else {
                                    //for invalid conditions like invalid credentials
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    lvAddress.setVisibility(View.GONE);
                                   /* CustomDialogManager.showOkDialog(SediActivity.this, jsonObject.optString("message"), new DialogListener() {
                                        @Override
                                        public void onButtonClicked(int type) {

                                        }
                                    });*/

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
            CustomDialogManager.showOkDialog(SediActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }
}
