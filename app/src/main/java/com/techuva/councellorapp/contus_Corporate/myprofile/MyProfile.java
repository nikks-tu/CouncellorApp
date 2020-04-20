package com.techuva.councellorapp.contus_Corporate.myprofile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.category.DepartmentAdapter;
import com.techuva.councellorapp.contus_Corporate.responsemodel.CategoryPollResponseModel;
import com.techuva.councellorapp.contus_Corporate.responsemodel.MyPersonalInfoModel;
import com.techuva.councellorapp.contus_Corporate.restclient.CategoryRestClient;
import com.techuva.councellorapp.contus_Corporate.restclient.MyPersonalInfoRestClient;
import com.techuva.councellorapp.contusfly_corporate.MApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 7/15/2015.
 */
public class MyProfile extends Fragment implements Constants {
    //Parent view
    private View parentView;
    //userid
    private String userId;
    //User name
    private String userName;
    //Text view user name
    private TextView txtUserName;
    //Location
    private TextView location;
    //Status details
    private TextView statusDetails;
    //Relative layout internet connection
    private ListView list;
    private RelativeLayout internetConnection;
    //Scroll view
    private RelativeLayout mainView;
    //Button retry
    private TextView btnRetry;
    private TextView connectionsCount;
    Activity mActivity;
    private List<CategoryPollResponseModel.Results> dataResults;
    //Category Adapter
    private DepartmentAdapter categoryAdapter;

