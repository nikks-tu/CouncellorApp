package com.techuva.new_changes_corporate.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.activity.CustomRequest;
import com.techuva.councellorapp.contus_Corporate.activity.VolleyResponseListener;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.utils.Utils;
import com.techuva.new_changes_corporate.models.CommentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsListAdapter extends  RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {

private final ArrayList<CommentModel> mValues;
        Context context;
        String userId;
    Activity activity;

public CommentsListAdapter(ArrayList<CommentModel> items, Activity activity) {
        mValues = items;
        this.activity=activity;

        }

@Override
public CommentsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.comments_list_item, parent, false);
        context=parent.getContext();
        return new CommentsListAdapter.ViewHolder(view);
        }

@Override
public void onBindViewHolder(final CommentsListAdapter.ViewHolder holder, final int position) {

        holder.title.setText(mValues.get(position).getName());
        Utils.loadImageWithGlideRounderCorner(context, mValues.get(position).getUser_image(), holder.image,
        R.drawable.img_ic_user);

         userId = MApplication.getString(context, Constants.USER_ID);
         if(userId.equalsIgnoreCase(mValues.get(position).getUser_id()))
         {
            holder.edit.setVisibility(View.VISIBLE);
         }
         else
         {
             holder.edit.setVisibility(View.GONE);
         }


    holder.edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            //open dilog


            // custom dialog
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.comments_dialog_layout);
            dialog.setCancelable(true);



            TextView save_tv = (TextView) dialog.findViewById(R.id.save_tv);
            final TextView delete_tv = (TextView) dialog.findViewById(R.id.detele_tv);
            final EditText comment_et=(EditText)dialog.findViewById(R.id.comments_et);

            comment_et.setText(mValues.get(position).getComments());

            save_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(comment_et.getText().length()>0)
                    {
                        dialog.dismiss();
                       mValues.get(position).setComments(comment_et.getText().toString());
                        notifyDataSetChanged();
                       updatedComments(mValues.get(position).getAnnouncement_id(),mValues.get(position).getId(),comment_et.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(activity,"Please Enter Comments", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    delete(mValues.get(position).getAnnouncement_id(),mValues.get(position).getId());
                    mValues.remove(position);
                    notifyDataSetChanged();


                }
            });


            dialog.show();

        }
    });




        holder.comment.setText(mValues.get(position).getComments());

       String a= MApplication.getTimeDifference(mValues.get(position).getCreated_at());
        holder.commntsage.setText(a);

        }


    public void updatedComments(String announcementid, String commentsid , String commnts)
        {
            JSONObject obj = new JSONObject();
            try {
                obj.put("action","update_announcement_comments");
                obj.put("user_id",userId);
                obj.put("announcement_id",announcementid);
                obj.put("announcement_comment_id",commentsid);
                obj.put("announcement_comments",commnts);
                Log.v("...", obj.toString());
            }
            catch (Exception ae)
            {

            }


            CustomRequest.makeJsonObjectRequest(activity, Constants.DILETE_COMMENTS,obj, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
//            dialog.dismiss();
                    Log.i("onErrormessage", "message= " +message);
                }

                @Override
                public void onResponse(JSONObject response) {


                    try {
                        //Log.i("PCCmessage", "message " + result.getString("msg"));
                        int seccesss=response.optInt("success");

                        if(seccesss==1)
                        {
                            Toast.makeText(activity,response.getString("msg"), Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(activity,response.getString("msg"), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }

    public void delete(String announcementid, String commentsid )
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("action","delete_announcement_comments");
            obj.put("user_id",userId);
            obj.put("announcement_id",announcementid);
            obj.put("announcement_comment_id",commentsid);
            Log.v("...", obj.toString());
        }
        catch (Exception ae)
        {

        }


        CustomRequest.makeJsonObjectRequest(activity, Constants.DILETE_COMMENTS,obj, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
//            dialog.dismiss();
                Log.i("onErrormessage", "message= " +message);
            }

            @Override
            public void onResponse(JSONObject response) {


                try {
                    //Log.i("PCCmessage", "message " + result.getString("msg"));
                    int seccesss=response.optInt("success");

                    if(seccesss==1)
                    {
                        Toast.makeText(activity,response.getString("msg"), Toast.LENGTH_SHORT).show();


                    }
                    else
                    {
                        Toast.makeText(activity,response.getString("msg"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

@Override
public int getItemCount() {
        return mValues.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    TextView title,comment,commntsage;
    ImageView image,edit;

    public ViewHolder(View view) {
        super(view);
        mView = view;
        title = (TextView) view.findViewById(R.id.title);
        image = (ImageView) view.findViewById(R.id.image);
        comment=(TextView)view.findViewById(R.id.textView4);
        commntsage=(TextView)view.findViewById(R.id.textView5);
        edit =(ImageView)view.findViewById(R.id.imageView2);

    }


}
}



