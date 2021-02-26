package com.ebveneto.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.adapters.RequestedServiceYearAdapter;
import com.ebveneto.custom_views.CustomTextViewBold;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.interfaces.ImageDialogActionListener;
import com.ebveneto.models.RequestedServiceYear;
import com.ebveneto.models.ServicesChildData;
import com.ebveneto.models.UploadedImageModule;
import com.ebveneto.utils.AppSession;
import com.ebveneto.utils.FileOperations;
import com.ebveneto.webservices.ApiClient;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class RequestedServicesActivity extends BaseActivity
{
    ArrayList<RequestedServiceYear> employeeList ;
    ArrayList<RequestedServiceYear> companyList ;
    ArrayList<RequestedServiceYear> requestedServiceYearList ;
    RequestedServiceYearAdapter requestedServiceYearAdapter;
    ArrayList<ServicesChildData> serviceList;

    private StickyListHeadersListView stickyList;
    private TextView tvEmpty;
    private TextView tvSommeLabel;
    private TextView tvSomme;
    private TextView tvBudget;
    private TextView tvBudgetLabel;
    private RadioGroup rgUser;
    private RadioButton rbCompany;

    private static final int PERMISSION_STORAGE = 3;
    private static final int PICK_PDF = 6;
    private static final int REQUEST_CODE = 99;
    private static final int PICK_DOC_FILE = 5;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int CROP_IMAGE = 4;
    Uri outputFileUri = null;
    String filePath=null;
    ArrayList<UploadedImageModule> uploadedImageModuleArrayList;
    CustomTextViewBold txtErrorMessage;
    int attachmentCounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_services);
        initialisation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "resume", Toast.LENGTH_SHORT).show();
    }

    private void initialisation()
    {

        if(requestedServiceYearList == null){
            requestedServiceYearList = new ArrayList<>();
        }else{
            requestedServiceYearList.clear();
        }
        if(companyList == null){
            companyList = new ArrayList<>();
        }else{
            companyList.clear();
        }
        if(employeeList == null){
            employeeList = new ArrayList<>();
        }else{
            employeeList.clear();
        }
        if(uploadedImageModuleArrayList == null){
            uploadedImageModuleArrayList = new ArrayList<>();
        }else{
            uploadedImageModuleArrayList.clear();
        }

        stickyList = (StickyListHeadersListView) findViewById(R.id.stickyList);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);

        tvSomme = (TextView) findViewById(R.id.tvSomme);
        tvBudget = (TextView) findViewById(R.id.tvBudget);

        tvSommeLabel = (TextView) findViewById(R.id.tvSommeLabel);
        tvBudgetLabel = (TextView) findViewById(R.id.tvBudgetLabel);

        rgUser = (RadioGroup) findViewById(R.id.rgUser);
        rbCompany = (RadioButton) findViewById(R.id.rbCompany);

        if(TextUtils.equals(AppSession.getInstance().getTable(RequestedServicesActivity.this),"Aziende"))
        {
            rgUser.setVisibility(View.VISIBLE);
            rbCompany.setChecked(true);
        }else {
            rgUser.setVisibility(View.GONE);
        }

        rgUser.setOnCheckedChangeListener(onUserSelect);


        setSommaAndBudget();

        //Added by sanket on 14 jan 2019
        String currentYear=""+Calendar.getInstance().get(Calendar.YEAR);
        Resources res = getResources();
        String sommeLabelWithyear = String.format(res.getString(R.string.somme_percepite_nell_anno_year), currentYear);
        tvSommeLabel.setText(sommeLabelWithyear);

        String budgetLabelWithyearCompany =res.getString(R.string.budget_residuo_company);
        String budgetLabelWithyear = String.format(res.getString(R.string.budget_residuo_per_l_anno_year), currentYear);

        String values = AppSession.getInstance().getTable(RequestedServicesActivity.this);
        if (values.equals("Aziende")) {
            tvBudgetLabel.setText(budgetLabelWithyearCompany);
        } else {
            tvBudgetLabel.setText(budgetLabelWithyear);
        }


        requestServices(RequestedServicesActivity.this);

        requestedServiceYearAdapter = new RequestedServiceYearAdapter(RequestedServicesActivity.this, requestedServiceYearList, new RequestedServiceYearAdapter.OnAttachmentClickListener() {
            @Override
            public void onItemClick(RequestedServiceYear item) {
             //   Toast.makeText(RequestedServicesActivity.this, ""+item.getIsAttachementShow(), Toast.LENGTH_SHORT).show();
                customeDialog(item.getIDS());
            }
        });
        stickyList.setAdapter(requestedServiceYearAdapter);

    }

    RadioGroup.OnCheckedChangeListener onUserSelect = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch(i) {
                case R.id.rbCompany:
                    requestedServiceYearList.clear();
                    requestedServiceYearList.addAll(companyList);
                    requestedServiceYearAdapter.notifyDataSetChanged();

                    break;
                case R.id.rbEmployee:
                    requestedServiceYearList.clear();
                    requestedServiceYearList.addAll(employeeList);
                    requestedServiceYearAdapter.notifyDataSetChanged();
                    break;
            }
            if(requestedServiceYearList.size()==0 ){
                tvEmpty.setVisibility(View.VISIBLE);
                stickyList.setVisibility(View.GONE);
            }else{
                tvEmpty.setVisibility(View.GONE);
                stickyList.setVisibility(View.VISIBLE);
            }

        }
    };

    private void requestServices(Context context) {

        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            Log.d("****tableName",""+AppSession.getInstance().getTable(RequestedServicesActivity.this));
            Log.d("****tableName",""+AppSession.getInstance().getUserId(RequestedServicesActivity.this));

            api.requestService("getUserServices",
                    AppSession.getInstance().getTable(RequestedServicesActivity.this),//table name
                    AppSession.getInstance().getUserId(RequestedServicesActivity.this),//user id
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            Log.d("****response",""+responseObject.toString()+" ");

                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    Helper.Log("URL", "response : ==>  " + jsonObject);

                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    JSONObject dataObject = jsondataObject.optJSONObject("data");
                                    //Added by sanket on 12 feb 2019
                                    //Added this to update somma and budget values dyanmically
                                    Log.d("****logData",""+jsondataObject.optString("budgetDisp"));
                                    //  Toast.makeText(getApplicationContext(), jsondataObject.optString("budgetDisp"), Toast.LENGTH_SHORT).show();
                                    AppSession.getInstance().saveBudget(RequestedServicesActivity.this, jsondataObject.optString("budgetDisp"));
                                    AppSession.getInstance().saveSomma(RequestedServicesActivity.this, jsondataObject.optString("sommaRichieste"));
                                    setSommaAndBudget();

                                    //current year request
                                    JSONArray currentYearListArray = dataObject.optJSONArray("richieste");
                                    for (int i = 0; i < currentYearListArray.length(); i++)
                                    {
                                        JSONObject currentYear = currentYearListArray.optJSONObject(i);

                                        RequestedServiceYear requestedServiceYear = new RequestedServiceYear();
                                        requestedServiceYear.setCurrentYear(true);
                                        requestedServiceYear.setAnnoCompetenza(currentYear.optString("AnnoCompetenza"));
                                        requestedServiceYear.setCurrentWebTitolo(currentYear.optString("webTitolo"));
                                        requestedServiceYear.setCurrentRequestedDate(currentYear.optString("dtRichiesta"));
                                        requestedServiceYear.setCurrentLastDateOfIntegration(currentYear.optString("IntegraData"));
                                        requestedServiceYear.setCurrentDatePayment(currentYear.optString("dtPagamento"));
                                        requestedServiceYear.setCurrentPaymentTiming(currentYear.optString("ggPAG"));
                                        requestedServiceYear.setCurrentState(currentYear.optString("Stato"));
                                        requestedServiceYear.setCurrentAmount(currentYear.optString("Importo"));
                                        requestedServiceYear.setNoteFiledValue(currentYear.optString("noteweb"));

                                        Log.d("***noteFileds",""+currentYear.optString("noteweb"));



                                        String Stato=currentYear.optString("Stato");
                                        String IntegraData=currentYear.optString("IntegraData");
                                        String IDProtocollo=currentYear.optString("IDProtocollo");
                                        String IDS=currentYear.optString("IDS");

                                        Log.d("***values ",""+Stato+"//"+IDProtocollo);
                                        requestedServiceYear.setIDS(IDS);


                                        if(Stato.equals("INTEGRA") && IDProtocollo!="" ){
                                            requestedServiceYear.setIsAttachementShow(true);
                                            //Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                                        }else {
                                            requestedServiceYear.setIsAttachementShow(false);
                                            //Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                                        }

                                        companyList.add(requestedServiceYear);
                                    }

                                    if(currentYearListArray.length()==0 && TextUtils.equals(dataObject.optJSONArray("resocontoDipXAnno").optString(0),"null")){
                                        tvEmpty.setVisibility(View.VISIBLE);
                                        stickyList.setVisibility(View.GONE);
                                    }else{
                                        tvEmpty.setVisibility(View.GONE);
                                        stickyList.setVisibility(View.VISIBLE);
                                    }
                                    //dtRichiesta
                                    // webTitolo
                                    //year records
                                    if(TextUtils.equals(AppSession.getInstance().getTable(RequestedServicesActivity.this),"Aziende"))
                                    {
                                        //services
                                        serviceList = new ArrayList<ServicesChildData>();
                                        JSONObject serviceObject = dataObject.optJSONObject("ServiziResoconto");
                                        serviceList.add(new ServicesChildData(serviceObject.optString("4"), "4"));
                                        serviceList.add(new ServicesChildData(serviceObject.optString("8"), "8"));
                                        serviceList.add(new ServicesChildData(serviceObject.optString("10"), "10"));
                                        serviceList.add(new ServicesChildData(serviceObject.optString("34"), "34"));
                                        serviceList.add(new ServicesChildData(serviceObject.optString("54"), "54"));
                                        JSONObject yearListObj = dataObject.optJSONObject("resocontoDipXAnno");
                                        // JSONObject yearListObj = yearListArray.optJSONObject(0);

                                        Iterator<String> iter = yearListObj.keys();
                                        ArrayList<String> yearList = new ArrayList<String>();
                                        while (iter.hasNext())
                                        {
                                            String key = iter.next();
                                            double total = 0.0;
                                            Log.d("****test",""+key);
                                            String currentYear=getCurrentYear();
                                            String prevYear=getPreviousYear();
                                            if(key.equals(currentYear)||key.equals(prevYear)){
                                                JSONArray yearDataArray = yearListObj.optJSONArray(key);
                                                for (int i = 0; i < yearDataArray.length(); i++)
                                                {
                                                    JSONObject yearDataObj = yearDataArray.optJSONObject(i);
                                                    RequestedServiceYear requestedServiceYear = new RequestedServiceYear();
                                                    requestedServiceYear.setCurrentYear(false);
                                                    requestedServiceYear.setAnnoCompetenza(yearDataObj.optString("AnnoCompetenza"));
                                                    requestedServiceYear.setIDAZ(yearDataObj.optString("IDAZ"));
                                                    requestedServiceYear.setNrTot(yearDataObj.optString("NrTot"));
                                                    requestedServiceYear.setIdServizio(yearDataObj.optString("idServizio"));
                                                    requestedServiceYear.setNrPagato(yearDataObj.optString("NrPagato"));
                                                    String TotPagato=yearDataObj.optString("TotPagato");

                                                    if(TotPagato.equals("null")){
                                                        requestedServiceYear.setTotPagato("0");
                                                    }else {
                                                        requestedServiceYear.setTotPagato(TotPagato);
                                                    }
                                                    Log.d("***TotPagato",""+TotPagato);
                                                    if (i == 0)
                                                    {
                                                        if ((i + 1) == yearDataArray.length()){
                                                            requestedServiceYear.setLastElement(true);
                                                        }else {
                                                            requestedServiceYear.setLastElement(false);
                                                        }

                                                    } else
                                                    {
                                                        if ((i + 1) == yearDataArray.length())
                                                        {
                                                            requestedServiceYear.setLastElement(true);
                                                        } else
                                                        {
                                                            requestedServiceYear.setLastElement(false);
                                                        }


                                                    }
                                                    for (int j = 0; j < serviceList.size(); j++)
                                                    {
                                                        if (TextUtils.equals(requestedServiceYear.getIdServizio(), serviceList.get(j).getServiceId()))
                                                        {
                                                            requestedServiceYear.setServiceName(serviceList.get(j).getName());
                                                        }
                                                    }
                                                    total = total + Double.parseDouble(requestedServiceYear.getTotPagato());
                                                    requestedServiceYear.setFinalTotal(total);
                                                    employeeList.add(requestedServiceYear);
                                                }
                                                // Object value = yearListObj.get(key);
                                                yearList.add(key);

                                            }


                                        }
                                    }
                                    if(TextUtils.equals(AppSession.getInstance().getTable(RequestedServicesActivity.this),"Aziende"))
                                    {
                                        requestedServiceYearList.addAll(companyList);
                                    }else {
                                        requestedServiceYearList.addAll(companyList);
                                    }

                                    requestedServiceYearAdapter.notifyDataSetChanged();


                                } else {
                                    JSONObject jsondataObject = new JSONObject(jsonObject.toString());
                                    AppSession.getInstance().saveBudget(RequestedServicesActivity.this, jsondataObject.optString("budgetDisp"));
                                    AppSession.getInstance().saveSomma(RequestedServicesActivity.this, jsondataObject.optString("sommaRichieste"));

                                    setSommaAndBudget();
                                    //for invalid conditions like invalid credentials
                                    if(requestedServiceYearList.size()==0 ){
                                        tvEmpty.setVisibility(View.VISIBLE);
                                        stickyList.setVisibility(View.GONE);
                                    }else{
                                        tvEmpty.setVisibility(View.GONE);
                                        stickyList.setVisibility(View.VISIBLE);
                                    }
                                    /*CustomDialogManager.showOkDialog(RequestedServicesActivity.this, jsonObject.optString("message"), new DialogListener() {
                                        @Override
                                        public void onButtonClicked(int type) {

                                        }
                                    });*/

                                }


                            } catch (Exception e) {
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
            CustomDialogManager.showOkDialog(RequestedServicesActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }
    public String getCurrentYear(){
        String year= String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return year;
    }
    private static String getPreviousYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        return String.valueOf(prevYear.get(Calendar.YEAR));
    }

    public void setSommaAndBudget()
    {
        String somme= AppSession.getInstance().getSomma(RequestedServicesActivity.this).replace(".",",")+" "+RequestedServicesActivity.this.getResources().getString(R.string.euro);
        tvSomme.setText(somme);
        String budget= AppSession.getInstance().getBudget(RequestedServicesActivity.this).replace(".",",")+" "+RequestedServicesActivity.this.getResources().getString(R.string.euro);
        //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        tvBudget.setText(budget);
    }

    //New code rohit
    public void customeDialog(final String ids){
        final Dialog dialog = new Dialog(RequestedServicesActivity.this);
        dialog.setContentView(R.layout.custom_dialog);

        Button btnBrowse = (Button) dialog.findViewById(R.id.btnBrowse);
        txtErrorMessage = (CustomTextViewBold) dialog.findViewById(R.id.txtMessage);
        Button btnUpload = (Button) dialog.findViewById(R.id.btnUpload);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        if(uploadedImageModuleArrayList!=null){
            uploadedImageModuleArrayList.clear();
        }
        attachmentCounter=0;

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hideDoc=true;
                showFilePickerDialog();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadedImageModuleArrayList.size()>0&&uploadedImageModuleArrayList.size()<=10){
                   // Toast.makeText(RequestedServicesActivity.this, ""+uploadedImageModuleArrayList.size(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    uploadMulitpleAttachments(ids);
                }else {
                   // Toast.makeText(RequestedServicesActivity.this, ""+uploadedImageModuleArrayList.size(), Toast.LENGTH_SHORT).show();
                    if(uploadedImageModuleArrayList.size()==0){
                        txtErrorMessage.setText("Seleziona almeno un file o un'immagine.");
                    }
                    if(uploadedImageModuleArrayList.size()==11){
                        txtErrorMessage.setText("Carica file meno di 10.");
                    }
                }

            }
        });

        dialog.show();
    }
    public void uploadMulitpleAttachments(String ids){

        final Dialog mDialog = CustomDialogManager.showProgressDialog(RequestedServicesActivity.this);

        RestAdapter restAdapter=ApiClient.getClient();
        NetworkAPI api = restAdapter.create(NetworkAPI.class);
        HashMap<String, TypedFile> dynamicFiles = new HashMap<>();

        for(int i=0;i<uploadedImageModuleArrayList.size();i++){
            File file = new File(uploadedImageModuleArrayList.get(i).getFileUrl());
            dynamicFiles.put("file_"+(i+1), new TypedFile("multipart/form-data", file));
        }

        Log.d("****values",dynamicFiles.toString()+" "+ids );
        Log.d("****values",""+AppSession.getInstance().getUserId(RequestedServicesActivity.this));

        api.uploadAttachment(
                new TypedString(AppSession.getInstance().getUserId(RequestedServicesActivity.this)),
                new TypedString(ids),
                dynamicFiles,
                new TypedString("uploadAttach"),
                new Callback<JsonObject>()
                {
                    @Override
                    public void success(JsonObject responseObject, Response response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(responseObject.toString());

                            CustomDialogManager.showOkDialog(RequestedServicesActivity.this, jsonObject.getString("message"), new DialogListener() {
                                @Override
                                public void onButtonClicked(int type) {
                                    initialisation();
                                }
                            });
                            mDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error)
                    {
                        mDialog.dismiss();
                        CustomDialogManager.showOkDialog(RequestedServicesActivity.this, "Server Error", new DialogListener() {
                            @Override
                            public void onButtonClicked(int type) {

                            }
                        });
                    }
                });
    }


    public  void showImagePickDialog(final Context context, String title, final ImageDialogActionListener listener) {
        final Dialog dialog= new Dialog(context, R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_photo_option, null);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        final TextView tvCamera= (TextView) view.findViewById(R.id.tvCamera);
        final TextView tvGallery = (TextView) view.findViewById(R.id.tvGallery);
        final ImageView ivCamera = (ImageView)view.findViewById(R.id.ivCamera);
        final ImageView ivGallery = (ImageView)view.findViewById(R.id.ivGallery);
        final TextView tvUploadImage = (TextView)view.findViewById(R.id.tvUploadImage);

        final ImageView ivPdfFile = (ImageView)view.findViewById(R.id.ivPdfFile);
        final TextView tvPdfFile = (TextView) view.findViewById(R.id.tvPdfFile);

        final ImageView ivDocFile = (ImageView)view.findViewById(R.id.ivDocFile);
        final TextView tvDocFile = (TextView)view.findViewById(R.id.tvDocFile);
        LinearLayout documentLayout= (LinearLayout)view.findViewById(R.id.documentLayout);

        documentLayout.setVisibility(View.GONE);
        ivDocFile.setVisibility(View.GONE);
        tvDocFile.setVisibility(View.GONE);

        tvUploadImage.setText(title);

        //Adding animation effects to the view
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate);
        Animation zoom_in = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);
        Animation zoom_animation = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);

        tvUploadImage.startAnimation(zoom_animation);
        tvCamera.startAnimation(zoom_in);
        tvGallery.startAnimation(zoom_in);
        tvPdfFile.startAnimation(zoom_in);
       // tvDocFile.startAnimation(zoom_in);

        ivCamera.startAnimation(hyperspaceJumpAnimation);
        ivGallery.startAnimation(hyperspaceJumpAnimation);
        ivPdfFile.startAnimation(hyperspaceJumpAnimation);
      //  ivDocFile.startAnimation(hyperspaceJumpAnimation);

        ivCamera.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA,dialog));
        ivGallery.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA, dialog));

        ivPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null)
                    listener.onPdfOptionClicked();
            }
        });

        ivDocFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null)
                    listener.onDocumentOptionClicked();
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

    private class ScanButtonClickListener implements View.OnClickListener
    {
        Dialog dialog;
        private int preference;

        public ScanButtonClickListener(int preference)
        {
            this.preference = preference;


        }

        public ScanButtonClickListener(int preference, Dialog dialog)
        {
            this.preference = preference;
            this.dialog = dialog;

        }

        public ScanButtonClickListener()
        {
        }

        @Override
        public void onClick(View v)
        {
            this.dialog.dismiss();
            startScan(preference);
        }
    }

    protected void startScan(int preference)
    {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }
    private void showFilePickerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                return;
            }
        }

        showImagePickDialog(RequestedServicesActivity.this, getString(R.string.upload_image), new ImageDialogActionListener() {
            @Override
            public void onCameraOptionClicked(Dialog dialog) {
            }

            @Override
            public void onGalleryOptionClicked() {
            }

            @Override
            public void onPdfOptionClicked() {
                Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mediaIntent.setType("application/pdf"); //set mime type as per requirement
                startActivityForResult(mediaIntent, PICK_PDF);
            }

            @Override
            public void onDocumentOptionClicked() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), PICK_DOC_FILE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(RequestedServicesActivity.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:

                try
                {
                    Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    String uriFile = Helper.getPath(uri, RequestedServicesActivity.this);
                    Log.d("***TEST1",""+uriFile);
                    Log.d("***file1",""+uriFile);
                    attachmentCounter++;
                    txtErrorMessage.setText(attachmentCounter+" l'allegato è stato aggiunto.");

//                    Toast.makeText(getApplicationContext(),"Attachment is added")
                    //getSelectedFilePath(uriFile);
                   // addSelectedFileToDyanamicsMap(uriFile);
                    UploadedImageModule u1=new UploadedImageModule();
                    u1.setFileName("camera");
                    u1.setFileUrl(uriFile);
                    uploadedImageModuleArrayList.add(u1);
//                    CustomDialogManager.showOkDialog(RequestedServicesActivity.this, getString(R.string.photo_was_saved_successfully), new DialogListener() {
//                        @Override
//                        public void onButtonClicked(int type) {
//
//                        }
//                    });
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;

            case PICK_FROM_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    String imagePathToCropActivity;
                    final boolean isCamera;
                    if (data == null) {
                        isCamera = true;
                    } else {
                        final String action = data.getAction();
                        if (action == null) {
                            isCamera = false;
                        } else {
                            isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        }
                    }
                    Uri selectedImageUri;
                    if (isCamera) {
                        selectedImageUri = outputFileUri;
                    } else {
                        selectedImageUri = data == null ? null : data.getData();
                    }
                    imagePathToCropActivity = Helper.getPath(selectedImageUri, RequestedServicesActivity.this);

                    if (TextUtils.isEmpty(imagePathToCropActivity)) {
                        imagePathToCropActivity = Helper.getPath(selectedImageUri, RequestedServicesActivity.this);
                    }
                    Intent intentGallery = new Intent(RequestedServicesActivity.this, ImageCropActivity.class);
                    if (imagePathToCropActivity != null && !imagePathToCropActivity.isEmpty()) {
                        intentGallery.putExtra("filepath", imagePathToCropActivity);
                    } else {
                        imagePathToCropActivity = Helper.getImagePathFromGalleryAboveKitkat(RequestedServicesActivity.this, selectedImageUri);
                        if (imagePathToCropActivity == null) {
                            Helper.showCropDialog(getString(R.string.select_image_from_device_folders), RequestedServicesActivity.this);
                            return;
                        }
                        intentGallery.putExtra("filepath", imagePathToCropActivity);
                    }
                    intentGallery.putExtra("isCamera", false);
                    startActivityForResult(intentGallery, CROP_IMAGE);
                }
                break;

            case CROP_IMAGE:
                filePath =new FileOperations().getPath(getApplicationContext(), data.getData());
                Log.d("***file2",""+filePath);
                attachmentCounter++;
                txtErrorMessage.setText(attachmentCounter+" attachment is added.");
                UploadedImageModule u1=new UploadedImageModule();
                u1.setFileName("camera");
                u1.setFileUrl(filePath);
                uploadedImageModuleArrayList.add(u1);
                break;

            case PICK_PDF:
                filePath =new FileOperations().getPath(getApplicationContext(), data.getData());
                Log.d("***file3",""+filePath);
                attachmentCounter++;
                txtErrorMessage.setText(attachmentCounter+" attachment is added.");
                UploadedImageModule u2=new UploadedImageModule();
                u2.setFileName("camera");
                u2.setFileUrl(filePath);
                uploadedImageModuleArrayList.add(u2);
              //  addSelectedFileToDyanamicsMap(filePath);
                break;

            case PICK_DOC_FILE:
                filePath = new FileOperations().getPath(getApplicationContext(), data.getData());
                Log.d("***file4",""+filePath);
                attachmentCounter++;
                txtErrorMessage.setText(attachmentCounter+" attachment is added.");
                UploadedImageModule u3=new UploadedImageModule();
                u3.setFileName("camera");
                u3.setFileUrl(filePath);
                uploadedImageModuleArrayList.add(u3);
              //  addSelectedFileToDyanamicsMap(filePath);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
