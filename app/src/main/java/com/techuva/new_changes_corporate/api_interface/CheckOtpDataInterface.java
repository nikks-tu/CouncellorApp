package com.techuva.new_changes_corporate.api_interface;


import com.google.gson.JsonElement;
import com.techuva.councellorapp.contus_Corporate.app.Constants;
import com.techuva.new_changes_corporate.post_parameters.OTPValidatePostParameters;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CheckOtpDataInterface {

    @POST("api/v1"+ Constants.SMS_VERIFY_API)
   // Call<CurrentDataMainObject> fetchCurrentData(@Part("deviceId") String deviceId, @Part("userId") String userId);
   // Call<CheckOTPMainObject> getStringScalar(@Body OTPValidatePostParameters postParameter);
    Call<JsonElement> getStringScalar(@Body OTPValidatePostParameters postParameter);

}
