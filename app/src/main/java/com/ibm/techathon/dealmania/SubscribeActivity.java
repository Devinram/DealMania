package com.ibm.techathon.dealmania;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.ibm.mobile.services.data.IBMDataObject;

import bolts.Continuation;
import bolts.Task;

public class SubscribeActivity extends Activity implements OnClickListener {

    private String[] category = { "Restaurant", "Cloths", "Mobile", "Laptops" };

    Spinner spinnerCategory;
    private EditText txtFromDate;
    private EditText txtToDate;
    private EditText txtVendor;
    private EditText txtLocation;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private  String USER_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        USER_NAME = getIntent().getStringExtra("USER_NAME");

        spinnerCategory = (Spinner) findViewById(R.id.subscribe_category);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter_state);

        txtVendor = (EditText) findViewById(R.id.subscribe_vendor);
        txtFromDate = (EditText) findViewById(R.id.subscribe_from_date);
        txtToDate = (EditText) findViewById(R.id.subscribe_to_date);
        txtLocation = (EditText) findViewById(R.id.subscribe_location);

        setDateTimeField();

        Button mSubmitButton = (Button) findViewById(R.id.subscribe_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postSubscribe();
            }
        });
    }

    private void postSubscribe() {
        IBMDataObject subscribe = new Subscribes();
        String varCategory = spinnerCategory.getSelectedItem().toString();
        String varVendor= txtVendor.getText().toString();
        String varLocation= txtLocation.getText().toString();
        String varValidFrom= txtFromDate.getText().toString();
        String varValidTo= txtToDate.getText().toString();

        subscribe.setObject("category",varCategory);
        subscribe.setObject("vendor",varVendor);
        subscribe.setObject("location",varLocation);
        subscribe.setObject("validFrom",varValidFrom);
        subscribe.setObject("validTo",varValidTo);
        subscribe.setObject("userName", USER_NAME);

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
                if (!isFinishing()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Your subscription has been saved successfully";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return null;
            }
        });

        Context context = getApplicationContext();
        CharSequence text = "Your subscription has been saved successfully";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        // Call Matching Subscriptions
        Intent intent = new Intent(getBaseContext(), SubscribedOfferActivity.class);
        intent.putExtra("USER_NAME", USER_NAME);
        startActivity(intent);
    }

    private void setDateTimeField() {
        txtFromDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String convertedText = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                txtFromDate.setText(convertedText);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String convertedText = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                txtToDate.setText(convertedText);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == txtFromDate) {
            fromDatePickerDialog.show();
        } else if(view == txtToDate) {
            toDatePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscribe, menu);
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
