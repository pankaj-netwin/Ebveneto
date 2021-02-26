package com.ebveneto.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Asmita on 04-01-2017.
 */

public class HttpHelper {
    //stagging URL
 //   public static String BASE_URL = "http://www.ebveneto.it/web_services/index_new.php";            // also make change in url of Versamenti and if any web view
    //LIVE URL
    //public static String BASE_URL = "http://www.ebveneto.it/web_services/index_production.php";   // also make change in url of Versamenti and if any web view
   // public static String BASE_URL = "http://www.ebveneto.it/web_services/index_production_july18.php";   // also make change in url of Versamenti and if any web view
    //public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_july18.php";   // also make change in url of Versamenti and if any web view

    //Chnaged by sanket on dec2018
    //public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_dec18.php";   //
    //public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_feb19.php";   //
    //Added by Sanket on 13 feb 2019
    //public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_feb19_android.php";
    //Added by sanket on 07 march 2019
    public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_march19.php";   //
//    public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_march19_test.php";   //

    //    public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_march19.php";   //
    //public static String BASE_URL = "https://www.ebvenetofvg.it/web_services/index_production_feb19_android.php";   //
   // public static String BASE_URL_VERSAMENTI = "http://www.ebveneto.it/web_services";
    public static String BASE_URL_VERSAMENTI = "https://www.ebvenetofvg.it/web_services";
    public static String PRIVACY_POLICY_URL = "https://www.iubenda.com/privacy-policy/8077999";
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            return false;
        }
        else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
