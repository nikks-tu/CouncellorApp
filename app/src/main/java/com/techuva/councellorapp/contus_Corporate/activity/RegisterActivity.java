/*
 * RegisterActivity.java
 * <p/>
 * Getting the country and code from response
 *
 * @category Contus
 * @package com.contus.activity
 * @version 1.0
 * @author Contus Team <developers@contus.in>
 * @copyright Copyright (C) 2015 Contus. All rights reserved.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */


package com.techuva.councellorapp.contus_Corporate.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.corporate.contus_Corporate.chatlib.utils.ContusConstantValues;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.residemenu.MenuActivity;
import com.techuva.councellorapp.contus_Corporate.responsemodel.ContactResponseModel;
import com.techuva.councellorapp.contus_Corporate.responsemodel.MobileNumberChange;
import com.techuva.councellorapp.contus_Corporate.responsemodel.SMSgatewayResponseModel;
import com.techuva.councellorapp.contus_Corporate.restclient.ContactsRestClient;
import com.techuva.councellorapp.contus_Corporate.restclient.MobileNumberCheckRestClient;
import com.techuva.councellorapp.contus_Corporate.restclient.SMSgatewayRestClient;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.service.ChatService;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;
import com.techuva.councellorapp.contusfly_corporate.utils.Utils;
import com.techuva.councellorapp.contusfly_corporate.views.CustomToast;
import com.techuva.new_changes_corporate.SignUpActivity;

import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.ACTIVITY_REQ_CODE;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.IS_CONTACTS_SYNCED;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.USERNAME;

/**
 * Created by user on 6/29/2015.
 */
public class RegisterActivity extends Activity implements Constants, Utils.ContactResult {
    //Activity
    private Dialog customDialogBox;
    @SuppressLint("StaticFieldLeak")
    public static RegisterActivity mRegisterActivity;
    //Country
    private TextView editCountry;
    //phone number code
    private TextView txtPhoneNumberCode;
    //Edittext phone number
    private EditText editPhoneNumber;
    //Code
    private String code;
    //Phone number
    private String phoneNumber;
    //Phone number code
    private String phoneCode;
    //Internet connection
    private RelativeLayout internetConnection;
    //Main Layout
    private FrameLayout mainLayout;
    //Text View
    private TextView txtPlus;
    //Smooth progressbar
    private SmoothProgressBar googleNow;

