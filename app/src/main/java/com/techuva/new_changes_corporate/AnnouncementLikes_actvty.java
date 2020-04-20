package com.techuva.new_changes_corporate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdView;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.activity.CustomRequest;
import com.techuva.councellorapp.contus_Corporate.activity.VolleyResponseListener;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;
import com.techuva.councellorapp.contusfly_corporate.utils.Constants;
import com.techuva.new_changes_corporate.adapters.LikedListAdapter;
import com.techuva.new_changes_corporate.models.LikeListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.techuva.councellorapp.contus_Corporate.app.Constants.ANNOUNCEMNET_LIKES;

public class AnnouncementLikes_actvty extends AppCompatActivity {
    //View
    private View parentView;
    //Iamge view chat
    private ImageView imgChat;

    private SmoothProgressBar googleNow;

    //Google ad
    private AdView mAdView;
    private Activity contectFragment;
    RecyclerView announcement_list;
    private SwipeRefreshLayout pull_to_refresh;
    String userId;
    ArrayList<LikeListModel> annonceList;
    ImageView imagBackArrow;

    int NextPageNumber,PageNumber,RowsPerPage,TotalCount;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    LikedListAdapter linearListAdapter;
    int pageno=1;
    private boolean loading = true;
    String announcementid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.announcement_likes_layout);

            mAdView = (AdView) findViewById(R.id.adView);
            googleNow = (SmoothProgressBar) findViewById(R.id.google_now);
            pull_to_refresh =(SwipeRefreshLayout)findViewById(R.id.pull_to_refresh);
        imagBackArrow     =(ImageView)findViewById(R.id.imagBackArrow);

            announcement_list   =(RecyclerView)findViewById(R.id.announcement_list);
            userId = MApplication.getString(getApplicationContext(), Constants.USER_ID);

        imagBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


            try {
                if(getIntent().getExtras()!=null)
                {
                    announcementid =getIntent().getExtras().getString("AnnouncemenrID");
                }
            }
            catch (Exception ae)
            {
                ae.printStackTrace();
            }



            getAnnouncement_list(false);

            pull_to_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageno=1;
                    loading = true;
                    getAnnouncement_list(false);
                }
            });



            announcement_list.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                {
                    if(dy > 0) //check for scroll down
                    {
                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                        if (loading)
                        {
                            if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                            {
                                loading = false;
                                Log.v("...", "Last Item Wow !");
                                //Do pagination.. i.e. fetch new data
                                //progressBar.setVisibility(View.VISIBLE);

                                //mAuthTask = new UserLoginTask("", "");
                                // mAuthTask.execute((Void) null);
                                pageno= pageno+1;

                                getAnnouncement_list(true);
                            }
                        }
                    }
                }
            });



    }




    public void getAnnouncement_list(final boolean more)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("action","get_announcement_likes");
            obj.put("announcement_id",announcementid);
            obj.put("page",pageno);
            Log.v("...", obj.toString());
        }
        catch (Exception ae)
        {

        }
        googleNow.setVisibility(View.VISIBLE);
        googleNow.progressiveStart();

        CustomRequest.makeJsonObjectRequest(getApplicationContext(), ANNOUNCEMNET_LIKES,obj, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
//            dialog.dismiss();
                Log.i("onErrormessage", "message= " +message);
            }

            @Override
            public void onResponse(JSONObject response) {
                JSONObject result = response.optJSONObject("results");
                googleNow.progressiveStop();
                googleNow.setVisibility(View.GONE);
                // stopping swipe refresh
                pull_to_refresh.setRefreshing(false);
                try {
                    //Log.i("PCCmessage", "message " + result.getString("msg"));
                    int seccesss=response.optInt("success");

                    if(seccesss==1)
                    {
                        TotalCount =result.optInt("total");
                        int per_page =result.optInt("per_page");
                        int current_page =result.optInt("current_page");
                        int last_page =result.optInt("last_page");
                        int from =result.optInt("from");
                        int to =result.optInt("to");

                        JSONArray data =result.optJSONArray("data");

                        if(!more) {
                            annonceList = new ArrayList<>();
                        }
                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject obj =data.getJSONObject(i);
                            String announcement_id= obj.optString("announcement_id");
                            String announcement_likes= obj.optString("announcement_likes");
                            String created_at= obj.optString("created_at");
                            String id= obj.optString("id");
                            String is_delete= obj.optString("is_delete");
                            String status= obj.optString("status");
                            String updated_at= obj.optString("updated_at");
                           JSONObject user_details = obj.optJSONObject("user_details");
                            String name="",image="";
                           if(user_details!=null)
                           {
                                name= user_details.optString("name");
                                image= user_details.optString("image");
                           }






                            LikeListModel obi=new LikeListModel();
                            obi.setAnnouncement_id(announcement_id);
                            obi.setAnnouncement_likes(announcement_likes);
                            obi.setCreated_at(created_at);
                            obi.setId(id);
                            obi.setImage(image);
                            obi.setIs_delete(is_delete);
                            obi.setStatus(status);
                            obi.setName(name);
                            obi.setUpdated_at(updated_at);


                            annonceList.add(obi);

                        }

                        if(!more) {
                            mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            announcement_list.setLayoutManager(mLayoutManager);
                            linearListAdapter=new LikedListAdapter(annonceList);
                            announcement_list.setAdapter(linearListAdapter);
                        }
                        else
                        {
                            linearListAdapter.notifyDataSetChanged();
                        }

                        if (more) {
                            if (announcement_list.getAdapter() != null && announcement_list.getAdapter().getItemCount() < TotalCount) {
                                loading = true;
                            } else {
                                loading = false;
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),result.getString("msg"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        MApplication.googleAd(mAdView);
    }



}

