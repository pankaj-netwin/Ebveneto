package com.ebveneto.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebveneto.R;
import com.ebveneto.activities.AddNewServicesActivity;
import com.ebveneto.activities.EmployeeCompanyActivity;
import com.ebveneto.interfaces.ChildClickListener;
import com.ebveneto.models.ServicesChildData;
import com.ebveneto.models.ServicesParentData;
import com.ebveneto.utils.AppSession;
import com.ebveneto.widgets.CustomDialogManager;
import com.ebveneto.widgets.Helper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by Asmita on 05-01-2017.
 */

public class ServicesAdapter extends BaseExpandableListAdapter {
    private final ImageLoader loader;
    private final DisplayImageOptions options;
    public Context context;
    private ArrayList<ServicesParentData> deptList;
    private ChildClickListener childClickListener;


    public ServicesAdapter(Context context, ArrayList<ServicesParentData> deptList, ChildClickListener childClickListener) {
        this.context = context;
        this.deptList = deptList;
        this.childClickListener = childClickListener;

        loader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .displayer(new SimpleBitmapDisplayer())
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageOnLoading(R.drawable.ic_empty_icon_24dp)
                .showImageOnFail(R.drawable.ic_empty_icon_24dp)
                .showImageForEmptyUri(R.drawable.ic_empty_icon_24dp)
                .build();
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ServicesChildData> productList = deptList.get(groupPosition).getProductList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ServicesChildData> productList = deptList.get(groupPosition).getProductList();
        return productList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void OnIndicatorClick(boolean isExpanded, int position){

    }

    public void OnTextClick(){

    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isLastChild, View view,
                             ViewGroup parent) {

        ServicesParentData headerInfo = (ServicesParentData) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.services_parent_item, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.heading);
        ImageView ivServiceImage = (ImageView) view.findViewById(R.id.ivServiceImage);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        ivArrow.setSelected(isLastChild);
        ivArrow.setTag(groupPosition);


        if (headerInfo.getName().equals("")||headerInfo.getName().equals("null")){
            heading.setVisibility(View.GONE);
        }
        loader.displayImage(headerInfo.getImageUrl(), ivServiceImage, options);
        heading.setText(headerInfo.getName().trim());
        if(isLastChild){
            ivArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_info_24));
        }else {
            ivArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_info_24));
        }
//        ivArrow.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                int position = (Integer)view.getTag();
//                Toast.makeText(context, "Indicator Clicked", Toast.LENGTH_LONG).show();
//              //  OnIndicatorClick(isLastChild,position);
//                onGroupExpanded(position);
//                childClickListener.onIconClick(isLastChild,position);
//            }
//        });
        heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Text Clicked", Toast.LENGTH_LONG).show();
                childClickListener.onProceedClick(isLastChild,groupPosition);
                Log.d("***group",""+isLastChild+"/"+groupPosition);

            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, final boolean isLastChild,
                             View view, ViewGroup parent) {
        ServicesChildData detailInfo = (ServicesChildData) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.services_child_item, null);
        }
        WebView wvchildItem = (WebView) view.findViewById(R.id.wvchildItem);
        //TextView tvChildItem = (TextView) view.findViewById(R.id.tvChildItem);
        TextView tvReadMore = (TextView)view.findViewById(R.id.tvReadMore);

       /* if (detailInfo.getName().trim().equals("null")||detailInfo.getName().trim().equals("")){
            tvChildItem.setVisibility(View.GONE);
        }else
        {
            tvChildItem.setVisibility(View.VISIBLE);
            String htmlString = detailInfo.getName().trim().replace("</br>","\n");
            tvChildItem.setText((Html.fromHtml(Html.fromHtml(htmlString).toString(), Html.FROM_HTML_MODE_COMPACT)));
        }*/
        if (detailInfo.getName().trim().equals("null")||detailInfo.getName().trim().equals("")){
            wvchildItem.setVisibility(View.GONE);
        }else {
            wvchildItem.setVisibility(View.VISIBLE);


            if (Build.VERSION.SDK_INT >= 19)
                wvchildItem.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            else
                wvchildItem.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            wvchildItem.getSettings().setLoadWithOverviewMode(true);
            wvchildItem.getSettings().setJavaScriptEnabled(true);
            wvchildItem.setBackgroundColor(Color.TRANSPARENT);
            wvchildItem.setWebViewClient(new WebViewClient(){
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

            wvchildItem.getSettings().setDefaultTextEncodingName("utf-8");
            wvchildItem.getSettings().setJavaScriptEnabled(true);
        //    wvchildItem.loadData(detailInfo.getName().trim(),"text/html; charset=utf-8", "utf-8");
            wvchildItem.loadDataWithBaseURL(null, detailInfo.getName().trim(), "text/html", "utf-8", null);
            //  wvchildItem.loadData(detailInfo.getName().trim(),"text/html; charset=UTF-8", null);
            String  replaceString =  detailInfo.getName().trim().replace("\\u0092","'");
        }
       tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childClickListener.onProceedClick(isLastChild,groupPosition);
                Log.d("***group","1/"+isLastChild+"/"+groupPosition);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
