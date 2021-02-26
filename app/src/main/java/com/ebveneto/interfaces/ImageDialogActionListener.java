package com.ebveneto.interfaces;

import android.app.Dialog;

/**
 * Created by Shreyas.Kansara on 09-12-2015.
 */
public interface ImageDialogActionListener {

    void onCameraOptionClicked(Dialog dialog);
    void onGalleryOptionClicked();
    void onPdfOptionClicked();
    void onDocumentOptionClicked();
}
