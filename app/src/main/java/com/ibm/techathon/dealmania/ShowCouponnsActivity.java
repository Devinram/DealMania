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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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


public class ShowCouponnsActivity extends Activity {
    private static final String CLASS_NAME = ShowSharedCouponsActivity.class.getSimpleName();
    private  String USER_NAME = "";
    Spinner spinnerCategory;
    private String[] category = { "Restaurant", "Cloths", "Mobile", "Laptops" };
    private EditText txtFromDate;
    private EditText txtToDate;
    private EditText txtVendor;
    private EditText txtCoupon;
    private EditText txtLocation;
    private ArrayAdapter<Offers> lvArrayAdapter;
    private List<Offers> offersList = new LinkedList<Offers>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_couponns);

        USER_NAME = getIntent().getStringExtra("USER_NAME");

        spinnerCategory = (Spinner) findViewById(R.id.show_coupon_category);
        ArrayAdapter avCatagory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        avCatagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(avCatagory);

        txtVendor = (EditText) findViewById(R.id.show_coupon_vendor);

        ListView itemsLV = (ListView)findViewById(R.id.show_coupon_list);
//        lvArrayAdapter = new ArrayAdapter<Offers>(this, R.layout.list_item_2, offersList);
        lvArrayAdapter = new ViewCouponListAdapter(this, offersList, USER_NAME);
        itemsLV.setAdapter(lvArrayAdapter);

        Button mSubmitButton = (Button) findViewById(R.id.btn_show_coupon);
        mSubmitButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOffers();
            }
        });
    }

    public void showMessage(final String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void showOffers() {
        try {
            showMessage("Please wait...");
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
                        showMessage("Still in progress, please wait...");
                        offersList.clear();
                        for(IBMDataObject item:objects) {
                            Offers varTempOffer = (Offers) item;
//                            Log.i("Test","varTempOffer.getCategory() : " + varTempOffer.getCategory());
//                            Log.i("Test","spinnerCategory.getSelectedItem().toString() : " + spinnerCategory.getSelectedItem().toString());
//                            Log.i("Test","varTempOffer.getVendor() : " + varTempOffer.getVendor());
//                            Log.i("Test","txtVendor.getText() : " + txtVendor.getText());
                            if (varTempOffer.getCategory().equalsIgnoreCase(spinnerCategory.getSelectedItem().toString()) &&
                                    varTempOffer.getVendor().equalsIgnoreCase(txtVendor.getText().toString())) {
                                offersList.add(varTempOffer);
                            }

                        }
                        sortItems(offersList);
                        lvArrayAdapter.notifyDataSetChanged();
                        showMessage("Process has been completed successfully");
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);
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
        getMenuInflater().inflate(R.menu.menu_show_couponns, menu);
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
