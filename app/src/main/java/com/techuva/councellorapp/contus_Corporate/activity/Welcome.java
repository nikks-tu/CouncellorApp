/**
 * Welcome.java
 * <p/>
 * Welcome screen of the project and details are loaded form api.
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
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.responsemodel.WelcomeResponseModel;
import com.techuva.councellorapp.contus_Corporate.restclient.WelcomeRestClient;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;
import com.techuva.new_changes_corporate.SignUpActivity;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.ACTIVITY_REQ_CODE;

/**
 * Created by user on 6/29/2015.
 */
public class Welcome extends Activity implements Constants {
    // Textview terms and condition
    private TextView txtTermsAndCondition;
    FrameLayout fl_data;
    //Activity
    public static Activity mTermsAndCondition;
   //Text view welcome message
    private TextView txtWelcomeMsg;
    LinearLayout ll_internet;
    //Smooth progress bar
    private SmoothProgressBar googleNow;
    //Agree and condition text
    private TextView txtAgreeAndContinue;
    //Welcome message
    private TextView txtWelcome;
    //View
    private View view;
 //Textview internet connection
    private TextView txtInternetConnection;
  //Internet retry
    private TextView internetRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        /**Calling the method when the activity is created**/
        init();
    }

    /**
     * Instantiate the method
     */
    private void init() {
        /**Initializing the utils**/
        txtAgreeAndContinue = (TextView) findViewById(R.id.txtAgreeAndCondition);
        txtWelcomeMsg = (TextView) findViewById(R.id.txtDetails);
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtInternetConnection = (TextView) findViewById(R.id.txtInternetConnection);
        googleNow = findViewById(R.id.google_now);
        view = findViewById(R.id.view);
        fl_data = findViewById(R.id.fl_data);
        ll_internet = findViewById(R.id.ll_internet);
        internetRetry = (TextView) findViewById(R.id.internetRetry);
        /**Initializing the activity**/
        mTermsAndCondition = this;
        MApplication.screenTracker(mTermsAndCondition, "Welcome Screen");
        /**Initalizing the views**/
        txtTermsAndCondition = (TextView) findViewById(R.id.txtTearmsAndCondition);
        /**UnderLining the text in android**/
        txtTermsAndCondition.setPaintFlags(txtTermsAndCondition.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /**Storing the value in shared prefernce**/
        MApplication.setString(mTermsAndCondition, Constants.COUNTRY, getResources().getString(R.string.activity_register_select));
        /*8Phone number code in shared prefernce**/
        MApplication.setString(mTermsAndCondition, Constants.PHONE_NUMBER_CODE, "");
        /**Version check**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**If it is lollipop sett the status bar color**/
            MApplication.settingStatusBarColor(mTermsAndCondition, getResources().getColor(android.R.color.black));
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        /**Google analytics will start screen tracking**/
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /**Google analytics will stop screen tracking**/
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    /**
     * The onclicklistener will be called when any widget like button, text, image etc is either clicked or touched or
     * focused upon by the user.
     * @param clickedView
     */
    public void onClick(final View clickedView) {
        if (clickedView.getId() == R.id.txtAgreeAndCondition) {
            //An intent is an abstract description of an operation to be performed.
            // It can be used with startActivity to launch an Activity
            setResult(RESULT_FIRST_USER);
            Intent a = new Intent(Welcome.this, RegisterActivity.class);
            /** Starting the activity **/
            startActivityForResult(a, ACTIVITY_REQ_CODE);
        } else if (clickedView.getId() == R.id.txtTearmsAndCondition) {
            /**Starting the activity**/
            //MApplication.startActivity(TERMS_AND_CONDITION_ACTION, mTermsAndCondition);
            Intent intent = new Intent(this, TermsAndCondition.class);
            startActivity(intent);
        } else if (clickedView.getId() == R.id.internetRetry) {
            /**Sending the request and response one the button is clicked**/
            response();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_FIRST_USER) {
            setResult(Activity.RESULT_FIRST_USER);
            finish();
        }
    }

    /**
     * Request and response api method
     */
    private void welcomeDetailsLoad() {
        /**Custom progressbar start**/
        googleNow.progressiveStart();
        /**  Requesting the response from the given base url**/
        WelcomeRestClient.getInstance().postLoginData(new String("welcomeapi")
                , new Callback<WelcomeResponseModel>() {
            @Override
            public void success(WelcomeResponseModel welcomeResponseModel, Response response) {
                /**If response is success this method is called**/
                welcomeResponse(welcomeResponseModel);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                /**Custom progress bar stop**/
                googleNow.progressiveStop();
                /**Visiblity gone**/
                googleNow.setVisibility(View.GONE);
                /**Error message will display when not able to connec t to the server**/
                MApplication.errorMessage(retrofitError, mTermsAndCondition);
            }
        });
    }

    /**
     * The values from the response are retrived using model class
     *
     * @param welcomeResponseModel model class
     */
    private void welcomeResponse(WelcomeResponseModel welcomeResponseModel) {
        /**welcome msg stored in model class are retrived**/
        String welcomeMessage = welcomeResponseModel.getData().get(0).getWelcomeMsg();
        /**Visiblity of the textview**/
        txtWelcome.setVisibility(View.VISIBLE);
        txtWelcomeMsg.setVisibility(View.VISIBLE);
        /**Visiblity of divider line**/
        view.setVisibility(View.VISIBLE);
        /**Visiblity of the textview**/
        txtTermsAndCondition.setVisibility(View.VISIBLE);
        /**String welcome message is displayed in text view**/
        txtWelcomeMsg.setText(welcomeMessage);
        /**Progressive bar stop**/
        googleNow.progressiveStop();
        /**Visiblity gone**/
        googleNow.setVisibility(View.GONE);
        /**Setting the button click enable true**/
        txtAgreeAndContinue.setVisibility(View.VISIBLE);
        /**Setting the text color**/
        txtAgreeAndContinue.setTextColor(getResources().getColor(R.color.blue_color));
    }


    @Override
    protected void onResume() {
        super.onResume();
        /**Sending the request and responce one the button is clicked**/
        response();


    }

    /**
     * Sending the request and responce one the button is clicked
     */
    private void response() {
        /**Once the internet connection is available**/
        if (MApplication.isNetConnected(mTermsAndCondition)) {
            /*8Textview invisible**/
            ll_internet.setVisibility(View.GONE);
            fl_data.setVisibility(View.VISIBLE);
           /* txtInternetConnection.setVisibility(View.INVISIBLE);
            *//**Invisible**//*
            internetRetry.setVisibility(View.INVISIBLE);
            *//**visible**//*
            googleNow.setVisibility(View.VISIBLE);
            *//**Api request is called owhile the screen is in resume**/
            welcomeDetailsLoad();
        } else {
            /**Visible**/
            ll_internet.setVisibility(View.VISIBLE);
            fl_data.setVisibility(View.GONE);
            txtInternetConnection.setVisibility(View.VISIBLE);
            /**Visible**/
            internetRetry.setVisibility(View.VISIBLE);
            /**Visiblity of the textview**/
            txtWelcome.setVisibility(View.INVISIBLE);
            /**Visiblity of the textview**/
            txtWelcomeMsg.setVisibility(View.INVISIBLE);
            /**Visiblity of divider line**/
            view.setVisibility(View.INVISIBLE);
            /**Visiblity of the textview**/
            txtTermsAndCondition.setVisibility(View.INVISIBLE);
            /**Visiblity gone**/
            googleNow.setVisibility(View.GONE);
            /**Setting the button click enable true**/
            txtAgreeAndContinue.setVisibility(View.INVISIBLE);

        }

    }
}
