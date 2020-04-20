package com.techuva.new_changes_corporate.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.activity.CustomRequest;
import com.techuva.councellorapp.contus_Corporate.activity.VolleyResponseListener;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.utils.Constants;
import com.techuva.councellorapp.contusfly_corporate.utils.Utils;
import com.techuva.new_changes_corporate.AnnouncementLikes_actvty;
import com.techuva.new_changes_corporate.Announcement_comments_Act;
import com.techuva.new_changes_corporate.models.AnnouncementModel;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.techuva.councellorapp.contus_Corporate.app.Constants.UPDATE_LIKE;

public class AnnouncementRecyclerAdapter extends RecyclerView.Adapter<AnnouncementRecyclerAdapter.ViewHolder> {

    private final ArrayList<AnnouncementModel> mValues;
    Context context;
    String userId;
    boolean like;
    public EventListener eventListener;
    public AnnouncementRecyclerAdapter(ArrayList<AnnouncementModel> items, EventListener eventListener) {
        mValues = items;
        this.eventListener = eventListener;
    }

    @Override
    public AnnouncementRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_item, parent, false);
        context=parent.getContext();
        return new AnnouncementRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnnouncementRecyclerAdapter.ViewHolder holder, final int position) {
        userId = MApplication.getString(context, Constants.USER_ID);
        holder.mItem = mValues.get(position);
        holder.txtName.setText(mValues.get(position).getTitle());

        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Light.ttf");
        Typeface faceRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");

        holder.txtName.setTypeface(faceRegular);
        holder.txtTime.setTypeface(face);
        holder.txtCategory.setTypeface(face);
        holder.txtPostedOn.setTypeface(face);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtStatus.setText(Html.fromHtml(mValues.get(position).getAnnouncement(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.txtStatus.setText(Html.fromHtml(mValues.get(position).getAnnouncement()));
        }


       /* if(mValues.get(position).getUser_participate_announcements()!=null)
        {
            if(mValues.get(position).getUser_participate_announcements().size()>0)
            {
                if(mValues.get(position).getUser_participate_announcements().get(0).getIsRead()==1
                        &&mValues.get(position).getUser_participate_announcements().get(0).getUserId().toString().equalsIgnoreCase(userId))
                {

                    holder.txtParticcipation.setBackgroundResource(R.drawable.double_tick_seen);
                    // Get the background, which has been compiled to an AnimationDrawable object.
                   // AnimationDrawable frameAnimation = (AnimationDrawable) holder.txtParticcipation.getBackground();
                    // Start the animation (looped playback by default).
                   // frameAnimation.start();
                }
                else {
                    holder.txtParticcipation.setBackgroundResource(R.drawable.single_tick);
                    //holder.txtParticcipation.setEnabled(true);
                }
            }

        }
        else {
            holder.txtParticcipation.setBackgroundResource(R.drawable.single_tick);
        }*/
        holder.txtLike2.setText(mValues.get(position).getAnnouncementLikesCounts());
        holder.txtCommentsCounts.setText(mValues.get(position).getAnnouncementCommentsCounts());
        if(mValues.get(position).getCreated_at()!=null)
        {
            String strCurrentDate = mValues.get(position).getCreated_at();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = null;
            try {
                newDate = format.parse(strCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("dd-MMM-yy HH:mm");
            String date = format.format(newDate);
            holder.txtTime.setText(date);
        }
        else
        {
        }
        Utils.loadImageWithGlideRounderCorner(context, mValues.get(position).getAdminProfilePic(), holder.imgProfile,
                R.drawable.img_ic_user);

        //Loading into the gilde
       if(mValues.get(position).getImage().equals(""))
       {
           holder.singleOption.setVisibility(View.GONE);
       }
       else {
           holder.singleOption.setVisibility(View.VISIBLE);
           Utils.loadImageWithGlideRounderCorner(context, mValues.get(position).getImage(), holder.singleOption,
                   R.drawable.placeholder_image);
            }
        holder.txtCommentsCounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*String commentsCounts= mValues.get(position).getAnnouncementCommentsCounts();
                if(!TextUtils.isEmpty(commentsCounts)&&Integer.parseInt(commentsCounts)>0)
                {*/
                    Intent i = new Intent(context, Announcement_comments_Act.class);
                    i.putExtra("AnnouncemenrID",mValues.get(position).getId());
                    context.startActivity(i);
               /* }
                else
                {
                    Toast.makeText(context,"No Comments Available",Toast.LENGTH_SHORT).show();
                }*/

            }
        });


        holder.unLikeDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mValues.get(position).getUserAnnouncementLikes().equalsIgnoreCase("0"))
                {
                    like=false;
                }
                else
                {
                    like=true;
                }


                if(like)
                {
                    like=false;
                    mValues.get(position).setUserAnnouncementLikes("0");
                }
                else
                {
                    like=true;
                    mValues.get(position).setUserAnnouncementLikes("1");
                }

                update_like(position,like ,mValues.get(position).getId(),holder.txtLike2);
            }
        });

       /* holder.unLikeDislike.s(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                update_like(isChecked,mValues.get(position).getId(),holder.txtLike2);
            }
        });*/

        holder.txtLike2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String likescount= mValues.get(position).getAnnouncementLikesCounts();
              /* if(!TextUtils.isEmpty(likescount)&&Integer.parseInt(likescount)>0)
               {*/
                   Intent i = new Intent(context, AnnouncementLikes_actvty.class);
                   i.putExtra("AnnouncemenrID",mValues.get(position).getId());
                   context.startActivity(i);
               /*}
               else
               {
                   Toast.makeText(context,"No Likes Available",Toast.LENGTH_SHORT).show();
               }*/

            }
        });
        if(mValues.get(position).getUserAnnouncementLikes().toString().equalsIgnoreCase("1"))
        {
            holder.unLikeDislike.setChecked(true);
            like=true;
        }
        else
        {
            holder.unLikeDislike.setChecked(false);
            like=false;
        }

        holder.txtCategory.setText(mValues.get(position).getCategory_name());

      /*  holder.txtParticcipation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnnouncement(position,mValues.get(position).getId());
            }
        });*/
    }



    public void update_like(final int position , final boolean like, String id, final TextView likecount)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("action","announcement_likes");
            obj.put("user_id",userId);
            obj.put("announcement_id",id);
            if(like)
            {
                obj.put("likes","1") ;
            }
            else
            {
                obj.put("likes","0");
            }

            Log.v("...", obj.toString());
        }
        catch (Exception ae)
        {

        }


        CustomRequest.makeJsonObjectRequest(context, UPDATE_LIKE,obj, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
//            dialog.dismiss();
                Log.i("onErrormessage", "message= " +message);
            }

            @Override
            public void onResponse(JSONObject response) {
                //Log.i("PCCmessage", "message " + result.getString("msg"));
                int success = response.optInt("success");
                int count=response.optInt("count");
                likecount.setText(""+count);
                mValues.get(position).setAnnouncementLikesCounts(""+count);



                if(success==1)
                {
                   // Toast.makeText(context,response.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else
                {
                   // Toast.makeText(context,response.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface EventListener {
        void onEvent(Boolean data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView txtTime,txtName,txtCategory,txtStatus,txtLike2,txtCommentsCounts, txtPostedOn;
        ImageView imgTime;
        AnnouncementModel mItem;
        ImageView singleOption,imgProfile,txtParticcipation;
        CheckBox unLikeDislike;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtTime =  view.findViewById(R.id.txtTime);
            txtName =  view.findViewById(R.id.txtName);
            txtCategory =  view.findViewById(R.id.txtCategory);
            txtStatus =  view.findViewById(R.id.txtStatus);
            txtLike2 =  view.findViewById(R.id.txtLike2);
            txtCommentsCounts =  view.findViewById(R.id.txtCommentsCounts);
            imgProfile = view.findViewById(R.id.imgProfile);
            imgProfile.setVisibility(View.GONE);
            singleOption = view.findViewById(R.id.singleOption);
            unLikeDislike  = view.findViewById(R.id.unLikeDislike);
            txtParticcipation = view.findViewById(R.id.txtParticcipation);
            txtPostedOn = view.findViewById(R.id.txtPostedOn);
            imgTime = view.findViewById(R.id.imgTime);
            imgTime.setVisibility(View.GONE);

        }
    }
}

