package com.ebveneto.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.adapters.ServicesAdapter;
import com.ebveneto.interfaces.ChildClickListener;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.models.ServicesChildData;
import com.ebveneto.models.ServicesParentData;
import com.ebveneto.utils.AppSession;
import com.ebveneto.webservices.HttpHelper;
import com.ebveneto.webservices.NetworkAPI;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

public class AddNewServicesActivity extends BaseActivity {
    private LinkedHashMap<String, ServicesParentData> hashMap = new LinkedHashMap<String, ServicesParentData>();
    private ArrayList<ServicesParentData> serviceList = new ArrayList<ServicesParentData>();
    private ServicesAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_services);
        // add data for displaying in expandable list view
        //  loadData();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(50 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.FIFO).discCacheExtraOptions(720, 1280, null).memoryCacheExtraOptions(720, 1080).build();
        ImageLoader.getInstance().init(config);
        getServiceListing(AddNewServicesActivity.this);
        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new ServicesAdapter(AddNewServicesActivity.this, serviceList,new ChildClickListener() {
            @Override
            public void onProceedClick(boolean isExpand,int pos) {
                ServicesParentData headerInfo = serviceList.get(pos);
                String service_id = headerInfo.getServiceId();
                String service_name = headerInfo.getName();
                Log.d("******OnIconClick",""+service_id+"/"+service_name);
                AppSession.getInstance().saveServiceId(AddNewServicesActivity.this,service_id);
                AppSession.getInstance().saveServicename(AddNewServicesActivity.this,service_name);

                final Dialog dialog = new Dialog(AddNewServicesActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.vw_custom_progress_bar);
                WebView imageView = (WebView) dialog.findViewById(R.id.wvLoader);
                imageView.setBackgroundColor(Color.TRANSPARENT); //for gif without background
                imageView.loadUrl("file:///android_asset/html/loader.html");
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            dialog.dismiss();
                            Intent employee_company_intent = new Intent(AddNewServicesActivity.this,EmployeeCompanyActivity.class);
                            startActivity(employee_company_intent);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                };
                thread.start();

            }

            @Override
            public void onIconClick(boolean isExpand, int pos) {
                ServicesParentData headerInfo = serviceList.get(pos);
                String service_id = headerInfo.getServiceId();
                String service_name = headerInfo.getName();
                Log.d("******OnIconClick",""+service_id+"/"+service_name);
                AppSession.getInstance().saveServiceId(AddNewServicesActivity.this,service_id);
                AppSession.getInstance().saveServicename(AddNewServicesActivity.this,service_name);
            }
        } );
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);
        setGroupIndicatorToRight();
        //expand all the Groups
        collapseAll();
        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                ServicesParentData headerInfo = serviceList.get(groupPosition);
                //get the child info
                ServicesChildData detailInfo = headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                Log.d("****onClickEXPAN","onChildClick");

                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                ServicesParentData headerInfo = serviceList.get(groupPosition);
                String service_id = headerInfo.getServiceId();
                String service_name = headerInfo.getName();
                AppSession.getInstance().saveServiceId(AddNewServicesActivity.this,service_id);
                AppSession.getInstance().saveServicename(AddNewServicesActivity.this,service_name);
                //display it or do something with it
                Log.d("****onClickEXPAN","onGroupClick");
                return false;
            }
        });
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        simpleExpandableListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //here we maintain our products in various departments
    private int addProduct(String service_header, String service_details, String service_id, String imageUrl) {

        int groupPosition = 0;

        //check the hash map if the group already exists
        ServicesParentData headerInfo = hashMap.get(service_header);
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new ServicesParentData();
            headerInfo.setName(service_header);
            headerInfo.setServiceId(service_id);
            headerInfo.setImageUrl(imageUrl);
            hashMap.put(service_header, headerInfo);
            serviceList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ServicesChildData> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ServicesChildData detailInfo = new ServicesChildData();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(service_details);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = serviceList.indexOf(headerInfo);
        return groupPosition;
    }

    //Webservice implementation of Add Services and its details
    private void getServiceListing(Context context) {
        if (HttpHelper.isNetworkAvailable(context)) {
            final Dialog mDialog = CustomDialogManager.showProgressDialog(context);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(HttpHelper.BASE_URL)
                    .build();
            NetworkAPI api = restAdapter.create(NetworkAPI.class);
            api.getAllServices(new TypedString("getServices"),
                    new TypedString(AppSession.getInstance().getTable(AddNewServicesActivity.this)),//table id
                    new TypedString(AppSession.getInstance().getUserId(AddNewServicesActivity.this)),//user id
                    new TypedString("1"),//mobile request
                    new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject responseObject, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(responseObject.toString());
                                if (jsonObject.optString("status").equals("true")) {
                                    Helper.Log("URL", "response : ==>  " + jsonObject);
                                    JSONArray service_array = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < service_array.length(); i++) {
                                        JSONObject eventObj = service_array.getJSONObject(i);
                                        addProduct(eventObj.optString("DESCRIZIONE"), eventObj.optString("WEBDESCRIZIONE"),eventObj.optString("IDSERVIZIO"), eventObj.optString("ICONPATH"));
                                    }
                                    listAdapter.notifyDataSetChanged();
                                } else {
                                    //for invalid conditions like invalid credentials
                                    CustomDialogManager.showOkDialog(AddNewServicesActivity.this, jsonObject.optString("message"), new DialogListener() {
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
            CustomDialogManager.showOkDialog(AddNewServicesActivity.this, getString(R.string.no_internet_connection), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}

