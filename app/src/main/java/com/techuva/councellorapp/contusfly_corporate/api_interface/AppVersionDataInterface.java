package com.techuva.councellorapp.contusfly_corporate.api_interface;

import com.google.gson.JsonElement;
import com.techuva.councellorapp.contusfly_corporate.model.AppVersionPostParameters;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.new_changes_corporate.post_parameters.SignupPostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface AppVersionDataInterface {

    @POST(Constants.APP_VERSION_CHECK)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<JsonElement> getStringScalar(@Header("authKey") String headerValue, @Body AppVersionPostParameters postParameter);


    @POST(Constants.SIGNUP_NEW_USER)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
    Call<JsonElement> newUserSignup(@Body SignupPostParameters postParameter);

}
