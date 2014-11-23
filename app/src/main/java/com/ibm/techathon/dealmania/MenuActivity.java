package com.ibm.techathon.dealmania;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

public class MenuActivity extends Activity {

    private  String USER_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        USER_NAME = getIntent().getStringExtra("USER_NAME");

        Button mSubscribeButton = (Button) findViewById(R.id.menu_subscribe_button);
        mSubscribeButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SubscribeActivity.class);
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

        Button mSubscribedSearchButton = (Button) findViewById(R.id.menu_subscribe_search_button);
        mSubscribedSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SubscribedOfferActivity.class);
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

        Button mPostButton = (Button) findViewById(R.id.menu_post_button);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PostActivity.class);//
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

        Button mSharedButton = (Button) findViewById(R.id.menu_show_shared);
        mSharedButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ShowSharedCouponsActivity.class);
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

        Button mSearchOffers = (Button) findViewById(R.id.menu_search_offers);
        mSearchOffers .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ShowCouponnsActivity.class);
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

//        Button mSearchButton = (Button) findViewById(R.id.menu_search_button);
//        mSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), SearchCouponActivity.class);
//                intent.putExtra("USER_NAME", USER_NAME);
//                startActivity(intent);
//            }
//        });

        Button mNotifyButton = (Button) findViewById(R.id.menu_notify_button);
        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NotificationActivity.class);//
                intent.putExtra("USER_NAME", USER_NAME);
                startActivity(intent);
            }
        });

        // Get the message from the intent
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
//
//        // Create the text view
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(message);

        // Set the text view as the activity layout
       // setContentView(textView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
