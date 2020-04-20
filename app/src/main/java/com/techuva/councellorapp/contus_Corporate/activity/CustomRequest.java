package com.techuva.councellorapp.contus_Corporate.activity;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/*-----------------------------------------------------------------------------
Class Name: CustomRequest
Created By: Ramakrishna
Created Date: 15-05-2017
Modified By:
Modified Date:
Purpose: This Class is used for to request the server
-----------------------------------------------------------------------------*/

public class CustomRequest {
    static JsonObjectRequest jsonObjectRequest = null;

    public static void makeJsonObjectRequest(Context context, String url, JSONObject params, final VolleyResponseListener listener) {
        jsonObjectRequest = new JsonObjectRequest
                (url,params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("commanresponse", "encrypted data" + response);
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("commanresponse","login error == "+error);
                        listener.onError(error.toString());
                    }
                }

                )
        {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    Log.d("commanresponse_success","jsonString"+jsonString);
                    Log.d("commanresponse_success","HttpHeaderParser.parseCacheHeaders(response)"+HttpHeaderParser.parseCacheHeaders(response));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    Log.d("commanresponse","custom"+e.toString());
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    Log.d("commanresponse","custom123"+je.toString());
                    return Response.error(new ParseError(je));
                }
            }
        };

        // Access the RequestQueue through singleton class.
        Mysingleton.getMysingleton(context).addTorequestqueue(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}