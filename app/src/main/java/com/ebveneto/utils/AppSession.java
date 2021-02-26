package com.ebveneto.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSession {
    public static final String PREFS_NAME = "Data";
    public static SharedPreferences sharedPreferences;
    static AppSession appSession;
    public static Editor editor;
    public static final String TAG = "tag";
    private static String IS_LOGIN= "is_login";
    private static String IS_POLICY_ACCEPT= "is_policy_accept";
    private static String IS_LOGIN_POLICY_ACCEPT= "is_login_policy_accept";
    private static String SAVE_EMAIL = "username";
    private static String SAVE_PASSWORD = "password";
    private static String SAVE_USER_ID = "save_user_id";
    private static String SAVE_SERVICE_ID = "save_service_id";
    private static String SAVE_SERVICE_NAME = "save_name";
    private static String SAVE_USER_NOME = "save_nome";
    private static String SAVE_USER_COGNOME = "save_cognome";
    private static String SAVE_COMPANY_ID = "save_company_id";
    private static String SAVE_USER_TYPE = "save_user_type";
    private static String SAVE_TABLE = "save_table";
    private static String SAVE_COMPANY_REGION = "save_region";
    private static String SAVE_COMPANY_NAME = "save_company_name";
    private static String SAVE_FIELD_LEGALE_RAPPRESENTANTE = "FIELD_LEGALE_RAPPRESENTANTE";

    private static String SAVE_CF = "CF";
    private static String SAVE_DateNascita = "DateNascita";
    private static String SAVE_Via = "Via";
    private static String SAVE_Citta = "Citta";
    private static String SAVE_Provincia = "Provincia";
    private static String SAVE_ebv_versamenti = "ebv_versamenti";
    private static String SAVE_Piva  = "Piva";
    private static String SAVE_Somma  = "Somma";
    private static String SAVE_Budget  = "Budget";

    private static String SAVE_SKIP_VERSION  = "SKIP";
    private static String SAVE_SKIP_FLAG  = "SKIP_FLAG";




    public static AppSession getInstance() {
        if (sharedPreferences == null) {
            appSession = new AppSession();
        }
        return appSession;
    }


    public void clearSharedPreference(Context context) {
        SharedPreferences sharedPreferences;
        Editor editor;
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void clearRateThisAppSharedPreference(Context context) {
        SharedPreferences sharedPreferences;
        Editor editor;
        sharedPreferences = context.getSharedPreferences("ebeveneto_rate_app", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

   /* Save Login details*/
    public void saveLoginStatus(Context context, boolean isLogin){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }


    public boolean getLoginStatus(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    /*Save user name */

    public  void saveEmail(Context context, String username){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_EMAIL, username);
        editor.commit();
    }

    public String getEmail(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_EMAIL, "");

    }
    /*Save user password */

    public  void savePasword(Context context, String password){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_PASSWORD, password);
        editor.commit();
    }

    public String getPassword(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_PASSWORD, "");

    }

    /*Save userId*/
    public void saveUserId(Context context, String userId){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_USER_ID, userId);
        editor.commit();
    }

    public String getUserId(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_USER_ID, "");

    }

    /*Save Nome*/
    public void saveUserNome(Context context, String userNome){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_USER_NOME, userNome);
        editor.commit();
    }

    public String getUserNome(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_USER_NOME, "");

    }

    /*Save Cognome*/
    public void saveUserCognome(Context context, String userCognome){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_USER_COGNOME, userCognome);
        editor.commit();
    }

    public String getUserCognome(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_USER_COGNOME, "");

    }

    /*Save Service Id*/
    public void saveServiceId(Context context, String serviceId){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_SERVICE_ID, serviceId);
        editor.commit();
    }

    public String getServiceId(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SERVICE_ID, "");

    }

    /*Save Company Id*/
    public void saveCompanyId(Context context, String companyId){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_COMPANY_ID, companyId);
        editor.commit();
    }

    public String getCompanyId(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_COMPANY_ID, "");

    }

    /*Save User Type*/
    public void saveUserType(Context context, String userType){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_USER_TYPE, userType);
        editor.commit();
    }

    public String getUserType(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_USER_TYPE, "");

    }


    /*Save Table*/
    public void saveTable(Context context, String tableName){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_TABLE, tableName);
        editor.commit();
    }

    public String getTable(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TABLE, "");

    }

    /*Save region*/
    public void saveRegion(Context context, String region){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_COMPANY_REGION, region);
        editor.commit();
    }

    public String getRegion(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_COMPANY_REGION, "");

    }

    /*Save service name*/
    public void saveServicename(Context context, String servicename){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_SERVICE_NAME, servicename);
        editor.commit();
    }

    public String getServicename(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SERVICE_NAME, "");

    }


    /*Save service name*/
    public void saveCompanyName(Context context, String CompanyName){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_COMPANY_NAME, CompanyName);
        editor.commit();
    }

    public String getCompanyName(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_COMPANY_NAME, "");
    }


    /*Save service name*/
    public void saveFIELD_LEGALE_RAPPRESENTANTE(Context context, String FIELD_LEGALE_RAPPRESENTANTE){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_FIELD_LEGALE_RAPPRESENTANTE, FIELD_LEGALE_RAPPRESENTANTE);
        editor.commit();
    }

    public String getFIELD_LEGALE_RAPPRESENTANTE(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_FIELD_LEGALE_RAPPRESENTANTE, "");
    }

    /*Save service name*/
    public void saveCF(Context context, String CF){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_CF, CF);
        editor.commit();
    }

    public String getCF(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_CF, "");
    }


    /*Save service name*/
    public void saveDateNascita(Context context, String DateNascita){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_DateNascita, DateNascita);
        editor.commit();
    }

    public String getDateNascita(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DateNascita, "");
    }


    /*Save service name*/
    public void saveVia(Context context, String Via){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Via, Via);
        editor.commit();
    }

    public String getVia(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Via, "");
    }

    /*Save service name*/
    public void saveCitta(Context context, String Citta){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Citta, Citta);
        editor.commit();
    }

    public String getCitta(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Citta, "");
    }

    /*Save service name*/
    public void saveProvincia(Context context, String Provincia){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Provincia, Provincia);
        editor.commit();
    }

    public String getProvincia(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Provincia, "");
    }

    /*Save service name*/
    public void saveEbv_versamenti(Context context, String ebv_versamenti){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_ebv_versamenti, ebv_versamenti);
        editor.commit();
    }

    public String getEbv_versamenti(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_ebv_versamenti, "");
    }

    //Save Piva
    public void savePiva(Context context, String ebv_versamenti){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Piva, ebv_versamenti);
        editor.commit();
    }

    public String getPiva(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Piva, "");
    }


    //Save Somma
    public void saveSomma(Context context, String ebv_versamenti){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Somma, ebv_versamenti);
        editor.commit();
    }

    public String getSomma(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Somma, "");
    }

    //Save Budget
    public void saveBudget(Context context, String Budget){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_Budget, Budget);
        editor.commit();
    }

    public String getBudget(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_Budget, "");
    }

    /* Save private Policy  details*/
    public void savePolicyStatus(Context context, boolean isPolicyAccept){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_POLICY_ACCEPT, isPolicyAccept);
        editor.commit();
    }


    public boolean getPolicyStatus(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_POLICY_ACCEPT, false);
    }

    //this is used for at the time of login accept terms and condition or not
    public void saveLoginPolicyStatus(Context context, boolean isPolicyAccept){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN_POLICY_ACCEPT, isPolicyAccept);
        editor.commit();
    }


    public boolean getLoginPolicyStatus(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN_POLICY_ACCEPT, false);
    }

    public  void saveSkipVersion(Context context, String saveSkipVersion){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_SKIP_VERSION, saveSkipVersion);
        editor.commit();
    }

    public String getSkipVersion(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SKIP_VERSION, "0");

    }
    public  void saveSkipFlag(Context context, String saveSkipFlag){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Editor editor=sharedPreferences.edit();
        editor.putString(SAVE_SKIP_FLAG, saveSkipFlag);
        editor.commit();
    }

    public String getSkipFlag(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SKIP_FLAG, "0");

    }
}

