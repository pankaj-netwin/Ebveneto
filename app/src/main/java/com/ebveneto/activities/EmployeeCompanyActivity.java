package com.ebveneto.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ebveneto.R;
import com.ebveneto.custom_views.CustomTextViewBold;
import com.ebveneto.custom_views.CustomTextViewLight;
import com.ebveneto.interfaces.DateListener;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.interfaces.ImageDialogActionListener;
import com.ebveneto.models.Country;
import com.ebveneto.models.Location;
import com.ebveneto.models.OtherInformatioData;
import com.ebveneto.models.Parente;
import com.ebveneto.utils.AppSession;
import com.ebveneto.utils.FileOperations;
import com.ebveneto.webservices.ApiClient;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class EmployeeCompanyActivity extends BaseActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int PERMISSION_STORAGE = 3;
    private static final int CROP_IMAGE = 4;
    private static final int PICK_DOC_FILE = 5;
    private static final int PICK_PDF = 6;
    private static final int HASHMAP_SIZE = 3;
    private static final int REQUEST_CODE = 99;
    LinearLayout llSendInquiry, llAddDynamicNewVoice, llQualifica, llLivello, llCCNL, llCompanyDropDown, llFamilyStatusorCertificate, llPediatricDoctororHospitalCertificate, llAbsensefromWork, llOtherInformation, llIsPagmentoParent, llChooseProfileDropDown, llSectionC, llBustaPage, llAnnose;
    EditText etYearAndMonthCheck, etIBANumber, etCourseTitle, etParentela,
            etParentelaCognome, etParentelaNome, etParentelaBirthplace, etParentelaBirthDate, etParentelaTaxCode, etCourseHours;
    TextView tvChooseProfile, tvChooseProfileList, tvCountryofBirthDropDown;
    int getMonthsDiffernce;
    int button_count = 1;
    RadioGroup radioGroupSendInformation, radioGroupSendSMS;
    RadioButton rbSendInfoYes, rbSendInfoNo, rbSendSMSYes, rbSendSMSNo;
    View dynamic_view, view_invoice, view_invoice_fatture, viewQualifica, viewLivello, viewCCNL;
    Button btnBustaPagaCarica, btnStatoDiCarica, btnCertificatoMedicoCarica, btnAttestazioneCarica, btnFatturaRicevuta, btnChargeAddNewVoice;
    TextView tvNome, tvCognome, tvLuogodiNascita, tvCof, tvDatadiNascita, tvIndirizzo, tvCitta, tvProvincia, tvEmail, tvTelephone, tvCellulare, tvChargeInVoice, tvUserNotice, tvCompanyDropDown, tvAllegato1, tvAllegato2, tvAllegato3, tvCourseTitle, tvCourseHours;
    TextView tvBusinessName, tvVATNumber, tvCodTaxCompany, tvRegisteredOfficeAddress, tvCap, tvCommon, tvCompanyProvincia, tvCompanyTelephone, tvFax, tvCompanyEmail, tvCompanyContact, tvBusinessArea, /*tvNumberofDaysofAbsence,*/
            tvSendRequest, tvCompanyDropDownKey;
    Uri fileUri = null, outputFileUri = null;
    int position;
    String getProfileYear = "", getProfileMonth = "";
    String bpFile, stato_di_famiglia_oFile, certificateFile, absentCertificateFile, rimborso_1File, rimborso_2File, rimborso_3File, rimborso_4File, rimborso_5File,
            rimborso_6File, rimborso_7File, rimborso_8File, rimborso_9File, rimborso_10File, listDependentiFile, CiFile, VisurFile, Allegato4file, codice_fiscale;
    String filePath, beneficiario = "";
    ArrayList<String> invoiceList;
    HashMap<Integer, String> hashMap;
    ArrayList<Location> locationList;
    ArrayList<Parente> parenteList;
    ArrayList<OtherInformatioData> otherInfoList;
    ArrayList<Country> countryList;
    int selectedFileNumber;

    View viewFamilyStato, viewCertificatoMedico, viewAbsentFromWork, viewCompanyDropDown,
            viewBustaPaga, viewAnnoEMesse, viewCourse, viewSuspension, viewDataFine;
    boolean isBustaPaga = false, isPagmento = false, isListaDependanti = false, isCiFile = false, isVisuraFile = false,
            isStatoFamilglia = false, isCertificato = false, isCodiceFiscaleFile;
    String dynamiccorso = "", dynamicoredelcorso = "", dynamicSuspension = "", dynamicDataFine = "";
    ImageView ivCalendar, ivCiFileCalendar, ivVisura;
    String declarationText = "", intialText = "";
    String dynamicValue1 = "", dynamicValue2 = "", dynamicValue3 = "", dynamicValue4 = "";
    String selectedAllegatoFile = "", selectedAllegatoDisplayNames = "", campiSezDFile = "", testiSezDFile = "", testiSezDFileWithHTML = "";

    LinearLayout llCiFile1, llCiFile2, llVisura1, llVisura2;
    EditText etCiFileCalendar, etVisura;
    Button btnCiFileCarica, btnVisuraCarica, btnListaDependenti;

    LinearLayout llSuspension, llDataFine, llListaDependati, llCourseTitle;
    TextView tvSuspensionTitle, tvDataFineTitle, tvListaDependati, tvTipoList;
    EditText etSuspensionTitle, etDataFineTitle;
    View viewListaDependati, viewCiFile1, viewCiFile2, viewVisura1, viewVisura2;
    boolean iscampiSezDValidation = false;
    boolean isCompanySelected = false;
    String parentelaDay = "";
    String parentelaMonth = "";
    String parentelaYear = "";
    boolean isNuovo = false;
    boolean isProfileIBAN = false;
    TextView tvFileSavedBustaPaga, tvFileSavedStatoDiCatica, tvFileSavedFattura,
            tvFileSavedCertificatoMedico, tvFileSavedAttestazione, tvFileCifile, tvFileSaveVisurafile, tvFileSavedLista;
    TextView tvFileSavedDynamic;
    //Added by Sanket
    HashMap<String, TypedFile> dynamicFiles = new HashMap<>();
    String selectedFileKey;
    List<TextView> tvUploadSuccessList;
    TextView tvUploadSuccess;
    ImageView mmyyCalender;
    int Perc = 0, remaingPercent = 0;
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(EmployeeCompanyActivity.this, "Permission Denied \n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }

    };
    PieChart pieChart;
    private TextView tvServiceName;
    private LinearLayout llAllegato4;
    private TextView tvAllegato4;
    private Button btnAllegato4Carica;
    private TextView tvFileAllegato4;
    private View viewAllegato4;
    private boolean isAllegato4;
    private LinearLayout llAllegato, llAttachedDocument;
    private String isPagamento;
    LinearLayout chartLayout;
    CustomTextViewBold txtPieChartTitle,txtAlertMessage;
    CustomTextViewLight txtBudgetDisponibilel,txtBudgetErogato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplyoe_company);
        initialise();
        getServicesDetails(EmployeeCompanyActivity.this);
        updateDeclaration();

    }

    private void initialise() {
        llSendInquiry = (LinearLayout) findViewById(R.id.llSendInquiry);
        llSendInquiry.setOnClickListener(this);
        chartLayout= (LinearLayout) findViewById(R.id.chartLayout);
        txtPieChartTitle= (CustomTextViewBold) findViewById(R.id.txtPieChartTitle);
        llAddDynamicNewVoice = (LinearLayout) findViewById(R.id.llAddDynamicNewVoice);
        llAddDynamicNewVoice.setOnClickListener(this);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvCognome = (TextView) findViewById(R.id.tvCognome);
        tvLuogodiNascita = (TextView) findViewById(R.id.tvLuogodiNascita);
        tvCof = (TextView) findViewById(R.id.tvCof);
        tvDatadiNascita = (TextView) findViewById(R.id.tvDatadiNascita);
        tvIndirizzo = (TextView) findViewById(R.id.tvIndirizzo);
        tvCitta = (TextView) findViewById(R.id.tvCitta);
        tvProvincia = (TextView) findViewById(R.id.tvProvincia);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvTelephone = (TextView) findViewById(R.id.tvTelephone);
        tvCellulare = (TextView) findViewById(R.id.tvCellulare);

        tvBusinessName = (TextView) findViewById(R.id.tvBusinessName);
        tvVATNumber = (TextView) findViewById(R.id.tvVATNumber);
        tvCodTaxCompany = (TextView) findViewById(R.id.tvCodTaxCompany);
        tvRegisteredOfficeAddress = (TextView) findViewById(R.id.tvRegisteredOfficeAddress);
        tvCap = (TextView) findViewById(R.id.tvCap);
        tvCommon = (TextView) findViewById(R.id.tvCommon);
        tvCompanyProvincia = (TextView) findViewById(R.id.tvCompanyProvincia);
        tvCompanyTelephone = (TextView) findViewById(R.id.tvCompanyTelephone);
        tvFax = (TextView) findViewById(R.id.tvFax);
        tvCompanyEmail = (TextView) findViewById(R.id.tvCompanyEmail);
        tvCompanyContact = (TextView) findViewById(R.id.tvCompanyContact);
        tvBusinessArea = (TextView) findViewById(R.id.tvBusinessArea);
        /* tvNumberofDaysofAbsence = (TextView) findViewById(R.id.tvNumberofDaysofAbsence);*/
        tvUserNotice = (TextView) findViewById(R.id.tvUserNotice);

        etYearAndMonthCheck = (EditText) findViewById(R.id.etYearAndMonthCheck);
        etIBANumber = (EditText) findViewById(R.id.etIBANumber);

        btnBustaPagaCarica = (Button) findViewById(R.id.btnBustaPagaCarica);
        btnBustaPagaCarica.setOnClickListener(this);

        btnStatoDiCarica = (Button) findViewById(R.id.btnStatoDiCarica);
        btnStatoDiCarica.setOnClickListener(this);

        btnCertificatoMedicoCarica = (Button) findViewById(R.id.btnCertificatoMedicoCarica);
        btnCertificatoMedicoCarica.setOnClickListener(this);

        btnAttestazioneCarica = (Button) findViewById(R.id.btnAttestazioneCarica);
        btnAttestazioneCarica.setOnClickListener(this);

        btnFatturaRicevuta = (Button) findViewById(R.id.btnFatturaRicevuta);
        btnFatturaRicevuta.setOnClickListener(this);

        tvSendRequest = (TextView) findViewById(R.id.tvSendRequest);
        tvSendRequest.setOnClickListener(this);

        btnChargeAddNewVoice = (Button) findViewById(R.id.btnChargeAddNewVoice);
        btnChargeAddNewVoice.setOnClickListener(this);

        view_invoice = (View) findViewById(R.id.view_invoice);
        view_invoice.setVisibility(View.VISIBLE);
        view_invoice_fatture = (View) findViewById(R.id.view_invoice_fatture);
        tvChargeInVoice = (TextView) findViewById(R.id.tvChargeInVoice);
        tvChargeInVoice.setOnClickListener(this);

        radioGroupSendInformation = (RadioGroup) findViewById(R.id.radioGroupSendInformation);
        rbSendInfoYes = (RadioButton) findViewById(R.id.rbSendInfoYes);
        rbSendInfoNo = (RadioButton) findViewById(R.id.rbSendInfoNo);

        radioGroupSendSMS = (RadioGroup) findViewById(R.id.radioGroupSendSMS);
        rbSendSMSYes = (RadioButton) findViewById(R.id.rbSendSMSYes);
        rbSendSMSNo = (RadioButton) findViewById(R.id.rbSendSMSNo);
        tvServiceName = (TextView) findViewById(R.id.tvServiceName);
        tvServiceName.setText(AppSession.getInstance().getServicename(EmployeeCompanyActivity.this).toUpperCase());

        llQualifica = (LinearLayout) findViewById(R.id.llQualifica);
        llLivello = (LinearLayout) findViewById(R.id.llLivello);
        llCCNL = (LinearLayout) findViewById(R.id.llCCNL);
        viewQualifica = (View) findViewById(R.id.viewQualifica);
        viewLivello = (View) findViewById(R.id.viewLivello);
        viewCCNL = (View) findViewById(R.id.viewCCNL);
        tvCompanyDropDown = (TextView) findViewById(R.id.tvCompanyDropDown);
        tvCompanyDropDown.setOnClickListener(this);

        llCompanyDropDown = (LinearLayout) findViewById(R.id.llCompanyDropDown);
        tvAllegato1 = (TextView) findViewById(R.id.tvAllegato1);
        tvAllegato2 = (TextView) findViewById(R.id.tvAllegato2);
        tvAllegato3 = (TextView) findViewById(R.id.tvAllegato3);

        llFamilyStatusorCertificate = (LinearLayout) findViewById(R.id.llFamilyStatusorCertificate);
        llPediatricDoctororHospitalCertificate = (LinearLayout) findViewById(R.id.llPediatricDoctororHospitalCertificate);
        llAbsensefromWork = (LinearLayout) findViewById(R.id.llAbsensefromWork);

        viewFamilyStato = (View) findViewById(R.id.viewFamilyStato);
        viewCertificatoMedico = (View) findViewById(R.id.viewCertificatoMedico);
        viewAbsentFromWork = (View) findViewById(R.id.viewAbsentFromWork);
        viewCompanyDropDown = (View) findViewById(R.id.viewCompanyDropDown);

        llOtherInformation = (LinearLayout) findViewById(R.id.llOtherInformation);
        tvCourseTitle = (TextView) findViewById(R.id.tvCourseTitle);
        tvCourseHours = (TextView) findViewById(R.id.tvCourseHours);
        viewCourse = (View) findViewById(R.id.viewCourse);

        llIsPagmentoParent = (LinearLayout) findViewById(R.id.llIsPagmentoParent);
        etCourseTitle = (EditText) findViewById(R.id.etCourseTitle);
        etCourseHours = (EditText) findViewById(R.id.etCourseHours);

        llChooseProfileDropDown = (LinearLayout) findViewById(R.id.llChooseProfileDropDown);
        tvChooseProfile = (TextView) findViewById(R.id.tvChooseProfile);
        tvChooseProfileList = (TextView) findViewById(R.id.tvChooseProfileList);
        tvChooseProfileList.setOnClickListener(this);

        etParentela = (EditText) findViewById(R.id.etParentela);
        etParentelaCognome = (EditText) findViewById(R.id.etParentelaCognome);
        etParentelaNome = (EditText) findViewById(R.id.etParentelaNome);
        etParentelaBirthplace = (EditText) findViewById(R.id.etParentelaBirthplace);
        etParentelaBirthDate = (EditText) findViewById(R.id.etParentelaBirthDate);
        etParentelaBirthDate.setOnClickListener(this);
        tvCountryofBirthDropDown = (TextView) findViewById(R.id.tvCountryofBirthDropDown);
        tvCountryofBirthDropDown.setOnClickListener(this);
        etParentelaTaxCode = (EditText) findViewById(R.id.etParentelaTaxCode);
        llSectionC = (LinearLayout) findViewById(R.id.llSectionC);

        llBustaPage = (LinearLayout) findViewById(R.id.llBustaPage);
        llAnnose = (LinearLayout) findViewById(R.id.llAnnose);
        viewBustaPaga = (View) findViewById(R.id.viewBustaPaga);
        viewAnnoEMesse = (View) findViewById(R.id.viewAnnoEMesse);

        ivCalendar = (ImageView) findViewById(R.id.ivCalendar);
        ivCalendar.setOnClickListener(this);

        ivCiFileCalendar = (ImageView) findViewById(R.id.ivCiFileCalendar);
        ivCiFileCalendar.setOnClickListener(this);

        ivVisura = (ImageView) findViewById(R.id.ivVisura);
        ivVisura.setOnClickListener(this);

        mmyyCalender = (ImageView) findViewById(R.id.mmyyCalender);
        mmyyCalender.setOnClickListener(this);


        tvCompanyDropDownKey = (TextView) findViewById(R.id.tvCompanyDropDownKey);

        llCiFile1 = (LinearLayout) findViewById(R.id.llCiFile1);
        llCiFile2 = (LinearLayout) findViewById(R.id.llCiFile2);
        llVisura1 = (LinearLayout) findViewById(R.id.llVisura1);
        llVisura2 = (LinearLayout) findViewById(R.id.llVisura2);
        etCiFileCalendar = (EditText) findViewById(R.id.etCiFileCalendar);
        etVisura = (EditText) findViewById(R.id.etVisura);
        btnCiFileCarica = (Button) findViewById(R.id.btnCiFileCarica);
        btnCiFileCarica.setOnClickListener(this);
        btnVisuraCarica = (Button) findViewById(R.id.btnVisuraCarica);
        btnVisuraCarica.setOnClickListener(this);

        llSuspension = (LinearLayout) findViewById(R.id.llSuspension);
        llDataFine = (LinearLayout) findViewById(R.id.llDataFine);

        tvSuspensionTitle = (TextView) findViewById(R.id.tvSuspensionTitle);
        tvDataFineTitle = (TextView) findViewById(R.id.tvDataFineTitle);

        etDataFineTitle = (EditText) findViewById(R.id.etDataFineTitle);
        etSuspensionTitle = (EditText) findViewById(R.id.etSuspensionTitle);

        viewSuspension = (View) findViewById(R.id.viewSuspension);
        viewDataFine = (View) findViewById(R.id.viewDataFine);
        llListaDependati = (LinearLayout) findViewById(R.id.llListaDependati);
        tvListaDependati = (TextView) findViewById(R.id.tvListaDependati);
        btnListaDependenti = (Button) findViewById(R.id.btnListaDependenti);
        btnListaDependenti.setOnClickListener(this);
        viewListaDependati = (View) findViewById(R.id.viewListaDependati);
        viewCiFile1 = (View) findViewById(R.id.viewCiFile1);
        viewCiFile2 = (View) findViewById(R.id.viewCiFile2);
        viewVisura1 = (View) findViewById(R.id.viewVisura1);
        viewVisura2 = (View) findViewById(R.id.viewVisura2);
        tvTipoList = (TextView) findViewById(R.id.tvTipoList);
        llCourseTitle = (LinearLayout) findViewById(R.id.llCourseTitle);
        tvFileSavedBustaPaga = (TextView) findViewById(R.id.tvFileSavedBustaPaga);
        tvFileSavedBustaPaga.setVisibility(View.GONE);
        tvFileSavedStatoDiCatica = (TextView) findViewById(R.id.tvFileSavedStatoDiCatica);
        tvFileSavedStatoDiCatica.setVisibility(View.GONE);
        tvFileSavedFattura = (TextView) findViewById(R.id.tvFileSavedFattura);
        tvFileSavedCertificatoMedico = (TextView) findViewById(R.id.tvFileSavedCertificatoMedico);
        tvFileSavedCertificatoMedico.setVisibility(View.GONE);
        tvFileSavedAttestazione = (TextView) findViewById(R.id.tvFileSavedAttestazione);
        tvFileSavedAttestazione.setVisibility(View.GONE);
        tvFileCifile = (TextView) findViewById(R.id.tvFileCifile);
        tvFileCifile.setVisibility(View.GONE);
        tvFileSaveVisurafile = (TextView) findViewById(R.id.tvFileSaveVisurafile);
        tvFileSaveVisurafile.setVisibility(View.GONE);
        tvFileSavedLista = (TextView) findViewById(R.id.tvFileSavedLista);
        tvFileSavedLista.setVisibility(View.GONE);

        txtBudgetDisponibilel = (CustomTextViewLight) findViewById(R.id.txtBudgetDisponibile);
        txtBudgetErogato = (CustomTextViewLight) findViewById(R.id.txtBudgetErogato);
        txtAlertMessage = (CustomTextViewBold) findViewById(R.id.txtAlertMessage);
        //setOtherInfoText();

        if (invoiceList == null) {
            invoiceList = new ArrayList<>();
        }
        if (locationList == null) {
            locationList = new ArrayList<>();
        }
        if (otherInfoList == null) {
            otherInfoList = new ArrayList<>();
        }
        if (parenteList == null) {
            parenteList = new ArrayList<>();
        }

        if (countryList == null) {
            countryList = new ArrayList<>();
        }
        if (hashMap == null) {
            hashMap = new HashMap<>();
            for (int i = 0; i < HASHMAP_SIZE; i++) {
                hashMap.put(i, null);
            }
        }

        if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
            beneficiario = "Dipendente";
        } else {
            beneficiario = "Azienda";
        }
        if (beneficiario.equals("Azienda")) {
            companyView();
        } else {
            hideCompanyView();
        }

        llAllegato4 = (LinearLayout) findViewById(R.id.llAllegato4);
        tvAllegato4 = (TextView) findViewById(R.id.tvAllegato4);
        tvFileAllegato4 = (TextView) findViewById(R.id.tvFileAllegato4);
        viewAllegato4 = (View) findViewById(R.id.viewAllegato4);

        btnAllegato4Carica = (Button) findViewById(R.id.btnAllegato4Carica);
        btnAllegato4Carica.setOnClickListener(this);

        llAllegato = (LinearLayout) findViewById(R.id.llAllegato);
        llAttachedDocument = (LinearLayout) findViewById(R.id.llAttachedDocument);

        tvNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  showGraph(Perc, remaingPercent);
            }
        });
    }

    public void showGraph(int used, int remaing) {
//        pieChart = (PieChart) findViewById(R.id.pie_chart);
//        List<ChartData> data = new ArrayList<>();
//        data.add(new ChartData("", 20, Color.WHITE, Color.parseColor("#ff0000")));
//        data.add(new ChartData("", 80, Color.WHITE, Color.parseColor("#00cc00")));
//        Log.d("***USED", "" + data.size()+used+remaing);
//        pieChart.setChartData(data);

        pieChart = findViewById(R.id.piechart);
        pieChart.addPieSlice(new PieModel("", remaing, Color.parseColor("#2A8B21")));
        pieChart.addPieSlice(new PieModel("", used, Color.parseColor("#B81511")));
        pieChart.setInnerValueString(String.valueOf(used)+"%");
        pieChart.startAnimation();

    }

    private void updateDeclaration() {
        if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
            isProfileIBAN = true;
            intialText = getString(R.string.ll_scotto) + " "
                    + AppSession.getInstance().getUserCognome(EmployeeCompanyActivity.this) + " " +
                    AppSession.getInstance().getUserNome(EmployeeCompanyActivity.this) + ", appartenente all'azienda" + " " +
                    AppSession.getInstance().getRegion(EmployeeCompanyActivity.this) + " "
                    + "essendo a conoscenza del regolamento dei servizi dell'Ente Bilaterale Veneto FVG, ";
        } else {
            isProfileIBAN = false;
            intialText = getString(R.string.ll_scotto) + " "
                    + AppSession.getInstance().getUserCognome(EmployeeCompanyActivity.this) + " " +
                    AppSession.getInstance().getUserNome(EmployeeCompanyActivity.this) + ", legale rappresentante dell'azienda " + " " +
                    AppSession.getInstance().getRegion(EmployeeCompanyActivity.this) + " "
                    + "essendo a conoscenza del regolamento dei servizi dell'Ente Bilaterale Veneto FVG, ";
        }
        tvUserNotice.setText(intialText);


    }

    private void companyView() {
        llQualifica.setVisibility(View.VISIBLE);
        llLivello.setVisibility(View.VISIBLE);
        llCCNL.setVisibility(View.VISIBLE);
        viewQualifica.setVisibility(View.VISIBLE);
        viewLivello.setVisibility(View.VISIBLE);
        viewCCNL.setVisibility(View.VISIBLE);
    }

    private void hideCompanyView() {
        llQualifica.setVisibility(View.GONE);
        llLivello.setVisibility(View.GONE);
        llCCNL.setVisibility(View.GONE);
        viewQualifica.setVisibility(View.GONE);
        viewLivello.setVisibility(View.GONE);
        viewCCNL.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAllegato4Carica:
                selectedFileNumber = 17;
                showFilePickerDialog();
                break;

            case R.id.tvSendRequest:
                validatePrivacyPolicies();
                break;
            case R.id.btnBustaPagaCarica:
                selectedFileNumber = 0;
                selectedFileKey = (String) view.getTag();
                showFilePickerDialog();
                break;

            case R.id.btnStatoDiCarica:
                selectedFileNumber = 1;
                showFilePickerDialog();
                break;
            case R.id.btnFatturaRicevuta:
                selectedFileKey = "rimborso_1";
                //selectedFileNumber = 2;
                tvUploadSuccess = tvFileSavedFattura;
                showFilePickerDialog();
                break;
            case R.id.btnCertificatoMedicoCarica:
                selectedFileNumber = 12;
                showFilePickerDialog();
                break;
            case R.id.btnAttestazioneCarica:
                selectedFileNumber = 13;
                showFilePickerDialog();
                break;

            case R.id.btnCiFileCarica:
                selectedFileNumber = 14;
                showFilePickerDialog();
                break;

            case R.id.btnVisuraCarica:
                selectedFileNumber = 15;
                showFilePickerDialog();
                break;

            case R.id.btnListaDependenti:
                selectedFileNumber = 16;
                showFilePickerDialog();
                break;


            case R.id.ivCalendar:
                Helper.showDate(true, getFragmentManager(), new DateListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                        final String date = Helper.showDate(Helper.createStringDate(year, monthOfYear, dayOfMonth));
                        monthOfYear = monthOfYear + 1;
                        if (monthOfYear < 10 && dayOfMonth >= 10) {
                            etParentelaBirthDate.setText(dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear >= 10) {
                            etParentelaBirthDate.setText("0" + dayOfMonth + "/" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear < 10) {
                            etParentelaBirthDate.setText("0" + dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else {
                            etParentelaBirthDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        }
                    }
                });
                break;

            case R.id.ivCiFileCalendar:
                Helper.showDate(false, getFragmentManager(), new DateListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                        final String date = Helper.showDate(Helper.createStringDate(year, monthOfYear, dayOfMonth));
                        monthOfYear = monthOfYear + 1;
                        if (monthOfYear < 10 && dayOfMonth >= 10) {
                            etCiFileCalendar.setText(dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear >= 10) {
                            etCiFileCalendar.setText("0" + dayOfMonth + "/" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear < 10) {
                            etCiFileCalendar.setText("0" + dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else {
                            etCiFileCalendar.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        }
                    }
                });
                break;

            case R.id.ivVisura:
                Helper.showDate(true, getFragmentManager(), new DateListener() {
                    @Override
                    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                        final String date = Helper.showDate(Helper.createStringDate(year, monthOfYear, dayOfMonth));
                        monthOfYear = monthOfYear + 1;
                        if (monthOfYear < 10 && dayOfMonth >= 10) {
                            etVisura.setText(dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear >= 10) {
                            etVisura.setText("0" + dayOfMonth + "/" + monthOfYear + "/" + year);
                        } else if (dayOfMonth < 10 && monthOfYear < 10) {
                            etVisura.setText("0" + dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                        } else {
                            etVisura.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        }
                    }
                });
                break;
            case R.id.mmyyCalender:
//                Helper.showDate(getFragmentManager(), new DateListener() {
//                    @Override
//                    public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
//                        final String date = Helper.showDate(Helper.createStringDate(year, monthOfYear, dayOfMonth));
//                        monthOfYear = monthOfYear + 1;
//                        if (monthOfYear < 10 && dayOfMonth >= 10) {
//                            etYearAndMonthCheck.setText("0" + monthOfYear + "-" + year);
//                        } else if (dayOfMonth < 10 && monthOfYear >= 10) {
//                            etYearAndMonthCheck.setText( monthOfYear + "-" + year);
//                        } else if (dayOfMonth < 10 && monthOfYear < 10) {
//                            etYearAndMonthCheck.setText("0" + monthOfYear + "-" + year);
//                        } else {
//                            etYearAndMonthCheck.setText( monthOfYear + "-" + year);
//                        }
//                    }
//                });
                new RackMonthPicker(this)
                        .setLocale(Locale.ITALIAN)
                        .setPositiveText("OK")
                        .setNegativeText("CANCELLA")
                        .setPositiveButton(new DateMonthDialogListener() {
                            @Override
                            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {
//                                Toast.makeText(EmployeeCompanyActivity.this, ""+month+year, Toast.LENGTH_SHORT).show();
                                //    etYearAndMonthCheck.setText( month + "-" + year);
                                if (month < 10) {
                                    etYearAndMonthCheck.setText("0" + month + "-" + year);
                                } else if (month >= 10) {
                                    etYearAndMonthCheck.setText(month + "-" + year);
                                } else if (month < 10) {
                                    etYearAndMonthCheck.setText("0" + month + "-" + year);
                                } else {
                                    etYearAndMonthCheck.setText(month + "-" + year);
                                }

                            }
                        })
                        .setNegativeButton(new OnCancelMonthDialogListener() {
                            @Override
                            public void onCancel(AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                break;

            case R.id.tvChooseProfileList:
                if (parenteList.isEmpty()) {

                } else {
                    Helper.showDropDown(tvChooseProfileList, new ArrayAdapter<Parente>(EmployeeCompanyActivity.this, android.R.layout.simple_list_item_1, parenteList),
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    Parente parenteItem = parenteList.get(position);
                                    tvChooseProfileList.setText(parenteItem.getParentela());

                                    if (tvChooseProfileList.getText().toString().equals("Nuovo")) {
                                        isNuovo = true;
                                        etParentela.setText("");
                                        etParentelaNome.setText("");
                                        etParentelaCognome.setText("");
                                        etParentelaBirthplace.setText("");
                                        tvCountryofBirthDropDown.setText("");
                                        etParentelaBirthDate.setText("");
                                        etParentelaTaxCode.setText("");
                                    } else {
                                        isNuovo = false;
                                        etParentela.setText(parenteItem.getParentelaName());
                                        etParentelaNome.setText(parenteItem.getNome());
                                        etParentelaCognome.setText(parenteItem.getCognome());
                                        etParentelaBirthplace.setText(parenteItem.getNatoComune());
                                        String natoData = parenteItem.getNatoData();
                                        parentelaDay = natoData.substring(8, natoData.length() - 9);
                                        parentelaMonth = natoData.substring(5, natoData.length() - 12);
                                        parentelaYear = natoData.substring(0, natoData.length() - 15);
                                        etParentelaBirthDate.setText(parentelaDay + "/" + parentelaMonth + "/" + parentelaYear);
                                        tvCountryofBirthDropDown.setText(parenteItem.getNatoProv());
                                        etParentelaTaxCode.setText(parenteItem.getCF());
                                    }


                                }
                            });
                }
                break;

            case R.id.tvCompanyDropDown:
                if (locationList.isEmpty()) {

                } else {
                    Helper.showDropDown(tvCompanyDropDown, new ArrayAdapter<Location>(EmployeeCompanyActivity.this, android.R.layout.simple_list_item_1, locationList),
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    isCompanySelected = true;
                                    Location locationItem = locationList.get(position);
                                    AppSession.getInstance().saveRegion(EmployeeCompanyActivity.this, locationList.get(position).getLocationRagione());
                                    getServicesDetails(EmployeeCompanyActivity.this);
                                    updateDeclaration();
                                    tvCompanyDropDown.setText(locationItem.getLocationTilte());
                                    tvBusinessName.setText(locationItem.getLocationRagione());
                                    tvVATNumber.setText(locationItem.getLocationIVA());
                                    tvCodTaxCompany.setText(locationItem.getLocationcf());
                                    tvRegisteredOfficeAddress.setText(locationItem.getLocationVia());
                                    tvCap.setText(locationItem.getLocationCAP());
                                    tvCommon.setText(locationItem.getLocationComune());
                                    tvCompanyProvincia.setText(locationItem.getLocationProvince());

                                    if (!locationItem.getLocationTelephone().equals("null")) {
                                        tvCompanyTelephone.setText(locationItem.getLocationTelephone());
                                    }
                                    if (!locationItem.getLocationFax().equals("null")) {
                                        tvFax.setText(locationItem.getLocationFax());
                                    }
                                    if (!locationItem.getLocationEmail().equals("null")) {
                                        tvCompanyEmail.setText(locationItem.getLocationEmail());
                                    }

                                    if (!locationItem.getLocationLegalerap().equals("null")) {
                                        tvCompanyContact.setText(locationItem.getLocationAttivia());
                                    }
                                    if (!locationItem.getLocationAttivia().equals("null")) {
                                        tvBusinessArea.setText(locationItem.getLocationAttivia());
                                    }
                                }
                            });
                }
                break;

            case R.id.tvTipoList:

                break;

            case R.id.tvCountryofBirthDropDown:
                if (countryList.isEmpty()) {

                } else {
                    Helper.showDropDown(tvCountryofBirthDropDown, new ArrayAdapter<Country>(EmployeeCompanyActivity.this, android.R.layout.simple_list_item_1, countryList),
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    Country countrytem = countryList.get(position);
                                    tvCountryofBirthDropDown.setText(countrytem.getCountryName());
                                    tvCompanyDropDownKey.setText(countrytem.getCountryKey());
                                }
                            });
                }
                break;


            case R.id.btnChargeAddNewVoice:
                view_invoice.setVisibility(View.GONE);
                view_invoice_fatture.setVisibility(View.VISIBLE);
                button_count++;
                if (button_count <= 10) {
                    dynamic_view = getLayoutInflater().inflate(R.layout.custom_invoice_view, llAddDynamicNewVoice, false);
                    TextView tvInvoice = (TextView) dynamic_view.findViewById(R.id.tvInvoice);
                    tvFileSavedDynamic = (TextView) dynamic_view.findViewById(R.id.tvFileSaveDynamic);
                    final Button btnDynamicInvoice = (Button) dynamic_view.findViewById(R.id.btnDynamicInvoice);
                    dynamic_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dynamic_view.getTag() != null) {
                                position = (int) view.getTag();
                            }
                        }
                    });
                    btnDynamicInvoice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //Rimberso 1 is already added
                            //so start from Rimberso 2
                            tvUploadSuccess = tvFileSavedDynamic;
                            selectedFileKey = "rimborso_" + (position + 2);
                            showFilePickerDialog();
                        }
                    });

                    tvInvoice.setText(getString(R.string.invoice_or_receipt_dynamic) + "" + button_count);
                    llAddDynamicNewVoice.addView(dynamic_view);
                    position = llAddDynamicNewVoice.indexOfChild(dynamic_view);
                    dynamic_view.setTag(position);

                    break;
                } else {
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.upto_10_views), new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                }
                break;
        }
    }

    private void requestForPermission() {
//        new TedPermission()
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .check();
    }

    private void showFilePickerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_STORAGE);
                return;
            }
        }

        showImagePickDialog(EmployeeCompanyActivity.this, getString(R.string.upload_image), new ImageDialogActionListener() {
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
                    Toast.makeText(EmployeeCompanyActivity.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void showImagePickDialog(final Context context, String title, final ImageDialogActionListener listener) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_photo_option, null);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        final TextView tvCamera = (TextView) view.findViewById(R.id.tvCamera);
        final TextView tvGallery = (TextView) view.findViewById(R.id.tvGallery);
        final ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        final ImageView ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        final TextView tvUploadImage = (TextView) view.findViewById(R.id.tvUploadImage);

        final ImageView ivPdfFile = (ImageView) view.findViewById(R.id.ivPdfFile);
        final TextView tvPdfFile = (TextView) view.findViewById(R.id.tvPdfFile);

        final ImageView ivDocFile = (ImageView) view.findViewById(R.id.ivDocFile);
        final TextView tvDocFile = (TextView) view.findViewById(R.id.tvDocFile);

        tvUploadImage.setText(title);

        //Adding animation effects to the view
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.animation_rotate);
        Animation zoom_in = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);
        Animation zoom_animation = AnimationUtils.loadAnimation(context, R.anim.animation_zoom_in);

        tvUploadImage.startAnimation(zoom_animation);
        tvCamera.startAnimation(zoom_in);
        tvGallery.startAnimation(zoom_in);
        tvPdfFile.startAnimation(zoom_in);
        tvDocFile.startAnimation(zoom_in);

        ivCamera.startAnimation(hyperspaceJumpAnimation);
        ivGallery.startAnimation(hyperspaceJumpAnimation);
        ivPdfFile.startAnimation(hyperspaceJumpAnimation);
        ivDocFile.startAnimation(hyperspaceJumpAnimation);

        ivCamera.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA, dialog));
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

    protected void startScan(int preference) {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:

                try {
                    Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    String uriFile = Helper.getPath(uri, EmployeeCompanyActivity.this);
                    //getSelectedFilePath(uriFile);
                    addSelectedFileToDyanamicsMap(uriFile);
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.photo_was_saved_successfully), new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                } catch (IOException e) {
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
                    imagePathToCropActivity = Helper.getPath(selectedImageUri, EmployeeCompanyActivity.this);

                    if (TextUtils.isEmpty(imagePathToCropActivity)) {
                        imagePathToCropActivity = Helper.getPath(selectedImageUri, EmployeeCompanyActivity.this);
                    }
                    Intent intentGallery = new Intent(EmployeeCompanyActivity.this, ImageCropActivity.class);
                    if (imagePathToCropActivity != null && !imagePathToCropActivity.isEmpty()) {
                        intentGallery.putExtra("filepath", imagePathToCropActivity);
                    } else {
                        imagePathToCropActivity = Helper.getImagePathFromGalleryAboveKitkat(EmployeeCompanyActivity.this, selectedImageUri);
                        if (imagePathToCropActivity == null) {
                            Helper.showCropDialog(getString(R.string.select_image_from_device_folders), EmployeeCompanyActivity.this);
                            return;
                        }
                        intentGallery.putExtra("filepath", imagePathToCropActivity);
                    }
                    intentGallery.putExtra("isCamera", false);
                    startActivityForResult(intentGallery, CROP_IMAGE);
                }
                break;

            case CROP_IMAGE:
                break;

            case PICK_PDF:
                filePath = new FileOperations().getPath(getApplicationContext(), data.getData());
                Log.d("***filePDF123", "" + filePath);
                addSelectedFileToDyanamicsMap(filePath);
                break;

            case PICK_DOC_FILE:
                filePath = new FileOperations().getPath(getApplicationContext(), data.getData());
                Log.d("***filePDF124", "" + filePath);
                addSelectedFileToDyanamicsMap(filePath);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SimpleDateFormat")
    private void validatePrivacyPolicies() {
        int selectedInfoId = radioGroupSendInformation.getCheckedRadioButtonId();
        int selectedSMSId = radioGroupSendSMS.getCheckedRadioButtonId();

        RadioButton selectedInfoButton = (RadioButton) findViewById(selectedInfoId);
        RadioButton selectedSMSButton = (RadioButton) findViewById(selectedSMSId);


        if (iscampiSezDValidation) {
            if (otherInfoList.size() == 1) {
                if (TextUtils.isEmpty(etCourseTitle.getText().toString())) {
                    String validationMessage;
                    if (testiSezDFile.length() > 23) {
                        validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    } else {
                        validationMessage = testiSezDFile;
                    }

                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + validationMessage, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseTitle.requestFocus();
                    return;
                }
            } else if (otherInfoList.size() == 2) {

                if (TextUtils.isEmpty(etCourseTitle.getText().toString())) {
                    Log.d("**testEBVN", "" + testiSezDFile.length());
                    Log.d("**testEBVN", "" + testiSezDFile.substring(0, testiSezDFile.length() - 23));
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[0];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseTitle.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etCourseHours.getText().toString())) {
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[1];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseHours.requestFocus();
                    return;
                }

            } else if (otherInfoList.size() == 3) {
                if (TextUtils.isEmpty(etCourseTitle.getText().toString())) {
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[0];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseTitle.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etCourseHours.getText().toString())) {
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[1];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseHours.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(etSuspensionTitle.getText().toString())) {
                    testiSezDFile = testiSezDFile.substring(2, testiSezDFile.length() - 1);
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + "" + testiSezDFile, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etSuspensionTitle.requestFocus();
                    return;
                }
            } else if (otherInfoList.size() == 4) {

                if (TextUtils.isEmpty(etCourseTitle.getText().toString())) {
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[0];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseTitle.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etCourseHours.getText().toString())) {
                    String validationMessage = testiSezDFile.substring(0, testiSezDFile.length() - 23);
                    String[] sortMessage = validationMessage.split(",");
                    String finalValidationText = sortMessage[1];
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + " " + finalValidationText, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etCourseHours.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(etSuspensionTitle.getText().toString())) {
                    testiSezDFile = testiSezDFile.substring(2, testiSezDFile.length() - 1);
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + "" + testiSezDFile, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etSuspensionTitle.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(etDataFineTitle.getText().toString())) {
                    testiSezDFile = testiSezDFile.substring(3, testiSezDFile.length() - 1);
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_course_title) + "" + testiSezDFile, new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    etDataFineTitle.requestFocus();
                    return;
                }
            }
        }

