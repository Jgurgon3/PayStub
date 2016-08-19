package com.tl.jg.beerhere.beerhere2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tl.jg.beerhere.beerhere2.Stadium.MySQLiteHelper;
import com.tl.jg.beerhere.beerhere2.Stadium.Vendors;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 7/13/2016.
 */
public class PayVendorActivity extends Activity{
    String selectedStadium;
    ImageButton payButton;
    EditText paymentTotal;

    public static final String CONFIG_CLIENT_ID = "Afe_fpyRIHc0kRy6xv0i6JiEvqn2Pdl7ijoPHCIrcggG5DqhHCM7ylkrNCTzNN9qU9Zvoa-X0td2YvnX";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_vendor);
        payButton = (ImageButton) findViewById(R.id.pay_vendor_button);
        payButton.setImageResource(R.drawable.pay_vendor_button_home);
        paymentTotal   = (EditText)findViewById(R.id.payTotal2);
        paymentTotal.setRawInputType(Configuration.KEYBOARD_QWERTY);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            selectedStadium = extras.getString("KEY");
            Log.d("the KEY is: ", selectedStadium);
        }

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        final MySQLiteHelper dbVendors = new MySQLiteHelper(this);

        dbVendors.deleteAllVendors();

        dbVendors.goVendors();


        List<Vendors> vendorList = new ArrayList<Vendors>(dbVendors.getAllVendors(selectedStadium));


        String[] array2 = new String[vendorList.size()];

        for (int i = 0; i < vendorList.size(); i++) {
            array2[i] = vendorList.get(i).getNumber().toString();
            Log.d("Added: ", array2[i].toString());
        }

        Log.d("Array Size: ", Integer.toString(array2.length));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array2);
        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView actv2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        actv2.setThreshold(1);//will start working from first character
        actv2.setAdapter(adapter2);//setting the adapter data into the AutoCompleteTextView

        payButton = (ImageButton) findViewById(R.id.pay_vendor_button);

        // Capture button clicks
        payButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Vendors theVendor = dbVendors.getVendorByNumber(actv2.getText().toString());

                Log.d("Vendor Number", String.valueOf(theVendor.getId()));

                dbVendors.editBalance(theVendor, Float.parseFloat(paymentTotal.getText().toString()));

                onBuyPressed();

                //ADD A TOAST HERE!!!!!!!!!!!

                actv2.getText().clear();
                paymentTotal.getText().clear();

            }
        });

    }

    public void onBuyPressed() {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PayVendorActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(Float.parseFloat(paymentTotal.getText().toString())), "USD", "your payment",
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.e("Show", confirm.toJSONObject().toString(4));
                        Log.e("Show", confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         */
                        Toast.makeText(getApplicationContext(), "Payment Confirmation info received" +
                                " from PayPal" + "for $" + paymentTotal.getText().toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "an extremely unlikely failure" +
                                " occurred:", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "The user canceled.", Toast.LENGTH_LONG).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getApplicationContext(), "An invalid Payment or PayPalConfiguration" +
                        " was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_vendor, menu);
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

