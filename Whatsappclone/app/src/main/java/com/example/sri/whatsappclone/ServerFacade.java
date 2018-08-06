package com.example.sri.whatsappclone;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sri.whatsappclone.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class ServerFacade {

    public static void sendRegistrationToServer(final Context context, String token) throws UnsupportedEncodingException {

        String url ="http://"+ Configuration.getInstance().getChatServerIp()+":"+Configuration.getInstance().getChatServerPort()+"/session/mobile/auth?androidid="+
                URLEncoder.encode(
                        Settings.Secure.getString(
                                context.getContentResolver(),
                                Settings.Secure.ANDROID_ID
                        )
                        , "UTF-8")
                +"&firebaseToken="+
                URLEncoder.encode(token, "UTF-8");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(context, "Sent DATA !!!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    public static void sendRegisterUserToQRCode(final Context context, String username, String qrCode) throws UnsupportedEncodingException {

        String url ="http://"+Configuration.getInstance().getChatServerIp()+":"+Configuration.getInstance().getChatServerPort()+"/session/mobile/assign?user="+
                URLEncoder.encode( username, "UTF-8") + "&androidid="+
                URLEncoder.encode(
                        Settings.Secure.getString(
                                context.getContentResolver(),
                                Settings.Secure.ANDROID_ID
                        )
                        , "UTF-8")
                +"&qrcode="+
                URLEncoder.encode(qrCode, "UTF-8");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(context, "Sent DATA !!!", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void sendMessage(final Context context, String username, String message) throws UnsupportedEncodingException {

        String url ="http://"
                +Configuration.getInstance().getChatServerIp()
                +":"
                +Configuration.getInstance().getChatServerPort()
                +"/session/msg?user="
                +URLEncoder.encode( username, "UTF-8")
                +"&message="
                +URLEncoder.encode(message, "UTF-8");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}