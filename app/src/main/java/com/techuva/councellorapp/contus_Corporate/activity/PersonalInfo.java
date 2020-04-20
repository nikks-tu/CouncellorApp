/**
 * Personal Info.java
 * <p/>
 * Getting the country and code from response
 *
 * @category Contus
 * @package com.contus.activity
 * @version 1.0
 * @author Contus Team <developers@contus.in>
 * @copyright Copyright (C) 2015 Contus. All rights reserved.
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */


package com.techuva.councellorapp.contus_Corporate.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.corporate.contus_Corporate.chatlib.listeners.OnTaskCompleted;
import com.corporate.contus_Corporate.chatlib.utils.ContusConstantValues;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.techuva.councellorapp.R;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.residemenu.MenuActivity;
import com.techuva.councellorapp.contus_Corporate.responsemodel.ContactResponseModel;
import com.techuva.councellorapp.contus_Corporate.responsemodel.PersonalInfoModel;
import com.techuva.councellorapp.contus_Corporate.restclient.ContactsRestClient;
import com.techuva.councellorapp.contus_Corporate.restclient.PersonalInfoRestClient;
import com.techuva.councellorapp.contusfly_corporate.MApplication;
import com.techuva.councellorapp.contusfly_corporate.activities.ActivityBase;
import com.techuva.councellorapp.contusfly_corporate.service.ChatService;
import com.techuva.councellorapp.contusfly_corporate.utils.ImageUploadS3;
import com.techuva.councellorapp.contusfly_corporate.utils.Utils;
import com.techuva.councellorapp.contusfly_corporate.views.CircularImageView;
import com.techuva.councellorapp.contusfly_corporate.views.CustomToast;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.IS_CONTACTS_SYNCED;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.IS_LOGGED_IN;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.NULL_VALUE;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.SECRET_KEY;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.USERNAME;
import static com.techuva.councellorapp.contusfly_corporate.utils.Constants.USER_STATUS;

/**
 * Created by user on 6/30/2015.
 */
