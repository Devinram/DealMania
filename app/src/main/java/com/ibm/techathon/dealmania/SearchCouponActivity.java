package com.ibm.techathon.dealmania;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMObjectResult;
import com.ibm.mobile.services.data.IBMQuery;
import com.ibm.mobile.services.data.IBMQueryResult;
import bolts.Continuation;
import bolts.Task;
import android.util.Log;
import android.widget.Toast;


public class SearchCouponActivity extends Activity {
    DealManiaApplication dealApplication;
    List<Offers> offersList;
    ArrayAdapter<Offers> lvArrayAdapter;
    private static final String CLASS_NAME = SearchCouponActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coupon);

        /* Use application class to maintain global state. */
        dealApplication = (DealManiaApplication) getApplication();
        offersList = dealApplication.getOffersList();

        Log.i(CLASS_NAME, "Offers Size: " + offersList.size());

		/* Set up the array adapter for items list view. */
        ListView itemsLV = (ListView)findViewById(R.id.offersList);
        lvArrayAdapter = new ArrayAdapter<Offers>(this, R.layout.list_item_1, offersList);
        itemsLV.setAdapter(lvArrayAdapter);

		/* Refresh the list. */
        listItems();

        Button mSearchButton = (Button) findViewById(R.id.menu_search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listItems();
            }
        });
    }

    public void listItems() {
        try {
            showProgress(true);
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
                        offersList.clear();
                        for(IBMDataObject item:objects) {
                            offersList.add((Offers) item);
                        }
                        sortItems(offersList);
                        lvArrayAdapter.notifyDataSetChanged();
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
            showProgress(false);
        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
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
        getMenuInflater().inflate(R.menu.menu_search_coupon, menu);
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

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {
        Context context = getApplicationContext();
        CharSequence text = "Please wait...";
        if (!show) {
            text = "Process completed successfully";
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
