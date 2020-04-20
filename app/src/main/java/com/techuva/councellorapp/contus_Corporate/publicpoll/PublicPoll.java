
package com.techuva.councellorapp.contus_Corporate.publicpoll;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.ads.AdView;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.responsemodel.PublicPollResponseModel;
import com.techuva.councellorapp.contus_Corporate.restclient.PublicPollRestClient;
import com.techuva.councellorapp.contus_Corporate.views.EndLessListView;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PublicPoll extends Fragment implements EndLessListView.EndlessListener {
    /**EndlessLIst view**/
    private EndLessListView listPublicPoll;
    /**Adview**/
    private AdView mAdView;
    /**Userid**/
    private String userId;
    /**Custtom adapter**/
    private CustomAdapter adapter;
    /**List to store from the response**/
    private List<PublicPollResponseModel.Data> dataResults;
    /**8LastPage**/
    private String getLastPage;
    /**Initially PAGE number is 1**/
    private int page;
    /**Getting the current PAGE number**/
    private String getCurrentPage;
    /**Progress bar**/
    private SmoothProgressBar googleNow;
    /**Layout internet connection***/
    private RelativeLayout internetConnection;
    /**Relative layout**/
    private RelativeLayout relativeList;
    /**View group**/
    private ViewGroup root;
    //Campaign like count
    private ArrayList<Integer> campaignLikeCount = new ArrayList<Integer>();
    //Campaign like user
    private ArrayList<Integer> campaignLikesUser = new ArrayList<Integer>();
    //campaign COMMENTS count
    private ArrayList<Integer> campaigncommentsCount = new ArrayList<Integer>();
    //No campaign results
    private TextView noCampaignResults;
    //model
    private String model;
    //swipe refresh layout
    private SwipeRefreshLayout swipeRefreshLayout;
    private CampaignPollInteractionListner mListener;
    private Activity publicPollFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.public_poll, null);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listPublicPoll = (EndLessListView) root.findViewById(R.id.listView);
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.empty_footer, null, false);
        mAdView = (AdView) root.findViewById(R.id.adView);
        googleNow=(SmoothProgressBar)root.findViewById(R.id.google_now);
        internetConnection = (RelativeLayout)root.findViewById(R.id.internetConnection);
        relativeList = (RelativeLayout)root.findViewById(R.id.list);
        noCampaignResults = (TextView) root.findViewById(R.id.noCampaignResults);
        TextView btnRetry = (TextView) root.findViewById(R.id.internetRetry);
        swipeRefreshLayout=(SwipeRefreshLayout)root.findViewById(R.id.activity_main_swipe_refresh_layout);
        //Model of the mobile using
        model =   android.os.Build.MODEL;
        //if matches samsung s3
        if(Constants.SAMSUNG_S3.equals(model)){
            //er-child layout information associated with RelativeLayout.
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20,5, 0);
           //Set the layout parameters associated with this view.
            listPublicPoll.setLayoutParams(params);
        }
        page=1;
         ///Add a fixed view to appear at the bottom of the list.
        listPublicPoll.addFooterView(footerView);
        //Getting the user id
        userId = MApplication.getString(publicPollFragment, Constants.USER_ID);
        //Requesting for the public poll
        publicPollRequest();
        //set lisner
        listPublicPoll.setListener(this);
        //Interface definition for a callback to be invoked when a view is clicked.
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Requesting for the public poll
                publicPollRequest();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.OnCampaignPollFragment("","");
                }
            }
        });
    }


    private void publicPollRequest() {
        //If new is connected
        if (MApplication.isNetConnected(publicPollFragment)) {
            //Internet connection gone
            internetConnection.setVisibility(View.GONE);
            //Vie wvisible
            relativeList.setVisibility(View.VISIBLE);
            /**Progree bar visiblity**/
            googleNow.setVisibility(View.VISIBLE);
            /**Progress bar start**/
            googleNow.progressiveStart();

            if (publicPollFragment!= null) {
                /**  Requesting the response from the given base url**/
                PublicPollRestClient.getInstance().postPublicApi(new String(Constants.PUBLICPOLLS_API), new String(userId), new Integer(page), new String(Constants.LIMIT)
                        , new Callback<PublicPollResponseModel>() {
                            @Override
                            public void success(PublicPollResponseModel welcomeResponseModel, Response response) {

                                //If the success value is 1 then the below functionality will take place
                                //else if  get success equals 1 and PAGE 1 or get successqueals 0 and PAGE equal to 1 views will be visible
                                if (("1").equals(welcomeResponseModel.getmSuccess())) {
                                    //data results from the response
                                    dataResults = welcomeResponseModel.getResults().getCampaignResults().getData();
                                    //egt the alst PAGE from the response
                                    getLastPage = welcomeResponseModel.getResults().getCampaignResults().getLastPage();
                                    //Get the current PAGE from the response
                                    getCurrentPage = welcomeResponseModel.getResults().getCampaignResults().getCurrentPage();
                                    //If data  results is not empty the belpow condition willtake place
                                    if (!dataResults.isEmpty()) {
                                        //If get the current PAGE equal to 1
                                        if (Integer.parseInt(getCurrentPage) == 1) {
                                            if (publicPollFragment != null) {
                                                //An Adapter object acts as a bridge between an AdapterView and the underlying data for that view.
                                                // The Adapter provides access to the data items.
                                                // The Adapter is also responsible for making a View for each item in the data set.
                                                adapter = new CustomAdapter(getActivity(), dataResults, R.layout.publicpoll_singleview, userId);
                                                //Set bottom footer view
                                                listPublicPoll.setLoadingView(R.layout.layout_loading);
                                                //Sets the data behind this ListView.
                                                listPublicPoll.setAdapter(adapter);
                                            }

                                        } else if (Integer.parseInt(getCurrentPage) <= Integer.parseInt(getLastPage)) {
                                            //the data is added into the array adapterfrom the response
                                            listPublicPoll.addNewData(dataResults);
                                            //clear the array list
                                            campaignLikeCount.clear();
                                            //clear the array list
                                            campaignLikesUser.clear();
                                            //clear the array list
                                            campaigncommentsCount.clear();
                                            //Loading the data from the prefernce and saved in the array list
                                            campaigncommentsCount = MApplication.loadArray(publicPollFragment, campaigncommentsCount, Constants.CAMPAIGN_COMMENTS_COUNT, Constants.CAMPAIGN_COMMENTS_SIZE);
                                            //Loading the data from the prefernce and saved in the array list
                                            campaignLikesUser = MApplication.loadArray(publicPollFragment, campaignLikesUser, Constants.CAMPAIGN_LIKES_USER_ARRAY, Constants.CAMPAIGN_LIKES_USER_SIZE);
                                            //Loading the data from the prefernce and saved in the array list
                                            campaignLikeCount = MApplication.loadArray(publicPollFragment, campaignLikeCount, Constants.CAMPIGN_LIKES_COUNT_ARRAY, Constants.CAMPIGN_LIKES_COUNT_SIZE);
                                        }
                                        //Loading the data into the arraylist using the response size
                                        for (int i = 0; dataResults.size() > i; i++) {
                                            //Adding the campaign COMMENTS count in array list
                                            campaigncommentsCount.add(Integer.valueOf(welcomeResponseModel.getResults().getCampaignResults().getData().get(i).getCampaignCommentsCounts()));
                                            //Saving in prefernce
                                            MApplication.saveArray(publicPollFragment, campaigncommentsCount, Constants.CAMPAIGN_COMMENTS_COUNT, Constants.CAMPAIGN_COMMENTS_SIZE);
                                            //Adding the campaign user likes in array list
                                            campaignLikesUser.add(Integer.valueOf(welcomeResponseModel.getResults().getCampaignResults().getData().get(i).getCampaignUserLikes()));
                                            //Saving in prefernce
                                            MApplication.saveArray(publicPollFragment, campaignLikesUser, Constants.CAMPAIGN_LIKES_USER_ARRAY, Constants.CAMPAIGN_LIKES_USER_SIZE);
                                            //Adding the campaign user count in array list
                                            campaignLikeCount.add(Integer.valueOf(welcomeResponseModel.getResults().getCampaignResults().getData().get(i).getCampaignLikesCounts()));
                                            //Saving in prefernce
                                            MApplication.saveArray(publicPollFragment, campaignLikeCount, Constants.CAMPIGN_LIKES_COUNT_ARRAY, Constants.CAMPIGN_LIKES_COUNT_SIZE);
                                        }
                                    }else if(page==1){
                                        //View visible
                                        noCampaignResults.setVisibility(View.VISIBLE);
                                        // View invisible
                                        internetConnection.setVisibility(View.INVISIBLE);
                                        //lsi invisible
                                        relativeList.setVisibility(View.INVISIBLE);
                                    }
                                } else if (("1").equals(welcomeResponseModel.getmSuccess()) && page == 1 || ("0").equals(welcomeResponseModel.getmSuccess()) && page == 1) {
                                    //View visible
                                    noCampaignResults.setVisibility(View.VISIBLE);
                                    // View invisible
                                    internetConnection.setVisibility(View.INVISIBLE);
                                    //lsi invisible
                                    relativeList.setVisibility(View.INVISIBLE);
                                }
                                //Progress bar stops
                                googleNow.progressiveStop();
                                //Visiblity gone
                                googleNow.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                //Error message popups when the user cannot able to coonect with the server
                                MApplication.errorMessage(retrofitError, publicPollFragment);
                                //Progressbar stops
                                googleNow.progressiveStop();
                                //Progressbar became invisible
                                googleNow.setVisibility(View.GONE);

                            }
                        });
            } else {
                //view visible
                internetConnection.setVisibility(View.VISIBLE);
                //view gone
                relativeList.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void loadData() {
        //Increamenting the PAGE number on scrolling
        page++;
        //Request for display the campaign
     //   publicPollRequest();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null) {
            //Notifies the attached observers that the underlying data has been changed
            // and any View reflecting the data set should refresh itself.
            adapter.notifyDataSetChanged();
        }
        //Google adview
        MApplication.googleAd(mAdView);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
     */

    public interface CampaignPollInteractionListner {
        void OnCampaignPollFragment(String type, String id);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            publicPollFragment = (Activity) context;
        }
        try {
            mListener = (CampaignPollInteractionListner) publicPollFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(publicPollFragment.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }




}