    //CategoryArray
    private ArrayList<String> categoryArray = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.activity_myprofile, container, false);
        //Setting the status bar color for version more than l;ollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Setting the status bar color
            MApplication.settingStatusBarColor(getActivity(), getResources().getColor(android.R.color.black));
        }
        mActivity = getActivity();
        txtUserName = (TextView) parentView.findViewById(R.id.userName);
        location = (TextView) parentView.findViewById(R.id.location);
        statusDetails = (TextView) parentView.findViewById(R.id.statusDetails);
        statusDetails = (TextView) parentView.findViewById(R.id.statusDetails);
        internetConnection = (RelativeLayout) parentView.findViewById(R.id.internetConnection);
        btnRetry = (TextView) parentView.findViewById(R.id.internetRetry);
        mainView =  parentView.findViewById(R.id.main_view);
        connectionsCount = (TextView) parentView.findViewById(R.id.connectionsCount);
        //Getting the user id from the prefernce
        list = (ListView) parentView.findViewById(R.id.listView);
        userId = MApplication.getString(getActivity(), Constants.USER_ID);
        //Interface definition for a callback to be invoked when a view is clicked.
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Web request fro the personal info
                webRequest();
            }
        });
        //returning the views
        return parentView;
    }


    private void webRequest() {
        Log.e("userId", userId + "");
        /*If internet connection is available**/
        if (MApplication.isNetConnected(getActivity())) {
            //Visiblity gone
            internetConnection.setVisibility(View.GONE);
            //View visible
            mainView.setVisibility(View.VISIBLE);
            MApplication.materialdesignDialogStart(getActivity());
            /* Calling the material design custom dialog **/
            MyPersonalInfoRestClient.getInstance().postPersonalInfo(new String("userProfileapi"), new String(userId)
                    , new Callback<MyPersonalInfoModel>() {
                        @Override
                        public void success(MyPersonalInfoModel personalInfoResponseModel, Response response) {
                            /*If response is success this method is called**/
                            personalResponse(personalInfoResponseModel);
                            MApplication.materialdesignDialogStop();
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //Error message popups when the user cannot able to coonect with the server
                            MApplication.errorMessage(retrofitError, getActivity());
                            MApplication.materialdesignDialogStop();
                        }

                    });

        } else {
            //View visible

            internetConnection.setVisibility(View.VISIBLE);
            //View gone
            mainView.setVisibility(View.GONE);
        }

    }

    /**
     * This method is used to bind the data in to the views
     *
     * @param personalInfoResponseModel personalInfoResponseModel
     */
    private void personalResponse(MyPersonalInfoModel personalInfoResponseModel) {
        //Success message
        String success = personalInfoResponseModel.getSuccess();
        //Isf the success message equals 1
        if (("1").equals(success)) {
            //User name from ther response
            userName = personalInfoResponseModel.getResults().getUserName();
            //Setting the string the username
            MApplication.setString(getContext(), Constants.PROFILE_USER_NAME, userName);
            //Get the profile image from the response
            String profileImage = personalInfoResponseModel.getResults().getProfileImg().replaceAll(" ", "%20");
            //Get the user location from the response
            String city = personalInfoResponseModel.getResults().getUserLocation();
            //Get the user gender from the response
            String gender = personalInfoResponseModel.getResults().getUserGender();
            //Get the country id from the response
            String countryId = personalInfoResponseModel.getResults().getCountryId();
            //Getting the user sttatus from teh response
            String status = personalInfoResponseModel.getResults().getUserStatus();
            //access token
            String accessToken = personalInfoResponseModel.getResults().getAccessToken();
            String friendCount = personalInfoResponseModel.getResults().getFriendsCount();
            //If the status is empty set the default status
            //else set the status from the response
            if (("").equals(status)) {
                //Setting the value in prefernce
                MApplication.setString(getContext(), Constants.STATUS, getResources().getString(R.string.status_empty));
            } else {
                //Setting the value in prefernce
                MApplication.setString(getContext(), Constants.STATUS, status);
            }
            //saving in prefernce
            MApplication.setString(getContext(), Constants.ACCESS_TOKEN, accessToken);
            //Setting the profile image in prefernce
            MApplication.setString(getContext(), Constants.PROFILE_IMAGE, profileImage);
            //Setting the country id in prefernce
            MApplication.setString(getContext(), Constants.COUNTRY_ID, countryId);
            //Setting the gender in prefernce
            MApplication.setString(getContext(), Constants.USER_GENDER, gender);
            //Setting the city in prefernce
            MApplication.setString(getContext(), Constants.CITY, city);
            //Setting the latitude in prefernce
            MApplication.setString(getContext(), Constants.LATITUDE, personalInfoResponseModel.getResults().getLatitude());
            //Setting the longitude in prefernce
            MApplication.setString(getContext(), Constants.LONGITUDE, personalInfoResponseModel.getResults().getLongtitude());
            //Setting the username
            txtUserName.setText(MApplication.getDecodedString(userName));
            //Setting the location
            location.setText(city);
           // list.setEnabled(false);
            connectionsCount.setText(friendCount);
            //If status is not empty decode the string in text view
            if (!status.isEmpty()) {
                statusDetails.setText(MApplication.getDecodedString(status));
            } else {
                //Setting the default text
                statusDetails.setText(getResources().getString(R.string.status_empty));
            }

            //Progressive bar stops

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Web request fro the personal info

        MApplication.materialdesignDialogStop();
        webRequest();
        categoryPollRequest();
    }


    private void categoryPollRequest() {
        //Checks wether internet connection is available
        if (MApplication.isNetConnected(mActivity)) {
            //View visiblity gone
            internetConnection.setVisibility(View.GONE);
            //View visible
            //relativeList.setVisibility(View.VISIBLE);
            CategoryRestClient.getInstance().postCategoryData(new String("categoryapi_list"), new String(userId)
                    , new Callback<CategoryPollResponseModel>() {
                        @Override
                        public void success(CategoryPollResponseModel categoryPollResponseModel, Response response) {
                            //If the response is success and the value is 1 then the data are binded into the adapter
                            if (("1").equals(categoryPollResponseModel.getSuccess())) {
                                //Response binding method
                               // Toast.makeText(getContext(), ""+categoryPollResponseModel.getMsg(), Toast.LENGTH_SHORT).show();
                                categoryResponse(categoryPollResponseModel);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //Error message popups when the user cannot able to coonect with the server
                            MApplication.errorMessage(retrofitError, mActivity);
                            //Progressivse bar stops
                        }
                    });
        } else {
            //Internet connection Layout visible
            internetConnection.setVisibility(View.VISIBLE);
            //List Layout gone
           // relativeList.setVisibility(View.GONE);
        }
    }

    /**
     * This method is used to bind the data into the adapter class.
     *
     * @param categoryPollResponseModel-datas are stored in model class
     */
    private void categoryResponse(CategoryPollResponseModel categoryPollResponseModel) {
        //Getting the details from the response
        dataResults = categoryPollResponseModel.getResults();
        categoryArray.clear();
        //If data results is not empty,checks wether the user has selected any category before
        //If it is selected the category id is save in the prefernce as arraylist
        if (!dataResults.isEmpty()) {
            for (int i = 0; i < dataResults.size(); i++) {
                if (!dataResults.get(i).getUserCategory().isEmpty()) {
                    categoryArray.add(dataResults.get(i).getUserCategory().get(0).getCategoryId());
                    MApplication.saveStringArray(mActivity, categoryArray, Constants.CATEGORY_COUNT_ARRAY, Constants.CATEGORY_COUNT_SIZE);
                }
            }
            //Setting the boolean false
            MApplication.setBoolean(mActivity, Constants.YES_OR_NO, false);
            //An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
            // The Adapter provides access to the data items.
            // The Adapter is also responsible for making a View for each item in the data set.
            categoryAdapter = new DepartmentAdapter(mActivity, dataResults);
            //Sets the data behind this ListView.
            list.setAdapter(categoryAdapter);
        }
    }

}