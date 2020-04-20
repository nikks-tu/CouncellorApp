/**
 * SplashScreenActivity.java
 * <p/>
 * Displays the splash screen for milliseconds.
 *
 * @category Contus
 * @package com.contus.activity
 * @version 1.0
 * @author Contus Team <developers@contus.in>
 * @copyright Copyright (C) 2015 Contus. All rights reserved.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */


package com.techuva.councellorapp.contus_Corporate.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gcm.GCMRegistrar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.councellorapp.BuildConfig;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.MApplication;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.residemenu.MenuActivity;
import com.techuva.councellorapp.contusfly_corporate.api_interface.AppVersionDataInterface;
import com.techuva.councellorapp.contusfly_corporate.model.AppVersionPostParameters;
import com.techuva.councellorapp.contusfly_corporate.utils.ContusPreferences;
import com.techuva.new_changes_corporate.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * Created by user on 6/29/2015.
 */
public class SplashScreenActivity extends Activity implements Constants {
    /**
     * Splash time
     **/
    int splashTimeOut = 1000;
    String deviceId;
    //Contus preference
    private ContusPreferences contusPreferences;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    FrameLayout linear_layout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        //Initializing the activity
        context = SplashScreenActivity.this;
        contusPreferences = new ContusPreferences(this);
        //Checking the device
        //GCMRegistrar.checkDevice(this);
        //device id from the mobile
        //String deviceId = GCMRegistrar.getRegistrationId(this);
        //Setting the tevice id in prefernce
        //////////FCM Code///////////////////////////////////

        FirebaseApp.initializeApp(context);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

//                    String message = intent.getStringExtra("message");
//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        deviceId=FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(this, FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
        contusPreferences.setDeviceToken(deviceId);
        //Log.d("deviceIdSpl",deviceId);
        //Set is notify false
        contusPreferences.setIsNotify(false);
        //Checking the device
        //GCMRegistrar.checkDevice(this);
        //Getting the registration id
        //deviceId = GCMRegistrar.getRegistrationId(this);
        //if device id is not empty
        if (deviceId != null && deviceId.isEmpty()) {
            //Registering is using t=sender id
            GCMRegistrar.register(this, SENDER_ID);
        }
        //if device id is not null
        if (deviceId != null && deviceId.length() > 0) {
            //Setting the prefernce
            contusPreferences.setDeviceToken(deviceId);
            //setting in prefernce

            MApplication.setString(SplashScreenActivity.this, Constants.GCM_REG_ID, deviceId);
        }
        Log.e("deviceId   ", deviceId + "");
        /**Setting the layout full screen**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Full screen view
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        /**layoput**/
        setContentView(R.layout.activity_splash);

