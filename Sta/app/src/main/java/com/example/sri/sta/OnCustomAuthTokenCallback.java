package com.example.sri.sta;

/**
 * Created by sri on 01-08-2018.
 */

public interface OnCustomAuthTokenCallback {

    void onCustomAuthRetrievedSuccess(String token);

    void onCustomAuthRetrievedWithError(Exception e);
}

