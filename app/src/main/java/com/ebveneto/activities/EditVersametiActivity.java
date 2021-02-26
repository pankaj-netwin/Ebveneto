package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.Versameti;
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

public class EditVersametiActivity extends BaseActivity
{
    Versameti versameti;
    private EditText etCodiceFiscale;
    private EditText etPiva;
    private EditText etCodiceINPSPrevalente;
    private EditText etJan;
    private EditText etFeb;
    private EditText etMar;
    private EditText etApr;
    private EditText etMay;
    private EditText etJun;
    private EditText etJul;
    private EditText etAug;
    private EditText etSep;
    private EditText etOct;
    private EditText etNov;
    private EditText etDec;
    private Button btSave;
    private TextView tvName;
    private TextView tvYear;
    private CheckBox cbEbvVersamenti;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_versameti);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            versameti = bundle.getParcelable("Versameti");
        }
        initialization();
    }

    private void initialization()
    {
        tvName = (TextView) findViewById(R.id.tvName);
        tvYear = (TextView) findViewById(R.id.tvYear);

        etCodiceFiscale = (EditText) findViewById(R.id.etCodiceFiscale);
        etPiva = (EditText) findViewById(R.id.etPiva);
        etCodiceINPSPrevalente = (EditText) findViewById(R.id.etCodiceINPSPrevalente);

        etJan = (EditText) findViewById(R.id.etJan);
        etFeb = (EditText) findViewById(R.id.etFeb);
        etMar = (EditText) findViewById(R.id.etMar);
        etApr = (EditText) findViewById(R.id.etApr);
        etMay = (EditText) findViewById(R.id.etMay);
        etJun = (EditText) findViewById(R.id.etJun);
        etJul = (EditText) findViewById(R.id.etJul);
        etAug = (EditText) findViewById(R.id.etAug);
        etSep = (EditText) findViewById(R.id.etSep);
        etOct = (EditText) findViewById(R.id.etOct);
        etNov = (EditText) findViewById(R.id.etNov);
        etDec = (EditText) findViewById(R.id.etDec);

        cbEbvVersamenti = (CheckBox) findViewById(R.id.cbEbvVersamenti);

        btSave = (Button) findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate();
            }
        });


        cbEbvVersamenti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (b)
                {
                    cbEbvVersamenti.setSelected(true);

                } else
                {
                    cbEbvVersamenti.setSelected(false);

                }
            }
        });
        setData();
    }

    private void setData()
    {
        etCodiceFiscale.setText(versameti.getCf());
        etPiva.setText(versameti.getPiva());
        etCodiceINPSPrevalente.setText(versameti.getINPS());

        tvYear.setText(versameti.getAnnoCompetenza());
        tvName.setText(versameti.getAzNome());

        if (TextUtils.equals(versameti.getEbv_versamenti(), "S"))
        {
            cbEbvVersamenti.setChecked(true);

        } else if (TextUtils.equals(versameti.getEbv_versamenti(), "N"))
        {
            cbEbvVersamenti.setChecked(false);

        }


        if (TextUtils.equals(versameti.getJAN(), "null"))
        {
            etJan.setText("");

        } else
        {
            etJan.setText(versameti.getJAN());
        }


        if (TextUtils.equals(versameti.getFEB(), "null"))
        {
            etFeb.setText("");
        } else
        {
            etFeb.setText(versameti.getFEB());
        }

        if (TextUtils.equals(versameti.getMAR(), "null"))
        {
            etMar.setText("");
        } else
        {
            etMar.setText(versameti.getMAR());
        }

        if (TextUtils.equals(versameti.getAPR(), "null"))
        {
            etApr.setText("");
        } else
        {
            etApr.setText(versameti.getAPR());
        }

        if (TextUtils.equals(versameti.getMAY(), "null"))
        {
            etMay.setText("");
        } else
        {
            etMay.setText(versameti.getMAY());
        }

        if (TextUtils.equals(versameti.getJUN(), "null"))
        {
            etJun.setText("");
        } else
        {
            etJun.setText(versameti.getJUN());
        }

        if (TextUtils.equals(versameti.getJUL(), "null"))
        {
            etJul.setText("");
        } else
        {
            etJul.setText(versameti.getJUL());
        }

        if (TextUtils.equals(versameti.getAUG(), "null"))
        {
            etAug.setText("");
        } else
        {
            etAug.setText(versameti.getAUG());
        }

        if (TextUtils.equals(versameti.getSEP(), "null"))
        {
            etSep.setText("");
        } else
        {
            etSep.setText(versameti.getSEP());
        }

        if (TextUtils.equals(versameti.getOCT(), "null"))
        {
            etOct.setText("");
        } else
        {
            etOct.setText(versameti.getOCT());
        }

        if (TextUtils.equals(versameti.getNOV(), "null"))
        {
            etNov.setText("");
        } else
        {
            etNov.setText(versameti.getNOV());
        }

        if (TextUtils.equals(versameti.getDEC(), "null"))
        {
            etDec.setText("");
        } else
        {
            etDec.setText(versameti.getDEC());
        }
    }

    private void saveData(Context context)
    {

       /* CustomDialogManager.showOkDialog(context, getString(R.string.this_section_is_under_construction), new DialogListener()
        {
            @Override
            public void onButtonClicked(int type)
            {
            }
        });*/
        if (HttpHelper.isNetworkAvailable(context))
        {
            String ebv_ver = "";
            if (cbEbvVersamenti.isChecked())
            {
                ebv_ver = "S";
            } else
            {
                ebv_ver = "N";
            }
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.editVersameti("updateVersionList",
                    AppSession.getInstance().getTable(EditVersametiActivity.this),//table name
                    AppSession.getInstance().getUserId(EditVersametiActivity.this),//user id
                    versameti.getAzNome(),
                    versameti.getAnnoCompetenza(),
                    etCodiceFiscale.getText().toString(),
                    etPiva.getText().toString(),
                    ebv_ver,
                    etCodiceINPSPrevalente.getText().toString(),
                    etJan.getText().toString(),
                    etFeb.getText().toString(),
                    etMar.getText().toString(),
                    etApr.getText().toString(),
                    etMay.getText().toString(),
                    etJun.getText().toString(),
                    etJul.getText().toString(),
                    etAug.getText().toString(),
                    etSep.getText().toString(),
                    etOct.getText().toString(),
                    etNov.getText().toString(),
                    etDec.getText().toString(),
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
                                    CustomDialogManager.showOkDialog(EditVersametiActivity.this, jsonObject.optString("message"), new DialogListener()
                                    {
                                        @Override
                                        public void onButtonClicked(int type)
                                        {

                                        }
                                    });

                                } else
                                {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(EditVersametiActivity.this, jsonObject.optString("message"), new DialogListener()
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
            CustomDialogManager.showOkDialog(EditVersametiActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(etCodiceFiscale.getText().toString())) {
            CustomDialogManager.showOkDialog(EditVersametiActivity.this, "Per favore, inserisci la codice fiscale.", new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etCodiceFiscale.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etPiva.getText().toString())) {
            CustomDialogManager.showOkDialog(EditVersametiActivity.this, "Per favore, inserisci la piva.", new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etPiva.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etCodiceINPSPrevalente.getText().toString())) {
            CustomDialogManager.showOkDialog(EditVersametiActivity.this, "Per favore, inserisci la Codice INPS prevalente.", new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etCodiceINPSPrevalente.requestFocus();
            return;
        }

        saveData(EditVersametiActivity.this);

    }
}
