/**
 * LocationActivity.java
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.responsemodel.LocationResponseModel;
import com.techuva.councellorapp.contus_Corporate.restclient.LocationRestClient;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 8/14/2015.
 */
public class LocationActivity extends Activity {
    /*8Activity**/
    private LocationActivity mLocationActivity;
    /**Edit text**/
    private AutoCompleteTextView editSearch;
    /**Smooth progress bar**/
    private SmoothProgressBar googleNow;
    //Locaton details in list
    private List<LocationResponseModel.LocationDetails> locationDetails;
    //String location
    private String location;
    //String address
    private String address;
    //Floating ACTION button
    private FloatingActionButton fabSearchLocation;
    //Relative Layout
    private RelativeLayout internetConnection;
    //Relative list
    private RelativeLayout relativeList;
    //arraylist
    private ArrayList<String> locationNames;
    //Simple adapter
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_location);
        /**View are creating when the activity is started**/
        init();
    }
    /**
     * Instantiate the method
     */
    private void init() {
        /**Initializing the activity**/
        mLocationActivity = this;
        /**Initializing the list view**/
        editSearch = (AutoCompleteTextView) findViewById(R.id.editSearch);
        googleNow = (SmoothProgressBar) findViewById(R.id.google_now);
        internetConnection = (RelativeLayout) findViewById(R.id.internetConnection);
        relativeList = (RelativeLayout) findViewById(R.id.list);
        fabSearchLocation =  findViewById(R.id.fab);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                 Log.e("1", "1");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationDetailsLoad(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Editable", s.getFilters().toString());
            }
        });
        /**Text change listner**/
        editSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {    /**If search icon is clicked in the keypad the condition goes inside**/
                    location = editSearch.getText().toString().trim();/**Value from the edittext**/
                    if (!location.isEmpty()) {
                        locationDetailsLoad(location); /**Request and response method is called**/
                    } else {
                        MApplication.hideKeyboard(mLocationActivity); /**Hiding the keyborad**/
                        Toast.makeText(mLocationActivity, getResources().getString(R.string.enter_valid_location_name), Toast.LENGTH_SHORT).show();  /**Toast message wthen search key is wrong**/
                        editSearch.requestFocus(); /**Requesting focus**/
                    }
                    return true;
                }
                return false;
            }

        });
       //Interface definition for a callback to be invoked when a view is clicked.
        fabSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = editSearch.getText().toString().trim();   //value from the edittext is stored in the string
                if (!location.isEmpty()) {
                    locationDetailsLoad(location); /**Request and response method is called**/
                } else {
                    MApplication.hideKeyboard(mLocationActivity);  /**Hiding the keyborad**/
                    Toast.makeText(mLocationActivity, getResources().getString(R.string.enter_valid_location_name), Toast.LENGTH_SHORT).show();  /**Toast message wthen search key is wrong**/
                    editSearch.requestFocus(); /**Requesting focus**/
                }
            }
        });

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Hiding the keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @SuppressLint("RestrictedApi")
    private void locationDetailsLoad(String s) {
        /**If internet connection is available**/
        if (MApplication.isNetConnected(mLocationActivity)) {
            //Internet connection visiblity gone
            internetConnection.setVisibility(View.GONE);
            /**Progree bar visiblity**/
            googleNow.setVisibility(View.VISIBLE);
            /**Progress bar start**/
           googleNow.progressiveStart();
            //If ocation is not null
                //Country stored in the prefence and location are binded and send for the request in the server.
                address = s;
            LocationRestClient.getInstance().getHomePage(new String("false"), new String(address), new Callback<LocationResponseModel>() {
                @Override
                public void success(LocationResponseModel welcomeResponseModel, Response response) {
                    //arraylist
                    locationNames = new ArrayList<String>();
                    /**on success response this method is clled to get the value from the model class**/
                    locationResponse(welcomeResponseModel);

                }
                @Override
                public void failure(RetrofitError retrofitError) {
                    //Error message popups when the user cannot able to coonect with the server
                    MApplication.errorMessage(retrofitError, mLocationActivity);
                    //Progressbar stops
                    googleNow.progressiveStop();
                    //Progressbar became invisible
                    googleNow.setVisibility(View.GONE);
                }
            });
        } else {
            //This layout will be visible when the internet connection is not available
            internetConnection.setVisibility(View.VISIBLE);
            //View invlisible
           relativeList.setVisibility(View.GONE);
            //View invisible
            fabSearchLocation.setVisibility(View.GONE);
        }
    }

    /**
     * Resposne from the model class is binded into the adapter
     * @param countryResponseModel reponse is bindied into the mdoel class
     */
    private void locationResponse(final LocationResponseModel countryResponseModel) {
        /**Value from the response is stored in array list**/
        locationDetails = countryResponseModel.getResults();
        /**If array list is not empty**/
        if (!locationDetails.isEmpty()) {
            /**List visiblity**/
            for(int i=0;i<countryResponseModel.getResults().size();i++){
                locationNames.add(locationDetails.get(i).getFormattedAddress());
            }
            mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, locationNames) {
                @Override
                public View getView(final int position,
                                    View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.BLACK);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Storing the address from the response
                            String addressArea = countryResponseModel.getResults().get(position).getFormattedAddress();
                            //Storing the latitude from the modelclass
                            String lat = countryResponseModel.getResults().get(position).getGeometry().getLocation().getLat();
                            //Storing the longitude from the model class
                            String longitude = countryResponseModel.getResults().get(position).getGeometry().getLocation().getLongitude();
                            //Storing the address in prefernce
                            MApplication.setString(mLocationActivity, Constants.CITY, addressArea);
                            //Storing the latitude in prefernce
                            MApplication.setString(mLocationActivity, Constants.LATITUDE, lat);
                            //Storing the longitude in prefernce
                            MApplication.setString(mLocationActivity, Constants.LONGITUDE, longitude);
                            //Finishing the activity
                            finish();
                        }
                    });
                    return view;
                }
            };
            //Setting the adapter
            editSearch.setAdapter(mAdapter);
            //notifydata set changed
            mAdapter.notifyDataSetChanged();
            //Progressive bar stops
            googleNow.progressiveStop();
            //Progressive bar view gone
            googleNow.setVisibility(View.GONE);
        } else {
            googleNow.progressiveStop();
        }
    }


    /**
     * Calling this method from xml file when performing the click on the ACTION
     * @param clickedView
     */
    public void onClick(final View clickedView) {

        if (clickedView.getId() == R.id.imgBackArrow) {
            /**Finish the activity**/
            this.finish();
        } else if (clickedView.getId() == R.id.internetRetry) {
            /**Finish the activity**/
            location = editSearch.getText().toString().trim();
            if (!location.isEmpty()) {
                /**Request and response method is called**/
                locationDetailsLoad(location);
            } else {
                /**Hiding the keyborad**/
                MApplication.hideKeyboard(mLocationActivity);
                /**Toast message wthen search key is wrong**/
                Toast.makeText(mLocationActivity, getResources().getString(R.string.enter_valid_location_name),
                        Toast.LENGTH_SHORT).show();
                /**Requesting focus**/
                editSearch.requestFocus();
            }
        }

    }
}