public class PersonalInfo extends ActivityBase implements Constants, OnTaskCompleted, Utils.ContactResult {
    /**Activity**/
    private Activity mPersonalInfo;
    /**Text view txtNext**/
    private TextView txtNext;
    /**Relative layout**/
    private RelativeLayout rootView;
    /**Edittext**/
    private EditText editUserName;
    /**View**/
    private View txtView;
    /*8Text view male**/
    private RadioButton txtMale;
    /**Textview female**/
    private RadioButton txtFemale;
    /**Image view profile image**/
    private CircularImageView imageView;
    /*8String user name**/
    private String userName;
    /**String edittext mLocation**/
    private TextView editLocation;
    /**String mLocation**/
    private String mLocation;
    /**String custom dialog**/
    private Dialog myPersonalInfolistDialog;
    /**String filepath of the image**/
    private File filepath;
    /**String mGenderPersonalInfo**/
    private RadioGroup mGenderPersonalInfo;
    /**String phone number**/
    private String phoneNumber;
    /*8String country code**/
    private String countryCode;
    /**String country id**/
    private String countryId;
    /**String phone code**/
    private String phoneCode;
    /*8String os version**/
    private int osVersion;
    /**Storing the os version in string**/
    private String mOsVersion;
    /**Uri of the image**/
    private Uri mImageCaptureUri;
    //file uri
    private Uri imageFileUri;
    //gender details
    private String mGenderDetails;
    //s3 image uploader
    private ImageUploadS3 imgTask;
    //image url
    private String imageUrl="";
    /** The m application. */
    private MApplication mApplication;
    /** The Constant DELAY_TIME. */
    private static final int DELAY_TIME = 5000;
    //password
    private String password;
    //device id
    private String deviceID;
    //chossen photo
    private String choosenPhoto;
    private String deviceCountryCode;
    private String userId;
    private ImageView imgCancel;
    private ImageView imgLocation;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private final int PERMISSION_ALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the fressco
        Fresco.initialize(PersonalInfo.this);
        setContentView(R.layout.activity_profile);
        /**View are creating when the activity is started**/
        init();
    }

    /**
     * Instantiate the method
     */
    private void init() {
        /*8Initializing the activity**/
        mPersonalInfo = this;
        mApplication = (MApplication) getApplication();
        /*8Initializing the views**/
        txtNext = (TextView) findViewById(R.id.txtNext);
        editUserName = (EditText) findViewById(R.id.editUserName);
        rootView = (RelativeLayout) findViewById(R.id.rootlayout);
        txtView = findViewById(R.id.view3);
        txtMale = (RadioButton) findViewById(R.id.txtMale);
        txtFemale = (RadioButton) findViewById(R.id.txtFemale);
        mGenderPersonalInfo =(RadioGroup)findViewById(R.id.gender);
        imageView = (CircularImageView) findViewById(R.id.imageView);
        imgLocation = (ImageView) findViewById(R.id.imgLocation);
        imgCancel=(ImageView)findViewById(R.id.imgCancel);
        editLocation=(TextView) findViewById(R.id.editLocation);
        //Telephone manager
        TelephonyManager tm = (TelephonyManager)getSystemService(Activity.TELEPHONY_SERVICE);
        //device country code.
        deviceCountryCode = tm.getNetworkCountryIso();
        /**Getting the phoneNumber from shared prefernce and storing the value in string**/
        phoneNumber = MApplication.getString(mPersonalInfo, Constants.PHONE_NUMBER);
        /**Getting the countryCode from shared prefernce and storing the value in string**/
        countryCode = MApplication.getString(mPersonalInfo, Constants.COUNTRY_CODE);
        /**Getting the countryId from shared prefernce and storing the value in string**/
        countryId = MApplication.getString(mPersonalInfo, Constants.COUNTRY_ID);
        /**Getting the phoneCode from shared prefernce and storing the value in string**/
        phoneCode = MApplication.getString(mPersonalInfo, Constants.PHONE_NUMBER_CODE);
        /**Calling this method to hide the button when the keypad opens**/
        MApplication.hideButtonKeypadOpens(mPersonalInfo, rootView, txtNext, txtView);
        /**Os version**/
        osVersion = Build.VERSION.SDK_INT;
        /**Conversion the int value to string value**/
        mOsVersion = String.valueOf(osVersion);
        /**Setting the boolean**/
        MApplication.setBoolean(mPersonalInfo, Constants.PROFILE_IMAGE, false);
        /*8Setting the status bar color for lollipop version**/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MApplication.settingStatusBarColor(mPersonalInfo, getResources().getColor(android.R.color.black));
        }
        //Uploading an image in S3 bucket
        imgTask = new ImageUploadS3(getApplicationContext());
        //call back method
        imgTask.uplodingCallback(this);
        //nterface definition for a callback to be invoked when the checked state of a compound button changed.
        mGenderPersonalInfo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                editUserName.setCursorVisible(false);
                if (checkedId == R.id.txtMale) {
                    //Getting the value
                    mGenderDetails = String.valueOf(txtMale.getText());
                    //Setting the text color
                    txtMale.setTextColor(Color.BLACK);
                    //view color
                    txtFemale.setTextColor(getResources().getColor(R.color.view_color));
                } else if (checkedId == R.id.txtFemale) {
                    //Getting the value from the prefernce
                    mGenderDetails = String.valueOf(txtFemale.getText());
                    //Setting the text color
                    txtFemale.setTextColor(Color.BLACK);
                    //Setting the tedxt color
                    txtMale.setTextColor(getResources().getColor(R.color.view_color));
                }
            }
        });

        editUserName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                editUserName.setCursorVisible(true);
                return false;
            }
        });

        editUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                editUserName.setCursorVisible(false);
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(editUserName.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });


        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show alert dialog
                showAlertDialog(EMPTY_STRING,
                        getString(R.string.remove_the_photo),
                        getString(R.string.text_yes), getString(R.string.text_no));
            }
        });

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PersonalInfo.this, LocationActivity.class);
                startActivity(a);
            }
        });

    }

    /**
     * Calling this method from xml file when performing the click on the ACTION
     *
     * @param personalInfoClickAction
     */
    public void onClick(final View personalInfoClickAction) {
        if(personalInfoClickAction.getId()==R.id.txtNext){
            checkValidation();
        }else if(personalInfoClickAction.getId()==R.id.imageView){
            // Added by Nikita
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(!hasPermissions(this, PERMISSIONS)){
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                    //  processInsideSplash();
                }
                else
                {
                    choosePic();
                }
            }
            else
            {
                choosePic();
            }

        }else if(personalInfoClickAction.getId()==R.id.editLocation){
            Intent a = new Intent(PersonalInfo.this, LocationActivity.class);
            startActivity(a);
        }
    }


    //Added By Nikita
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
                else
                    return true;
            }
        }
        return true;
    }


    private void checkValidation() {
        /**Getting the value from edit text***/
        userName = editUserName.getText().toString().trim();
        /**Getting the value from edit text**/
        if (TextUtils.isEmpty(userName)) {
            //Toast message will display
            Toast.makeText(mPersonalInfo, getResources().getString(R.string.enter_your_username),
                    Toast.LENGTH_SHORT).show();
            //request focus
            editUserName.requestFocus();
        } else if (userName.length() < 2) {
            //Toast message will display
            Toast.makeText(mPersonalInfo, getResources().getString(R.string.enter_enter_valid_name),
                    Toast.LENGTH_SHORT).show();
            //requesting focus
            editUserName.requestFocus();
        } else if (!txtMale.isChecked()&&!txtFemale.isChecked()) {

            //Toast message will display requesting focus
            Toast.makeText(mPersonalInfo, getResources().getString(R.string.enter_select_gender),
                    Toast.LENGTH_SHORT).show();
            //empty
        } else if (TextUtils.isEmpty(mLocation)) {
            editUserName.setCursorVisible(false);
            //Toast message will display
            Toast.makeText(mPersonalInfo, getResources().getString(R.string.enter_select_location),
                    Toast.LENGTH_SHORT).show();
        } else {

                webRequest();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 15:
                if (resultCode == RESULT_OK) {
                    //file uri
                    mImageCaptureUri = data.getData();
                    //file path
                    filepath = new File(MApplication.getRealPathFromURI(mPersonalInfo, mImageCaptureUri));
                    //set boolean
                    MApplication.setBoolean(mPersonalInfo, Constants.PROFILE_IMAGE_TRUE, true);
                    //set image uri
                    Utils.loadImageWithGlide(PersonalInfo.this,filepath,imageView,R.drawable.img_ic_user);
                    imgCancel.setVisibility(View.VISIBLE);//visible
                    /** Calling the material design custom dialog **/
                    MApplication.materialdesignDialogStart(mPersonalInfo);
                    //s3 bucket
                    imgTask.executeUpload(filepath, "image", "","PROFILES/");
                }
                break;
            case 10:
                if (resultCode == RESULT_OK) {
                    try {
                        //file uri
                        mImageCaptureUri = data.getData();
                        //file path
                        filepath = new File(MApplication.getPath(mPersonalInfo, mImageCaptureUri));
                    } catch (URISyntaxException e) {
                        Log.e("","",e);
                    }
                    /*//An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent
                    // to send it to any interested BroadcastReceiver components, and startService(Intent)
                    Intent newIntent1 = new AviaryIntent.Builder(this)
                            .setData(mImageCaptureUri) // input image src
                            .withOutput(Uri.parse(Constants.pictureFile + filepath)) // output file
                            .withOutputFormat(Bitmap.CompressFormat.JPEG) // output format
                            .withOutputSize(MegaPixels.Mp5) // output size
                            .withOutputQuality(90) // output quality
                            .build();
                    // start the activity
                    startActivityForResult(newIntent1, 15);*/

                    MApplication.setBoolean(mPersonalInfo, Constants.PROFILE_IMAGE_TRUE, true);
                    //set image uri
                    Utils.loadImageWithGlide(PersonalInfo.this,filepath,imageView,R.drawable.img_ic_user);
                    imgCancel.setVisibility(View.VISIBLE);//visible
                    /** Calling the material design custom dialog **/
                    MApplication.materialdesignDialogStart(mPersonalInfo);
                    //s3 bucket
                    imgTask.executeUpload(filepath, "image", "","PROFILES/");
                }
                break;
            case 11:
                if (resultCode == Activity.RESULT_OK) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    //file path
                    filepath = new File(MApplication.getRealPathFromURI(mPersonalInfo, imageFileUri));
                    //An intent is an abstract description of an operation to be performed. It can be used with startActivity to launch an Activity, broadcastIntent
                    // to send it to any interested BroadcastReceiver components, and startService(Intent)
                   /* Intent newIntent1 = new AviaryIntent.Builder(this)
                            .setData(imageFileUri) // input image src
                            .withOutput(Uri.parse(Constants.pictureFile + filepath)) // output file
                            .withOutputFormat(Bitmap.CompressFormat.JPEG) // output format
                            .withOutputSize(MegaPixels.Mp5) // output size
                            .withOutputQuality(90) // output quality
                            .build();
                    // start the activity
                    startActivityForResult(newIntent1, 15);*/

                    MApplication.setBoolean(mPersonalInfo, Constants.PROFILE_IMAGE_TRUE, true);
                    //set image uri
                    Utils.loadImageWithGlide(PersonalInfo.this,filepath,imageView,R.drawable.img_ic_user);
                    imgCancel.setVisibility(View.VISIBLE);//visible
                    /** Calling the material design custom dialog **/
                    MApplication.materialdesignDialogStart(mPersonalInfo);
                    //s3 bucket
                    imgTask.executeUpload(filepath, "image", "","PROFILES/");
                }
                break;
            default:
                break;
        }

    }


    private void choosePic() {
        //A dialog is a small window that prompts the user to make a decision or enter additional information.
        myPersonalInfolistDialog = new Dialog(mPersonalInfo);
        //Enable extended window features.
        myPersonalInfolistDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Change the background of this window to a custom Drawable.
        myPersonalInfolistDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myPersonalInfolistDialog.setContentView(R.layout.custom_dialog_adapter);
        ListView list = (ListView) myPersonalInfolistDialog.findViewById(R.id.component_list);
        //plits this string using the supplied regularExpression
        String[] cameraOptions = new String[]{getResources().getString(R.string.take_photo),getResources().getString(R.string.choose_pic),getResources().getString(R.string.cancel_gallery)};
        //dilog adapter
        CustomDialogAdapter adapter = new CustomDialogAdapter(this, cameraOptions);
        list.setAdapter(adapter);
        //show
        myPersonalInfolistDialog.show();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        takePersonalImage();
                        break;
                    case 1:
                        //An intent is an abstract description of an operation to be performed.
                        // It can be used with startActivity to launch an Activity
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,getResources().getString(R.string.action_complete_using)), 10);
                        myPersonalInfolistDialog.cancel();
                        break;
                    case 2:
                        //cancel the dialog
                        myPersonalInfolistDialog.cancel();
                        break;
                    default:
                        break;
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
    /**
     * Take picture intent.
     */
    public void takePersonalImage() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //Returns the current state of the primary shared/external storage media.
        File folder = new File(Environment.getExternalStorageDirectory() + "/"
                + getResources().getString(R.string.app_name));
       //If folder does not exist
        if (!folder.exists()) {
            //create the folder
            folder.mkdir();
        }
        //Calendar is an abstract base class for converting between a Date object and a set of integer
        // fields such as YEAR, MONTH, DAY, HOUR, and so on. (A Date object represents a specific instant in time with millisecond precision.
        final Calendar c = Calendar.getInstance();
        //Storing the calended ate to the string
        String newDate = c.get(Calendar.DAY_OF_MONTH) + "-" + ((c.get(Calendar.MONTH)) + 1) + "-" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR) + "-" + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND);
        //Image name
        choosenPhoto = String.format(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name) + "/%s.png", "profilepic(" + newDate + ")");
        //Converting the bitmap into the fiel
        File photo = new File(choosenPhoto);
        // f set, the activity will not be launched if it is already running at the top of the history stack.
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //The name of the Intent-extra used to control the orientation of a ViewImage or a MovieView.
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //file uri
        imageFileUri= Uri.fromFile(photo);
        //image file uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageFileUri);
        startActivityForResult(intent, 11);
        //list dialog cancel
        myPersonalInfolistDialog.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MApplication.materialdesignDialogStop();
    }

    /**
     * Request and response api method
     */
    private void webRequest() {
        /**If internet connection is available**/
        if (MApplication.isNetConnected(mPersonalInfo)) {
            /** Calling the material design custom dialog **/
            MApplication.materialdesignDialogStart(mPersonalInfo);
            //Getting the latitiude from the prefernce
            String latitude = MApplication.getString(mPersonalInfo, Constants.LATITUDE);
            //Getting the latitiude from the prefernce
            String longitude = MApplication.getString(mPersonalInfo, Constants.LONGITUDE);
            //Getting the latitiude from the prefernce
            mLocation = MApplication.getString(mPersonalInfo, Constants.CITY);
            //gcm id
            //deviceID = com.contusfly.MApplication.getString(mPersonalInfo,Constants.GCM_REG_ID);
            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
             deviceID = pref.getString("regId", null);

            String CurrentString = imageUrl;
            String[] separated = CurrentString.split("com/");



            try {

                CurrentString = separated[1];

            }catch (Exception e){
                CurrentString = separated[0];
            }



            /**  Requesting the response from the given base url**/
                    PersonalInfoRestClient.getInstance().postProfileInfo(new String("appuserapi"), new String(phoneCode+phoneNumber),new String(userName), new String(mGenderDetails), new String(countryId), new String(phoneCode), new String(deviceID), new String(phoneNumber), new String(countryCode), new String("1"), new String(CurrentString), new String(latitude), new String(longitude), new String(mOsVersion), new String("Android"), new String(mLocation)
                            , new Callback<PersonalInfoModel>() {
                        @Override
                        public void success(PersonalInfoModel welcomeResponseModel, Response response) {
                            /**If response is success this method is called**/

                            if("1".equalsIgnoreCase(welcomeResponseModel.getSuccess())){
                                Log.d("onSucces",welcomeResponseModel.getSuccess());
                                personalInfo(welcomeResponseModel);

                            }else {
                                Log.d("onSucces",welcomeResponseModel.getSuccess());
                                onRegResponceError();
                            }



                        }
                        @Override
                        public void failure(RetrofitError retrofitError) {
                            MApplication.materialdesignDialogStop();//materiald dialog
                            MApplication.errorMessage(retrofitError, mPersonalInfo);//error message
                        }
                    });
                }
            else {
            //toast message will display
                Toast.makeText(mPersonalInfo, getResources().getString(R.string.check_internet_connection),
                        Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * The values from the response are retrived using model class
     *
     * @param welcomeResponseModel model class
     */
    private void personalInfo(PersonalInfoModel welcomeResponseModel) {
        /**welcome msg stored in model class are retrived**/
        String success = welcomeResponseModel.getSuccess();
        if (("1").equals(success)) {
            Log.d("onpersonalInfo==1",welcomeResponseModel.getSuccess());
            MApplication.setString(PersonalInfo.this, VIBRATION_TYPE, "0");
            //user id
            String userId = welcomeResponseModel.getResults().getUserId();
            //profile image
            String profileImage = welcomeResponseModel.getResults().getProfileImage();
            //access token
            String accessToken=welcomeResponseModel.getResults().getAccessToken();
            //password
           password=welcomeResponseModel.getResults().getPassword();
            //username
            userName = welcomeResponseModel.getResults().getUsername();
            //saving in prefernce
            MApplication.setString(mPersonalInfo, Constants.PROFILE_USER_NAME, userName);
            //saving in prefernce
            MApplication.setString(mPersonalInfo, Constants.PROFILE_IMAGE, profileImage);
            //saving in prefernce
            MApplication.setBoolean(mPersonalInfo, Constants.FIRST_TIME, true);
            //saving in prefernce
            MApplication.setString(mPersonalInfo, Constants.USER_ID, userId);
            //saving in prefernce
            MApplication.setString(mPersonalInfo, Constants.ACCESS_TOKEN, accessToken);
            MApplication.setBoolean(getApplicationContext(),"notification click",false);
            MApplication.setBoolean(getApplicationContext(),"contact_sync",true);
           //saving in prefernce
            mApplication.storeBooleanInPreference(IS_CONTACTS_SYNCED, true);
            //saving in prefernce
            mApplication.storeStringInPreference(USERNAME,phoneCode + phoneNumber);
            //saving in prefernce
            mApplication.storeStringInPreference("password",password);
            //stopping the progress bar
            MApplication.materialdesignDialogStop();

            startService(new Intent(mPersonalInfo, ChatService.class)
                    .putExtra("username", phoneCode + phoneNumber)
                    .putExtra("password", password)
                    .setAction(
                            ContusConstantValues.CONTUS_XMPP_ACTION_CONNECTION_REQUEST));
            Utils.getMobileContacts(this, this, deviceCountryCode);
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("fromActivityName","yes");
            //starting the activity
            startActivity(intent);

        }else {
            Log.d("onpersonalInfo==0",welcomeResponseModel.getSuccess());
            onRegResponceError();
        }

    }
public void onRegResponceError(){
    MApplication.materialdesignDialogStop();
    //Creates a builder for an alert dialog that uses the default alert dialog theme.
    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfo.this);
    //set message
    builder.setMessage("Somthing went wrong please try again later!");
    builder.setTitle("Network Error");
    builder.setNegativeButton("OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog
                    dialog.dismiss();
                }
            });
    builder.create().show();
}

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mLocation = MApplication.getString(mPersonalInfo, Constants.CITY);
        if (mLocation != null) {
            editLocation.setText(mLocation);
        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", imageFileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        imageFileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    public void onTaskCompleted(URL url, String s, String s1, int i) {
       imageUrl=url.toString();
       MApplication.materialdesignDialogStop();
    }
    @Override
    protected void handleLoggedIn() {
        try {
            mApplication.storeStringInPreference("user_name_chat",phoneCode + phoneNumber);
            mApplication.storeStringInPreference(SECRET_KEY, password);
            mApplication.storeStringInPreference(USER_STATUS,
                    getString(R.string.default_status));
            mApplication.storeBooleanInPreference(IS_LOGGED_IN, true);
            mApplication.storeStringInPreference(CHAT_ONGOING_NAME, NULL_VALUE);
            getRosterFromXMPP();
                    //material dialog stop
                    MApplication.materialdesignDialogStop();
            Intent intent = new Intent(getApplicationContext(),
                    MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            setResult(Activity.RESULT_FIRST_USER);
            finish();
        } catch (Exception e) {
            Log.e("",""+e);
        }
    }
    /**
     * Gets the roster from xmpp.
     *
     * @return the roster from xmpp
     */
    private void getRosterFromXMPP() {
        startService(new Intent(this, ChatService.class)
                .setAction(ContusConstantValues.CONTUS_XMPP_ACTION_GET_PROFILE));
        startService(new Intent(this, ChatService.class)
                .setAction(ContusConstantValues.CONTUS_XMPP_ACTION_GET_ROSTER));
    }

    @Override
    public void OnContactResult(String mobileNos) {
        Log.e("mobileNo","mobileNo"+"");
        if (mobileNos != null && !mobileNos.isEmpty()) {
            contactsLoad(mobileNos);
        } else {
            CustomToast.showToast(this,
                    getString(R.string.text_no_contacts_to_sync));
        }
    }

    /**
     * Request and response api method
     * @param contactsString
     * @param contactsString
     */
    private void contactsLoad(String contactsString) {
        Log.e("contactsString",contactsString+"");
        userId= MApplication.getString(this, USER_ID);
        if (MApplication.isNetConnected(PersonalInfo.this)) {
            /**  Requesting the response from the given base url**/
            ContactsRestClient.getInstance().postContactDetails(new String("contact_api"), new String(userId), new String(contactsString),
                    new Callback<ContactResponseModel>() {
                        @Override
                        public void success(ContactResponseModel contactResponseModel, retrofit.client.Response response) {
                           startService(new Intent(PersonalInfo.this, ChatService.class).setAction(ContusConstantValues.CONTUS_XMPP_ACTION_GET_ROSTER));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Refresh conatcts
                                    MApplication.setBoolean(PersonalInfo.this,"contact_sync",false);

                                }
                            }, 3000);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //Error message popups when the user cannot able to coonect with the server
                            MApplication.errorMessage(error,PersonalInfo.this);
                        }
                    });
        }
    }

    /**
     * Show alert dialog.
     *
     * @param title             the title
     * @param msg               the msg
     * @param positiveString    the positive string
     * @param negativeString    the negative string
     * @param
     * @param
     */
    public void showAlertDialog(String title, String msg, String positiveString, String negativeString) {
        //Creates a builder for an alert dialog that uses the default alert dialog theme.
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfo.this);
        //set message
        builder.setMessage(msg);
        if (!title.isEmpty())
            //setting the title
            builder.setTitle(title);
        builder.setNegativeButton(negativeString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton(positiveString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        imgCancel.setVisibility(View.INVISIBLE);
                        imageUrl="";
                        Utils.loadImageWithGlide(PersonalInfo.this,"",imageView,R.drawable.img_ic_user);
                        dialog.dismiss();
                    }
                });
        //create

        builder.create().show();
    }
}
