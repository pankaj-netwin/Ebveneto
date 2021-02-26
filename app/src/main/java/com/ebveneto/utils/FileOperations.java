package com.ebveneto.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

@SuppressLint("NewApi")
public class FileOperations 

{


	public String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
//	            final Uri contentUri = ContentUris.withAppendedId(
//	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            String fileName= getFilePath(context, uri);

                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/"+ fileName;
                }
                else
                    return "";
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int columnIndex = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(columnIndex);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public  boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public  boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public  boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}


	/* Get uri related content real local file path. */
	private String getUriRealPath(Context ctx, Uri uri)
	{
		String ret = "";

		if( isAboveKitKat() )
		{
			// Android OS above sdk version 19.
			ret = getUriRealPathAboveKitkat(ctx, uri);
		}else
		{
			// Android OS below sdk version 19
			ret = getImageRealPath(ctx.getContentResolver(), uri, null);
		}

		return ret;
	}

	public static String  getUriRealPathAboveKitkat(Context ctx, Uri uri)
	{
		String ret = "";

		if(ctx != null && uri != null) {

//			if(isContentUri(uri))
//			{
//				if(isGooglePhotoDoc(uri.getAuthority()))
//				{
//					ret = uri.getLastPathSegment();
//				}
//
//				else {
//					ret = getImageRealPath(ctx.getContentResolver(), uri, null);
//				}
//			}else

			    if(isFileUri(uri)) {
				ret = uri.getPath();
			}else if(isDocumentUri(ctx, uri)){

				// Get uri related document id.
				String documentId = DocumentsContract.getDocumentId(uri);

				// Get uri authority.
				String uriAuthority = uri.getAuthority();

				if(isMediaDoc(uriAuthority))
				{
					String idArr[] = documentId.split(":");
					if(idArr.length == 2)
					{
						// First item is document type.
						String docType = idArr[0];

						// Second item is document real id.
						String realDocId = idArr[1];

						// Get content uri by document type.
						Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
						if("image".equals(docType))
						{
							mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
						}else if("video".equals(docType))
						{
							mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
						}else if("audio".equals(docType))
						{
							mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
						}

						// Get where clause with real document id.
						String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;

						ret = getImageRealPath(ctx.getContentResolver(), mediaContentUri, whereClause);
					}

				}else if(isDownloadDoc(uriAuthority))
				{
					// Build download uri.
					Uri downloadUri = Uri.parse("content://downloads/public_downloads");

					// Append download document id at uri end.
					Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));

					ret = getImageRealPath(ctx.getContentResolver(), downloadUriAppendId, null);

				}else if(isExternalStoreDoc(uriAuthority))
				{
					String idArr[] = documentId.split(":");
					if(idArr.length == 2)
					{
						String type = idArr[0];
						String realDocId = idArr[1];

						if("primary".equalsIgnoreCase(type))
						{
							ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
						}
					}
				}
			}
		}

		return ret;
	}

	/* Check whether current android os version is bigger than kitkat or not. */
	private boolean isAboveKitKat()
	{
		boolean ret = false;
		ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		return ret;
	}

	/* Check whether this uri represent a document or not. */
    public static boolean isDocumentUri(Context ctx, Uri uri)
	{
		boolean ret = false;
		if(ctx != null && uri != null) {
			ret = DocumentsContract.isDocumentUri(ctx, uri);
		}
		return ret;
	}

	/* Check whether this uri is a content uri or not.
	 *  content uri like content://media/external/images/media/1302716
	 *  */
	public static boolean isContentUri(Uri uri)
	{
		boolean ret = false;
		if(uri != null) {
			String uriSchema = uri.getScheme();
			if("content".equalsIgnoreCase(uriSchema))
			{
				ret = true;
			}
		}
		return ret;
	}

	/* Check whether this uri is a file uri or not.
	 *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
	 * */
    public static boolean isFileUri(Uri uri)
	{
		boolean ret = false;
		if(uri != null) {
			String uriSchema = uri.getScheme();
			if("file".equalsIgnoreCase(uriSchema))
			{
				ret = true;
			}
		}
		return ret;
	}


	/* Check whether this document is provided by ExternalStorageProvider. */
    public static boolean isExternalStoreDoc(String uriAuthority)
	{
		boolean ret = false;

		if("com.android.externalstorage.documents".equals(uriAuthority))
		{
			ret = true;
		}

		return ret;
	}

	/* Check whether this document is provided by DownloadsProvider. */
    public static boolean isDownloadDoc(String uriAuthority)
	{
		boolean ret = false;

		if("com.android.providers.downloads.documents".equals(uriAuthority))
		{
			ret = true;
		}

		return ret;
	}

	/* Check whether this document is provided by MediaProvider. */
    public static boolean isMediaDoc(String uriAuthority)
	{
		boolean ret = false;

		if("com.android.providers.media.documents".equals(uriAuthority))
		{
			ret = true;
		}

		return ret;
	}

	/* Check whether this document is provided by google photos. */
    public static boolean isGooglePhotoDoc(String uriAuthority)
	{
		boolean ret = false;

		if("com.google.android.apps.photos.content".equals(uriAuthority))
		{
			ret = true;
		}

		return ret;
	}

	/* Return uri represented document file real local path.*/
    public static String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause)
	{
		String ret = "";

		// Query the uri with condition.
		Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);

		if(cursor!=null)
		{
			boolean moveToFirst = cursor.moveToFirst();
			if(moveToFirst)
			{

				// Get columns name by uri type.
				String columnName = MediaStore.Images.Media.DATA;

				if( uri==MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
				{
					columnName = MediaStore.Images.Media.DATA;
				}else if( uri==MediaStore.Audio.Media.EXTERNAL_CONTENT_URI )
				{
					columnName = MediaStore.Audio.Media.DATA;
				}else if( uri==MediaStore.Video.Media.EXTERNAL_CONTENT_URI )
				{
					columnName = MediaStore.Video.Media.DATA;
				}

				// Get column index.
				int imageColumnIndex = cursor.getColumnIndex(columnName);

				// Get column value which is the uri related file local path.
				ret = cursor.getString(imageColumnIndex);
			}
		}

		return ret;
	}

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    public static String getFilePath(Context context, Uri uri) {
        Cursor cursor = null;
        final String[] projection = {MediaStore.Files.FileColumns.DISPLAY_NAME};
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
