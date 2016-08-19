package com.tl.jg.beerhere.beerhere2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.tl.jg.beerhere.beerhere2.Stadium.MySQLiteHelper;

/**
 * Created by Jim on 6/24/2016.
 */
public class SelectStadiumActivity extends Activity {

    ImageButton goButtonIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_stadium_activity);
        goButtonIB = (ImageButton) findViewById(R.id.select_stadium_button_home);
        goButtonIB.setImageResource(R.drawable.selectstadiumgobutton);
        MySQLiteHelper dbStadiums = new MySQLiteHelper(this);

        dbStadiums.deleteAllStadiums();

        dbStadiums.goStadiums();



        final String[] array = new String[dbStadiums.getStadiumsCount()];

        for (int i = 0; i < dbStadiums.getStadiumsCount(); i++) {
            array[i] = dbStadiums.getStadiums(i + 1).getName().toString();
           Log.d("Added: ", array[i].toString());
        }

//        Log.d("Array Size: ",  Integer.toString(array.length));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array);
        //Getting the instance of AutoCompleteTextView
        final AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//        actv.setTextColor(Color.RED);

        goButtonIB = (ImageButton) findViewById(R.id.select_stadium_button_home);

        // Capture button clicks
        goButtonIB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent i = new Intent(SelectStadiumActivity.this, PayVendorActivity.class);
                i.putExtra("KEY", actv.getText().toString());
                startActivity(i);

            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_stadium, menu);
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