        linear_layout =findViewById(R.id.linear_layout);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                //validateAppVersion();
               Boolean isNetConnected =  MApplication.isNetConnected(SplashScreenActivity.this);
               if(isNetConnected)
               {
                   validateAppWithRetrofit();
               }
               else {
                  // Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                  // finish();
                  /* Snackbar snackbar = Snackbar.make(linear_layout, "Please check your internet connection!", Snackbar.LENGTH_LONG);
                   snackbar.show();*/
                   AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
                   builder.setMessage(Html.fromHtml("<font color='#000000'>Please check your internet connection!</font>"))
                           .setCancelable(false)
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   finish();
                               }
                           });
                   AlertDialog alert = builder.create();
                   alert.show();
               }
                //Getting the boolean from preference


            }
        }, splashTimeOut);

    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        deviceId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + deviceId);

        //if (!TextUtils.isEmpty(regId))
            //txtRegId.setText("Firebase Reg Id: " + regId);
       // else
            //txtRegId.setText("Firebase Reg Id is not received yet!");
    }



    public void validateAppVersion(){
        final int versionName = BuildConfig.VERSION_CODE;
        final HashMap<String, Integer> paramss = new HashMap<>();
        paramss.put("device_id", versionName);
        CustomRequest.makeJsonObjectRequest(SplashScreenActivity.this,Constants.LIVE_BASE_URL+"api/v1/version",new JSONObject(paramss), new VolleyResponseListener() {
            @Override
            public void onError(String message) {
//            dialog.dismiss();
                Log.i("onErrormessage", "message= " +message);
            }

            @Override
            public void onResponse(JSONObject response) {
                Log.i("PCCmessage", "message " + response.toString());
                JSONObject result = response;
                int version_no=0;
                try {
                    Log.i("PCCmessage", "message " + result.getString("msg"));

                    version_no =Integer.parseInt(result.getString("results"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (versionName<version_no) {
                        context = SplashScreenActivity.this;
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreenActivity.this);
                        final View view = inflater.inflate(R.layout.versionpopup, null);
                        alertDialog.setView(view);
                        final AlertDialog alert = alertDialog.show();
                        TextView updateText = view.findViewById(R.id.id_updateText);
                        TextView update = view.findViewById(R.id.id_update);
                        TextView cancel = view.findViewById(R.id.cancel);
                        updateText.setText(result.getString("msg"));
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.techuva.councellorapp" )));
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (alert != null && alert.isShowing()) {
                                    alert.dismiss();


                                    if (!MApplication.getBoolean(SplashScreenActivity.this,
                                            FIRST_TIME)) {
                                        //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                                        // to communicate with a background Service.
                                        Intent intent = new Intent(getApplicationContext(),
                                                Welcome.class);
                                       /* Intent intent = new Intent(getApplicationContext(),
                                                SignUpActivity.class);*/
                                        //starting the activity
                                        startActivity(intent);
                                    } else {
                                        ((MApplication) getApplication()).startLoginService(SplashScreenActivity.this);
                                        //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                                        // to communicate with a background Service.
                                        Intent intent = new Intent(getApplicationContext(),
                                                MenuActivity.class);
                                        //starting the activity
                                        startActivity(intent);
                                    }

                                    finish();
                                }
                            }
                        });

                        alert.show();
                        alert.setCancelable(false);
                    }
                    else
                    {
                        if (!MApplication.getBoolean(SplashScreenActivity.this,
                                FIRST_TIME)) {
                            //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                            // to communicate with a background Service.
                            Intent intent = new Intent(getApplicationContext(),
                                    Welcome.class);
                            /*Intent intent = new Intent(getApplicationContext(),
                                    SignUpActivity.class);*/
                            //starting the activity
                            startActivity(intent);
                        } else {
                            ((MApplication) getApplication()).startLoginService(SplashScreenActivity.this);
                            //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                            // to communicate with a background Service.
                            Intent intent = new Intent(getApplicationContext(),
                                    MenuActivity.class);

                            /*Intent intent = new Intent(getApplicationContext(),
                                    Rewards_Activty.class);*/
                            //starting the activity
                            startActivity(intent);
                        }

                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void validateAppWithRetrofit(){
        final int versionName = BuildConfig.VERSION_CODE;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AppVersionDataInterface service = retrofit.create(AppVersionDataInterface.class);
        String header ="syNJrVjWAAvnzmsNuhELNeYo2BB0Ol6dAs1T4gq2";
        Call<JsonElement> call = service.getStringScalar(header, new AppVersionPostParameters(String.valueOf(versionName)));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                int version_no;
                if(response.body()!=null)
                {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    version_no = jsonObject.get("results").getAsInt();
                    //Toast.makeText(context, "Got Response", Toast.LENGTH_SHORT).show();
                    if (versionName<version_no) {
                        context = SplashScreenActivity.this;
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreenActivity.this);
                        final View view = inflater.inflate(R.layout.versionpopup, null);
                        alertDialog.setView(view);
                        final String updateUrl = jsonObject.get("updateLink").getAsString();
                        JsonObject obj1 = jsonObject.get("aws_s3").getAsJsonObject();
                        SharedPreferences.Editor editor = getSharedPreferences("KEYS", MODE_PRIVATE).edit();
                        editor.putString("access_key", obj1.get("access_key").getAsString());
                        editor.putString("secret_key",  obj1.get("secret_key").getAsString());
                        editor.putString("bucket", obj1.get("bucket").getAsString());
                        editor.apply();
                        final AlertDialog alert = alertDialog.show();
                        TextView updateText =  view.findViewById(R.id.id_updateText);
                        TextView update =  view.findViewById(R.id.id_update);
                        TextView cancel =  view.findViewById(R.id.cancel);
                        updateText.setText(jsonObject.get("msg").getAsString());
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(updateUrl!=null && !updateUrl.isEmpty())
                                {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl)));
                                }
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (alert != null && alert.isShowing()) {
                                    alert.dismiss();

                                    if (!MApplication.getBoolean(SplashScreenActivity.this,
                                            FIRST_TIME)) {
                                        //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                                        // to communicate with a background Service.
                                        Intent intent = new Intent(getApplicationContext(),
                                                Welcome.class);
                                      /*  Intent intent = new Intent(getApplicationContext(),
                                                SignUpActivity.class);*/

                                        //starting the activity
                                        startActivity(intent);
                                    } else {
                                        ((MApplication) getApplication()).startLoginService(SplashScreenActivity.this);
                                        //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested BroadcastReceiver components, and startService(Intent) or bindService(Intent, ServiceConnection, int)
                                        // to communicate with a background Service.
                                        Intent intent = new Intent(getApplicationContext(),
                                                MenuActivity.class);
                                        //starting the activity
                                        startActivity(intent);
                                    }

                                    finish();
                                }
                            }
                        });

                        alert.show();
                        alert.setCancelable(false);
                    }
                    else
                    {
                        JsonObject obj1 = jsonObject.get("aws_s3").getAsJsonObject();
                        SharedPreferences.Editor editor = getSharedPreferences("KEYS", MODE_PRIVATE).edit();
                        editor.putString("access_key", obj1.get("access_key").getAsString());
                        editor.putString("secret_key",  obj1.get("secret_key").getAsString());
                        editor.putString("bucket", obj1.get("bucket").getAsString());
                        editor.apply();
                        if (!MApplication.getBoolean(SplashScreenActivity.this,
                                FIRST_TIME)) {
                            Intent intent = new Intent(getApplicationContext(),
                                    Welcome.class);
                            /* Intent intent = new Intent(getApplicationContext(),
                                    SignUpActivity.class);*/

                            //starting the activity
                            startActivity(intent);
                        } else {
                            ((MApplication) getApplication()).startLoginService(SplashScreenActivity.this);
                            Intent intent = new Intent(getApplicationContext(),
                                    MenuActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
             //Toast.makeText(context, "Error" +t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
