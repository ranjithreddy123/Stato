package com.example.sri.sta;

/**
 * Created by sri on 03-08-2018.
 */
public interface OnResponseRetrievedCallback<T> {
    void onSuccess(T response);

    void onError(Exception e);
}