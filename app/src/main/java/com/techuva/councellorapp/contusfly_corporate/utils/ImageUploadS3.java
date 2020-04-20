/*
 * @category ContusMessanger
 * @package com.contusfly.utils
 * @version 1.0
 * @author ContusTeam <developers@contus.in>
 * @copyright Copyright (C) 2015 <Contus>. All rights reserved. 
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 */

package com.techuva.councellorapp.contusfly_corporate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.corporate.contus_Corporate.chatlib.listeners.OnTaskCompleted;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.UUID;
/*class ImageUploadS3 */

/**
 * The Class ImageUploadS3.
 */
public class ImageUploadS3 {

    private Context mContext;
    /** The s3 client. */
    private AmazonS3Client s3Client;


    /** The task completed. */
    private OnTaskCompleted taskCompleted;

    /** The en code img. */
    private String bucketName, type, enCodeImg,folder;

    String Imagetype;

    /** The length of file. */
    private int lengthOfFile;

    /**
     * Instantiates a new image upload s3.
     *
     */
    public ImageUploadS3(Context context) {
        mContext = context;
        InitialSetup();
    }
    /**
     * Uploading callback.
     *
     * @param taskCompleted
     *            the task completed
     */
    public void uplodingCallback(OnTaskCompleted taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    /* Async task to upload images to s3 */

    /**
     * Execute upload.
     *
     * @param file
     *            the file
     * @param type
     *            the type
     * @param encodeImag
     *            the encode image
     */
    public void executeUpload(File file, String type, String encodeImag, String folder) {
        this.type = type;
        this.folder=folder;
        this.enCodeImg = encodeImag;
        new S3PutObjectTask().execute(file);
    }



    /**
     * Initial setup.
     */
    public void InitialSetup() {

        SharedPreferences prefs = mContext.getSharedPreferences("KEYS", Context.MODE_PRIVATE);
        bucketName = prefs.getString("bucket", "");
        //bucketName = "corp-techuva";
        s3Client = new AmazonS3Client(new BasicAWSCredentials(
                prefs.getString("access_key", ""),
                prefs.getString("secret_key", "")));
        s3Client.setEndpoint("s3.amazonaws.com");

    }
    //https://s3-us-west-2.amazonaws.com/thinqapp/

    /* class for S3PutObjectTask perform Async task for image uploading */

    /**
     * The Class S3PutObjectTask.
     */
    public class S3PutObjectTask extends AsyncTask<File, Integer, S3TaskResult> {

        /** The id value. */
        String idValue;
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            LogMessage.v("Image Upload::::", "::progress::" + values[0]);
        }
        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
         */
        protected S3TaskResult doInBackground(File... params) {
            S3TaskResult result = null;
            try {
                result = new S3TaskResult();
                String uuid = UUID.randomUUID().toString();
                if ("image".equalsIgnoreCase(type)) {

                    java.util.Date dt = new java.util.Date();
                    String FOLDER_TYPE = folder ; //[folders are type POLLS, PROFILES]
                    String fileName =FOLDER_TYPE + Calendar.getInstance().get(Calendar.YEAR) +"" +
                            (Calendar.getInstance().get(Calendar.MONTH)+1) +"" +
                            (Calendar.getInstance().get(Calendar.DATE)) +"_" +
                            dt.getTime() + ".jpg";
                    idValue=fileName;
                    //idValue = (uuid + "_" + System.currentTimeMillis() / 1000)
                          //  + ".jpg";
                } else if ("audio".equalsIgnoreCase(type)) {
                    idValue = (uuid + "_" + System.currentTimeMillis() / 1000)
                            + ".aac";
                } else if ("video".equalsIgnoreCase(type)) {
                    idValue = (uuid + "_" + System.currentTimeMillis() / 1000)
                            + ".mp4";
                }
                PutObjectRequest por = new PutObjectRequest(bucketName,
                        idValue, params[0]);
                por.setCannedAcl(CannedAccessControlList.PublicRead);
                try {
                    s3Client.putObject(por);
                }catch (Exception e){
                    Log.d("incatch",e.toString());
                }
                ResponseHeaderOverrides override = new ResponseHeaderOverrides();
                if (type.equalsIgnoreCase(Constants.MSG_TYPE_VIDEO)) {
                    override.setContentType("video/mp4");
                } else if (type.equalsIgnoreCase(Constants.MSG_TYPE_IMAGE)) {
                    override.setContentType("image/jpg");
                }
                GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                        bucketName, idValue);
                urlRequest.setResponseHeaders(override);
                URL url = s3Client.getUrl(bucketName, idValue);
                if (url != null) {
                    result.setUrl(url);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    lengthOfFile = connection.getContentLength();
                }
            } catch (AmazonServiceException e) {
                throw new RuntimeException(e);
            } catch (AmazonClientException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (OutOfMemoryError e) {
                throw new RuntimeException(e);
            }

            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @SuppressWarnings("unused")
        @Override
        protected void onPostExecute(S3TaskResult result) {
            super.onPostExecute(result);
            if (taskCompleted != null) {
                if (result.getUrl() != null) {
                    taskCompleted.onTaskCompleted(result.getUrl(), type,
                            enCodeImg, lengthOfFile);
                } else {
                    taskCompleted.onTaskCompleted(null, null, null, 0);
                }
            }
        }
    }

    /**
     * The Class S3TaskResult.
     */
    class S3TaskResult {

        /** The uri. */
        private URL uri = null;
        /*
         * method for get the url amazon s3
         */
        /**
         * Gets the url.
         *
         * @return the url
         */
        public URL getUrl() {
            return uri;
        }

        /*
         * method for set the url amazon s3
         */

        /**
         * Sets the url.
         * @param uri
         * the new url
         */
        public void setUrl(URL uri) {
            this.uri = uri;
        }

    }

}
