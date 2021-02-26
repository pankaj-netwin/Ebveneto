package com.ebveneto.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Mayur on 05-05-2017.
 */
public class WebviewActivity extends BaseActivity {
    Dialog mDialog;
    private WebView wvCMSDetails;
    private String mTitle;

    String url ="";
    private LinearLayout llCMS;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final int RESULT_OK = 2;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_webview);
        initialise();
        if (HttpHelper.isNetworkAvailable(WebviewActivity.this)) {
            if (getIntent().getExtras() != null) {
                String option = getIntent().getExtras().getString("screen");
                getCmsUrl(option);
            }
        }else {
            CustomDialogManager.showOkDialog(WebviewActivity.this, getString(R.string.no_internet_connection), new DialogListener()
            {
                @Override
                public void onButtonClicked(int type)
                {

                }
            });
        }
    }

    private void initialise()
    {
        wvCMSDetails = (WebView) findViewById(R.id.wvCMSDetails);
        if (Build.VERSION.SDK_INT >= 19)
            wvCMSDetails.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        else
            wvCMSDetails.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wvCMSDetails.getSettings().setLoadWithOverviewMode(true);
        wvCMSDetails.getSettings().setJavaScriptEnabled(true);
        wvCMSDetails.setBackgroundColor(Color.TRANSPARENT);

        wvCMSDetails.getSettings().setJavaScriptEnabled(true);
        wvCMSDetails.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvCMSDetails.setWebViewClient(new WebViewClient());
        wvCMSDetails.getSettings().setPluginState(WebSettings.PluginState.OFF);
        wvCMSDetails.getSettings().setLoadWithOverviewMode(true);
        wvCMSDetails.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvCMSDetails.getSettings().setUseWideViewPort(true);
        wvCMSDetails.getSettings().setUserAgentString("Android Mozilla/5.0 AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        wvCMSDetails.getSettings().setAllowFileAccess(true);
        wvCMSDetails.getSettings().setAllowFileAccess(true);
        wvCMSDetails.getSettings().setAllowContentAccess(true);
        wvCMSDetails.getSettings().supportZoom();

        wvCMSDetails.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Helper.Log("url", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        wvCMSDetails.setWebChromeClient(new ChromeClient());
        //  wvCMSDetails.setWebChromeClient(new ChromeClient());
    }


    public class ChromeClient extends WebChromeClient
    {

        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(WebviewActivity.this.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("*/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;

        }

        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            mUploadMessage = uploadMsg;
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard

            File imageStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES)
                    , "AndroidExampleFolder");

            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }

            // Create camera captured image file path and name
            File file = new File(
                    imageStorageDir + File.separator + "IMG_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");

            mCapturedImageURI = Uri.fromFile(file);

            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            // Create file chooser intent
            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                    , new Parcelable[] { captureIntent });

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);


        }

        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture) {

            openFileChooser(uploadMsg, acceptType);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;

        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            if (requestCode == FILECHOOSER_RESULTCODE) {

                if (null == this.mUploadMessage) {
                    return;
                }

                Uri result = null;

                try {
                    if (resultCode != RESULT_OK) {

                        result = null;

                    } else {

                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }

                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }

        return;
    }

    private void downloadPdf()
    {
        wvCMSDetails.setWebViewClient(new WebViewClient()
        {
            Dialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url)
            {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                if (progressDialog == null)
                {
                    progressDialog = CustomDialogManager.showProgressDialog(WebviewActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url)
            {
                try
                {
                    if (progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(browserIntent);

                } catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }


    private void getCmsUrl(String option){
        switch (option){
            case "Dipendenti":
                mTitle = option;
              //  String url = "http://www.ebveneto.it/it/web-view-dipendenti-273a1-ita.php?tab_Flag=dipendenti&tabella=Aziende&user_id="+00097A AppSession.getInstance().getUserId(WebviewActivity.this);
              //  getCMS(AppDataHolder.getSession(WebviewActivity.this).getDeviceId(),1);
                                 //    "http://www.ebveneto.it/it/web-view-dipendenti-273a1-ita.php?tab_Flag=dipendenti&tabella=<objuserDetails.data.tabella>&user_id=<strUserId>”
                wvCMSDetails.loadUrl("http://www.ebveneto.it/it/web-view-dipendenti-273a1-ita.php?tab_Flag=dipendenti&tabella=Aziende&user_id="+ AppSession.getInstance().getUserId(WebviewActivity.this));
                break;
            case "Qualifiche Dipendenti":
                mTitle = option;
                wvCMSDetails.loadUrl("http://www.ebveneto.it/it/elenco-dip-lavori-276a1-ita.php?tab_flag=dipendenti_qua&tabella=Aziende&idAZ="+ AppSession.getInstance().getUserId(WebviewActivity.this));
                break;

            case "EBVF Card":
                mTitle = option;
                String userType = AppSession.getInstance().getTable(WebviewActivity.this);
                String userId = AppSession.getInstance().getUserId(WebviewActivity.this);
            //    http://www.ebveneto.it/it/ant_ebvcard-281a1-ita.php?tabella=Aziende&user_id=002E6A
                wvCMSDetails.loadUrl("http://www.ebveneto.it/it/ebvcard-281a1-ita.php?tabella="+ userType+"&user_id="+userId);
                downloadPdf();
                break;

            case "modifica_i_dati":
                wvCMSDetails.loadUrl("http://www.ebveneto.it//it/gestione-utenti/gestione-utenti-183a1-ita.php");
                break;


        }
    }



}
