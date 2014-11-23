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
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


public class SubscribedOfferActivity extends Activity {

    private List<Offers> listOfOffers = new LinkedList<Offers>();
    private List<Subscribes> listOfSubscribes = new LinkedList<Subscribes>();

    List<Offers> subscribedOffersList = new LinkedList<Offers>();
    ArrayAdapter<Offers> lvArrayAdapter;
    private static final String CLASS_NAME = SubscribedOfferActivity.class.getSimpleName();
    private  String USER_NAME = "";
    TextView txtCountLable ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_offer);
        Log.i(CLASS_NAME,"SubscribedOfferActivity.onCreate called");
        USER_NAME = getIntent().getStringExtra("USER_NAME");

//         /* Use application class to maintain global state. */
//        dealApplication = (DealManiaApplication) getApplication();
//        offersList = dealApplication.getOffersList();

//        Log.i(CLASS_NAME, "Offers Size: " + offersList.size());

		/* Set up the array adapter for items list view. */
        ListView itemsLV = (ListView)findViewById(R.id.subscribed_offers_list);
//        lvArrayAdapter = new ArrayAdapter<Offers>(this, R.layout.list_item_2, subscribedOffersList);
        lvArrayAdapter = new NewListAdapter(this, subscribedOffersList, USER_NAME);
        itemsLV.setAdapter(lvArrayAdapter);

        txtCountLable = (TextView)findViewById(R.id.total_count);;
		/* Refresh the list. */
        getOfferList();

        Button mSearchButton = (Button) findViewById(R.id.Button_subscribed_offers_refresh);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOfferList();
            }
        });


    }

    public void populateSubscribedOffers() {
        try {
            subscribedOffersList.clear();
            for (Subscribes sc : listOfSubscribes) {
                for (Offers of : listOfOffers) {
                    if (sc.getUserName().equalsIgnoreCase(USER_NAME) &&
                            sc.getCategory().equalsIgnoreCase(of.getCategory()) &&
                            sc.getVendor().equalsIgnoreCase(of.getVendor()) &&
                            sc.getLocation().equalsIgnoreCase(of.getLocation()) ) {
                        subscribedOffersList.add(of);
                    }
                }
            }
            Log.i(CLASS_NAME,"populateSubscribedOffers - subscribedOffersList - " + subscribedOffersList.size());
            txtCountLable.setText("You have " + subscribedOffersList.size() + " matching coupons");
            sortItems(subscribedOffersList);
            lvArrayAdapter.notifyDataSetChanged();
            showMessage("Offer has been retrieved successfully");
        }  catch (Exception error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
    }


    public List<Offers> getOfferList() {
        try {
            showMessage("Please wait...");
            listOfOffers.clear();
            IBMQuery<Offers> query = IBMQuery.queryForClass(Offers.class);
            // Query all the Item objects from the server

            query.find().continueWith(new Continuation<List<Offers>, Void>() {
                @Override
                public Void then(Task<List<Offers>> task) throws Exception {
                    final List<Offers> objects = task.getResult();
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
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.

                        for(IBMDataObject item:objects) {
                            listOfOffers.add((Offers) item);
                        }
                        Log.i(CLASS_NAME,"populateSubscribedOffers - listOfOffers - " + listOfOffers.size());
                        getSubscribedList();
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
        return listOfOffers;
    }

    public List<Subscribes> getSubscribedList() {
        try {
            showMessage("Still loading in progress...");
            listOfSubscribes.clear();
            IBMQuery<Subscribes> query = IBMQuery.queryForClass(Subscribes.class);
            // Query all the Item objects from the server

            query.find().continueWith(new Continuation<List<Subscribes>, Void>() {
                @Override
                public Void then(Task<List<Subscribes>> task) throws Exception {
                    final List<Subscribes> objects = task.getResult();
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
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        for(IBMDataObject item:objects) {
                            listOfSubscribes.add((Subscribes) item);
                        }
                        Log.i(CLASS_NAME,"populateSubscribedOffers - listOfSubscription - " + listOfSubscribes.size());
                        populateSubscribedOffers();
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
        return listOfSubscribes;
    }

    public void showMessage(final String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void sortItems(List<Offers> theList) {
        // Sort collection by case insensitive alphabetical order.
        Collections.sort(theList, new Comparator<Offers>() {
            public int compare(Offers lhs, Offers rhs) {
                String lhsName = lhs.getCategory();
                String rhsName = rhs.getCategory();
                return lhsName.compareToIgnoreCase(rhsName);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscribed_offer, menu);
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
