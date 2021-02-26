package com.ebveneto.widgets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;

import com.ebveneto.BuildConfig;
import com.ebveneto.fragments.DatePickerFragment;
import com.ebveneto.interfaces.DateListener;
import com.ebveneto.interfaces.DialogListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Asmita on 04-01-2017.
 */

public class Helper {
    public static void Log(String tag, String msg) {
        if (msg == null)
            return;

        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg);
    }

    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    public static String getPath(Uri uri, Activity activity) {

        Cursor cursor = null;
        int column_index = 0;
        try {

            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = activity.managedQuery(uri, projection, null, null, null);
            if(cursor!=null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return cursor.getString(column_index);
    }

    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile() {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mremploy");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    public static void showCropDialog(CharSequence msg, Context ctx) {
        if (ctx != null) {
            CustomDialogManager.showOkDialog(ctx, msg.toString(), new DialogListener() {
                @Override
                public void onButtonClicked(int type) {

                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePathFromGalleryAboveKitkat(Context cntxt, Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = null;
        if (Build.VERSION.SDK_INT > 19) {
            try {
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(uri);
                // Split at colon, use second item in the array
                if (!wholeID.contains(":"))
                    return null;

                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = cntxt.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            cursor = cntxt.getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return path;
    }

    public static void copyAssets(Activity activity) {
        AssetManager assetManager = activity.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("Files");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "ebVeneto/picture/");

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        for (String filename : files) {
            System.out.println("File name => " + filename);
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("Files/" + filename);   // if files resides inside the "Files" directory itself
                out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/ebVeneto/picture/" + filename);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    public static void copyReadmeFile(Activity activity){
        AssetManager assetManager = activity.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("Readme");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "ebVeneto/picture/");

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        for (String filename : files) {
            System.out.println("File name => " + filename);
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("Readme/" + filename);   // if files resides inside the "Readme" directory itself
                out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/ebVeneto/picture/readme");
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }

    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static ArrayList<String> readSDCard() {
        final ArrayList<String> tFileList = new ArrayList<>();

        // It have to be matched with the directory in SDCard
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "ebVeneto/picture");// Here you take your specific folder//

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        final File[] files = myDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return ((name.endsWith(".jpg")) || (name.endsWith(".png")));
            }
        });
        try {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
            /*
             * It's assumed that all file in the path are in supported type
             */
                tFileList.add(file.getPath());
            }
        } catch (Exception e) {

        }

        return tFileList;
    }

    public static void showDropDown(View view, ListAdapter adapter, final AdapterView.OnItemClickListener clickListener) {
        final ListPopupWindow window = new ListPopupWindow(view.getContext());
        window.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                window.dismiss();
                clickListener.onItemClick(arg0, arg1, arg2, arg3);
            }
        });

        window.setAdapter(adapter);
        window.setModal(true);
        window.setAnchorView(view);
        window.setWidth(ListPopupWindow.WRAP_CONTENT);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        window.show();
        if (window.isShowing()){
            window.getListView().setVerticalScrollBarEnabled(false);
            window.getListView().setFastScrollEnabled(true);
        }
    }

    public static final int getMonthsDifference(String date1, String date2) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date11 = format.parse(date1);
        Date date22 = format.parse(date2);

        int m1 = date11.getYear() * 12 + date11.getMonth();
        int m2 = date22.getYear() * 12 + date22.getMonth();
        return m1 - m2;
    }

    //Method for Date Picker
    public static void showDate(boolean mFlag,FragmentManager fragmentManager, DateListener dateListener) {
        DatePickerFragment endDate = new DatePickerFragment();

        endDate.setDateListener(
                dateListener);
        endDate.show(fragmentManager, "datePicker");
    }

    public static String showDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy", Locale.US);
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String newDate = "";
        try {
            Date date = formatter.parse(dateInString);
            newDate = formatedDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static String createStringDate(int year, int monthOfYear, int dayOfMonth) {
        String day = "" + dayOfMonth;
        if (day.length() == 1)
            day = "0" + day;

        String month = "" + (monthOfYear + 1);
        if (month.length() == 1)
            month = "0" + month;

        return day + "" + month + "" + year;
    }

    public static String formatDate(String dateInString) {
        Log.d("***DATA_format",""+dateInString);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String newDate = "";
        if(TextUtils.equals(dateInString,"null")){
            newDate = "-";
        }else {
            try {
                Date date = formatter.parse(dateInString);
                newDate = formatedDate.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return newDate;
    }
}
