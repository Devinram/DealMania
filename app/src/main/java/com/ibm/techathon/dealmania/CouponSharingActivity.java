package com.ibm.techathon.dealmania;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataObject;

import bolts.Continuation;
import bolts.Task;


public class CouponSharingActivity extends Activity {
    private  String USER_NAME = "";

    private EditText txtCoupon;
    private EditText txtCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_sharing);

        USER_NAME = getIntent().getStringExtra("USER_NAME");
        String couponDetails = getIntent().getStringExtra("COUPON_DETAILS");

        txtCoupon = (EditText) findViewById(R.id.coupon_share_details);
        txtCoupon.setText(couponDetails);

        txtCount = (EditText) findViewById(R.id.coupon_share_count);

        Button mSubmitButton = (Button) findViewById(R.id.btn_coupon_share_save);
        mSubmitButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedCoupons();
            }
        });
    }

    private void saveSharedCoupons() {
        IBMDataObject sharedCoupon = new SharedCoupons();
        String varCoupon= txtCoupon.getText().toString();
        String varCount= txtCount.getText().toString();

        sharedCoupon.setObject("user",USER_NAME);
        sharedCoupon.setObject("coupon_details",varCoupon);
        sharedCoupon.setObject("count",varCount);

        // Use the IBMDataObject to create and persist the Item object
        sharedCoupon.save().continueWith(new Continuation<IBMDataObject, Void>() {
            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                // Log error message, if the save task fail.
                if (task.isFaulted()) {
                    return null;
                }
//                if (task.isCompleted()) {
                    Context context = getApplicationContext();
                    CharSequence text = "You have successfully shared the coupon";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Button mSharedButton = (Button) findViewById(R.id.menu_show_shared);
                    mSharedButton .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getBaseContext(), ShowSharedCouponsActivity.class);
                            intent.putExtra("USER_NAME", USER_NAME);
                            startActivity(intent);
                        }
                    });
//                }
//                If the result succeeds, load the list
                if (isFinishing()) {

                }
                return null;
            }
        });
        Context context = getApplicationContext();
        CharSequence text = "You have successfully shared the coupon";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Call Matching Subscriptions
        Intent intent = new Intent(getBaseContext(), ShowSharedCouponsActivity.class);
        intent.putExtra("USER_NAME", USER_NAME);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon_sharing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
