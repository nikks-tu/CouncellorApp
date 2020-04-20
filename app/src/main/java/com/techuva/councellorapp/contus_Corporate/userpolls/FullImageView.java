package com.techuva.councellorapp.contus_Corporate.userpolls;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.android.gms.ads.AdView;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.frescoimagezoomable.ZoomableDraweeView;
import com.techuva.councellorapp.contusfly_corporate.views.RoundedCornersTransformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.techuva.councellorapp.R.drawable.placeholder_image;

/**
 * Created by user on 7/13/2015.
 */
public class FullImageView extends Activity {
    //Imkage view
    private ZoomableDraweeView imgFullView;
    //Textview
    private TextView txtName;
    //Textview
    private TextView txtTime;
    //Textcategory
    private TextView txtCategory;
    //Simple drawee view
    private ImageView imgProfile;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);
        imgFullView =  findViewById(R.id.imgFullView);
        txtName =  findViewById(R.id.txtName);
        txtTime =  findViewById(R.id.txtTime);
        txtCategory =  findViewById(R.id.txtCategory);
        imgProfile =  findViewById(R.id.imgProfile);
        mAdView =  findViewById(R.id.adView);
        //Getting the value in intent
        Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Quicksand-Light.ttf");
        Typeface faceRegular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Quicksand-Regular.otf");
        String question1 = getIntent().getExtras().getString(Constants.QUESTION1);
        boolean isAdmin = getIntent().getExtras().getBoolean("is_admin");
        //Getting the value from preference
        String campaignName = MApplication.getString(FullImageView.this, Constants.CAMPAIGN_NAME);
        //Getting the value from preference
        String campaignCategory = MApplication.getString(FullImageView.this, Constants.CAMPAIGN_CATEGORY);
        //Getting the value from preference
        String campaignLogo = MApplication.getString(FullImageView.this, Constants.CAMPAIGN_LOGO);
        //Getting the value from preference
        String updatedTime = MApplication.getString(FullImageView.this, Constants.DATE_UPDATED);
        //Setting the value in text view
        txtCategory.setText(campaignCategory);
        //setting in text view
        txtName.setText(MApplication.getDecodedString(campaignName));
        //Setting in text view
        if(updatedTime!=null)
        {
            /*String strCurrentDate = updatedTime;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = null;
            try {
                newDate = format.parse(strCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("dd-MMM-yy HH:mm");
            String date = format.format(newDate);*/
            txtTime.setText(updatedTime);
        }
        else
        {
        }
        txtName.setTypeface(faceRegular);
        txtTime.setTypeface(face);
        txtCategory.setTypeface(face);
        Log.i("ImageDetail",campaignLogo);
        //Setting the image uri
        //Utils.loadImageWithGlideProfileRounderCorner(FullImageView.this,"",imgProfile,R.drawable.placeholder_image);

        if(isAdmin){
            Glide.with(this).load(R.drawable.ic_logo_icon).error(getApplicationContext().getResources().getDrawable(placeholder_image)).fitCenter().bitmapTransform(new RoundedCornersTransformation(this, 5, 5, 0,
                    RoundedCornersTransformation.CornerType.ALL))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .placeholder(placeholder_image).into(imgProfile);
        }else {
            Glide.with(this).load(campaignLogo).error(placeholder_image).fitCenter().bitmapTransform(new RoundedCornersTransformation(this, 5, 5, 0,
                    RoundedCornersTransformation.CornerType.ALL))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .placeholder(placeholder_image).into(imgProfile);
        }

        //The DraweeController is the class responsible for actually dealing with the un
        // derlying image loader - whether Fresco's own image pipeline, or another.
        DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(question1)).setTapToRetryEnabled(true).build();
        //This class does not do deep copies of most of the input parameters. There should be one
        // instance of the hierarchy per DraweeView, so that each hierarchy has a unique set of drawables.
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        //set controller
        imgFullView.setController(ctrl);
        //set hierarchy
        imgFullView.setHierarchy(hierarchy);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Setting the status bar color
            MApplication.settingStatusBarColor(FullImageView.this, getResources().getColor(android.R.color.black));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        MApplication.googleAd(mAdView);
    }

    /**
     * Calling this method from xml file when performing the click on the ACTION
     *
     * @param clickedView
     */
    public void onClick(final View clickedView) {
        if (clickedView.getId() == R.id.imgBackArrow) {
            //finishing the activity
            this.finish();
        }
    }
}
