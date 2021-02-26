package com.ebveneto.webservices;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mayur on 23-10-2017.
 */


/*public class ApiClient
{
    private static RestAdapter restAdapter;
    public static RestAdapter getClient() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder().setEndpoint(HttpHelper.BASE_URL).setClient().build();
        }
        return restAdapter;
    }
}*/

public class ApiClient {

   /* private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/

    private static RestAdapter restAdapter;
    public static RestAdapter getClient() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder().setEndpoint(HttpHelper.BASE_URL). setLogLevel(RestAdapter.LogLevel.FULL)//{
          //  restAdapter = new RestAdapter.Builder().setEndpoint(HttpHelper.BASE_URL). setLogLevel(RestAdapter.LogLevel.FULL). setLog(new RestAdapter.Log() {
//                @Override
//                public void log(String msg) {
//                    Log.i("Request", msg);
//                }
//            })
        .build();
        }
        return restAdapter;
    }

    public static OkHttpClient getHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //TODO : remove logging interceptors as it is to be used for development purpose
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS).
                        addInterceptor(logging).
                        build();

        return client;
    }


}
