package com.ibm.techathon.dealmania;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataObject;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class ViewCouponListAdapter extends ArrayAdapter <Offers> {

    private Context context;
    private List<Offers> mItems;
    private String userName;
    private View tmpConvertView;

    public ViewCouponListAdapter(Context context, List<Offers> items, String userName) {
        super(context, -1, items);
        this.context = context;
        this.mItems = items;
        this.userName = userName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom view
        LayoutInflater mInflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_5, null);

        final Offers offersItem = (Offers) mItems.get(position);

        ((TextView) convertView.findViewById(R.id.li_show_coupon_category)).setText("Category: " + offersItem.getCategory());
        ((TextView) convertView.findViewById(R.id.li_show_coupon_vendor)).setText("Vendor: " + offersItem.getVendor());
        ((TextView) convertView.findViewById(R.id.li_show_coupon_start)).setText("Valid From: " + offersItem.getValidFrom());
        ((TextView) convertView.findViewById(R.id.li_show_coupon_end)).setText("Valid Till: " + offersItem.getValidTo());
        ((TextView) convertView.findViewById(R.id.li_show_coupon_details)).setText("Coupon Details: " + offersItem.getCoupon());

                ((Button) convertView.findViewById(R.id.li_btn_show_coupon_subscribe)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence text = "You have successfully subscribe this coupon";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        postSubscribe(offersItem);

                    }
                });

        return convertView;
    }

    private void postSubscribe(Offers offersItem) {
        IBMDataObject subscribe = new Subscribes();
        String varCategory = offersItem.getCategory();
        String varVendor= offersItem.getVendor();
        String varLocation= offersItem.getLocation();
        String varValidFrom= offersItem.getValidFrom();
        String varValidTo= offersItem.getValidTo();

        subscribe.setObject("category",varCategory);
        subscribe.setObject("vendor",varVendor);
        subscribe.setObject("location",varLocation);
        subscribe.setObject("validFrom",varValidFrom);
        subscribe.setObject("validTo",varValidTo);
        subscribe.setObject("userName", userName);

        Log.i(SubscribeActivity.class.getSimpleName(), "About to same the subscription, " + subscribe.toString());
        // Use the IBMDataObject to create and persist the Item object
        subscribe.save().continueWith(new Continuation<IBMDataObject, Void>() {
            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                // Log error message, if the save task fail.
                if (task.isFaulted()) {
                    return null;
                }
                // If the result succeeds, load the list
                return null;
            }
        });

        CharSequence text = "Your subscription has been saved successfully";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Call Matching Subscriptions
        Intent intent = new Intent(context, SubscribedOfferActivity.class);
        intent.putExtra("USER_NAME", userName);
        context.startActivity(intent);
    }

}

