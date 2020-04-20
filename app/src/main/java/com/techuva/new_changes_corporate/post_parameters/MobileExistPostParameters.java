package com.techuva.new_changes_corporate.post_parameters;

public class MobileExistPostParameters {


    private String action;
    private String mobile_no;

    public MobileExistPostParameters(String action, String mobile_no) {
        this.action = action;
        this.mobile_no = mobile_no;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
