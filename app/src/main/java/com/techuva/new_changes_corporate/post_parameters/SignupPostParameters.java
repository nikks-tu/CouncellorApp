package com.techuva.new_changes_corporate.post_parameters;

public class SignupPostParameters {

    String name;
    String email;
    String mobile_number;
    String reason;

    public SignupPostParameters(String name, String email, String mobile_number, String reason) {
        this.name = name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.reason = reason;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }




}
