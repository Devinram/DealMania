package com.ibm.techathon.dealmania;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("SharedCoupons")
public class SharedCoupons extends IBMDataObject {
    public static final String CLASS_NAME = "SharedCoupons";
    private static final String S_NO = "s_no";
    private static final String USER = "user";
    private static final String SHARED_WITH = "shared_with";
    private static final String COUPON_DETAILS = "coupon_details";
    private static final String COUNT = "count";
    private static final String ACCEPTED_USERS = "accepted_users";

    public String getSno() {
        return (String) getObject(S_NO);
    }
    public void setSno(String itemSno) {
        setObject(S_NO, (itemSno != null) ? itemSno : "");
    }

    public String getUser() {
        return (String) getObject(USER);
    }
    public void setUser(String itemUser) {
        setObject(USER, (itemUser != null) ? itemUser : "");
    }

    public String getSharedWith() {
        return (String) getObject(SHARED_WITH);
    }
    public void setSharedWith(String itemSharedWith) {
        setObject(SHARED_WITH, (itemSharedWith != null) ? itemSharedWith : "");
    }

    public String getCouponDetails() {
        return (String) getObject(COUPON_DETAILS);
    }
    public void setCouponDetails(String itemCouponDetails) {
        setObject(COUPON_DETAILS, (itemCouponDetails != null) ? itemCouponDetails : "");
    }

    public String getCount() {
        return (String) getObject(COUNT);
    }
    public void setCount(String itemCount) {
        setObject(COUNT, (itemCount != null) ? itemCount : "");
    }

    public String getAcceptedUsers() {
        return (String) getObject(ACCEPTED_USERS);
    }
    public void setAcceptedUsers(String itemAcceptedUsers) {
        setObject(ACCEPTED_USERS, (itemAcceptedUsers != null) ? itemAcceptedUsers : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     * @return String theItemName
     */
    public String toString() {
        String theItemName = "";
        //theItemName = getCategory() + ", " + getVendor() + ", " + getValidFrom() + ", " + getValidTo() + ", " + getCoupon();
//        theItemName = getCoupon() + " on " + getVendor() + " at " + getLocation();
//
//        if (getValidFrom() != null && !getValidFrom().equals("") && !getValidFrom().equalsIgnoreCase("null")) {
//            theItemName = theItemName + ", offers valid from " + getValidFrom() + " to " + getValidTo();
//        }
        return theItemName;
    }
}
