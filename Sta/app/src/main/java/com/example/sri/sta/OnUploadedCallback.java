package com.example.sri.sta;
import android.net.Uri;


/**
 * Created by sri on 03-08-2018.
 */

public interface OnUploadedCallback {
    void onUploadSuccess(String uid, Uri downloadUrl, String type);

    void onProgress(double progress);

    void onUploadFailed(Exception e);
}
