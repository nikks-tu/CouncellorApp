package com.techuva.new_changes_corporate.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOTPMainObject  {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("results")
    @Expose
    private CheckOtpResultObject results;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CheckOtpResultObject getResults() {
        return results;
    }

    public void setResults(CheckOtpResultObject results) {
        this.results = results;
    }

}