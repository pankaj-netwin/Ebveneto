package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.Sedi;
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

public class EditSediActivity extends BaseActivity
{
    Sedi sedi;
    private EditText etNome;
    private EditText etIndirizzo;
    private EditText etCap;
    private EditText etComune;
    private EditText etFrazione;
    private EditText etProvincia;
    private EditText etTipo;
    private EditText etFonte;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            sedi = bundle.getParcelable("sedi");
        }
        initialisation();
    }

    private void initialisation()
    {
        etNome = (EditText) findViewById(R.id.etNome);
        etIndirizzo = (EditText) findViewById(R.id.etIndirizzo);
        etCap = (EditText) findViewById(R.id.etCap);
        etComune = (EditText) findViewById(R.id.etComune);
        etFrazione = (EditText) findViewById(R.id.etFrazione);
        etProvincia = (EditText) findViewById(R.id.etProvincia);
        etTipo = (EditText) findViewById(R.id.etTipo);
        etFonte = (EditText) findViewById(R.id.etFonte);

        btSave = (Button) findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editSedi(EditSediActivity.this);
            }
        });

        setData();
    }

    private void setData()
    {
        if (TextUtils.equals(sedi.getNome(), "null"))
        {
            etNome.setText("");
        } else
        {
            etNome.setText(sedi.getNome());
        }

        if (!TextUtils.equals(sedi.getVia(), "null"))
        {
            etIndirizzo.setText(sedi.getVia());
        }

        if (!TextUtils.equals(sedi.getCap(), "null"))
        {
            etCap.setText(sedi.getCap());
        }

        if (!TextUtils.equals(sedi.getComune(), "null"))
        {
            etComune.setText(sedi.getComune());
        }

        if (!TextUtils.equals(sedi.getFrazione(), "null"))
        {
            etFrazione.setText(sedi.getFrazione());
        }

        if (!TextUtils.equals(sedi.getProv(), "null"))
        {
            etProvincia.setText(sedi.getProv());
        }

        if (!TextUtils.equals(sedi.getTipo(), "null"))
        {
            etTipo.setText(sedi.getTipo());
        }
        if (!TextUtils.equals(sedi.getFonte(), "null"))
        {
            etFonte.setText(sedi.getFonte());
        }
    }

    private void editSedi(Context context)
    {
        if (HttpHelper.isNetworkAvailable(context))
        {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.editSedi("updateSeatList",
                    AppSession.getInstance().getTable(EditSediActivity.this),//table name
                    AppSession.getInstance().getUserId(EditSediActivity.this),//user id
                    sedi.getIdSede(),
                    etNome.getText().toString(),
                    etIndirizzo.getText().toString(),
                    etCap.getText().toString(),
                    etComune.getText().toString(),
                    etFrazione.getText().toString(),
                    etProvincia.getText().toString(),
                    etTipo.getText().toString(),
                    etFonte.getText().toString(),
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
                                    CustomDialogManager.showOkDialog(EditSediActivity.this, jsonObject.optString("message"), new DialogListener()
                                    {
                                        @Override
                                        public void onButtonClicked(int type)
                                        {
                                            EditSediActivity.this.finish();
                                        }
                                    });

                                } else
                                {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(EditSediActivity.this, jsonObject.optString("message"), new DialogListener()
                                    {
                                        @Override
                                        public void onButtonClicked(int type)
                                        {

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
            CustomDialogManager.showOkDialog(EditSediActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }
}
