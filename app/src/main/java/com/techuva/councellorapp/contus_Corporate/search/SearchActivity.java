package com.techuva.councellorapp.contus_Corporate.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.residemenu.MenuActivity;
import com.techuva.councellorapp.contus_Corporate.responsemodel.UserPollResponseModel;
import com.techuva.councellorapp.contus_Corporate.restclient.SearchRestClient;
import com.techuva.councellorapp.contus_Corporate.views.EndLessListView;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
 * Created by user on 10/9/2015.
 */
public class SearchActivity extends Activity implements EndLessListView.EndlessListener {
    /**List view**/
    private EditText editSearch;
    /**Text view**/
    private TextView noSearchResults;
    //String location
    private AdView mAdView;
    private TextView txtTitle;
    private String location;
    //floating action button
    private FloatingActionButton fab;
    //Endless list view
    private EndLessListView list;
    //user id
    private String userId;
    //search adapter
    private SearchAdapter adapter;
    //last  page
    private String getLastPage;
    //page
    private int page;
    //current page
    private String getCurrentPage;
    //Google now
    private SmoothProgressBar googleNow;
    //Internet connection
    private RelativeLayout internetConnection;
    //relative list
    private RelativeLayout relativeList;
    //array list
    private List<UserPollResponseModel.Results.Data> dataResults = new ArrayList<>();
    //array list
    private ArrayList<Integer> searchPollLikeCount = new ArrayList<Integer>();
    //array list
    private ArrayList<Integer> searchPollLikesUser = new ArrayList<Integer>();
    //array list
    private ArrayList<Integer> searchPollcommentsCount = new ArrayList<Integer>();
    //array list
    private ArrayList<String> searchPollIdAnwserCheck = new ArrayList<String>();
    //array list
    private ArrayList<String> searchPollIdAnwser = new ArrayList<String>();
    //search activity
    private SearchActivity mSearchActivity;
    //array list
    private ArrayList<Integer> searchPollParticipateCount = new ArrayList<Integer>();
    //swipe refresh layout
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //initialize the activity
        init();
    }

    public void init(){
        mSearchActivity =this;
        /* Initializing the list view**/
        list =  findViewById(R.id.component_list);
        editSearch =  findViewById(R.id.editSearch);
        googleNow =  findViewById(R.id.google_now);
        txtTitle =  findViewById(R.id.selectCountry);
        noSearchResults =  findViewById(R.id.noSearchResults);
        internetConnection =  findViewById(R.id.internetConnection);
        relativeList =  findViewById(R.id.list);
        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);
        //getting the value from preference
        mAdView =  findViewById(R.id.adView);
        MApplication.googleAd(mAdView);
        userId = MApplication.getString(mSearchActivity, Constants.USER_ID);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");
        txtTitle.setTypeface(face);

        //set listner
        list.setListener(this);
        page = 1;
        /**Text change listner**/
        editSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {        /*If search icon is clicked in the keypad the condition goes inside**/
                    location = editSearch.getText().toString().trim();
                    if (!location.isEmpty()) {
                        editSearch.setText("");/**Value from the edittext**/
                        dataResults.clear();
                        page = 1;
                        searchPollRequest();/**Request and response method is called**/
                    } else {
                        Toast.makeText(mSearchActivity, getResources().getString(R.string.enter_valid_location_name),   /**Toast message wthen search key is wrong**/Toast.LENGTH_SHORT).show();
                        editSearch.requestFocus();   /**Requesting focus**/
                    }
                    MApplication.hideKeyboard(mSearchActivity);    /**Hiding the keyborad**/
                    return true;
                }
                return false;
            }

        });
        //page equal to 1
        page=1;
        //model number using
        String model = android.os.Build.MODEL;
        if (Constants.SAMSUNG_S3.equals(model)) {
            //er-child layout information associated with RelativeLayout.
            RelativeLayout.LayoutParams userPollLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            userPollLayout.setMargins(20, 20, 5, 0);
            //Set the layout parameters associated with this view.
            list.setLayoutParams(userPollLayout);
        }
        fab =  findViewById(R.id.fab);
        //Interface definition for a callback to be invoked when a view is clicked.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //geting the value from the edittext
                location = editSearch.getText().toString().trim();
                MApplication.hideKeyboard(mSearchActivity);      /**Hiding the keyborad**/
                if (!location.isEmpty()) {  /**Request and response method is called**/
                    searchPollRequest();
                } else {
                    //Toast message will display
                    Toast.makeText(mSearchActivity,"Please enter the text to search", Toast.LENGTH_SHORT).show();    /**Toast message wthen search key is wrong**/
                    editSearch.requestFocus();/**Requesting focus**/
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**Request and response method is called**/
                searchPollRequest();
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
    private void searchPollRequest() {
        /**If internet connection is available**/
        if (MApplication.isNetConnected(mSearchActivity)) {
            //view gone
            internetConnection.setVisibility(View.GONE);
            //view visible
            relativeList.setVisibility(View.VISIBLE);
            //view invisible
            noSearchResults.setVisibility(View.INVISIBLE);
            /**Progree bar visiblity**/
            googleNow.setVisibility(View.VISIBLE);
            /**Progress bar start**/
            googleNow.progressiveStart();
            SearchRestClient.getInstance().searchUserPOll(new String(Constants.SEARCH_POLLS_API), new String(location),new Integer(page),new String(userId),new Callback<UserPollResponseModel>() {
                @Override
                public void success(UserPollResponseModel userPollResponseModel, Response response) {
                    if (("1").equals(userPollResponseModel.getSuccess())) {
                        //like count clear
                        list.setVisibility(View.VISIBLE);
                        searchPollLikeCount.clear();
                        searchPollLikesUser.clear();//clear
                        searchPollcommentsCount.clear();//clear
                        searchPollParticipateCount.clear();
                        //Data results
                        dataResults = userPollResponseModel.getResults().getData();
                        //get the last page
                        getLastPage = userPollResponseModel.getResults().getLastPage();
                        //get the current page
                        getCurrentPage = userPollResponseModel.getResults().getCurrentPage();
                        //If it is not empty
                        if (!dataResults.isEmpty()) {
                            //Current page equal to 1
                            if (Integer.parseInt(getCurrentPage) == 1) {
                                //An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
                                // The Adapter provides access to the data items.
                                // The Adapter is also responsible for making a View for each item in the data set.
                                list.setVisibility(View.VISIBLE);
                                adapter = new SearchAdapter(mSearchActivity, dataResults, R.layout.publicpoll_singleview, userId,list);
                                //setting the bottom view
                                list.setLoadingView(R.layout.layout_loading);
                                //setting the adapter
                                list.setSearchPollAdapter(adapter);
                            } else if (Integer.parseInt(getCurrentPage) <= Integer.parseInt(getLastPage)) {
                                //adding the value in array list
                                list.addSearchPollData(dataResults);
                                searchPollLikeCount.clear();//clear
                                searchPollLikesUser.clear();//clear
                                searchPollcommentsCount.clear();//clear
                                searchPollParticipateCount.clear();
                                //Loading the array from preference
                                searchPollcommentsCount = MApplication.loadArray(mSearchActivity, searchPollcommentsCount, Constants.SEARCH_POLL_COMMENTS_COUNT, Constants.SEARCH_POLL_COMMENTS_SIZE);
                                //Loading the array from preference
                                searchPollLikesUser = MApplication.loadArray(mSearchActivity, searchPollLikesUser, Constants.SEARCH_POLL_LIKES_USER_ARRAY, Constants.SEARCH_POLL_LIKES_USER_SIZE);
                                //Loading the array from preference
                                searchPollLikeCount = MApplication.loadArray(mSearchActivity, searchPollLikeCount, Constants.SEARCH_POLL_LIKES_COUNT_ARRAY, Constants.SEARCH_POLL_LIKES_COUNT_SIZE);
                                //Loading the array from preference
                                searchPollParticipateCount = MApplication.loadArray(mSearchActivity, searchPollParticipateCount, Constants.SEARCH_POLL_PARTICIPATE_COUNT_ARRAY, Constants.SEARCH_POLL_PARTICIPATE_COUNT_SIZE);
                                //Loading the details from the arraylist
                                searchPollIdAnwserCheck= MApplication.loadStringArray(mSearchActivity, searchPollIdAnwserCheck, Constants.SEARCH_POLL_ID_ANSWER_ARRAY, Constants.SEARCH_POLL_ID_ANSWER_SIZE);
                                //Loading the details from the arraylist
                                searchPollIdAnwser= MApplication.loadStringArray(mSearchActivity, searchPollIdAnwser, Constants.SEARCH_POLL_ID_ANSWER_SELECTED_ARRAY, Constants.SEARCH_POLL_ID_SELECTED_SIZE);
                            }

                        }
                        for (int i = 0; dataResults.size() > i; i++) {
                            //adding into the arraylist
                            searchPollcommentsCount.add(Integer.valueOf(userPollResponseModel.getResults().getData().get(i).getCampaignCommentsCounts()));
                            //saving in preference
                            MApplication.saveArray(mSearchActivity, searchPollcommentsCount, Constants.SEARCH_POLL_COMMENTS_COUNT, Constants.SEARCH_POLL_COMMENTS_SIZE);
                            //adding into the arraylist
                            searchPollLikesUser.add(Integer.valueOf(userPollResponseModel.getResults().getData().get(i).getCampaignUserLikes()));
                            //saving in preference
                            MApplication.saveArray(mSearchActivity, searchPollLikesUser, Constants.SEARCH_POLL_LIKES_USER_ARRAY, Constants.SEARCH_POLL_LIKES_USER_SIZE);
                            //adding into the arraylist
                            searchPollLikeCount.add(Integer.valueOf(userPollResponseModel.getResults().getData().get(i).getCampaignLikesCounts()));
                            //saving in preference
                            MApplication.saveArray(mSearchActivity, searchPollLikeCount, Constants.SEARCH_POLL_LIKES_COUNT_ARRAY, Constants.SEARCH_POLL_LIKES_COUNT_SIZE);
                            //adding into the arraylist
                            searchPollParticipateCount.add(Integer.valueOf(userPollResponseModel.getResults().getData().get(i).getPollParticipateCount()));
                            //saving in preference
                            MApplication.saveArray(mSearchActivity, searchPollParticipateCount, Constants.SEARCH_POLL_PARTICIPATE_COUNT_ARRAY, Constants.SEARCH_POLL_PARTICIPATE_COUNT_SIZE);

                            if (!userPollResponseModel.getResults().getData().get(i).getUserParticipatePolls().isEmpty()){
                                for(int j=0;userPollResponseModel.getResults().getData().get(i).getUserParticipatePolls().size()>j;j++) {
                                    if (userPollResponseModel.getResults().getData().get(i).getUserParticipatePolls().get(j).getUserId().equals(userId)) {
                                        //adding the response to the array list
                                        searchPollIdAnwserCheck.add(userPollResponseModel.getResults().getData().get(i).getUserParticipatePolls().get(j).getPollId());
                                        //adding the response to the array list
                                        searchPollIdAnwser.add(userPollResponseModel.getResults().getData().get(i).getUserParticipatePolls().get(j).getPollAnswer());
                                        //Save the string array in preference
                                        MApplication.saveStringArray(mSearchActivity, searchPollIdAnwserCheck, Constants.SEARCH_POLL_ID_ANSWER_ARRAY, Constants.SEARCH_POLL_ID_ANSWER_SIZE);
                                        //Save the string array in preference
                                        MApplication.saveStringArray(mSearchActivity, searchPollIdAnwser, Constants.SEARCH_POLL_ID_ANSWER_SELECTED_ARRAY, Constants.SEARCH_POLL_ID_SELECTED_SIZE);
                                    }
                                }
                            }

                        }

                    }
                    else if(("0").equals(userPollResponseModel.getSuccess())&&page==1) {
                        //visible
                       // dataResults.clear();
                        relativeList.setVisibility(View.GONE);
                        list.setVisibility(View.GONE);
                        noSearchResults.setVisibility(View.VISIBLE);
                        //invisible
                        internetConnection.setVisibility(View.INVISIBLE);
                        //invisible
                        relativeList.setVisibility(View.INVISIBLE);
                    }
                    //progressive stop
                    googleNow.progressiveStop();
                    //visiblity gone
                    googleNow.setVisibility(View.GONE);
                    //swipe refresyh
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    MApplication.errorMessage(retrofitError, mSearchActivity);
                }

            });
        } else {
            //visibility gone
            internetConnection.setVisibility(View.VISIBLE);
            //list gone
            relativeList.setVisibility(View.GONE);
            //gone
            //fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadData() {
        //incrementing the page count
        page++;
        //adding the request
        searchPollRequest();
    }

    /**
     * Calling this method from xml file when performing the click on the ACTION
     * @param clickedView
     */
    public void onClick(final View clickedView) {
        if (clickedView.getId() == R.id.imgBackArrow) {
            MApplication.setBoolean(getApplicationContext(), Constants.SEARCH_BACKPRESS_BOOLEAN, true);
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        } else if (clickedView.getId() == R.id.internetRetry) {
            /**Finish the activity**/
            location = editSearch.getText().toString().trim();
            if (!location.isEmpty()) {
                /**Request and response method is called**/
                searchPollRequest();
            } else {
                /**Hiding the keyborad**/
                MApplication.hideKeyboard(mSearchActivity);
                /**Toast message wthen search key is wrong**/
                Toast.makeText(mSearchActivity, "Please enter the text to search",
                        Toast.LENGTH_SHORT).show();
                /**Requesting focus**/
                editSearch.requestFocus();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //If adapter is not null
        if(adapter!=null){
            //notify data set changed
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        MApplication.setBoolean(this, Constants.SEARCH_BACKPRESS_BOOLEAN, true);
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
