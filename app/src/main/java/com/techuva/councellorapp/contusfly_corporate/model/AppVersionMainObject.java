package com.techuva.councellorapp.contusfly_corporate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppVersionMainObject {


    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("results")
    @Expose
    private String results;



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

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

}
