package com.techuva.councellorapp.contus_Corporate.residemenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.MApplication;
import com.techuva.councellorapp.contus_Corporate.aboutus.AboutUs;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.category.Category;
import com.techuva.councellorapp.contus_Corporate.category.IFragmentReplace;
import com.techuva.councellorapp.contus_Corporate.contactus.ContactUs;
import com.techuva.councellorapp.contus_Corporate.mypolls.MyPolls;
import com.techuva.councellorapp.contus_Corporate.myprofile.EditProfile;
import com.techuva.councellorapp.contus_Corporate.myprofile.MyProfile;
import com.techuva.councellorapp.contus_Corporate.publicpoll.PublicPoll;
import com.techuva.councellorapp.contus_Corporate.residemenu.ResideMenu;
import com.techuva.councellorapp.contus_Corporate.residemenu.ResideMenuItem;
import com.techuva.councellorapp.contus_Corporate.restclient.UserPollRestClient;
import com.techuva.councellorapp.contus_Corporate.search.SearchActivity;
import com.techuva.councellorapp.contus_Corporate.settings.Settings;
import com.techuva.councellorapp.contus_Corporate.userpolls.UserPolls;
import com.techuva.councellorapp.contusfly_corporate.chat.HomeChat;
import com.techuva.councellorapp.contusfly_corporate.chooseContactsDb.DatabaseHelper;
import com.techuva.councellorapp.contusfly_corporate.createpoll.MultipleOptions;
import com.techuva.councellorapp.contusfly_corporate.createpoll.PhotoComparison;
import com.techuva.councellorapp.contusfly_corporate.createpoll.YesOrNo;
import com.techuva.councellorapp.contusfly_corporate.fragments.FragmentContacts;
import com.techuva.councellorapp.contusfly_corporate.fragments.FragmentRecentChats;
import com.techuva.councellorapp.contusfly_corporate.model.PollUnseenStatusModel;
import com.techuva.councellorapp.contusfly_corporate.utils.Utils;
import com.techuva.councellorapp.contusfly_corporate.views.CircularImageView;
import com.techuva.new_changes_corporate.fragments.Announcements;
import com.techuva.new_changes_corporate.fragments.SearchAnnouncements;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MenuActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, IFragmentReplace, View.OnClickListener, MyPolls.MyPollsOnInteractionListner, UserPolls.UserPollOnFragmentInteractionListner, PublicPoll.CampaignPollInteractionListner {
    //reside menu
    private ResideMenu resideMenu;
    //menu activity
    private MenuActivity mContext;
    //item home
    private ResideMenuItem itemHome;
    //item profile
    private ResideMenuItem itemProfile;
    //item categories
    private ResideMenuItem itemCategories;
    //item settings
    //private ResideMenuItem itemSettings;
    //item feedback
    //  private ResideMenuItem itemFeedback;
    //item about us
    private ResideMenuItem itemAboutUs;
    //item contact us
    private ResideMenuItem itemContactUs;

    //item Announcement
    private ResideMenuItem itemAnnouncement;
    //Linear layout
    LinearLayout userPoll, chat;
    //Linear layout
    private LinearLayout publicPoll;
    //Frame layout
    private FrameLayout customFragmnet;
    //Frame Layout
    private FrameLayout mFragment;
    //Textview
    private TextView txtTitle;
    //imageview
    private ImageView imgSearch;
    //Reside menu item
    private ResideMenuItem itemMyPolls;
    //chat
    private ImageView imageChat;
    private static TextView getChatViewUnseenCount;
    private static TextView getUserPollUnseenCountChatview;
    private static TextView getAdminPollUnseenCountChatview;
    private static TextView getPublicPollChatUnseenCount;
    private static TextView getUserPollCountPrivateView;
    private static TextView getAdminPollCountAdminView;
    private static TextView getPrivatePollChatUnseenCount;
    private static TextView getUserPollCount;
    private static TextView getAdminPollCount;
    //user
    private ImageView imageUser;
    //public
    private ImageView imagePublic;
    private ImageView imagePublic_;
    //image profile
    private CircularImageView imgProfile;
    //title
    private TextView title;
    //icon
    private ImageView icon;
    //divider line
    private View dividerLine;
    //relative
    private RelativeLayout relative;
    //ImageView
    private String searchKey;
    private String userId;
    //Fragment
    private FragmentRecentChats fragmentRecentChats;
    //Fragment
    private FragmentContacts fragmentContacts;
    //Create poll
    private ImageView btnCreatePoll;
    //linear layout
    private LinearLayout layoutTop;
    private ArrayList<String> mCategory;
    private ArrayList<String> listGroupid;
    private ArrayList<String> mArrayList;
    private ArrayList<String> mContactName;
    private ArrayList<String> mGroupName;
    private TextView mTitle;
    private Button bDone;
    private Toolbar toolbar;
    private String backStateName;
    private LinearLayout linearUserPoll;
    private LinearLayout linearChat;
    private LinearLayout linearPublicPoll;
    private LinearLayout ll_tool;
    private Fragment currFrag;
    private ImageView imgMore;
    private LinearLayout imageAddComments;
    private ImageView imgEdit1;
    private ImageView imgEdit;
    private FrameLayout frame1;
    private ImageView imgEdit2;
    private TextView announcementtextUser;
    private TextView announcementtextPublic;
    private TextView userpolltext;
    private ImageView titleBarLeftMenu1;
    private ImageView titleBarLeftMenu;
    private MApplication mApplication;
    private DatabaseHelper db;
    public String fromActivity="";
    ImageView annouse_search,close_iv,search_iv;
    LinearLayout search_layout;
    EditText search_et;
    TextView txtTitle1;
    LinearLayout ll_announcement;
    LinearLayout ll_department_poll;
    Boolean doubleBackToExitPressedOnce = false;
    Toast exitToast;
    LinearLayout viewline;

    /**
     * TextViewCalled when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MApplication.setBoolean(getApplicationContext(), Constants.FromEditProfile, false);
        //Fresco initialization
        Fresco.initialize(this);
        setContentView(R.layout.main);
        viewline =findViewById(R.id.viewline);
        viewline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Initializing the activity
        mContext = this;
        userPoll =  findViewById(R.id.userpoll);
        imageUser =  userPoll.findViewById(R.id.imgUserPoll);
        publicPoll =  findViewById(R.id.publicpoll);
        announcementtextUser = userPoll.findViewById(R.id.anouncetext);
        announcementtextPublic = publicPoll.findViewById(R.id.anouncetext_);
        userpolltext=findViewById(R.id.userpolltext);
        chat =  findViewById(R.id.chat);
        imageChat =  chat.findViewById(R.id.imgChat);
        Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        userpolltext.setTypeface(faceRegular);
        announcementtextUser.setTypeface(faceRegular);
        announcementtextPublic.setTypeface(faceRegular);
        getChatViewUnseenCount =  chat.findViewById(R.id.chat_count);
        getUserPollUnseenCountChatview =  chat.findViewById(R.id.user_poll_unseen_count_chat);
        getAdminPollUnseenCountChatview =  chat.findViewById(R.id.admin_poll_count_chat_view);
        getPublicPollChatUnseenCount =  publicPoll.findViewById(R.id.unseen_public_poll);
        getUserPollCountPrivateView =  publicPoll.findViewById(R.id.user_poll_unseen_count_private_poll);
        getAdminPollCountAdminView =  publicPoll.findViewById(R.id.admin_poll_count);
        ll_announcement = findViewById(R.id.ll_announcement);
        ll_department_poll = findViewById(R.id.ll_department_poll);
       /* getPrivatePollChatUnseenCount =  userPoll.findViewById(R.id.get_unseen_chat_count_unseen_window);
        getUserPollCount =  userPoll.findViewById(R.id.user_poll_count);
        getAdminPollCount =  userPoll.findViewById(R.id.admin_poll_count_public_view);*/
        imagePublic =  publicPoll.findViewById(R.id.imgPublicPoll);
        imagePublic_ =  userPoll.findViewById(R.id.imgPublicPoll_);
        customFragmnet =  findViewById(R.id.main_fragment);
        mFragment =  findViewById(R.id.fragment);
        txtTitle =  findViewById(R.id.txtTitle);
        imgSearch =  findViewById(R.id.imgSearch);
        imgProfile =  findViewById(R.id.imageProfile);
        btnCreatePoll =  this.findViewById(R.id.imgEdit);
        imgMore =  findViewById(R.id.imgMore);
        imageAddComments =  findViewById(R.id.imageAddComments);
        layoutTop =  findViewById(R.id.layout_top);
        toolbar =  findViewById(R.id.mToolbar);
        txtTitle1 =  findViewById(R.id.txtTitle1);

        toolbar.findViewById(R.id.imgEdit).setVisibility(View.GONE);//A Toolbar is a generalization of action bars for use within application layouts.
        mTitle =  toolbar.findViewById(R.id.toolbar_title);
        db = new DatabaseHelper(this);
        userId = MApplication.getString(mContext, Constants.USER_ID);
        bDone =  findViewById(R.id.txtDone);
        imgEdit1 =  findViewById(R.id.imgEdit1);
        imgEdit =  findViewById(R.id.imgEdit);
        linearUserPoll = findViewById(R.id.userpoll);
        linearChat = findViewById(R.id.chat);
        linearPublicPoll =  findViewById(R.id.publicpoll);
        frame1 =  findViewById(R.id.frame1);
        imgEdit2 =  findViewById(R.id.imgEdit2);
        titleBarLeftMenu1 =  findViewById(R.id.title_bar_left_menu1);
        titleBarLeftMenu =  findViewById(R.id.title_bar_left_menu);
        annouse_search   = findViewById(R.id.annouse_search);
        search_layout    = findViewById(R.id.search_layout);
        close_iv         =  findViewById(R.id.close_iv);
        search_iv        = findViewById(R.id.search_iv);
        search_et        = findViewById(R.id.search_et);
        ll_tool        = findViewById(R.id.ll_tool);
        MApplication.setString(mContext, Constants.SearchKey, "");
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set whether home should be displayed as an "up" affordance.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Set whether an activity title/subtitle should be displayed.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        try{
            fromActivity=getIntent().getStringExtra("fromActivityName");
        }catch (NullPointerException e){
            e.printStackTrace();
            fromActivity="";
        }

        //Navigation icon
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open menu to the left
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        ll_department_poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MApplication.materialdesignDialogStart(mContext);
                UserPollRestClient.getInstance().postUnseenCount(new String("getUserPollsSeen"), new String(userId), new String("user")
                        , new Callback<PollUnseenStatusModel>() {
                            @Override
                            public void success(PollUnseenStatusModel userPollResponseModel, Response response) {
                                if (userPollResponseModel.getResponseCode().equals("200")) {
                                /*getUserPollCount.setVisibility(View.GONE);
                                getUserPollUnseenCountChatview.setVisibility(View.GONE);
                                getUserPollCountPrivateView.setVisibility(View.GONE);*/
                                    toolbar.setVisibility(View.VISIBLE);
                                    toolbar.findViewById(R.id.imgEdit).setVisibility(View.GONE);
                                    layoutTop.setVisibility(View.GONE);
                                    //setting the text
                                    mTitle.setVisibility(View.VISIBLE);
                                    mTitle.setText("Polls");
                                    Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
                                    userpolltext.setTypeface(faceRegular);
                                    announcementtextPublic.setTypeface(faceRegular);
                                    //View visible
                                    announcementtextPublic.setTextColor(getResources().getColor(R.color.grey_color_text));
                                    announcementtextUser.setTextColor(getResources().getColor(R.color.grey_color_text));
                                    userpolltext.setTextColor(getResources().getColor(R.color.text_colore_blue));
                                    imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
                                    imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
                                    imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollblue));
                                    btnCreatePoll.setVisibility(View.GONE);
                                    //View visible
                                    imgMore.setVisibility(View.GONE);
                                    imgEdit1.setVisibility(View.GONE);
                                    imgEdit2.setVisibility(View.GONE);
                                    imgEdit.setVisibility(View.GONE);
                                    ll_tool.setVisibility(View.VISIBLE);
                                    search_layout.setVisibility(View.GONE);
                                    //View visible
                                    imgSearch.setVisibility(View.VISIBLE);
                                    //view visible
                                    frame1.setVisibility(View.GONE);
                                    //View visible
                                    //View visible
                                    userPoll.setVisibility(View.VISIBLE);
                                    //View gone
                                    chat.setVisibility(View.GONE);
                                    //View gone
                                    publicPoll.setVisibility(View.GONE);
                                    MApplication.materialdesignDialogStop();
                                    search_layout.setVisibility(View.GONE);
                                    userpolltext.setTypeface(faceRegular);
                                    announcementtextUser.setTypeface(faceRegular);
                                    announcementtextPublic.setTypeface(faceRegular);
                                    frame1.setVisibility(View.GONE);
                                    //User polls mFragment
                                    imageChangeFragment(new UserPolls());
                                }
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                MApplication.materialdesignDialogStop();
                            }
                        });
            }
        });



        ll_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
                layoutTop.setVisibility(View.GONE);
                Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
                userpolltext.setTypeface(faceRegular);
                announcementtextUser.setTypeface(faceRegular);
                announcementtextPublic.setTypeface(faceRegular);
                //view invisible
                imgSearch.setVisibility(View.GONE);
                //view invisible
                btnCreatePoll.setVisibility(View.GONE);
                //view invisible
                imgMore.setVisibility(View.GONE);
                search_layout.setVisibility(View.GONE);
                ll_tool.setVisibility(View.VISIBLE);
                annouse_search.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                search_et.setText("");
                userPoll.setVisibility(View.GONE);
                publicPoll.setVisibility(View.VISIBLE);
                announcementtextUser.setTextColor(getResources().getColor(R.color.colorPrimary));
                announcementtextPublic.setTextColor(getResources().getColor(R.color.colorPrimary));
                userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
                imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
                frame1.setVisibility(View.GONE);
                chat.setVisibility(View.GONE);
                annouse_search.setVisibility(View.VISIBLE);
                mTitle.setText("Announcements");
                imgSearch.setVisibility(View.GONE);
                publicPoll.setVisibility(View.VISIBLE);
                imageChangeFragment(new Announcements());
            }
        });

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.ttf");
        // Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        userpolltext.setTypeface(faceRegular);
        announcementtextUser.setTypeface(faceRegular);
        announcementtextPublic.setTypeface(faceRegular);
        txtTitle.setTypeface(face);
        mTitle.setTypeface(face);

        mApplication = (MApplication) getApplicationContext();

        mCategory = new ArrayList<>();
        listGroupid = new ArrayList<>();
        mArrayList = new ArrayList<>();
        mContactName = new ArrayList<>();
        mGroupName = new ArrayList<>();
        MApplication.setBoolean(MenuActivity.this, "createpoll", false);
        //Hide sysytem UI
        MApplication.hideSystemUI(MenuActivity.this);

        fragmentRecentChats = new FragmentRecentChats();
        fragmentContacts = new FragmentContacts();

        announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
        announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
        userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
        imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
        imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
        imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
        if (savedInstanceState == null) {
            layoutTop.setVisibility(View.GONE);
            //Setting the text
            if (MApplication.getBoolean(this, "notification click")) {
                mTitle.setText("Chat");
            } else {
                mTitle.setText("Department");
                ll_tool.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
            }
            //visibility gone
            imageAddComments.setVisibility(View.GONE);
        } //Set up menu
        setUpMenu();

        //Interface definition for a callback to be invoked when a view is clicked.
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(search);
            }
        });

        annouse_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_tool.setVisibility(View.GONE);
                search_layout.setVisibility(View.VISIBLE);
                search_et.setHint("Search with any keyword");
                mTitle.setVisibility(View.GONE);
                annouse_search.setVisibility(View.GONE);
            }
        });

        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_layout.setVisibility(View.GONE);
                mTitle.setVisibility(View.VISIBLE);
                annouse_search.setVisibility(View.VISIBLE);
                search_et.setText("");
                MApplication.setString(mContext, Constants.SearchKey, "");
                userPoll.setVisibility(View.VISIBLE);
                publicPoll.setVisibility(View.GONE);
                imageChangeFragment(new Announcements());
            }
        });

        search_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_tool.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
                userpolltext.setTypeface(faceRegular);
                announcementtextUser.setTypeface(faceRegular);
                announcementtextPublic.setTypeface(faceRegular);
                //view invisible
                imgSearch.setVisibility(View.GONE);
                //view invisible
                btnCreatePoll.setVisibility(View.GONE);
                //view invisible
                imgMore.setVisibility(View.GONE);
                //setting the text
                mTitle.setVisibility(View.VISIBLE);
                MApplication.setString(mContext, Constants.SearchKey, search_et.getText().toString().trim());
                mTitle.setText("Announcements");
                search_et.setText("");
                announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
                announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
                userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
                imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
                //view visible
                frame1.setVisibility(View.GONE);
                //public poll
                imageChangeFragment(new SearchAnnouncements());
                //start animation
                imagePublic.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_click));
                //visibility gone
                userPoll.setVisibility(View.GONE);
                //visibility gone
                chat.setVisibility(View.GONE);
                //visibility visible
                publicPoll.setVisibility(View.VISIBLE);
                annouse_search.setVisibility(View.VISIBLE);
                mTitle.setText("Announcements");
                imgSearch.setVisibility(View.GONE);
                imageChangeFragment(new SearchAnnouncements());
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ll_tool.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    search_layout.setVisibility(View.GONE);
                    Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
                    userpolltext.setTypeface(faceRegular);
                    announcementtextUser.setTypeface(faceRegular);
                    announcementtextPublic.setTypeface(faceRegular);
                    //view invisible
                    imgSearch.setVisibility(View.GONE);
                    //view invisible
                    btnCreatePoll.setVisibility(View.GONE);
                    //view invisible
                    imgMore.setVisibility(View.GONE);
                    //setting the text
                    mTitle.setVisibility(View.VISIBLE);
                    MApplication.setString(mContext, Constants.SearchKey, search_et.getText().toString().trim());
                    mTitle.setText("Announcements");
                    //search_et.setText("");
                    announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
                    announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
                    userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
                    imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                    imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
                    imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
                    //view visible
                    frame1.setVisibility(View.GONE);
                    //public poll
                    imageChangeFragment(new SearchAnnouncements());
                    //start animation
                    imagePublic.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_click));
                    //visibility gone
                    userPoll.setVisibility(View.GONE);
                    //visibility gone
                    chat.setVisibility(View.GONE);
                    //visibility visible
                    publicPoll.setVisibility(View.VISIBLE);
                    annouse_search.setVisibility(View.VISIBLE);
                    mTitle.setText("Announcements");
                    imgSearch.setVisibility(View.GONE);
                    imageChangeFragment(new SearchAnnouncements());
                    return true;
                }
                return false;
            }
        });

        //Interface definition for a callback to be invoked when a view is clicked.
        findViewById(R.id.txtDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This method is used to save the catgories which is choosen by the user
                Category categoryfragment = new Category();
                categoryfragment.categoryPollSave(MenuActivity.this);
            }
        });
    /*    toolbar.findViewById(R.id.imgEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSpinner(view);
            }
        });*/
        String getBatchCount = mApplication.getStringFromPreference(com.techuva.councellorapp.contus_Corporate.app.Constants.GET_MESSAGE_UNREAD_COUNT);
        if (TextUtils.isEmpty(getBatchCount)) {
            mApplication.storeStringInPreference(com.techuva.councellorapp.contus_Corporate.app.Constants.GET_MESSAGE_UNREAD_COUNT, "0");
        }
      /*  if (mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT).equals("0")) {
            getChatViewUnseenCount.setVisibility(View.GONE);
            getPublicPollChatUnseenCount.setVisibility(View.GONE);
            getPrivatePollChatUnseenCount.setVisibility(View.GONE);
        } else {
            getChatViewUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
            getPublicPollChatUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
            getPrivatePollChatUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
        }*/
    }


    /**
     * Setting up the menu
     */
    private void setUpMenu() {
        Log.e("testtimes", "testtimes");
        resideMenu = new ResideMenu(this);
        // attach to current activity
        resideMenu.attachToActivity(this);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        userpolltext.setTypeface(faceRegular);
        announcementtextUser.setTypeface(faceRegular);
        announcementtextPublic.setTypeface(faceRegular);
        if (MApplication.getBoolean(this, "notification click")) {
            //chat mFragment
            imageChangeFragment(new HomeChat());
            //setting the text
            mTitle.setText("Chat");
            //view gone
            imageAddComments.setVisibility(View.GONE);
            //image profile gone
            imgProfile.setVisibility(View.GONE);
            //image edit
            imgEdit1.setVisibility(View.GONE);
            //image more visiblity gone
            imgMore.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.GONE);
            //view visible
            //view visible
            imgSearch.setVisibility(View.GONE);
            //view gone
            bDone.setVisibility(View.GONE);
            imageChat.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_click));
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            layoutTop.setVisibility(View.GONE);
            //view gone
            userPoll.setVisibility(View.GONE);
            //view visible
            chat.setVisibility(View.VISIBLE);
            //view gone
            publicPoll.setVisibility(View.GONE);
        } else {
            //linearUserPoll.setVisibility(View.VISIBLE);
            //view visible
            frame1.setVisibility(View.GONE);
            userPoll.setVisibility(View.GONE);
            publicPoll.setVisibility(View.VISIBLE);
            announcementtextUser.setTextColor(getResources().getColor(R.color.colorPrimary));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.colorPrimary));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            imageChangeFragment(new Announcements());
        }
        // create itemHome items
        itemHome = new ResideMenuItem(this, R.drawable.announcegrya, getResources().getString(R.string.activity_home),false);
        // create itemMyPolls items
        itemMyPolls = new  ResideMenuItem(this, R.drawable.ic_my_polls_gray, getResources().getString(R.string.activity_mypolls),false);
        // create itemProfile items
        itemProfile = new ResideMenuItem(this, R.drawable.ic_profile_gray, getResources().getString(R.string.activity_myprofile),false);
        // create itemCategories items
        itemCategories = new ResideMenuItem(this, R.drawable.ic_categories_gray, getResources().getString(R.string.activity_mycategory),true);
        // create itemSettings items
        // itemSettings = new ResideMenuItem(this, R.drawable.ic_settings_gray, getResources().getString(R.string.activity_settings));
        // create itemAboutUs items
        itemAboutUs = new ResideMenuItem(this, R.drawable.ic_about_app_gray, getResources().getString(R.string.activity_about_app),true);
        // create itemFeedback items
        // itemFeedback = new ResideMenuItem(this, R.drawable.ic_feedback_gray, getResources().getString(R.string.activity_feedback),true);
        // create itemContactUs items
        itemContactUs = new ResideMenuItem(this, R.drawable.ic_contact_gray, getResources().getString(R.string.activity_contactus),true);

        itemAnnouncement = new ResideMenuItem(this,R.drawable.ic_contact_gray,"Announcements",false);
        //divider line
        dividerLine = itemHome.findViewById(R.id.view);
        //title
        title = (TextView) itemHome.findViewById(R.id.tv_title);
        //icon
        icon = (ImageView) itemHome.findViewById(R.id.iv_icon);
        //view visible
        dividerLine.setVisibility(View.VISIBLE);
        //setting the text color
        title.setTextColor(getResources().getColor(R.color.blue_color));
        //Setting the icon color
        icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
        //Interface definition for a callback to be invoked when a view is clicked.
        itemHome.setOnClickListener(this);
        itemMyPolls.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCategories.setOnClickListener(this);
        //itemSettings.setOnClickListener(this);
        //itemFeedback.setOnClickListener(this);
        itemAboutUs.setOnClickListener(this);
        itemContactUs.setOnClickListener(this);
        itemAnnouncement.setOnClickListener(this);

        //Adding home to the menu
        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        //Adding itemMyPolls to the menu
       // resideMenu.addMenuItem(itemMyPolls, ResideMenu.DIRECTION_LEFT);
        //Adding home to the menu
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        //Adding itemCategories to the menu
        //resideMenu.addMenuItem(itemCategories, ResideMenu.DIRECTION_LEFT);
        //Adding itemSettings to the menu
        // resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);
        //Adding itemAboutUs to the menu
        resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_LEFT);
        //Adding itemFeedback to the menu
        //      resideMenu.addMenuItem(itemFeedback, ResideMenu.DIRECTION_LEFT);
        //Adding itemContactUs to the menu
        resideMenu.addMenuItem(itemContactUs, ResideMenu.DIRECTION_LEFT);
        //resideMenu.addMenuItem(itemAnnouncement,ResideMenu.DIRECTION_LEFT);
        // You can disable a direction by setting ->
        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open menu to the left
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        titleBarLeftMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open menu to the left
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        imgEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining the components you are targeting.
                // For example, via the startActivity() method you can define that the intent should be used to start an activity.
                Intent a = new Intent(MenuActivity.this, EditProfile.class);
                startActivity(a);
            }
        });
    }

    /**
     * Calling this method from xml file when performing the click on the ACTION
     *
     * @param view
     */
    public void onClickSpinner(View view) {
        mCategory = MApplication.loadStringArray(MenuActivity.this, mCategory, Constants.ACTIVITY_CATEGORY_COMPLETE_INFO, Constants.ACTIVITY_CATEGORY__COMPLETE_INFO_SIZE);
        mCategory.clear();
        //  if (view.getId() == R.id.imgEdit||view.getId() == R.id.imgEdit2) {
        mCategory.add("Public");
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteTable();
        db.deleteGroupTable();

        MApplication.saveStringArray(MenuActivity.this, mCategory, Constants.ACTIVITY_CATEGORY_COMPLETE_INFO, Constants.ACTIVITY_CATEGORY__COMPLETE_INFO_SIZE);
        MApplication.saveStringArray(MenuActivity.this, mCategory, Constants.ACTIVITY_CATEGORY_INFO, Constants.ACTIVITY_CATEGORY_INFO_SIZE);
        MApplication.saveStringArray(MenuActivity.this, listGroupid, Constants.ACTIVITY_GROUP_INFO, Constants.ACTIIVTY_GROUP_INFO_SIZE);
        MApplication.saveStringArray(MenuActivity.this, mArrayList, Constants.ACTIVITY_CONTACT_INFO, Constants.ACTIIVTY_CONTACT_INFO_SIZE);
        MApplication.saveStringArray(MenuActivity.this, mContactName, Constants.ACTIVITY_CONTACT_NAME_INFO, Constants.ACTIIVTY_CONTACT_NAME_INFO_SIZE);
        MApplication.saveStringArray(MenuActivity.this, mGroupName, Constants.ACTIVITY_GROUP_NAME_INFO, Constants.ACTIVITY_GROUP_NAME_INFO_SIZE);
        MApplication.setString(MenuActivity.this, "imageQuestion1", "");
        MApplication.setString(MenuActivity.this, "imageQuestion2", "");
        MApplication.setString(MenuActivity.this, "imageMultipleOptionQuestion1", "");
        MApplication.setString(MenuActivity.this, "imageMultipleOptionQuestion2", "");
        MApplication.setString(MenuActivity.this, "imagePhotoComparisionQuestion1", "");
        MApplication.setString(MenuActivity.this, "imagePhotoComparisionQuestion2", "");
        MApplication.setString(MenuActivity.this, "imageFilePathQuestion1", "");
        MApplication.setString(MenuActivity.this, "imageFilePathQuestion2", "");
        MApplication.setString(MenuActivity.this, "imageMultipleOptionFilePathQuestion1", "");
        MApplication.setString(MenuActivity.this, "imageMultipleOptionFilePathQuestion2", "");
        MApplication.setString(MenuActivity.this, "imagePhotoComparisionFilePathQuestion1", "");
        MApplication.setString(MenuActivity.this, "imagePhotoComparisionFilePathQuestion2", "");
        MApplication.setString(MenuActivity.this, "option1", "");
        MApplication.setString(MenuActivity.this, "option2", "");
        MApplication.setString(MenuActivity.this, "option3", "");
        MApplication.setString(MenuActivity.this, "option4", "");
        MApplication.setString(MenuActivity.this, "filePathOption1", "");
        MApplication.setString(MenuActivity.this, "filePathOption2", "");
        MApplication.setString(MenuActivity.this, "filePathOption3", "");
        MApplication.setString(MenuActivity.this, "filePathOption4", "");

        Context wrapper = new ContextThemeWrapper(MenuActivity.this, R.style.PopupMenu);
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(wrapper, btnCreatePoll);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_yesorNo:
                        MApplication.setBoolean(MenuActivity.this, "createpoll", true);
                        //Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining the components you are targeting.
                        // For example, via the startActivity() method you can define that the intent should be used to start an activity.
                        Intent a = new Intent(MenuActivity.this, YesOrNo.class);
                        startActivity(a);
                        return true;
                    case R.id.item_multiple_options:
                        MApplication.setBoolean(MenuActivity.this, "createpoll", true);
                        //Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining the components you are targeting.
                        // For example, via the startActivity() method you can define that the intent should be used to start an activity.
                        Intent b = new Intent(MenuActivity.this, MultipleOptions.class);
                        startActivity(b);
                        return true;
                    case R.id.item_photocomparison:
                        MApplication.setBoolean(MenuActivity.this, "createpoll", true);
                        //Intents are objects of the android.content.Intent type. Your code can send them to the Android system defining the components you are targeting.
                        // For example, via the startActivity() method you can define that the intent should be used to start an activity.
                        Intent c = new Intent(MenuActivity.this, PhotoComparison.class);
                        startActivity(c);
                        return true;
                }
                return false;
            }
        });
        popup.show();//s
        //}
    }

    /**
     * Calling this method from xml file when performing the click on the ACTION
     *
     * @param view
     */
    public void onClickButton(View view) {

        if (view.getId() == R.id.ll_department_poll) {
            MApplication.materialdesignDialogStart(mContext);
            UserPollRestClient.getInstance().postUnseenCount(new String("getUserPollsSeen"), new String(userId), new String("user")
                    , new Callback<PollUnseenStatusModel>() {
                        @Override
                        public void success(PollUnseenStatusModel userPollResponseModel, Response response) {
                            if (userPollResponseModel.getResponseCode().equals("200")) {
                                /*getUserPollCount.setVisibility(View.GONE);
                                getUserPollUnseenCountChatview.setVisibility(View.GONE);
                                getUserPollCountPrivateView.setVisibility(View.GONE);*/
                                toolbar.setVisibility(View.VISIBLE);
                                toolbar.findViewById(R.id.imgEdit).setVisibility(View.GONE);
                                layoutTop.setVisibility(View.GONE);
                                //setting the text
                                mTitle.setVisibility(View.VISIBLE);
                                mTitle.setText("Polls");
                                Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
                                userpolltext.setTypeface(faceRegular);
                                announcementtextPublic.setTypeface(faceRegular);
                                //View visible
                                announcementtextPublic.setTextColor(getResources().getColor(R.color.grey_color_text));
                                announcementtextUser.setTextColor(getResources().getColor(R.color.grey_color_text));
                                userpolltext.setTextColor(getResources().getColor(R.color.text_colore_blue));
                                imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
                                imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
                                imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollblue));
                                btnCreatePoll.setVisibility(View.GONE);
                                //View visible
                                imgMore.setVisibility(View.GONE);
                                imgEdit1.setVisibility(View.GONE);
                                imgEdit2.setVisibility(View.GONE);
                                imgEdit.setVisibility(View.GONE);
                                ll_tool.setVisibility(View.VISIBLE);
                                search_layout.setVisibility(View.GONE);
                                //View visible
                                imgSearch.setVisibility(View.VISIBLE);
                                //view visible
                                frame1.setVisibility(View.GONE);
                                //View visible
                                //View visible
                                userPoll.setVisibility(View.VISIBLE);
                                //View gone
                                chat.setVisibility(View.GONE);
                                //View gone
                                publicPoll.setVisibility(View.GONE);
                                MApplication.materialdesignDialogStop();
                                search_layout.setVisibility(View.GONE);
                                userpolltext.setTypeface(faceRegular);
                                announcementtextUser.setTypeface(faceRegular);
                                announcementtextPublic.setTypeface(faceRegular);
                                frame1.setVisibility(View.GONE);
                                //User polls mFragment
                                imageChangeFragment(new UserPolls());
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            MApplication.materialdesignDialogStop();
                        }
                    });
        }
        else if (view.getId() == R.id.ll_announcement) {
            toolbar.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.GONE);
            Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
            userpolltext.setTypeface(faceRegular);
            publicPoll.setVisibility(View.VISIBLE);
            announcementtextUser.setTypeface(faceRegular);
            announcementtextPublic.setTypeface(faceRegular);
            //view invisible
            imgSearch.setVisibility(View.GONE);
            //view invisible
            btnCreatePoll.setVisibility(View.GONE);
            //view invisible
            imgMore.setVisibility(View.GONE);
            search_layout.setVisibility(View.GONE);
            ll_tool.setVisibility(View.VISIBLE);
            annouse_search.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
            search_et.setText("");
            userPoll.setVisibility(View.VISIBLE);
            publicPoll.setVisibility(View.VISIBLE);
            announcementtextUser.setTextColor(getResources().getColor(R.color.colorPrimary));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.colorPrimary));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            frame1.setVisibility(View.GONE);
            chat.setVisibility(View.GONE);
            annouse_search.setVisibility(View.VISIBLE);
            mTitle.setText("Announcements");
            imgSearch.setVisibility(View.GONE);
            imageChangeFragment(new Announcements());

        }
        else if (view.getId() == R.id.imgChat) {
            toolbar.setVisibility(View.VISIBLE);
            //chat mFragment
            imageChangeFragment(new HomeChat());
            //setting the text
            mTitle.setText("Chat");
            //visiblity gone
            findViewById(R.id.imgSearch).setVisibility(View.GONE);
            //view visible
            imgMore.setVisibility(View.VISIBLE);
            //view gone
            findViewById(R.id.imgEdit).setVisibility(View.GONE);
            //start animation
            Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
            userpolltext.setTypeface(faceRegular);
            announcementtextUser.setTypeface(faceRegular);
            announcementtextPublic.setTypeface(faceRegular);
            imageChat.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_click));
            //view visible
            frame1.setVisibility(View.GONE);
            //view gone
            userPoll.setVisibility(View.GONE);
            //view visible
            chat.setVisibility(View.VISIBLE);userpolltext.setTypeface(faceRegular);
            //view gone
            publicPoll.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
        //title
        title =  view.findViewById(R.id.tv_title);
        //icon
        icon =  view.findViewById(R.id.iv_icon);
        //divider line
        dividerLine = view.findViewById(R.id.view);
        //setting the text color
        title.setTextColor(getResources().getColor(R.color.blue_color));
        //Setting the icon color
        icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
        //divider line visible
        dividerLine.setVisibility(View.VISIBLE);
        //relative layout
        relative =  view.findViewById(R.id.relative);
        mTitle.setVisibility(View.VISIBLE);
        search_layout.setVisibility(View.GONE);
        annouse_search.setVisibility(View.GONE);
        search_et.setText("");
        //If the view matches itemHome
        if (view == itemHome) {
            announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            MApplication.setBoolean(this, "mypollfragment", false);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            //If user poll get visibility setting the text title as user poll
            //If Chats get visibility setting the text title as Chats
            //If Public Poll get visibility setting the text title as Public Poll
            annouse_search.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.GONE);
            //setting the text`
            txtTitle.setText("Announcements");
            //View visible
            announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            //User polls mFragment
            //If view does not equal to home
            ll_tool.setVisibility(View.VISIBLE);
            annouse_search.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
            //View visible
            imgSearch.setVisibility(View.VISIBLE);
            //view visible
            frame1.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            //view visible
            imgProfile.setVisibility(View.GONE);
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Announcements");
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgSearch.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgEdit2.setVisibility(View.GONE);
            imageChangeFragment(new Announcements());

        } else if (!view.equals(itemHome)) {
            imgEdit.setVisibility(View.GONE);
            //      toolbar.setVisibility(View.GONE);
            //title
            title =  itemHome.findViewById(R.id.tv_title);
            //icon
            icon =  itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //visibility invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative
            relative =  itemHome.findViewById(R.id.relative);
            //title set text color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon set text color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //relative set text color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));

        }
        //If the view matches itemMyPolls
        if (view == itemMyPolls) {
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            annouse_search.setVisibility(View.GONE);
            //visibility gone
            imgMore.setVisibility(View.GONE);
            //visibility gone
            imageAddComments.setVisibility(View.GONE);
            //visibility gone
            imgProfile.setVisibility(View.GONE);
            //visibility gone
            imgEdit1.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.VISIBLE);
            //view visible
            imgEdit2.setVisibility(View.GONE);
            //view gone
            imgSearch.setVisibility(View.GONE);
            //view gone
            bDone.setVisibility(View.GONE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            //set the title as my polls
            txtTitle.setText(getResources().getString(R.string.activity_mypolls));
            //Set the boolean
            MApplication.setBoolean(this, "createpoll", false);
            //This view contains only create poll icon
            OnMyPollsFragment("", "");
        } else if (!view.equals(itemMyPolls)) {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemMyPolls.findViewById(R.id.tv_title);
            //icon
            icon =  itemMyPolls.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemMyPolls.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemMyPolls.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        //If the view matches itemProfile
        if (view == itemProfile) {
            userPoll.setVisibility(View.GONE);
            publicPoll.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view visible
            imageAddComments.setVisibility(View.VISIBLE);
            //view visible
            imgProfile.setVisibility(View.VISIBLE);
            //image profile is visible
            if (imgProfile.getVisibility() == View.VISIBLE) {
                //   Getting the string from image url
                Bitmap image = db.getImageCache("profile_pic");
                if (image != null) {
                    imgProfile.setImageBitmap(image);
                } else {
                    String imageUrl = MApplication.getString(mContext, Constants.PROFILE_IMAGE);
                    Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                }
                //Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                //view visible
                imgEdit1.setVisibility(View.VISIBLE);
                imgEdit1.setImageResource(R.drawable.ic_edit_profile);
                //set background resource
                imageAddComments.setBackgroundResource(R.drawable.gradient);
                //view invisible
                frame1.setVisibility(View.VISIBLE);
                //text view done
                bDone.setVisibility(View.GONE);
                //view visible
                titleBarLeftMenu1.setVisibility(View.VISIBLE);
                //view invisible
                imgSearch.setVisibility(View.INVISIBLE);
                //setting the text as my profile
                toolbar.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.activity_myprofile));
                //This view contains only create poll icon
                imageChangeFragment(new MyProfile());
            }
        } else if (!view.equals(itemProfile)) {
            annouse_search.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = itemProfile.findViewById(R.id.tv_title);
            //icon
            icon =  itemProfile.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemProfile.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemProfile.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        if (view == itemCategories) {
            toolbar.setVisibility(View.GONE);
            annouse_search.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //Image gone
            imageAddComments.setVisibility(View.GONE);
            //Image profile
            imgProfile.setVisibility(View.GONE);
            //edit icon
            imgEdit1.setVisibility(View.GONE);
            //edit icon
            findViewById(R.id.imgEdit).setVisibility(View.GONE);
            //search icon
            findViewById(R.id.imgSearch).setVisibility(View.GONE);
            //create poll more icon
            imgMore.setVisibility(View.GONE);
            //frame 1 visible
            frame1.setVisibility(View.VISIBLE);
            //text done visible
            bDone.setVisibility(View.VISIBLE);
            //image search invisible
            imgSearch.setVisibility(View.INVISIBLE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            //category
            txtTitle.setText(getResources().getString(R.string.activity_mycategory));
            // Default fragment
            imageChangeFragment(new Category());

        } else if (!view.equals(itemCategories)) {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemCategories.findViewById(R.id.tv_title);
            //icon
            icon =  itemCategories.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemCategories.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative = itemCategories.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
       /* if (view == itemSettings) {
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //mTitle the title
            txtTitle.setText(getResources().getString(R.string.activity_settings));
            // Default fragment
            imageChangeFragment(new Settings());
        } *//*else if (!view.equals(itemSettings)) {
            layoutTop.setVisibility(View.VISIBLE);
            //title
           // title = (TextView) itemSettings.findViewById(R.id.tv_title);
            //icon
           // icon = (ImageView) itemSettings.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
           // dividerLine = itemSettings.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            //relative = (RelativeLayout) itemSettings.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }*/
        if (view == itemAboutUs) {

            userPoll.setVisibility(View.GONE);
            publicPoll.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            annouse_search.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText(getResources().getString(R.string.activity_about_app));
            // Default fragment
            imageChangeFragment(new AboutUs());

        } else if (!view.equals(itemAboutUs)) {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = itemAboutUs.findViewById(R.id.tv_title);
            //icon
            icon =  itemAboutUs.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemAboutUs.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemAboutUs.findViewById(R.id.relative);
            annouse_search.setVisibility(View.VISIBLE);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }

        if(view==itemAnnouncement)
        {
            toolbar.setVisibility(View.GONE);

            layoutTop.setVisibility(View.VISIBLE);

            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText("Announcements");
            imageChangeFragment(new Announcements());
        }
        /*else if(!view.equals(itemAnnouncement))
        {
            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = (TextView) itemAnnouncement.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemAnnouncement.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemAnnouncement.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative = (RelativeLayout) itemAnnouncement.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }*/
        if (view == itemContactUs) {

            userPoll.setVisibility(View.GONE);
            publicPoll.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            annouse_search.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText(getResources().getString(R.string.activity_contactus));
            imageChangeFragment(new ContactUs());
        } else if (!view.equals(itemContactUs)) {
            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = itemContactUs.findViewById(R.id.tv_title);
            //icon
            icon = itemContactUs.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemContactUs.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative = itemContactUs.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }

        resideMenu.closeMenu();
    }

    /**
     * Views are initialized
     */
    private void viewsBinding() {
        //Image gone
        imageAddComments.setVisibility(View.GONE);
        //Image profile
        imgProfile.setVisibility(View.GONE);
        //frame 1 visible
        frame1.setVisibility(View.VISIBLE);
        //create poll more icon
        imgMore.setVisibility(View.GONE);  //edit icon
        imgEdit1.setVisibility(View.GONE);
        imgEdit2.setVisibility(View.GONE);
        //edit icon
        btnCreatePoll.setVisibility(View.GONE);
        //search icon
        imgSearch.setVisibility(View.GONE);
        //text done visible
        bDone.setVisibility(View.GONE);
        //image search invisble
        //view gone
        titleBarLeftMenu1.setVisibility(View.GONE);
    }


    /**
     * ChangeFragment
     *
     * @param targetFragment
     */

    private void imageChangeFragment(Fragment targetFragment) {
        //clear view list
        resideMenu.clearIgnoredViewList();
        //Visibility gone
        customFragmnet.setVisibility(View.VISIBLE);
        //set visibility view
        mFragment.setVisibility(View.INVISIBLE);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack

        String backStateName = targetFragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment2, targetFragment, targetFragment.getClass().getName());
            ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
       /* if (backStateName == UserPolls.class.getName()) {
            linearChat.setVisibility(View.INVISIBLE);
            linearPublicPoll.setVisibility(View.INVISIBLE);
            linearUserPoll.setVisibility(View.VISIBLE);
        } else {
            linearChat.setVisibility(View.INVISIBLE);
            linearPublicPoll.setVisibility(View.INVISIBLE);
            linearUserPoll.setVisibility(View.INVISIBLE);
        }*/
    }

    private boolean isInHomeFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "Announcements".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInSearchAnnouncement() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "SearchAnnouncements".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInPollsFragment() {
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            // if (item.isVisible() && "HomeActivity".equals(item.getClass().getSimpleName())) {
            if (item.isVisible() && "UserPolls".equals(item.getClass().getSimpleName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {

        search_layout.setVisibility(View.GONE);
        ll_tool.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        exitToast = Toast.makeText(getApplicationContext(), "Press back again to exit Ananth Reddy App", Toast.LENGTH_SHORT);
        if(isInHomeFragment() && !doubleBackToExitPressedOnce){
            // Do what ever you want
            exitToast.show();
            doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 10000);
        }

        else if (isInPollsFragment() ) {
            announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            MApplication.setBoolean(this, "mypollfragment", false);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            //If user poll get visibility setting the text title as user poll
            //If Chats get visibility setting the text title as Chats
            //If Public Poll get visibility setting the text title as Public Poll
            annouse_search.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.GONE);
            //setting the text`
            txtTitle.setText("Announcements");
            //View visible
           /* announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
           */ btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            //User polls mFragment
            imageChangeFragment(new Announcements());
            //View visible
            imgSearch.setVisibility(View.VISIBLE);
            //view visible
            frame1.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            //view visible
            imgProfile.setVisibility(View.GONE);
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Announcements");
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgSearch.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgEdit2.setVisibility(View.GONE);
            //If view does not equal to home
            ll_tool.setVisibility(View.VISIBLE);
            annouse_search.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
        }else if(isInSearchAnnouncement()){
            announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
            MApplication.setBoolean(this, "mypollfragment", false);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            //If user poll get visibility setting the text title as user poll
            //If Chats get visibility setting the text title as Chats
            //If Public Poll get visibility setting the text title as Public Poll
            annouse_search.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.GONE);
            //setting the text`
            txtTitle.setText("Announcements");
            //View visible
           /* announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
            announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
            userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
           */ btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            //User polls mFragment
            imageChangeFragment(new Announcements());
            //View visible
            imgSearch.setVisibility(View.VISIBLE);
            //view visible
            frame1.setVisibility(View.GONE);
            imgEdit.setVisibility(View.GONE);
            //view visible
            imgProfile.setVisibility(View.GONE);
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Announcements");
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgSearch.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            imgEdit2.setVisibility(View.GONE);
            //If view does not equal to home
            ll_tool.setVisibility(View.VISIBLE);
            annouse_search.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
        }
       /* FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            currFrag = manager.
                    findFragmentById(R.id.fragment2);
        }
        if (currFrag != null) {
            if (currFrag.getTag() == UserPolls.class.getName() || currFrag.getTag() == HomeChat.class.getName() || currFrag.getTag() == PublicPoll.class.getName()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
                finish();
            } else {

                super.onBackPressed();

            }
        } else {

            super.onBackPressed();
        }*/
        else {
            finishAffinity();
            finish();
            super.onBackPressed();
        }
    }


    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNotificationBadgeReceiver();
        removeChatUnseenCount();
        MApplication.setString(mContext, Constants.SearchKey, "");
        if (imgProfile.getVisibility() == View.VISIBLE) {
            //Get the url from the  preference
            Bitmap image = db.getImageCache("profile_pic");
            if (image != null) {
                imgProfile.setImageBitmap(image);
            } else {
                String imageUrl = MApplication.getString(mContext, Constants.PROFILE_IMAGE);
                Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
            }
        }

        ll_tool.setVisibility(View.VISIBLE);
        mTitle.setText("Announcement");
        userPoll.setVisibility(View.GONE);
        publicPoll.setVisibility(View.VISIBLE);
        annouse_search.setVisibility(View.VISIBLE);
        announcementtextUser.setTextColor(getResources().getColor(R.color.text_colore_blue));
        announcementtextPublic.setTextColor(getResources().getColor(R.color.text_colore_blue));
        userpolltext.setTextColor(getResources().getColor(R.color.grey_color_text));
        imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
        imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announceblue));
        imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollgray));
        if (MApplication.getBoolean(this, Constants.SEARCH_BACKPRESS_BOOLEAN) == true) {
            Log.e("usermy", "usermy");
            announcementtextPublic.setTextColor(getResources().getColor(R.color.grey_color_text));
            announcementtextUser.setTextColor(getResources().getColor(R.color.grey_color_text));
            userpolltext.setTextColor(getResources().getColor(R.color.text_colore_blue));
            imagePublic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
            imagePublic_.setImageDrawable(mContext.getResources().getDrawable(R.drawable.announcegrya));
            imageUser.setImageDrawable(mContext.getResources().getDrawable(R.drawable.userpollblue));
            imageChangeFragment(new UserPolls());
        }
        else if(MApplication.getBoolean(this, Constants.FromEditProfile))
        {

            userPoll.setVisibility(View.GONE);
            publicPoll.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view visible
            imageAddComments.setVisibility(View.VISIBLE);
            //view visible
            imgProfile.setVisibility(View.VISIBLE);
            //image profile is visible
            if (imgProfile.getVisibility() == View.VISIBLE) {
                //   Getting the string from image url
                Bitmap image = db.getImageCache("profile_pic");
                if (image != null) {
                    imgProfile.setImageBitmap(image);
                } else {
                    String imageUrl = MApplication.getString(mContext, Constants.PROFILE_IMAGE);
                    Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                }
                //Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                //view visible
                imgEdit1.setVisibility(View.VISIBLE);
                imgEdit1.setImageResource(R.drawable.ic_edit_profile);
                //set background resource
                imageAddComments.setBackgroundResource(R.drawable.gradient);
                //view invisible
                frame1.setVisibility(View.VISIBLE);
                //text view done
                bDone.setVisibility(View.GONE);
                //view visible
                titleBarLeftMenu1.setVisibility(View.VISIBLE);
                //view invisible
                imgSearch.setVisibility(View.INVISIBLE);
                //setting the text as my profile
                toolbar.setVisibility(View.VISIBLE);
                txtTitle.setText(getResources().getString(R.string.activity_myprofile));
                //This view contains only create poll icon
                imageChangeFragment(new MyProfile());
            }
        }
        if (MApplication.getBoolean(this, "createpoll") && MApplication.getBoolean(this, "mypollfragment")) {
            imageChangeFragment(new MyPolls());
        }
    }

    @Override
    public void OnMyPollsFragment(String type, String id) {
        imageChangeFragment(new MyPolls());
    }

    @Override
    public void OnUserPollFragment(String type, String id) {
        imageChangeFragment(new UserPolls());
    }

    @Override
    public void OnCampaignPollFragment(String type, String id) {
        imageChangeFragment(new PublicPoll());
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null) {
            Fragment currFrag = manager.
                    findFragmentById(R.id.fragment2);
            if (itemHome != null && currFrag != null) {
                navigateBackPressed(currFrag);
            }
        }
    }

    private void navigateBackPressed(Fragment currFrag) {
        String selectedTag = currFrag.getTag();
        Log.e("selectedTag", selectedTag + "");
        if (selectedTag.equals(UserPolls.class.getName())) {
            layoutTop.setVisibility(View.GONE);
            //View visible
            btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            //View visible
            imgSearch.setVisibility(View.VISIBLE);
            //view visible
            frame1.setVisibility(View.GONE);
            //View visible
            // view visible
            imgProfile.setVisibility(View.GONE);
            //title
            title =  itemHome.findViewById(R.id.tv_title);
            //icon
            icon =  itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Polls");
            ll_tool.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
            //View visible
            userPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            publicPoll.setVisibility(View.GONE);

            Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
            userpolltext.setTypeface(faceRegular);
            announcementtextUser.setTypeface(faceRegular);
            announcementtextPublic.setTypeface(faceRegular);
        }
        else if(selectedTag.equals(Announcements.class.getName()))
        {
            layoutTop.setVisibility(View.GONE);
            //View visible
            btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            findViewById(R.id.imgEdit).setVisibility(View.GONE);
            //View visible
            imgSearch.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.GONE);
            //View visible
            // view visible
            imgProfile.setVisibility(View.GONE);
            //title
            title =  itemHome.findViewById(R.id.tv_title);
            //icon
            icon =  itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
            //View visible
            publicPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            userPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Announcements");
            ll_tool.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
            //View visible
            publicPoll.setVisibility(View.VISIBLE);
            Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
            userpolltext.setTypeface(faceRegular);
            announcementtextUser.setTypeface(faceRegular);
            announcementtextPublic.setTypeface(faceRegular);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            userPoll.setVisibility(View.GONE);
        }
        else if(selectedTag.equals(SearchAnnouncements.class.getName()))
        {
            //Toast.makeText(mContext, "This", Toast.LENGTH_SHORT).show();
            layoutTop.setVisibility(View.GONE);
            //View visible
            btnCreatePoll.setVisibility(View.GONE);
            //View visible
            imgMore.setVisibility(View.GONE);
            imageAddComments.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            imgEdit1.setVisibility(View.GONE);
            findViewById(R.id.imgEdit).setVisibility(View.GONE);
            //View visible
            imgSearch.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.GONE);
            //View visible
            // view visible
            imgProfile.setVisibility(View.GONE);
            //title
            title =  itemHome.findViewById(R.id.tv_title);
            //icon
            icon =  itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
            //View visible
            publicPoll.setVisibility(View.VISIBLE);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            userPoll.setVisibility(View.GONE);
            bDone.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            //setting the text
            mTitle.setText("Announcements");
            ll_tool.setVisibility(View.VISIBLE);
            search_layout.setVisibility(View.GONE);
            annouse_search.setVisibility(View.VISIBLE);
            //View visible
            publicPoll.setVisibility(View.VISIBLE);
            Typeface faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
            userpolltext.setTypeface(faceRegular);
            announcementtextUser.setTypeface(faceRegular);
            announcementtextPublic.setTypeface(faceRegular);
            //View gone
            chat.setVisibility(View.GONE);
            //View gone
            userPoll.setVisibility(View.GONE);
        }


        else if (selectedTag.equals(PublicPoll.class.getName())) {
            toolbar.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.GONE);
            //view gone
            imageAddComments.setVisibility(View.GONE);
            //image profile gone
            imgProfile.setVisibility(View.GONE);
            //image edit gone
            imgEdit1.setVisibility(View.GONE);
            //image more visiblity gone
            imgMore.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.GONE);
            //view visible
            btnCreatePoll.setVisibility(View.GONE);
            //view visible
            imgSearch.setVisibility(View.GONE);
            //view gone
            bDone.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);
            //View visible
            userPoll.setVisibility(View.GONE);
            //View gone
            chat.setVisibility(View.GONE);
            //title
            title = (TextView) itemHome.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
            //View gone
            publicPoll.setVisibility(View.VISIBLE);
            //view gone
            mTitle.setText("Campaigns");

        } else if (selectedTag.equals(HomeChat.class.getName())) {
            toolbar.setVisibility(View.VISIBLE);
            //view gone
            imageAddComments.setVisibility(View.GONE);
            //image profile gone
            imgProfile.setVisibility(View.GONE);
            titleBarLeftMenu1.setVisibility(View.GONE);

            imgEdit1.setVisibility(View.GONE);
            //image more visiblity gone
            imgMore.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.GONE);
            //view visible
            btnCreatePoll.setVisibility(View.GONE);
            //view visible
            imgSearch.setVisibility(View.GONE);
            //view gone
            bDone.setVisibility(View.GONE);
            //title
            title = (TextView) itemHome.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
            //View visible
            userPoll.setVisibility(View.GONE);
            //View gone
            chat.setVisibility(View.VISIBLE);
            //View gone
            publicPoll.setVisibility(View.GONE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            layoutTop.setVisibility(View.GONE);
            mTitle.setText("Chats");

        } else if (!selectedTag.equals(UserPolls.class.getName())) {
            title =  itemHome.findViewById(R.id.tv_title);
            //icon
            icon =  itemHome.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemHome.findViewById(R.id.view);
            //visiblity invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative
            relative =  itemHome.findViewById(R.id.relative);
            //title set text color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon set text color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //relative set text color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        if (selectedTag.equals(Category.class.getName())) {

            toolbar.setVisibility(View.GONE);

            layoutTop.setVisibility(View.VISIBLE);
            //Image gone
            imageAddComments.setVisibility(View.GONE);
            //Image profile
            imgProfile.setVisibility(View.GONE);
            //edit icon
            imgEdit1.setVisibility(View.GONE);
            //edit icon
            btnCreatePoll.setVisibility(View.GONE);
            //search icon
            imgSearch.setVisibility(View.GONE);
            //create poll more icon
            imgMore.setVisibility(View.GONE);
            //frame 1 visible
            frame1.setVisibility(View.VISIBLE);
            //text done visible
            bDone.setVisibility(View.VISIBLE);
            //image search invisble
            imgSearch.setVisibility(View.INVISIBLE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            //category
            txtTitle.setText(getResources().getString(R.string.activity_mycategory));
            // Default fragment
            //title
            title =  itemCategories.findViewById(R.id.tv_title);
            //icon
            icon =  itemCategories.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemCategories.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);

        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemCategories.findViewById(R.id.tv_title);
            //icon
            icon =  itemCategories.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemCategories.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemCategories.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }

        if (selectedTag.equals(MyPolls.class.getName())) {
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //visiblity gone
            imgMore.setVisibility(View.GONE);
            //visiblity gone
            imageAddComments.setVisibility(View.GONE);
            //visiblity gone
            imgProfile.setVisibility(View.GONE);
            //visiblity gone
            imgEdit1.setVisibility(View.GONE);
            //view visible
            frame1.setVisibility(View.VISIBLE);
            //view visible
            imgSearch.setVisibility(View.VISIBLE);
            //view visible
            findViewById(R.id.imgEdit2).setVisibility(View.VISIBLE);
            //view gone
            imgSearch.setVisibility(View.GONE);
            //view gone
            bDone.setVisibility(View.GONE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            //set the title as my polls
            txtTitle.setText(getResources().getString(R.string.activity_mypolls));
            //Set the boolean
            MApplication.setBoolean(this, "createpoll", false);
            //title
            title =  itemMyPolls.findViewById(R.id.tv_title);
            //icon
            icon =  itemMyPolls.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemMyPolls.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);

        } else if (!selectedTag.equals(MyPolls.class.getName())) {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemMyPolls.findViewById(R.id.tv_title);
            //icon
            icon =  itemMyPolls.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemMyPolls.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemMyPolls.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }

        if (selectedTag.equals(MyProfile.class.getName())) {
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view visible
            imageAddComments.setVisibility(View.VISIBLE);
            //view visible
            imgProfile.setVisibility(View.VISIBLE);
            //image profile is visible
            if (imgProfile.getVisibility() == View.VISIBLE) {
                //   Getting the string from image url

                Bitmap image = db.getImageCache("profile_pic");
                if (image != null) {
                    imgProfile.setImageBitmap(image);
                } else {
                    String imageUrl = MApplication.getString(mContext, Constants.PROFILE_IMAGE);
                    Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                }

//                Utils.loadImageWithGlideSingleImageRounderCorner(mContext, imageUrl, imgProfile, R.drawable.img_ic_user);
                //v1iew visible
                /*imgEdit1.setVisibility(View.VISIBLE);*/
                //set background resource
                imageAddComments.setBackgroundResource(R.drawable.gradient);
                //view invisible
                frame1.setVisibility(View.GONE);
                //text view done
                bDone.setVisibility(View.GONE);
                //view visible
                titleBarLeftMenu1.setVisibility(View.VISIBLE);
                //view invisible
                imgSearch.setVisibility(View.INVISIBLE);
                //setting the text as my profile
                txtTitle.setText(getResources().getString(R.string.activity_myprofile));
                //Set the boolean
                MApplication.setBoolean(this, "createpoll", false);
                //title
                title =  itemProfile.findViewById(R.id.tv_title);
                //icon
                icon =  itemProfile.findViewById(R.id.iv_icon);
                //divider line
                dividerLine = itemProfile.findViewById(R.id.view);
                //setting the text color
                title.setTextColor(getResources().getColor(R.color.blue_color));
                //Setting the icon color
                icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
                //divider line visible
                dividerLine.setVisibility(View.VISIBLE);

            }
        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemProfile.findViewById(R.id.tv_title);
            //icon
            icon =  itemProfile.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemProfile.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemProfile.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        if (selectedTag.equals(Category.class.getName())) {

            toolbar.setVisibility(View.GONE);

            layoutTop.setVisibility(View.VISIBLE);
            //Image gone
            imageAddComments.setVisibility(View.GONE);
            //Image profile
            imgProfile.setVisibility(View.GONE);
            //edit icon
            imgEdit1.setVisibility(View.GONE);
            //edit icon
            btnCreatePoll.setVisibility(View.GONE);
            //search icon
            imgSearch.setVisibility(View.GONE);
            //create poll more icon
            imgMore.setVisibility(View.GONE);
            //frame 1 visible
            frame1.setVisibility(View.VISIBLE);
            //text done visible
            bDone.setVisibility(View.VISIBLE);
            //image search invisible
            imgSearch.setVisibility(View.INVISIBLE);
            //view gone
            titleBarLeftMenu1.setVisibility(View.GONE);
            //category
            txtTitle.setText(getResources().getString(R.string.activity_mycategory));
            // Default fragment
            //title
            title =  itemCategories.findViewById(R.id.tv_title);
            //icon
            icon =  itemCategories.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemCategories.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);

        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemCategories.findViewById(R.id.tv_title);
            //icon
            icon =  itemCategories.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemCategories.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemCategories.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        if (selectedTag.equals(Settings.class.getName())) {
           /* toolbar.setVisibility(View.GONE);

            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //mTitle the title
            txtTitle.setText(getResources().getString(R.string.activity_settings));
            // Default fragment
            //title
            title = (TextView) itemSettings.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemSettings.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemSettings.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);*/

        } else {
/*
            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = (TextView) itemSettings.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemSettings.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemSettings.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative = (RelativeLayout) itemSettings.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));*/
        }
        if (selectedTag.equals(AboutUs.class.getName())) {
            toolbar.setVisibility(View.GONE);
            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText(getResources().getString(R.string.activity_about_app));
            //title
            title = (TextView) itemAboutUs.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemAboutUs.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemAboutUs.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemAboutUs.findViewById(R.id.tv_title);
            //icon
            icon =  itemAboutUs.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemAboutUs.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemAboutUs.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }

     /*   if (selectedTag.equals(Feedback.class.getName())) {
            toolbar.setVisibility(View.GONE);

            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText(getResources().getString(R.string.activity_feedback));
            //title
            title = (TextView) itemFeedback.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemFeedback.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemFeedback.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title = (TextView) itemFeedback.findViewById(R.id.tv_title);
            //icon
            icon = (ImageView) itemFeedback.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemFeedback.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative = (RelativeLayout) itemFeedback.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }*/
        if (selectedTag.equals(ContactUs.class.getName())) {
            toolbar.setVisibility(View.GONE);


            layoutTop.setVisibility(View.VISIBLE);
            //view are initialized
            viewsBinding();
            //setting the text
            txtTitle.setText(getResources().getString(R.string.activity_contactus));

            //title
            title =  itemContactUs.findViewById(R.id.tv_title);
            //icon
            icon =  itemContactUs.findViewById(R.id.iv_icon);
            //divider line
            dividerLine = itemContactUs.findViewById(R.id.view);
            //setting the text color
            title.setTextColor(getResources().getColor(R.color.blue_color));
            //Setting the icon color
            icon.setColorFilter(getResources().getColor(R.color.blue_color), PorterDuff.Mode.SRC_ATOP);
            //divider line visible
            dividerLine.setVisibility(View.VISIBLE);
        } else {

            layoutTop.setVisibility(View.VISIBLE);
            //title
            title =  itemContactUs.findViewById(R.id.tv_title);
            //icon
            icon =  itemContactUs.findViewById(R.id.iv_icon);
            //title color
            title.setTextColor(getResources().getColor(R.color.helvetica_light_greycolor));
            //icon color
            icon.setColorFilter(getResources().getColor(R.color.helvetica_light_greycolor));
            //divider line
            dividerLine = itemContactUs.findViewById(R.id.view);
            //divider line invisible
            dividerLine.setVisibility(View.INVISIBLE);
            //relative layout
            relative =  itemContactUs.findViewById(R.id.relative);
            //set background color
            relative.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
    }

    public void registerNotificationBadgeReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getChatViewUnseenCount != null && getPublicPollChatUnseenCount != null && getPrivatePollChatUnseenCount != null) {
                    getChatViewUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
                    getPublicPollChatUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
                    getPrivatePollChatUnseenCount.setText(mApplication.getStringFromPreference(Constants.GET_MESSAGE_UNREAD_COUNT));
                    getChatViewUnseenCount.setVisibility(View.VISIBLE);
                    getPublicPollChatUnseenCount.setVisibility(View.VISIBLE);
                    getPrivatePollChatUnseenCount.setVisibility(View.VISIBLE);
                }
            }
        }, new IntentFilter("get_chat_unread_count"));
    }

    public void removeChatUnseenCount() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getChatViewUnseenCount != null && getPublicPollChatUnseenCount != null && getPrivatePollChatUnseenCount != null) {
                    getChatViewUnseenCount.setVisibility(View.GONE);
                    getPublicPollChatUnseenCount.setVisibility(View.GONE);
                    getPrivatePollChatUnseenCount.setVisibility(View.GONE);
                    mApplication.storeStringInPreference(Constants.GET_MESSAGE_UNREAD_COUNT, "0");
                }
            }
        }, new IntentFilter("clear_unseen_count_chat"));
    }

    public void getPollCountDetails() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String userCount = intent.getStringExtra("user_count");
                String adminCount = intent.getStringExtra("admin_count");
                Log.d("get_count_details", userCount + "--" + adminCount);
                if (!userCount.equals("0")) {
                    getUserPollCount.setText(userCount);
                    getUserPollUnseenCountChatview.setText(userCount);
                    getUserPollCountPrivateView.setText(userCount);
                    //getUserPollCount.setVisibility(View.VISIBLE);
                    //getUserPollUnseenCountChatview.setVisibility(View.VISIBLE);
                    //getUserPollCountPrivateView.setVisibility(View.VISIBLE);
                } else {
                    getUserPollCount.setVisibility(View.GONE);
                    getUserPollUnseenCountChatview.setVisibility(View.GONE);
                    getUserPollCountPrivateView.setVisibility(View.GONE);
                }
                if (!adminCount.equals("0")) {
                    getAdminPollCountAdminView.setText(adminCount);
                    getAdminPollUnseenCountChatview.setText(adminCount);
                    getAdminPollCount.setText(adminCount);
                    //getAdminPollCountAdminView.setVisibility(View.VISIBLE);
                    //getAdminPollUnseenCountChatview.setVisibility(View.VISIBLE);
                    //getAdminPollCount.setVisibility(View.VISIBLE);
                } else {
                    getAdminPollCountAdminView.setVisibility(View.GONE);
                    getAdminPollUnseenCountChatview.setVisibility(View.GONE);
                    getAdminPollCount.setVisibility(View.GONE);
                }
            }
        }, new IntentFilter("get_user_poll_details"));
    }

    public void getAnnouncementCountDetails() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String userCount = intent.getStringExtra("ann_count");
                if (!userCount.equals("0")) {
                    getUserPollCount.setText(userCount);
                    getUserPollUnseenCountChatview.setText(userCount);
                    getUserPollCountPrivateView.setText(userCount);
                    //getUserPollCount.setVisibility(View.VISIBLE);
                    //getUserPollUnseenCountChatview.setVisibility(View.VISIBLE);
                    //getUserPollCountPrivateView.setVisibility(View.VISIBLE);
                } else {
                    getUserPollCount.setVisibility(View.GONE);
                    getUserPollUnseenCountChatview.setVisibility(View.GONE);
                    getUserPollCountPrivateView.setVisibility(View.GONE);
                }
            }
        }, new IntentFilter("get_user_poll_details"));
    }

    @Override
    public void replaceFragment() {
        imageChangeFragment(new Announcements());
    }
}