//        if (isBustaPaga) {
//            if (bpFile == null) {
//                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_select_busta_paga_file), new DialogListener() {
//                    @Override
//                    public void onButtonClicked(int type) {
//
//                    }
//                });
//                return;
//            }
//        }
        Log.d("***testCheck", "" + isBustaPaga);
        if (isBustaPaga) {

            Calendar c = Calendar.getInstance();
            int currentDay = c.get(Calendar.DAY_OF_MONTH);

            String checkMonthYear = etYearAndMonthCheck.getText().toString();
            List<String> checkMonthYearArray = Arrays.asList(checkMonthYear.split("-"));
            int checkMonth1 = Integer.parseInt(checkMonthYearArray.get(0));
            String removeLeadingZeros = removeLeadingZeros(String.valueOf(checkMonth1));
            int checkMonth = Integer.parseInt(removeLeadingZeros);
            int checkYear = Integer.parseInt(checkMonthYearArray.get(1));

            String checkDATE = checkYear + "-" + checkMonth + "-" + currentDay;

            Date checkDate = null;
            try {
                checkDate = new SimpleDateFormat("yyyy-MM-dd").parse(checkDATE);
                Date today = new Date();
                long diff = today.getTime() - checkDate.getTime();
                int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                Log.d("*diffrance", "" + numOfDays);

                boolean dateNow = new Date().before(checkDate);
                if (dateNow) {

                } else {
                    if (numOfDays > 90) {
                        CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, "Inserire una busta paga più recente, non più vecchia di 3 mesi", new DialogListener() {
                            @Override
                            public void onButtonClicked(int type) {

                            }
                        });
                        return;
                    } else {

                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (testiSezDFile.contains("Anno e mese busta paga")) {
                if (TextUtils.isEmpty(etYearAndMonthCheck.getText().toString())) {
                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_select_anno_mese), new DialogListener() {
                        @Override
                        public void onButtonClicked(int type) {

                        }
                    });
                    return;
                }
            }
        }
        if (TextUtils.isEmpty(etIBANumber.getText().toString())) {
            CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_iban), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            etIBANumber.requestFocus();
            return;
        }
        if (radioGroupSendInformation.getCheckedRadioButtonId() == -1) {
            CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.service_info), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            return;
        }
        if (radioGroupSendSMS.getCheckedRadioButtonId() == -1) {
            CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.sms), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
            return;
        }
        if (isNuovo) {
            tvChooseProfileList.setText("");
            if (TextUtils.isEmpty(etParentela.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_parentela), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentela.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etParentelaCognome.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_parentelacognome), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentelaCognome.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etParentelaNome.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_parentelanome), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentelaNome.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etParentelaBirthplace.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_parentelabirthplace), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentelaBirthplace.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etParentelaBirthDate.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_parentelabirthdate), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentelaBirthDate.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etParentelaTaxCode.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_cof), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                etParentelaTaxCode.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(tvCountryofBirthDropDown.getText().toString())) {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.please_enter_birthdropdown), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                tvCountryofBirthDropDown.requestFocus();
                return;
            }
        }

