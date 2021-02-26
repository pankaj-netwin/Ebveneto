package com.ebveneto.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebveneto.R;
import com.ebveneto.activities.EmployeeCompanyActivity;
import com.ebveneto.interfaces.DialogListener;
import com.ebveneto.interfaces.ImageDialogActionListener;
import com.ebveneto.webservices.HttpHelper;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;


public class CustomDialogManager {

    public static void showOkCancelDialog(Context context, String message, String yesButtonMessage, String noButtonMessage,
                                          final DialogListener dialogListener) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog okCancelDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_yes_no, null);
        build.setView(view);
        okCancelDialog = build.create();
        okCancelDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        final ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        final TextView btnYes = (TextView) view.findViewById(R.id.btnYes);
        btnYes.setText(yesButtonMessage);
        final TextView btnNo = (TextView) view.findViewById(R.id.btnNo);
        btnNo.setText(noButtonMessage);

        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });

        btnNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_NEGATIVE);
            }

        });
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                okCancelDialog.dismiss();
            }
        });

        tvTitle.setText(context.getString(R.string.app_name));
        if (TextUtils.isEmpty(message))
            tvMessage.setText("");
        else
            tvMessage.setText(message);

        okCancelDialog.show();
    }
    public static void showPrivacyPolicyDialog(final Context context,String url,int noOfButtton,final DialogListener dialogListener) {

        final Dialog[] mDialog = new Dialog[1];
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog privacyPolicyalog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_privacy_policy, null);
        build.setView(view);
        privacyPolicyalog = build.create();
        privacyPolicyalog.setCancelable(false);
        //privacyPolicyalog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final WebView wvPrivacyPolicy = (WebView) view.findViewById(R.id.wvPrivacyPolicy);
        wvPrivacyPolicy.getSettings().setBuiltInZoomControls(true);
        wvPrivacyPolicy.setVerticalScrollBarEnabled(false);
        wvPrivacyPolicy.setHorizontalScrollBarEnabled(false);
        wvPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
        //To show a progress dialog while webpage is loading
        wvPrivacyPolicy.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
                mDialog[0] = new ProgressDialog(context);
                mDialog[0].setTitle("Accesso");
                mDialog[0].setCancelable(true);
                mDialog[0].show();
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO hide your progress image
                super.onPageFinished(view, url);
                mDialog[0].dismiss();
            }
        });
        wvPrivacyPolicy.loadUrl(url);
       // wvPrivacyPolicy.loadUrl(HttpHelper.PRIVACY_POLICY_URL);
        final Button btnClose = (Button) view.findViewById(R.id.btnClose);
        final Button btnDisAgree = (Button) view.findViewById(R.id.btnDisAgree);
        final Button btnIAgree = (Button) view.findViewById(R.id.btnIAgree);

        switch (noOfButtton)
        {
            case 1:
                btnDisAgree.setVisibility(View.GONE);
                btnIAgree.setVisibility(View.GONE);
                btnClose.setVisibility(View.VISIBLE);
                break;

            case 2:
                btnDisAgree.setVisibility(View.VISIBLE);
                btnIAgree.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.GONE);
                break;

        }


        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                privacyPolicyalog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_NEGATIVE);


            }

        });
        btnDisAgree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                privacyPolicyalog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_NEGATIVE);


            }

        });
        btnIAgree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                privacyPolicyalog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);


            }

        });



        privacyPolicyalog.show();
    }

    public static void showOkDialog(Context context, CharSequence message, final DialogListener dialogListener) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog okCancelDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ok, null);
        build.setView(view);
        if(message.toString().contains("Cognome e nome apprendsta")){
            message = message.toString().replace("Cognome e nome apprendsta","Cognome e nome apprendista");
        }

        if( message.toString().contains("Numero di dipendeti (sottoposti a visita medica)")){
            message = message.toString().replace("Numero di dipendeti (sottoposti a visita medica)","Numero di dipendenti (sottoposti a visita medica)");
        }

        if( message.toString().contains("Numeri dei lavoratori per il quali si richiede la sospensione")){
            message = message.toString().replace("Numeri dei lavoratori per il quali si richiede la sospensione","Numero di lavoratori per il quale si richiede la sospensione");
        }

        if(message.toString().contains("Scadenza carta d'identi")){
            message = message.toString().replace("Scadenza carta d'identi","Scadenza carta d'identità");
        }

        if(message.toString().contains("Stato di faiimglia o attestazione paternità/maternità")){
            message = message.toString().replace("Stato di faiimglia o attestazione paternità/maternità","stato di famiglia o attestazione paternità/maternità");
        }

        if(message.toString().contains(",")){
            message = message.toString().replace(",",", ");
        }
        okCancelDialog = build.create();
        okCancelDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        final ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        final TextView btnYes = (TextView) view.findViewById(R.id.btnYes);
        btnYes.setText("OK");

        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                okCancelDialog.dismiss();
            }
        });

        tvTitle.setText(R.string.app_name);
        if (TextUtils.isEmpty(message))
            tvMessage.setText("");
        else
            tvMessage.setText(message);

        okCancelDialog.show();
        okCancelDialog.setCanceledOnTouchOutside(false);
    }

    public static void showOkDialogWithFinish(Context context, CharSequence message, final DialogListener dialogListener) {
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        final AlertDialog okCancelDialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ok, null);
        build.setView(view);
        if(message.toString().contains("Cognome e nome apprendsta")){
            message = message.toString().replace("Cognome e nome apprendsta","Cognome e nome apprendista");
        }

        if( message.toString().contains("Numero di dipendeti (sottoposti a visita medica)")){
            message = message.toString().replace("Numero di dipendeti (sottoposti a visita medica)","Numero di dipendenti (sottoposti a visita medica)");
        }

        if( message.toString().contains("Numeri dei lavoratori per il quali si richiede la sospensione")){
            message = message.toString().replace("Numeri dei lavoratori per il quali si richiede la sospensione","Numero di lavoratori per il quale si richiede la sospensione");
        }

        if(message.toString().contains("Scadenza carta d'identi")){
            message = message.toString().replace("Scadenza carta d'identi","Scadenza carta d'identità");
        }

        if(message.toString().contains("Stato di faiimglia o attestazione paternità/maternità")){
            message = message.toString().replace("Stato di faiimglia o attestazione paternità/maternità","stato di famiglia o attestazione paternità/maternità");
        }

        if(message.toString().contains(",")){
            message = message.toString().replace(",",", ");
        }
        okCancelDialog = build.create();
        okCancelDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        final ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        final TextView btnYes = (TextView) view.findViewById(R.id.btnYes);
        btnYes.setText("OK");

        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                okCancelDialog.dismiss();
                if (dialogListener != null)
                    dialogListener.onButtonClicked(Dialog.BUTTON_POSITIVE);
            }
        });

        tvTitle.setText(R.string.app_name);
        if (TextUtils.isEmpty(message))
            tvMessage.setText("");
        else
            tvMessage.setText(message);

        okCancelDialog.show();
        okCancelDialog.setCanceledOnTouchOutside(false);
    }
    public static Dialog showProgressDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.vw_custom_progress_bar);
        WebView imageView = (WebView) dialog.findViewById(R.id.wvLoader);
        imageView.setBackgroundColor(Color.TRANSPARENT); //for gif without background
        imageView.loadUrl("file:///android_asset/html/loader.html");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

}
