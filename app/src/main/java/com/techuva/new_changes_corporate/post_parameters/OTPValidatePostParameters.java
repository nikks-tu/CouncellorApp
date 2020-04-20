package com.techuva.new_changes_corporate.post_parameters;

public class OTPValidatePostParameters {


    private String action;
    private String phone_no;
    private String optcode;

    public OTPValidatePostParameters(String action, String phone_no, String optcode) {
        this.action = action;
        this.phone_no = phone_no;
        this.optcode = optcode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode;
    }




}