    private TextView txtNext;
    String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
    };
    private final int PERMISSION_ALL = 1;
    Toast exitToast;
    Boolean doubleBackToExitPressedOnce = true;
    private String password;
    private String deviceCountryCode;
    private String userId;
    ImageView iv_back;
    TextView tv_already_register, tv_register_here;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /*View are creating when the activity is started**/
        init();
        exitToast = Toast.makeText(getApplicationContext(), "Press back again to exit Ananth Reddy App", Toast.LENGTH_SHORT);

        editPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                txtNext.setEnabled(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                txtNext.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                txtNext.setEnabled(true);
            }
        });

        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });


        tv_register_here.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });



    }


    /**
     * Instantiate the method
     */
    private void init() {
        /*Initializing the activity**/
        mRegisterActivity = this;
        /*Screen tracker**/
        Tracker trackerRegister = MApplication.getTracker(mRegisterActivity, MApplication.TrackerName.APP_TRACKER);
        /*Setting the screen name to display in analytics**/
        trackerRegister.setScreenName("Register Screen");
        /*hit**/
        trackerRegister.send(new HitBuilders.AppViewBuilder().build());
        /*Initializing the textview**/
        //Text next
        txtNext = findViewById(R.id.txtAgreeAndCondition);
        txtNext.setEnabled(true);
        /*Initializing the edittext**/
        editCountry = findViewById(R.id.txtCountry);
        /*Initializing the edittext**/
        txtPhoneNumberCode = findViewById(R.id.txtPhoneNumberCode);
        /*Initializing the edittext**/
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        googleNow = findViewById(R.id.google_now);
        /*Initializing the relative layout**/
        mainLayout = findViewById(R.id.mainLayout);
        internetConnection = findViewById(R.id.internetConnection);
        txtPlus = findViewById(R.id.txtPlus);
        /*Initializing the view**/
        //Divider line
        View txtView = findViewById(R.id.view);
        iv_back = findViewById(R.id.iv_back);
        // tv_already_register, tv_register_here;
        tv_already_register = findViewById(R.id.tv_already_register);
        tv_register_here = findViewById(R.id.tv_register_here);
        /*initializing the relative layout**/
        //Root view
        RelativeLayout rootView = findViewById(R.id.rootlayout);
        /*Calling this method to hide the button when the keypad opens**/
        MApplication.hideButtonKeypadOpens(mRegisterActivity, rootView, txtNext, txtView);
        /*Setting the string value as default**/
        MApplication.setString(mRegisterActivity, Constants.DIALOG_POSITION, "-1");
        //Setting the opt code as null in preference
        MApplication.setString(mRegisterActivity, Constants.OTPCODE, "");

    }

    /**
     * The onclicklistener will be called when any widget like button, text, image etc is either clicked or touched or
     * focused upon by the user.
     *
     * @param clickedView
     */
    public void onClick(final View clickedView) {
        if (clickedView.getId() == R.id.txtAgreeAndCondition) {
            //nikita
            if(isWorkingInternetPersent()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(!hasPermissions(this, PERMISSIONS)){
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                        //  processInsideSplash();
                    }
                    else
                    {
                        txtNext.setEnabled(false);
                        registerValidation();
                    }
                }
                else{
                    txtNext.setEnabled(false);
                    registerValidation();
                }
            }
            // Internet Warning
            else{
                showAlertDialog(RegisterActivity.this, "Internet Connection",
                        "You need an active Internet Connection for this app", false);
            }

        } else if (clickedView.getId() == R.id.txtCountry) {
            /*calling custom list dialog method**/
            Intent a = new Intent(RegisterActivity.this, CountryActivity.class);
            startActivity(a);
        } else if (clickedView.getId() == R.id.internetRetry) {
            /*calling custom list dialog method**/
            response();
        } else if (clickedView.getId() == R.id.txtPhoneNumberCode) {
            Intent a = new Intent(RegisterActivity.this, CountryActivity.class);
            startActivity(a);
        }
    }


    private void registerValidation() {
        /*Getting the value from edit text***/
        code = txtPhoneNumberCode.getText().toString().trim();
        /*Getting the value from edit text**/
        phoneNumber = editPhoneNumber.getText().toString().trim();
        MApplication.setString(mRegisterActivity, Constants.PHONE_NUMBER, phoneNumber);
        //If edittext country matches
        if (editCountry.getText().toString().trim().equals(getResources().getString(R.string.activity_register_select))) {
            //Toast message will display if the country is not chosen
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.select_your_country),
                    Toast.LENGTH_SHORT).show();
            //If the code field is empty
        } else if (TextUtils.isEmpty(code)) {
            // toast message will display
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.enter_your_country_code),
                    Toast.LENGTH_SHORT).show();
            //Requesting focus
            txtPhoneNumberCode.requestFocus();
            //If the code length is less than 2
        } else if (code.length() < 1) {
            // toast message will display
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.enter_valid_code),
                    Toast.LENGTH_SHORT).show();
            //Requesting focus
            txtPhoneNumberCode.requestFocus();
            //If the phone number field is empty
        } else if (TextUtils.isEmpty(phoneNumber)) {
            //Toast message will display
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.enter_your_phone_number),
                    Toast.LENGTH_SHORT).show();
            //Requesting focus
            editPhoneNumber.requestFocus();
            //If the phone number field is less than 2
        } else if (phoneNumber.length() < 9) {
            //Toast message will display
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.entered_mobile_number_invalid),
                    Toast.LENGTH_SHORT).show();
            //Requesting focus
            editPhoneNumber.requestFocus();
        } else {
            MApplication.setString(mRegisterActivity, Constants.PHONE_NUMBER, phoneNumber);
            //Resquest to the server
            //Nikita
            //smsGateway();
            checkMobileNumber();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*Google analytics will start screen tracking**/
        GoogleAnalytics.getInstance(this).reportActivityStart(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_FIRST_USER) {
            setResult(Activity.RESULT_FIRST_USER);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*Google analytics will stop screen tracking**/
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        txtNext.setEnabled(true);
        //Hides the keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /*Sending the request and getting the response using the method**/
        response();

    }


    /**
     * Sending the request and getting the response using the method
     */
    private void response() {
        /*If internet connection is available**/
        if (MApplication.isNetConnected(mRegisterActivity)) {
            /*Getting the country value from shared prefernce**/
            String countryName = MApplication.getString(mRegisterActivity, Constants.COUNTRY);
            /*Getting the phone number code value from shared prefernce**/
            String phoneCode = MApplication.getString(mRegisterActivity, Constants.PHONE_NUMBER_CODE);
            /*If country name is not null and phone code is not null**/
            if (countryName != null && phoneCode != null) {
                /*Comparing the values**/
                if (MApplication.getString(mRegisterActivity, Constants.COUNTRY).equals(getResources().getString(R.string.activity_register_select))) {
                    /*Setting the text color**/
                    editCountry.setTextColor(getResources().getColor(R.color.view_color));
                    //Setting the text color
                    txtPhoneNumberCode.setTextColor(getResources().getColor(R.color.view_color));
                } else {
                    /*Setting the text color**/
                    editCountry.setTextColor(Color.BLACK);
                    /*Setting the text color**/
                    txtPlus.setTextColor(getResources().getColor(android.R.color.black));
                }
                /*Setting the country value for text**/
                editCountry.setText(countryName);
                /*Setting the phone code value for text**/
                txtPhoneNumberCode.setText(phoneCode);
                /*Setting the color for text**/
                txtPhoneNumberCode.setTextColor(Color.BLACK);
                /*Layout visibility**/
                mainLayout.setVisibility(View.VISIBLE);
                /*Internet connection invisible**/
                internetConnection.setVisibility(View.INVISIBLE);
            }
        } else {
            /*Layout visibility**/
            mainLayout.setVisibility(View.INVISIBLE);
            /*Internet connection invisible**/
            internetConnection.setVisibility(View.VISIBLE);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            // Do what ever you want
            exitToast.show();
            doubleBackToExitPressedOnce = false;
        } else{
            finishAffinity();
            finish();
            /*Setting the text**/
            editCountry.setText(MApplication.getString(mRegisterActivity, Constants.COUNTRY));
            // Do exit app or back press here
            super.onBackPressed();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int[] scrcoords = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getWindow().getCurrentFocus()).getWindowToken(), 0);
            }
        }
        return ret;
    }

    /**
     * Sending the request and getting the response using the method
     */
    private void smsGateway() {
        if (MApplication.isNetConnected(mRegisterActivity)) {
            Log.e("check", code + phoneNumber + "");
            /*Progress bar visibility*/
            googleNow.setVisibility(View.VISIBLE);
            /*Progress bar start*/
            googleNow.progressiveStart();
            /*Request and response in retrofit*/
            SMSgatewayRestClient.getInstance().postSMSgateway("sms_api", code + phoneNumber
                    , new Callback<SMSgatewayResponseModel>() {
                        @Override
                        public void success(SMSgatewayResponseModel smsGateWayResponse, Response response) {
                            //Response from the server if the api request is success
                            smsGateWayResponse(smsGateWayResponse);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //Error message popups when the user cannot able to connect with the server
                            MApplication.errorMessage(retrofitError, mRegisterActivity);
                        }

                    });
            /*Progressive bar stop**/
            googleNow.progressiveStop();
            /*Visibility gone**/
            googleNow.setVisibility(View.GONE);
        }
    }

    private void smsGateWayResponse(SMSgatewayResponseModel smsGateWayResponse) {


       /* if (("0").equals(smsGateWayResponse.getSuccess())) {
            String msg = smsGateWayResponse.getMsg();
            Toast.makeText(mRegisterActivity, msg, Toast.LENGTH_SHORT).show();
        } else {*/
        MApplication.setString(mRegisterActivity, Constants.PHONE_NUMBER, phoneNumber);
        /*starting the activity**/
        Intent a = new Intent(RegisterActivity.this, AccountVerification.class);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /* Starting the activity **/
        startActivityForResult(a, ACTIVITY_REQ_CODE);
        /* }*/
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            }
        }
        return true;
    }

    // Check Internet Connection
    public boolean isWorkingInternetPersent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    txtNext.performClick();

                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    // Alert Dialog for Internet

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= 21)
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.create().show();
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        // alertDialog.setIcon((status) ? R.mipmap.ic_launcher : R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton(-1,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private void checkMobileNumber() {
        if (MApplication.isNetConnected(mRegisterActivity)) {
            googleNow.setVisibility(View.VISIBLE);
            /*Custom progressbar start**/
            googleNow.progressiveStart();

            /*  Requesting the checkOTPCodeResponse from the given base url**/
            MobileNumberCheckRestClient.getInstance().postCheckMobileData("mobilenoexistapi", this.phoneNumber
                    , new Callback<JsonElement>() {
                        @Override
                        public void success(JsonElement welcomeResponseModel, Response response) {
                            /*If checkOTPCodeResponse is success this method is called**/
                            googleNow.progressiveStop();
                            googleNow.setVisibility(View.GONE);
                            JsonObject jsonObject = welcomeResponseModel.getAsJsonObject();
                            int errorCode = jsonObject.get("success").getAsInt();
                            String msg = jsonObject.get("msg").getAsString();
                            if(errorCode ==1)
                            {
                                smsGateway();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), ""+msg, Toast.LENGTH_LONG).show();
                            }
                            //checkMobileNumberResponse(welcomeResponseModel);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //Toast.makeText(getApplicationContext(), ""+retrofitError, Toast.LENGTH_SHORT).show();
                            /*Progressive bar stop**/
                            googleNow.progressiveStop();
                            /*Visibility gone**/
                            googleNow.setVisibility(View.GONE);
                            //Error message popups when the user cannot able to coonect with the server
                           // MApplication.errorMessage(retrofitError, mRegisterActivity);

                        }
                    });
        } else {
            //Toast message will display when the internet connection is not available
            Toast.makeText(mRegisterActivity, getResources().getString(R.string.check_internet_connection),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void checkMobileNumberResponse(MobileNumberChange mobileNumberChange) {
        /*success value is retrived from model class**/
        String success = mobileNumberChange.getSuccess();
        /*message value is retrived from model class**/
        String msg = mobileNumberChange.getMsg();
        /*mobileKey value is retrived from model class**/
        String mobileKey = mobileNumberChange.getResults().getMobileKey();
        /*Progressive bar stops**/

        if (("1").equals(success) && ("0").equals(mobileKey)) {
            /*Toast message is displayed when above condition satisfied**/
            Toast.makeText(mRegisterActivity, msg,
                    Toast.LENGTH_SHORT).show();
            /*Progressive bar stop**/
            googleNow.progressiveStop();
            /*Visibility gone**/
            googleNow.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(),PersonalInfo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, ACTIVITY_REQ_CODE);
        } else if (("2").equals(success) && ("1").equals(mobileKey)) {
            //setting the string
            MApplication.setString(RegisterActivity.this, VIBRATION_TYPE,
                    "0");
            /*Toast message is displayed when the following field is empty**/
            String clickValue = "1";
            /*Getting the user id from checkOTPCodeResponse**/
            userId = mobileNumberChange.getResults().getUserId();
            /*Getting the profile image from checkOTPCodeResponse**/
            String profileImage = mobileNumberChange.getResults().getProfileImg();
            /*Getting the user name checkOTPCodeResponse**/
            String userName = mobileNumberChange.getResults().getUserName();
            password=mobileNumberChange.getResults().getPassword();
            /*Setting the username string in preference**/
            MApplication.setString(mRegisterActivity, Constants.PROFILE_USER_NAME, userName);
            /*Setting the profileImage string in preference**/
            MApplication.setString(mRegisterActivity, Constants.PROFILE_IMAGE, profileImage);
            /*Progressive bar stop**/
            googleNow.progressiveStop();
            /*Visibility gone**/
            googleNow.setVisibility(View.GONE);
            /*Setting the USER_ID string in preference**/
            MApplication.setString(mRegisterActivity, Constants.USER_ID, userId);
            //saving in preference
            MApplication.storeBooleanInPreference(IS_CONTACTS_SYNCED, true);
            //saving in preference
            MApplication.storeStringInPreference(USERNAME,phoneCode + phoneNumber);
            //saving in preference
            MApplication.storeStringInPreference("password",password);
            Utils.getMobileContacts(this, this, deviceCountryCode);
            /*Custom dialog**/
            customDialog(mRegisterActivity, msg, clickValue);

        }
        MApplication.setBoolean(getApplicationContext(),"notification click",false);
    }

    public void customDialog(final Activity activity, final String msg, final String clickValue) {
        /* Custom dialog **/
        customDialogBox = new Dialog(activity);
        /* No title **/
        customDialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* setting the transparent window **/
        customDialogBox.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        /* Setting the custom dialog **/
        customDialogBox.setContentView(R.layout.custom_dialog);
        TextView txtResult = customDialogBox
                .findViewById(R.id.textView_dialog);
        /* Setting the value from string in to the text view* */
        txtResult.setText(msg);
        /* Setting the back button false **/
        customDialogBox.setCancelable(false);
        /* dialog show **/
        customDialogBox.show();
        /* Button initialization **/
        Button okButton =  customDialogBox
                .findViewById(R.id.okBtn);
        final Button cancelBtn =  customDialogBox
                .findViewById(R.id.noBtn);
        /* button click listener **/
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (("1").equals(clickValue)) {
                    /* Calling the material design custom dialog **/
                    MApplication.materialdesignDialogStart(mRegisterActivity);
                    MApplication.setBoolean(mRegisterActivity,"test",true);
                    /*Setting the boolean value as true on the first time installation**/
                    MApplication.setBoolean(mRegisterActivity, FIRST_TIME, true);
                   /* activity.startService(new Intent(activity, ChatService.class).putExtra("username", phoneCode +
                            phoneNumber).putExtra("password", password).setAction(ContusConstantValues.CONTUS_XMPP_ACTION_CONNECTION_REQUEST))*/
                    activity.startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                    customDialogBox.cancel();// custom dialog cancel
                    Intent intent=new Intent(RegisterActivity.this,MenuActivity.class);
                    intent.putExtra("fromActivityName","yes");
                    startActivity(intent);
                    finish();
                }
            }

        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* custom dialog cancel **/
                customDialogBox.cancel();
            }
        });
    }

    @Override
    public void OnContactResult(String mobileNos) {
        Log.e("mobileNo","mobileNo"+"");
        if (mobileNos != null && !mobileNos.isEmpty()) {
            contactsLoad(mobileNos);
        } else {
            CustomToast.showToast(this,
                    getString(R.string.text_no_contacts_to_sync));
        }
    }


    private void contactsLoad(String contactsString) {
        Log.e("contactsString",contactsString+"");
        if (MApplication.isNetConnected(RegisterActivity.this)) {
            /*  Requesting the response from the given base url**/
            ContactsRestClient.getInstance().postContactDetails("contact_api", userId, contactsString,
                    new Callback<ContactResponseModel>() {
                        @Override
                        public void success(ContactResponseModel contactResponseModel, retrofit.client.Response response) {
                            startService(new Intent(RegisterActivity.this, ChatService.class).setAction(ContusConstantValues.CONTUS_XMPP_ACTION_GET_ROSTER));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Refresh conatcts
                                    MApplication.setBoolean(RegisterActivity.this,"contact_sync",false);
                                }
                            }, 3000);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //Error message popups when the user cannot able to coonect with the server
                            MApplication.errorMessage(error,RegisterActivity.this);
                        }
                    });
        }
    }

}