//        etCiFileCalendar

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(etCiFileCalendar.getText().toString());
            boolean dateNow = new Date().before(date);
            Log.d("**test", "" + dateNow);

            if (dateNow) {

            } else {
                CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, " La tua carta d'identità risulta scaduta, caricane una più recente.", new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {

                    }
                });
                return;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        saveServicesDetailsWithDyanamicFilesMap(
                EmployeeCompanyActivity.this,
                etYearAndMonthCheck.getText().toString(),
                etIBANumber.getText().toString(),
                selectedInfoButton.getText().toString(),
                selectedSMSButton.getText().toString(),
                tvCompanyDropDown.getText().toString(),
                tvChooseProfileList.getText().toString(),
                etParentela.getText().toString(),
                etParentelaCognome.getText().toString(),
                etParentelaNome.getText().toString(),
                etParentelaBirthplace.getText().toString(),
                etParentelaBirthDate.getText().toString(),
                etParentelaTaxCode.getText().toString(),
                tvCompanyDropDownKey.getText().toString(),
                etCiFileCalendar.getText().toString(),
                etVisura.getText().toString(),
                etCourseTitle.getText().toString(),
                etCourseHours.getText().toString());


    }

    public String removeLeadingZeros(String digits) {
        //String.format("%.0f", Double.parseDouble(digits)) //Alternate Solution
        String regex = "^0+";
        return digits.replaceAll(regex, "");
    }

    //Webservice implementation to get all services details
    private void getServicesDetails(Context context) {
        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getServicesDetails(new TypedString("getServiceDetails"),
                    new TypedString(AppSession.getInstance().getTable(EmployeeCompanyActivity.this)),//table name
                    new TypedString(AppSession.getInstance().getUserId(EmployeeCompanyActivity.this)),//user id
                    new TypedString("1"),
                    new TypedString(AppSession.getInstance().getServiceId(EmployeeCompanyActivity.this)),//service id
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
                                    JSONObject service_object = dataObject.getJSONObject("service");
                                    Log.d("#########", "" + service_object);
                                    if (service_object.has("percent_data")) {
                                        chartLayout.setVisibility(View.VISIBLE);
                                        JSONObject percent_data_object = service_object.getJSONObject("percent_data");
                                        String Anno = percent_data_object.getString("Anno");
                                        String Budget = percent_data_object.getString("Budget");
                                        String Erogato = percent_data_object.getString("Erogato");
                                        String Alert = percent_data_object.getString("Alert");

                                        Perc = Integer.parseInt(percent_data_object.getString("Perc"));
                                        remaingPercent = 100 - Perc;
                                        txtPieChartTitle.setText("BUDGET "+Anno);
                                        showGraph(Perc, remaingPercent);
                                        txtBudgetDisponibilel.setText(Budget+" €");
                                        txtBudgetErogato.setText(Erogato+" €");

                                        if(!TextUtils.isEmpty(Alert)&&!Alert.equals("null")){
                                            txtAlertMessage.setVisibility(View.VISIBLE);
                                            txtAlertMessage.setText(Alert);
                                        }else {
                                            txtAlertMessage.setVisibility(View.GONE);
                                        }

                                    } else {
                                        chartLayout.setVisibility(View.GONE);
                                    }


                                    //fetch all user profile data
                                    JSONObject json_profile_object = new JSONObject(dataObject.toString());
                                    {
                                        if (dataObject.optString("profile").equals("false")) {
                                            isBustaPaga = false;
                                            llBustaPage.setVisibility(View.GONE);
                                            llAnnose.setVisibility(View.GONE);
                                            viewBustaPaga.setVisibility(View.GONE);
                                            viewAnnoEMesse.setVisibility(View.GONE);

                                        } else {
                                            JSONObject profileObject = json_profile_object.getJSONObject("profile");
                                            tvNome.setText(profileObject.optString("Nome"));
                                            tvCognome.setText(profileObject.optString("Cognome"));
                                            if (!profileObject.optString("LuogoNascita").equals("null")) {
                                                tvLuogodiNascita.setText(profileObject.optString("LuogoNascita"));
                                            }
                                            if (!profileObject.optString("cf").equals("null")) {
                                                tvCof.setText(profileObject.optString("cf"));
                                            }
                                            if (!profileObject.optString("dtNascita").equals("null")) {
                                                tvDatadiNascita.setText(profileObject.optString("dtNascita"));
                                            }
                                            if (!profileObject.optString("Via").equals("null")) {
                                                tvIndirizzo.setText(profileObject.optString("Via"));
                                            }
                                            if (!profileObject.optString("Cap").equals("null") && !dataObject.optString("Comune").equals("null") && !dataObject.optString("Frazione").equals("null")) {
                                                tvCitta.setText(profileObject.optString("Cap") + " " + profileObject.optString("Comune") + " " + profileObject.optString("Frazione"));
                                            }
                                            if (!profileObject.optString("Prov").equals("null")) {
                                                tvProvincia.setText(profileObject.optString("Prov"));
                                            }
                                            if (!profileObject.optString("email").equals("null")) {
                                                tvEmail.setText(profileObject.optString("email"));
                                            }
                                            if (!profileObject.optString("tel1").equals("null")) {
                                                tvTelephone.setText(profileObject.optString("tel1"));
                                            }
                                            if (!profileObject.optString("tel2").equals("null")) {
                                                tvCellulare.setText(profileObject.optString("tel2"));
                                            }
                                            if (!profileObject.optString("ciScadenza").equals("null")) {
                                                //2020-07-25
                                                String ciScadenzaDate = profileObject.optString("ciScadenza");
                                                SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
                                                Date d = dateFormatprev.parse(ciScadenzaDate);
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                String changedDate = dateFormat.format(d);
                                                etCiFileCalendar.setText(changedDate);
                                            }

                                            if (isProfileIBAN) {

                                                Log.d("****isProfileIBAN1", "" + isProfileIBAN);

//                                                if(TextUtils.equals(dataObject.optString("IBAN"),"null")){
//                                                    etIBANumber.setText("");
//                                                }else{
//                                                    etIBANumber.setText(dataObject.optString("IBAN"));
//                                                }

                                                if (TextUtils.equals(profileObject.optString("IBAN"), "null")) {
                                                    etIBANumber.setText("");
                                                } else {
                                                    etIBANumber.setText(profileObject.optString("IBAN"));
                                                }


                                            }
                                            if (!profileObject.optString("bpAnnoMese").equals("null")) {
                                                // etYearAndMonthCheck.setText(profileObject.optString("bpAnnoMese"));
                                                String bpAnnoMese = profileObject.optString("bpAnnoMese");
                                                Log.d("*///", bpAnnoMese);
                                                getProfileMonth = bpAnnoMese.substring(4);
                                                getProfileYear = bpAnnoMese.substring(0, bpAnnoMese.length() - 2);
                                                etYearAndMonthCheck.setText(getProfileMonth + "-" + getProfileYear);

                                            } else {
                                                etYearAndMonthCheck.setText("-");
                                            }
                                            Calendar c = Calendar.getInstance();
                                            int current_year = c.get(Calendar.YEAR);
                                            int current_month = c.get(Calendar.MONTH) + 1;
                                            int current_day = c.get(Calendar.DAY_OF_MONTH);

                                            String get_current_year = String.valueOf(current_year);
                                            String get_current_month = String.valueOf(current_month);

                                            String get_current_day = String.valueOf(current_day);
                                            String currentDate = get_current_day + "-" + get_current_month + "-" + get_current_year;

                                            if (etYearAndMonthCheck.getText().toString().equals("") || etYearAndMonthCheck.getText().toString().equals("-")) {
                                                getMonthsDiffernce = 1;

                                            } else {
                                                getMonthsDiffernce = (Helper.getMonthsDifference(currentDate, "01-" + etYearAndMonthCheck.getText().toString()));
                                            }

                                        }
                                    }
                                    if (dataObject.optString("company").equals("false")) {

                                    } else {
                                        JSONObject json_company_object = new JSONObject(dataObject.toString());

                                        JSONObject companyObject = json_company_object.getJSONObject("company");
                                        AppSession.getInstance().saveCompanyId(EmployeeCompanyActivity.this, companyObject.optString("idAZ"));
                                        AppSession.getInstance().saveRegion(EmployeeCompanyActivity.this, companyObject.optString("Ragione"));
                                        if (!isCompanySelected) {
                                            if (!companyObject.optString("Ragione").equals("null")) {
                                                tvCompanyDropDown.setText(companyObject.optString("Ragione"));
                                            }
                                            if (!companyObject.optString("Ragione").equals("null")) {
                                                tvBusinessName.setText(companyObject.optString("Ragione"));
                                            }
                                            if (!companyObject.optString("piva").equals("null")) {
                                                tvVATNumber.setText(companyObject.optString("piva"));
                                            }
                                            if (!companyObject.optString("cf").equals("null")) {
                                                tvCodTaxCompany.setText(companyObject.optString("cf"));
                                            }
                                            if (!companyObject.optString("Via").equals("null")) {
                                                tvRegisteredOfficeAddress.setText(companyObject.optString("Via"));
                                            }
                                            if (!companyObject.optString("Cap").equals("null")) {
                                                tvCap.setText(companyObject.optString("Cap"));
                                            }
                                            if (!companyObject.optString("Comune").equals("null")) {
                                                tvCommon.setText(companyObject.optString("Comune"));
                                            }
                                            if (!companyObject.optString("Prov").equals("null")) {
                                                tvCompanyProvincia.setText(companyObject.optString("Prov"));
                                            }
                                            if (!companyObject.optString("tel1").equals("null")) {
                                                tvCompanyTelephone.setText(companyObject.optString("tel1"));
                                            }
                                            if (!companyObject.optString("fax").equals("null")) {
                                                tvFax.setText(companyObject.optString("fax"));
                                            }
                                            if (!companyObject.optString("email").equals("null")) {
                                                tvCompanyEmail.setText(companyObject.optString("email"));
                                            }
                                            if (!companyObject.optString("legalerap").equals("null")) {
                                                tvCompanyContact.setText(companyObject.optString("legalerap"));
                                            }
                                            if (!companyObject.optString("attivita").equals("null")) {
                                                tvBusinessArea.setText(companyObject.optString("attivita"));
                                            }
                                            if (!isProfileIBAN) {
                                                //   etIBANumber.setText(dataObject.optString("IBAN"));
                                                Log.d("****isProfileIBAN2", "" + isProfileIBAN);

//                                                if(TextUtils.equals(dataObject.optString("IBAN"),"null")){
//                                                    etIBANumber.setText("");
//                                                }else{
//                                                    etIBANumber.setText(dataObject.optString("IBAN"));
//                                                }

                                                if (TextUtils.equals(companyObject.optString("IBAN"), "null")) {
                                                    etIBANumber.setText("");
                                                } else {
                                                    etIBANumber.setText(companyObject.optString("IBAN"));
                                                }
                                            }
                                        }

                                    }
                                    //fetch all campiSezD data

                                    if (!dataObject.optString("campiSezD").equals("null")) {
                                        JSONArray campiSezD_array = dataObject.getJSONArray("campiSezD");
                                        {
                                            if (campiSezD_array.length() == 0) {
                                                llOtherInformation.setVisibility(View.GONE);
                                            } else {
                                                llOtherInformation.setVisibility(View.VISIBLE);
                                                iscampiSezDValidation = true;
                                                otherInfoList.clear();
                                                for (int i = 0; i < campiSezD_array.length(); i++) {
                                                    JSONObject campiSezDObject = campiSezD_array.getJSONObject(i);
                                                    OtherInformatioData otherInformatioItem = new OtherInformatioData();
                                                    otherInformatioItem.setCourseTitle(campiSezDObject.optString("etichetta"));

                                                    if (otherInformatioItem.getCourseTitle().contains("Cognome e nome apprendsta")) {
                                                        otherInformatioItem.setCourseTitle(otherInformatioItem.getCourseTitle().toString().replace("Cognome e nome apprendsta", "Cognome e nome apprendista"));
                                                    }

                                                    if (otherInformatioItem.getCourseTitle().contains("Numero di dipendeti (sottoposti a visita medica)")) {
                                                        otherInformatioItem.setCourseTitle(otherInformatioItem.getCourseTitle().toString().replace("Numero di dipendeti (sottoposti a visita medica)", "Numero di dipendenti (sottoposti a visita medica)"));
                                                    }

                                                    if (otherInformatioItem.getCourseTitle().contains("Numeri dei lavoratori per il quali si richiede la sospensione")) {
                                                        otherInformatioItem.setCourseTitle(otherInformatioItem.getCourseTitle().toString().replace("Numeri dei lavoratori per il quali si richiede la sospensione", "Numero di lavoratori per il quale si richiede la sospensione"));
                                                    }
                                                    otherInformatioItem.setCodCampo(campiSezDObject.optString("codCampo"));
                                                    //   otherInformatioItem.setListaValori(campiSezDObject.optString("listaValori"));

                                                    if (campiSezDObject.optString("etichetta").equals("null")) {
                                                        llOtherInformation.setVisibility(View.GONE);
                                                    } else {
                                                        llOtherInformation.setVisibility(View.VISIBLE);
                                                    }
                                                    otherInfoList.add(otherInformatioItem);
                                                }
                                            }
                                        }

                                    } else {
                                        llOtherInformation.setVisibility(View.GONE);
                                    }
                                    //fetch all location related data
                                    JSONArray location_array = dataObject.getJSONArray("locations");
                                    {
                                        if (location_array.length() == 0) {
                                            llCompanyDropDown.setVisibility(View.GONE);
                                            viewCompanyDropDown.setVisibility(View.GONE);
                                        } else {
                                            llCompanyDropDown.setVisibility(View.VISIBLE);
                                            viewCompanyDropDown.setVisibility(View.VISIBLE);
                                            locationList.clear();
                                            for (int i = 0; i < location_array.length(); i++) {
                                                JSONObject locationObj = location_array.getJSONObject(i);
                                                Location locationItem = new Location();
                                                locationItem.setLocationId(locationObj.optInt("idAZ"));
                                                locationItem.setLocationTilte(locationObj.optString("ragione"));

                                                JSONObject locationDetailsObject = new JSONObject(locationObj.toString());
                                                {
                                                    JSONObject detailsObject = locationDetailsObject.getJSONObject("details");
                                                    locationItem.setLocationRagione(detailsObject.optString("Ragione"));

                                                    locationItem.setLocationIVA(detailsObject.optString("piva"));
                                                    locationItem.setLocationcf(detailsObject.optString("cf"));
                                                    locationItem.setLocationVia(detailsObject.optString("Via"));
                                                    locationItem.setLocationCAP(detailsObject.optString("Cap"));
                                                    locationItem.setLocationComune(detailsObject.optString("Comune"));
                                                    locationItem.setLocationProvince(detailsObject.optString("Prov"));
                                                    locationItem.setLocationTelephone(detailsObject.optString("tel1"));
                                                    locationItem.setLocationFax(detailsObject.optString("fax"));
                                                    locationItem.setLocationEmail(detailsObject.optString("email"));
                                                    locationItem.setLocationAttivia(detailsObject.optString("attivita"));
                                                    locationItem.setLocationLegalerap(detailsObject.optString("legalerap"));
                                                }
                                                locationList.add(locationItem);
                                            }
                                            // tvCompanyDropDown.setText(locationList.get(0).getLocationTilte());
                                        }

                                    }
                                    //fetch IBAN
                                   /* if (!dataObject.optString("IBAN").equals("null")) {
                                        etIBANumber.setText(dataObject.optString("IBAN"));
                                    }*/

                                    //fetch all service related data
                                    JSONObject json_service_object = new JSONObject(dataObject.toString());
                                    {
                                        final JSONObject serviceObject = json_service_object.getJSONObject("service");
                                        for (int i = 0; i < otherInfoList.size(); i++) {
                                            if (otherInfoList.get(0) != null) {
                                                tvCourseTitle.setVisibility(View.VISIBLE);
                                                etCourseTitle.setVisibility(View.VISIBLE);

                                                tvCourseHours.setVisibility(View.GONE);
                                                etCourseHours.setVisibility(View.GONE);
                                                viewCourse.setVisibility(View.GONE);

                                                tvCourseTitle.setText(otherInfoList.get(0).getCourseTitle());
                                                dynamicValue1 = otherInfoList.get(0).getCodCampo();

                                                /*
                                                 * Added by sanekt on 14/02/2019
                                                 * As per client comment
                                                 * Add date picker for Decorrenza dimissioni
                                                 * */

                                                if (otherInfoList.get(0).getCourseTitle().equalsIgnoreCase("Decorrenza dimissioni")) {
                                                    etCourseTitle.setFocusable(false);
                                                    etCourseTitle.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Helper.showDate(true, getFragmentManager(), new DateListener() {
                                                                @Override
                                                                public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                                                                    final String date = Helper.showDate(Helper.createStringDate(year, monthOfYear, dayOfMonth));
                                                                    monthOfYear = monthOfYear + 1;
                                                                    if (monthOfYear < 10 && dayOfMonth >= 10) {
                                                                        etCourseTitle.setText(dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                                                                    } else if (dayOfMonth < 10 && monthOfYear >= 10) {
                                                                        etCourseTitle.setText("0" + dayOfMonth + "/" + monthOfYear + "/" + year);
                                                                    } else if (dayOfMonth < 10 && monthOfYear < 10) {
                                                                        etCourseTitle.setText("0" + dayOfMonth + "/" + "0" + monthOfYear + "/" + year);
                                                                    } else {
                                                                        etCourseTitle.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            } else {
                                                tvCourseTitle.setVisibility(View.GONE);
                                                etCourseTitle.setVisibility(View.GONE);
                                            }

                                            if (otherInfoList.size() == 2) {
                                                if (otherInfoList.get(1) != null) {
                                                    tvCourseHours.setVisibility(View.VISIBLE);
                                                    etCourseHours.setVisibility(View.VISIBLE);
                                                    viewCourse.setVisibility(View.VISIBLE);

                                                    tvCourseHours.setText(otherInfoList.get(1).getCourseTitle());
                                                    dynamicValue2 = otherInfoList.get(1).getCodCampo();

                                                } else {
                                                    tvCourseHours.setVisibility(View.GONE);
                                                    etCourseHours.setVisibility(View.GONE);
                                                    viewCourse.setVisibility(View.GONE);
                                                }
                                            }

                                            if (otherInfoList.size() == 3) {

                                                if (otherInfoList.get(1) != null) {
                                                    tvCourseHours.setVisibility(View.VISIBLE);
                                                    etCourseHours.setVisibility(View.VISIBLE);
                                                    viewCourse.setVisibility(View.VISIBLE);

                                                    tvCourseHours.setText(otherInfoList.get(1).getCourseTitle());
                                                    dynamicValue2 = otherInfoList.get(1).getCodCampo();

                                                } else {
                                                    tvCourseHours.setVisibility(View.GONE);
                                                    etCourseHours.setVisibility(View.GONE);
                                                    viewCourse.setVisibility(View.GONE);
                                                }


                                                if (otherInfoList.get(2) != null) {
                                                    llSuspension.setVisibility(View.VISIBLE);
                                                    viewSuspension.setVisibility(View.VISIBLE);

                                                    tvSuspensionTitle.setText(otherInfoList.get(2).getCourseTitle());
                                                    dynamicValue3 = otherInfoList.get(2).getCodCampo();


                                                } else {
                                                    llSuspension.setVisibility(View.GONE);
                                                    viewSuspension.setVisibility(View.GONE);

                                                }
                                            }

                                            if (otherInfoList.size() == 4) {

                                                if (otherInfoList.get(1) != null) {
                                                    tvCourseHours.setVisibility(View.VISIBLE);
                                                    etCourseHours.setVisibility(View.VISIBLE);
                                                    viewCourse.setVisibility(View.VISIBLE);

                                                    tvCourseHours.setText(otherInfoList.get(1).getCourseTitle());
                                                    dynamicValue2 = otherInfoList.get(1).getCodCampo();

                                                } else {
                                                    tvCourseHours.setVisibility(View.GONE);
                                                    etCourseHours.setVisibility(View.GONE);
                                                    viewCourse.setVisibility(View.GONE);
                                                }

                                                if (otherInfoList.get(2) != null) {
                                                    llSuspension.setVisibility(View.VISIBLE);
                                                    viewSuspension.setVisibility(View.VISIBLE);

                                                    tvSuspensionTitle.setText(otherInfoList.get(2).getCourseTitle());
                                                    dynamicValue3 = otherInfoList.get(2).getCodCampo();


                                                } else {
                                                    llSuspension.setVisibility(View.GONE);
                                                    viewSuspension.setVisibility(View.GONE);

                                                }


                                                if (otherInfoList.get(3) != null) {
                                                    llDataFine.setVisibility(View.VISIBLE);
                                                    viewDataFine.setVisibility(View.VISIBLE);

                                                    tvDataFineTitle.setText(otherInfoList.get(3).getCourseTitle());
                                                    dynamicValue4 = otherInfoList.get(3).getCodCampo();
                                                } else {
                                                    llDataFine.setVisibility(View.GONE);
                                                    viewDataFine.setVisibility(View.GONE);
                                                }
                                            }

                                        }


                                        //For Course Title
                                        etCourseTitle.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                dynamiccorso = charSequence.toString();
                                                if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                                    declarationText = serviceObject.optString("dichiaraDIP");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                } else {
                                                    declarationText = serviceObject.optString("dichiaraAZ");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });
                                        //For Course Hours
                                        etCourseHours.addTextChangedListener(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                dynamicoredelcorso = charSequence.toString();
                                                if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                                    declarationText = serviceObject.optString("dichiaraDIP");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                } else {
                                                    declarationText = serviceObject.optString("dichiaraAZ");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        //For Suspension
                                        etSuspensionTitle.addTextChangedListener(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                dynamicSuspension = charSequence.toString();
                                                if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                                    declarationText = serviceObject.optString("dichiaraDIP");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                } else {
                                                    declarationText = serviceObject.optString("dichiaraAZ");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });


                                        //For Data Fine
                                        etDataFineTitle.addTextChangedListener(new TextWatcher() {

                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                dynamicDataFine = charSequence.toString();
                                                if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                                    declarationText = serviceObject.optString("dichiaraDIP");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                } else {
                                                    declarationText = serviceObject.optString("dichiaraAZ");
                                                    declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                                    String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                                    String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                                    String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                                    tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        });

                                        if (intialText.contains(" ,")) {
                                            intialText = intialText.replace(" ,", ",");
                                        }

                                        //Declaration Condition
                                        if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                            declarationText = serviceObject.optString("dichiaraDIP");
                                            declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                            String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                            String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                            String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                            if (intialText.contains(" ,")) {
                                                intialText = intialText.replace(" ,", ",");
                                            }
                                            tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                        } else {
                                            declarationText = serviceObject.optString("dichiaraAZ");
                                            declarationText = declarationText.replace("#" + dynamicValue1 + "#", dynamiccorso);
                                            if (declarationText.contains(" .")) {
                                                declarationText = declarationText.replace(" .", ":");
                                            }
                                            if (declarationText.contains(" ,")) {
                                                declarationText = declarationText.replace(" ,", ",");
                                            }
                                            String combinedValue = declarationText.replace("#" + dynamicValue2 + "#", dynamicoredelcorso);
                                            String combinedValue3 = combinedValue.replace("#" + dynamicValue3 + "#", dynamicSuspension);
                                            String combinedValue4 = combinedValue3.replace("#" + dynamicValue4 + "#", dynamicDataFine);
                                            if (intialText.contains(" ,")) {
                                                intialText = intialText.replace(" ,", ",");
                                            }
                                            tvUserNotice.setText(Html.fromHtml(intialText + combinedValue4));
                                        }

                                        //Check for c section visiblity
                                        if (serviceObject.optString("Famiglia").equals("1") & AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                                            //set C section visible
                                            llSectionC.setVisibility(View.VISIBLE);
                                        } else {
                                            llSectionC.setVisibility(View.GONE);
                                        }

                                        isPagamento = serviceObject.optString("serviceObject");
                                    }
                                    //Check for parente
                                    JSONArray parente_array = dataObject.getJSONArray("parente");
                                    {
                                        parenteList.clear();
                                        if (parente_array.length() == 0) {
                                            tvChooseProfileList.setVisibility(View.GONE);
                                        } else {
                                            for (int i = 0; i < parente_array.length(); i++) {
                                                JSONObject parenteObj = parente_array.getJSONObject(i);
                                                Parente parenteItem = new Parente();
                                                parenteItem.setParentela(parenteObj.optString("Parentela"));
                                                parenteItem.setParentelaName(parenteObj.optString("Parentela"));
                                                parenteItem.setNome(parenteObj.optString("Nome"));
                                                parenteItem.setCognome(parenteObj.optString("Cognome"));
                                                parenteItem.setNatoComune(parenteObj.optString("natoComune"));
                                                parenteItem.setNatoData(parenteObj.optString("natoData"));
                                                parenteItem.setNatoProv(parenteObj.optString("natoProv"));
                                                parenteItem.setCF(parenteObj.optString("CF"));
                                                parenteList.add(parenteItem);
                                            }
                                        }
                                    }
                                    //Check to get country list
                                    countryList.clear();
                                    JSONArray province_array = dataObject.getJSONArray("province");
                                    if (province_array.length() == 0) {

                                    } else {
                                        for (int i = 0; i < province_array.length(); i++) {
                                            JSONObject provinceObj = province_array.getJSONObject(i);
                                            Country countryItem = new Country();
                                            countryItem.setCountryName(provinceObj.optString("value"));
                                            countryItem.setCountryKey(provinceObj.optString("key"));
                                            countryList.add(countryItem);
                                        }
                                    }
                                    //Check for allegati file
                                    selectedAllegatoFile = dataObject.optString("allegati");
                                    selectedAllegatoDisplayNames = dataObject.optString("allegatiDesc");

                                    // selectedAllegatoFile=null;
                                    if (!TextUtils.isEmpty(selectedAllegatoFile) && !TextUtils.equals(selectedAllegatoFile, "false"))
                                        addAllegatiFileUploadLayouts(selectedAllegatoFile.split("\\|\\|"), selectedAllegatoDisplayNames.split("\\|\\|"), isPagamento);
                                    //Check for bp file
                                    campiSezDFile = String.valueOf(Html.fromHtml(dataObject.optString("campiSezD_string")));
                                    testiSezDFileWithHTML = dataObject.optString("testiSezD_string");
                                    testiSezDFile = String.valueOf(Html.fromHtml(dataObject.optString("testiSezD_string")));


                                } else {
                                    CustomDialogManager.showOkDialogWithFinish(EmployeeCompanyActivity.this, jsonObject.optString("message"), new DialogListener() {
                                        @Override
                                        public void onButtonClicked(int type) {
                                            finish();
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
                            CustomDialogManager.showOkDialogWithFinish(EmployeeCompanyActivity.this, EmployeeCompanyActivity.this.getString(R.string.please_try_again), new DialogListener() {
                                @Override
                                public void onButtonClicked(int type) {
                                    finish();
                                }
                            });

                        }
                    });
        } else {
            CustomDialogManager.showOkDialogWithFinish(EmployeeCompanyActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {
                    finish();
                }
            });
        }
    }

    String path() {
        String path = "";
        try {
            path = Helper.readSDCard().get(0);
        } catch (Exception e) {

        }
        return path;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent employee_company_intent = new Intent(EmployeeCompanyActivity.this, AddNewServicesActivity.class);
        startActivity(employee_company_intent);
        finish();
    }

    public void addAllegatiFileUploadLayouts(String[] allegati, String[] displayNames, String isPagamento) {
        if (tvUploadSuccessList == null) {
            tvUploadSuccessList = new ArrayList<>();
        }
        for (int j = 0; j < allegati.length; j++) {
            //rimborso
            if (allegati[j].contains("rimborso")) {
                if (isPagamento.equals("0")) {
                    llIsPagmentoParent.setVisibility(View.GONE);
                    isPagmento = false;
                } else {
                    isPagmento = true;
                    llIsPagmentoParent.setVisibility(View.VISIBLE);
                }
            } else {
                //Allegati, codice_fiscale,CiFile,Busta Paga,llListaDependati
                if (allegati[j].contains("ciFile")) {
                    llCiFile2.setVisibility(View.VISIBLE);
                    viewCiFile2.setVisibility(View.VISIBLE);
                } else if (allegati[j].contains("VisuraFile")) {
                    llVisura2.setVisibility(View.VISIBLE);
                    viewVisura2.setVisibility(View.VISIBLE);

                }
                if (allegati[j].equalsIgnoreCase("bpFile")) {
                    isBustaPaga = true;
                    llAnnose.setVisibility(View.VISIBLE);
                    viewAnnoEMesse.setVisibility(View.VISIBLE);
                }
                //Add File upload dyanamic view
                LinearLayout childLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.view_allegati_child, null);
                TextView tvAllegato = childLayout.findViewById(R.id.tvAllegato);
                final TextView tvFileAllegato = childLayout.findViewById(R.id.tvFileAllegato);
                final Button btnAllegatoCarica = childLayout.findViewById(R.id.btnAllegatoCarica);

                if (TextUtils.equals(AppSession.getInstance().getTable(EmployeeCompanyActivity.this), "Aziende")) {
                    //Aziende
//                    llAllegato.setVisibility(View.VISIBLE);
                    btnAllegatoCarica.setVisibility(View.VISIBLE);
                    tvAllegato.setVisibility(View.VISIBLE);
                } else {
                    //dipendente
                    if (displayNames[j].equals("Lista dipendenti")) {
//                        llAllegato.setVisibility(View.GONE);
                        btnAllegatoCarica.setVisibility(View.GONE);
                        tvAllegato.setVisibility(View.GONE);
                    } else {
//                        llAllegato.setVisibility(View.VISIBLE);
                        btnAllegatoCarica.setVisibility(View.VISIBLE);
                        tvAllegato.setVisibility(View.VISIBLE);
                    }
                }
                tvAllegato.setText(Html.fromHtml(displayNames[j]));

                btnAllegatoCarica.setTag(allegati[j]);

                btnAllegatoCarica.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFileKey = (String) btnAllegatoCarica.getTag();
                        showFilePickerDialog();
                        tvUploadSuccess = tvFileAllegato;
                        //tvFileAllegato.setVisibility(View.VISIBLE);
                    }
                });

                llAllegato.addView(childLayout);
            }
        }
    }

    private void addSelectedFileToDyanamicsMap(String filePath) {
        if (filePath != null) {
            tvUploadSuccess.setVisibility(View.VISIBLE);
            String key = new String(selectedFileKey);
            Log.d("**key12", "" + key + " " + key);
            if (TextUtils.equals(key, "elenco_partecipanti_al_corso_cognome_nome_e_c.f.")) {
                key = key.replace("elenco_partecipanti_al_corso_cognome_nome_e_c.f.", "elenco_partecipanti_al_corso_cognome_nome_e_c_f_");
                selectedAllegatoFile = selectedAllegatoFile.replace("elenco_partecipanti_al_corso_cognome_nome_e_c.f.", "elenco_partecipanti_al_corso_cognome_nome_e_c_f_");
            } else {
                if (key.startsWith("rimborso")) {
                    if (!key.equals("rimborso_1")) {
                        selectedAllegatoFile = selectedAllegatoFile + "||" + key;
                    }
                }
            }
            Log.d("**key", selectedAllegatoFile);
            dynamicFiles.put(key, new TypedFile("multipart/form-data", new File(filePath)));
        }
    }

    private void saveServicesDetailsWithDyanamicFilesMap(Context context, String yearAndMonth, String IBANumber, String privacyPolicy1,
                                                         String privacyPolicy2, String countAz, String parente, String parentela,
                                                         String cognomeParente, String nomeParente, String luogoNascitaParente, String dtNascitaParente
            , String parentelaTaxCode, String parentelaBirthcountry
            , String ciFileData, String visurFileData, String otherInfo1, String otherInfo2) {
        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = ApiClient.getClient();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            if (parentela.equals("")) {
                parentela = "null";
            }
            if (cognomeParente.equals("")) {
                cognomeParente = "null";
            }
            if (nomeParente.equals("")) {
                nomeParente = "null";
            }
            if (luogoNascitaParente.equals("")) {
                luogoNascitaParente = "null";
            }
            if (dtNascitaParente.equals("")) {
                dtNascitaParente = "null";
            }
            if (parentelaTaxCode.equals("")) {
                parentelaTaxCode = "null";
            }
            if (parentelaBirthcountry.equals("")) {
                parentelaBirthcountry = "null";
            }
            String companyId = AppSession.getInstance().getCompanyId(EmployeeCompanyActivity.this);
            String serviceId = AppSession.getInstance().getServiceId(EmployeeCompanyActivity.this);
            String userId = AppSession.getInstance().getUserId(EmployeeCompanyActivity.this);
            String table = AppSession.getInstance().getTable(EmployeeCompanyActivity.this);
            Map<String, TypedString> userTypeMap = new HashMap<>();
            if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Aziende")) {
                userTypeMap.put("idAZ", new TypedString(userId));
            } else if (AppSession.getInstance().getTable(EmployeeCompanyActivity.this).equals("Dipendenti")) {
                userTypeMap.put("idDIP", new TypedString(userId));
            }

            if (!TextUtils.isEmpty(yearAndMonth)) {
                userTypeMap.put("bpAnnoMese", new TypedString(yearAndMonth));
            }
            Log.d("****dynamicaFiles", "" + dynamicFiles.toString());

            api.saveServicesDetails(new TypedString(table),// table name
                    new TypedString("SaveServiceRequest"),//action
                    userTypeMap,//user id
                    new TypedString(campiSezDFile),//madatory field campiSezD_string
                    // new TypedString(yearAndMonth),//month and year
                    new TypedString(testiSezDFileWithHTML),//madatory field testiSezD_string
                    new TypedString(selectedAllegatoFile),//madatory field
                    new TypedString(IBANumber),//IBAN Number
                    new TypedString(serviceId),// service id
                    new TypedString(privacyPolicy1),// privacy 1
                    new TypedString(privacyPolicy2),// privacy 2
                    new TypedString(companyId),// company id
                    new TypedString("1"),// is mobile request
                    new TypedString(countAz),// location dropdown countAz
                    new TypedString(parente),// parente nuovo
                    new TypedString(parentela),// parentela
                    new TypedString(cognomeParente),//cognomeParente
                    new TypedString(nomeParente),//nomeParente
                    new TypedString(luogoNascitaParente),//luogoNascitaParente
                    new TypedString(dtNascitaParente),//dtNascitaParente
                    new TypedString(parentelaTaxCode),//cfParente parentelaTaxCode
                    new TypedString(parentelaBirthcountry),//ProvNascita country list
                    new TypedString(ciFileData),//ciFileData
                    new TypedString(visurFileData),//visurFileData
                    new TypedString(""),
                    new TypedString(otherInfo1),
                    new TypedString(otherInfo1),
                    new TypedString(otherInfo2),
                    new TypedString(otherInfo1),
                    new TypedString(otherInfo1),     // Added by Mayur for extra paramters corsoImportoAllievo
                    new TypedString(otherInfo2),     // Added by Mayur for extra paramters NALLIEVI
                    new TypedString(otherInfo1),     // Added by Mayur for extra paramters ANNOISCRIZIONESCUOLA
                    new TypedString(otherInfo2),     // Added by Mayur for extra paramters NOMESCUOLA,
                    dynamicFiles,
                    //  new TypedFile("multipart/form-data", new File(codice_fiscale)),//Added by Sanket for extra paramters codice_fiscale
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            Log.d("****submitResponse", "" + responseObject.toString() + " " + response.toString());
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    tvFileSavedBustaPaga.setVisibility(View.GONE);
                                    Intent thank_you_intent = new Intent(EmployeeCompanyActivity.this, ThankyouActivity.class);
                                    startActivity(thank_you_intent);
                                    finish();
                                    overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);

                                } else {
                                    CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, jsonObject.optString("message"), new DialogListener() {
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
                            CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, EmployeeCompanyActivity.this.getString(R.string.please_try_again), new DialogListener() {
                                @Override
                                public void onButtonClicked(int type) {

                                }
                            });
                        }
                    });
        } else {
            CustomDialogManager.showOkDialog(EmployeeCompanyActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }

    private class ScanButtonClickListener implements View.OnClickListener {
        Dialog dialog;
        private int preference;

        public ScanButtonClickListener(int preference) {
            this.preference = preference;


        }

        public ScanButtonClickListener(int preference, Dialog dialog) {
            this.preference = preference;
            this.dialog = dialog;

        }

        public ScanButtonClickListener() {
        }

        @Override
        public void onClick(View v) {
            this.dialog.dismiss();
            startScan(preference);
        }
    }
}
