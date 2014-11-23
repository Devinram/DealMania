package com.ibm.techathon.dealmania;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.DatePicker;
import android.widget.EditText;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.text.InputType;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.content.Context;
import android.widget.Toast;
import com.ibm.mobile.services.data.IBMDataObject;
import bolts.Continuation;
import bolts.Task;


public class PostActivity extends Activity implements OnClickListener {
    Spinner spinnerCategory;
    TextView selVersion;
    private String[] category = { "Restaurant", "Cloths", "Mobile", "Laptops" };

    private EditText txtFromDate;
    private EditText txtToDate;
    private EditText txtVendor;
    private EditText txtCoupon;
    private EditText txtLocation;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private  String USER_NAME = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        USER_NAME = getIntent().getStringExtra("USER_NAME");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        spinnerCategory = (Spinner) findViewById(R.id.post_category);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter_state);

        txtVendor = (EditText) findViewById(R.id.post_vendor);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        dateFormatter.setLenient(false);

        txtFromDate = (EditText) findViewById(R.id.post_from_date);
        txtToDate = (EditText) findViewById(R.id.post_to_date);
        txtCoupon = (EditText) findViewById(R.id.post_coupon);
        txtLocation = (EditText) findViewById(R.id.post_location);

        setDateTimeField();

        Button mSubmitButton = (Button) findViewById(R.id.post_submit_button);
        mSubmitButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOffers();
            }
        });
    }

    private void postOffers() {
        IBMDataObject offer = new Offers();
        String varCategory = spinnerCategory.getSelectedItem().toString();
        String varVendor= txtVendor.getText().toString();
        String varLocation= txtLocation.getText().toString();
        String varValidFrom= txtFromDate.getText().toString();
        String varValidTo= txtToDate.getText().toString();
        String varCoupon = txtCoupon.getText().toString();

        offer.setObject("category",varCategory);
        offer.setObject("vendor",varVendor);
        offer.setObject("location",varLocation);
        offer.setObject("validFrom",varValidFrom);
        offer.setObject("validTo",varValidTo);
        offer.setObject("coupon",varCoupon);

        // Use the IBMDataObject to create and persist the Item object
        offer.save().continueWith(new Continuation<IBMDataObject, Void>() {
            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                // Log error message, if the save task fail.
                if (task.isFaulted()) {
                    return null;
                }
                // If the result succeeds, load the list
                if (!isFinishing()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Offer saved successfully";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return null;
            }
        });
    }
    private void setDateTimeField() {
        txtFromDate.setOnClickListener(this);
        txtToDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String convertedText = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                txtFromDate.setText(convertedText);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

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
        getMenuInflater().inflate(R.menu.menu_post, menu);
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
