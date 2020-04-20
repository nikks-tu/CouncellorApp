package com.techuva.new_changes_corporate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.smoothprogressbar.SmoothProgressBar;
import com.techuva.new_changes_corporate.adapters.CommentsListAdapter;
import com.techuva.new_changes_corporate.models.CommentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Announcement_comments_Act extends AppCompatActivity {
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
    ArrayList<CommentModel> annonceList;
    ImageView imagBackArrow;

    int NextPageNumber,PageNumber,RowsPerPage,TotalCount;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    CommentsListAdapter linearListAdapter;
    int pageno=1;
    private boolean loading = true;
    String announcementid;
    ImageView imageAddComments;
    EditText comment_et;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_comments);

        mAdView = (AdView) findViewById(R.id.adView);
        googleNow = (SmoothProgressBar) findViewById(R.id.google_now);
        pull_to_refresh =(SwipeRefreshLayout)findViewById(R.id.pull_to_refresh);
        imagBackArrow     =(ImageView)findViewById(R.id.imagBackArrow);
        imageAddComments  =(ImageView)findViewById(R.id.imageAddComments);
        comment_et =(EditText)findViewById(R.id.txtCountry);

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


        imageAddComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comment_et.getText().length()>0)
                {
                  //

                    submit_comments();
                    comment_et.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Comment", Toast.LENGTH_SHORT).show();
                }

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
            obj.put("action","get_announcement_comments");
            obj.put("announcement_id",announcementid);
            obj.put("page",pageno);
            Log.v("...", obj.toString());
        }
        catch (Exception ae)
        {

        }
        googleNow.setVisibility(View.VISIBLE);
        googleNow.progressiveStart();

        CustomRequest.makeJsonObjectRequest(getApplicationContext(), Constants.ANNOUNCEMNET_COMMENTS,obj, new VolleyResponseListener() {
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
                            String comments= obj.optString("comments");
                            String created_at= obj.optString("created_at");
                            String id= obj.optString("id");
                            String is_delete= obj.optString("is_delete");
                            String status= obj.optString("status");
                            String updated_at= obj.optString("updated_at");
                            String user_id =obj.optString("user_id");
                            JSONObject user_details = obj.optJSONObject("user_infos");
                            String name="",image="";
                            if(user_details!=null)
                            {
                                name= user_details.optString("name");
                                image= user_details.optString("image");
                            }
                            CommentModel obi=new CommentModel();
                            obi.setAnnouncement_id(announcement_id);
                            obi.setComments(comments);
                            obi.setCreated_at(created_at);
                            obi.setId(id);
                            obi.setUser_image(image);
                            obi.setIs_delete(is_delete);
                            obi.setStatus(status);
                            obi.setName(name);
                            obi.setUpdated_at(updated_at);
                            obi.setUser_id(user_id);
                            annonceList.add(obi);

                        }

                        if(!more) {
                            mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            announcement_list.setLayoutManager(mLayoutManager);
                            linearListAdapter=new CommentsListAdapter(annonceList,Announcement_comments_Act.this);
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



    public void submit_comments( )
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("action","announcement_comments");
            obj.put("user_id",userId);
            obj.put("announcement_id",announcementid);
            obj.put("comments",comment_et.getText().toString());
            Log.v("...", obj.toString());
        }
        catch (Exception ae)
        {

        }
        googleNow.setVisibility(View.VISIBLE);
        googleNow.progressiveStart();

        CustomRequest.makeJsonObjectRequest(getApplicationContext(), Constants.SUBMIT_COMMENTS,obj, new VolleyResponseListener() {
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


                        pageno=1;
                        loading = true;
                        getAnnouncement_list(false);
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
