package com.ibm.techathon.dealmania;

import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class NewListAdapter extends ArrayAdapter <Offers> {

    private Context context;
    private List<Offers> mItems;
    private String userName;

    public NewListAdapter(Context context, List<Offers> items, String userName) {
        super(context, -1, items);
        this.context = context;
        this.mItems = items;
        this.userName = userName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom view
        LayoutInflater mInflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.list_item_3, null);

        final Offers offerItem = (Offers) mItems.get(position);

        // Set the Date text
        ((TextView) convertView.findViewById(R.id.coupon_details)).setText(offerItem.toString());

        ((Button) convertView.findViewById(R.id.btn_share_coupon)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, "Coupon details is: " + offerItem.getCoupon(), duration);
//                toast.show();

//                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to share this coupon?");

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(context, CouponSharingActivity.class);
                        intent.putExtra("USER_NAME", userName);
                        intent.putExtra("COUPON_DETAILS", offerItem.toString());
                        context.startActivity(intent);
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });
        return convertView;
    }
}

