package com.techuva.councellorapp.contus_Corporate.apiinterface;


import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.councellorapp.contus_Corporate.responsemodel.UserPollResponseModel;
import com.techuva.councellorapp.contusfly_corporate.model.PollUnseenStatusModel;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by user on 10/6/2015.
 */
public interface UserPollApiInterface {
    // annotation used to post the data
    @Multipart
    @POST(Constants.USER_POLL_API)
//An object can be specified for use as an HTTP request body with the @Body annotation.
    //Request for user polls
    void postUserApi(@Part(Constants.ACTION) String action, @Part(Constants.PAGE) int page, @Part(Constants.USER_ID) String userId, @Part(Constants.DEVICE_ID) String deviceId, Callback<UserPollResponseModel> callback);

    @Multipart
    @POST(Constants.POLL_UNSEEN_API)
//An object can be specified for use as an HTTP request body with the @Body annotation.
        //Request for user polls
    void postUnseenCount(@Part(Constants.ACTION) String action, @Part(Constants.USER_ID) String userId, @Part("type") String type, Callback<PollUnseenStatusModel> callback);

    // annotation used to post the data
    @Multipart
    @POST(Constants.MYPOLL_API)
//An object can be specified for use as an HTTP request body with the @Body annotation.
    //Request for my polls
    void postMyPollApi(@Part(Constants.ACTION) String action, @Part(Constants.PAGE) int page, @Part(Constants.USER_ID) String userId, Callback<UserPollResponseModel> callback);
}
