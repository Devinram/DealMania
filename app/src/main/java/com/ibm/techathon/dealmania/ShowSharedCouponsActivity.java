package com.ibm.techathon.dealmania;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class ShowSharedCouponsActivity extends Activity {
    private static final String CLASS_NAME = ShowSharedCouponsActivity.class.getSimpleName();
    private  String USER_NAME = "";

    private List<SharedCoupons> listOfSharedCoupons = new LinkedList<SharedCoupons>();
    ArrayAdapter<SharedCoupons> lvArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shared_coupons);

        USER_NAME = getIntent().getStringExtra("USER_NAME");

        ListView itemsLV = (ListView)findViewById(R.id.shared_coupons_list);
        lvArrayAdapter = new SharedCouponListAdapter(this, listOfSharedCoupons, USER_NAME);
        itemsLV.setAdapter(lvArrayAdapter);

        Button mSearchButton = (Button) findViewById(R.id.btn_shared_coupons_refresh);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateSharedCoupons();
            }
        });

        populateSharedCoupons();
    }

    public void populateSharedCoupons() {
        try {
            showMessage("Please wait...");
            listOfSharedCoupons.clear();
            IBMQuery<SharedCoupons> query = IBMQuery.queryForClass(SharedCoupons.class);
            // Query all the Item objects from the server

            query.find().continueWith(new Continuation<List<SharedCoupons>, Void>() {
                @Override
                public Void then(Task<List<SharedCoupons>> task) throws Exception {
                    final List<SharedCoupons> objects = task.getResult();
                    // Log if the find was cancelled.
                    if (task.isCancelled()){
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the find task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }
                    // If the result succeeds, load the list.
                    else {
                        Log.i(CLASS_NAME,"ShowSharedCouponsActivity - Came her " + objects.size());
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        for(IBMDataObject item:objects) {
                            SharedCoupons tempItem = (SharedCoupons) item;
                            try {
//                                if (Integer.parseInt(tempItem.getCount()) > 0)
                                    listOfSharedCoupons.add(tempItem);
                            }
                            catch (Exception ex) {}
                        }
                        lvArrayAdapter.notifyDataSetChanged();
                        Log.i(CLASS_NAME,"ShowSharedCouponsActivity - listOfSharedCoupons - " + listOfSharedCoupons.size());
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
    }

    public void showMessage(final String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_shared_coupons, menu);
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
