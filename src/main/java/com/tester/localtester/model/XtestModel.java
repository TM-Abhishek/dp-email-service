package com.tester.localtester.model;

import java.util.Map;

public class XtestModel {
    public String pincode;
    public Map<String, Map<String, Boolean>> plans;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Map<String, Map<String, Boolean>> getPlans() {
        return plans;
    }

    public void setPlans(Map<String, Map<String, Boolean>> plans) {
        this.plans = plans;
    }
}
