package com.ibm.techathon.dealmania;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SharedCouponListAdapter extends ArrayAdapter <SharedCoupons> {

    private Context context;
    private List<SharedCoupons> mItems;
    private String userName;
    private View tmpConvertView;

    public SharedCouponListAdapter(Context context, List<SharedCoupons> items, String userName) {
        super(context, -1, items);
        this.context = context;
        this.mItems = items;
        this.userName = userName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom view
        LayoutInflater mInflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_4, null);

        final SharedCoupons sharedCouponItem = (SharedCoupons) mItems.get(position);

        ((TextView) convertView.findViewById(R.id.coupon_details)).setText("Coupon Details: " + sharedCouponItem.getCouponDetails());
        ((TextView) convertView.findViewById(R.id.accepted_users)).setText("Accepted users: " + sharedCouponItem.getAcceptedUsers());
        ((TextView) convertView.findViewById(R.id.remaining_users)).setText("Remaining users: " + sharedCouponItem.getCount());
        ((TextView) convertView.findViewById(R.id.shared_person)).setText("Shared by: " + sharedCouponItem.getUser());

            if (sharedCouponItem.getUser().equalsIgnoreCase(userName) )
                ((Button) convertView.findViewById(R.id.btn_avail_coupon)).setVisibility(View.INVISIBLE);
            else {
                ((Button) convertView.findViewById(R.id.btn_avail_coupon)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence text = "You have successfully avail the coupon with " + sharedCouponItem.getUser();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        ((Button) view.findViewById(R.id.btn_avail_coupon)).setVisibility(View.INVISIBLE);

                    }
                });
            }
        return convertView;
    }
}

