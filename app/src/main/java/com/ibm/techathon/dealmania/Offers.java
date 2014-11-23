package com.ibm.techathon.dealmania;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("Offers")
public class Offers extends IBMDataObject {
    public static final String CLASS_NAME = "Offers";
    private static final String CATEGORY = "category";
    private static final String LOCATION = "location";
    private static final String VENDOR = "vendor";
    private static final String VALID_FROM = "validFrom";
    private static final String VALID_TO = "validTo";
    private static final String COUPON = "coupon";

    public String getCategory() {
        return (String) getObject(CATEGORY);
    }
    public void setCategory(String itemCategory) {
        setObject(CATEGORY, (itemCategory != null) ? itemCategory : "");
    }

    public String getVendor() {
        return (String) getObject(VENDOR);
    }
    public void setVendor(String itemVendor) {
        setObject(VENDOR, (itemVendor != null) ? itemVendor : "");
    }

    public String getLocation() {
        return (String) getObject(LOCATION);
    }
    public void setLocation(String itemLocation) {
        setObject(LOCATION, (itemLocation != null) ? itemLocation : "");
    }

    public String getValidFrom() {
        return (String) getObject(VALID_FROM);
    }
    public void setValidFrom(String itemValidFrom) {
        setObject(VALID_FROM, (itemValidFrom != null) ? itemValidFrom : "");
    }

    public String getValidTo() {
        return (String) getObject(VALID_TO);
    }
    public void setValidTo(String itemValidTo) {
        setObject(VALID_TO, (itemValidTo != null) ? itemValidTo : "");
    }

    public String getCoupon() {
        return (String) getObject(COUPON);
    }
    public void setCoupon(String itemCoupon) {
        setObject(COUPON, (itemCoupon != null) ? itemCoupon : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     * @return String theItemName
     */
    public String toString() {
        String theItemName = "";
        //theItemName = getCategory() + ", " + getVendor() + ", " + getValidFrom() + ", " + getValidTo() + ", " + getCoupon();
        theItemName = getCoupon() + " on " + getVendor() + " at " + getLocation();

        if (getValidFrom() != null && !getValidFrom().equals("") && !getValidFrom().equalsIgnoreCase("null")) {
            theItemName = theItemName + ", offers valid from " + getValidFrom() + " to " + getValidTo();
        }
        return theItemName;
    }
}
