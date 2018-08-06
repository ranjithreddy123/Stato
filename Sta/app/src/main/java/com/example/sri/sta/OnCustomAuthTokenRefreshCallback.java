package com.example.sri.sta;

/**
 * Created by sri on 01-08-2018.
 */


public interface OnCustomAuthTokenRefreshCallback<T> {
    void onRefreshSuccess(T data);

    void onRefreshError(Exception e);
}