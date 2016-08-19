package com.tl.jg.beerhere.beerhere2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class UserActivity extends Activity {

    public static Button selStad_btn;
    public static Button payMethd_btn;
    public static Button sets_btn;
    public static Button help_btn;

    public static ImageButton selStad_btn_pic;
    public static ImageButton payMethd_btn_pic;
    public static ImageButton sets_btn_pic;
    public static ImageButton help_btn_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        help_btn_pic = (ImageButton)findViewById(R.id.help_button_home);
        help_btn_pic.setImageResource(R.drawable.help_button_home);
        sets_btn_pic = (ImageButton)findViewById(R.id.settings_button_home);
        sets_btn_pic.setImageResource(R.drawable.settings_button__home);
        selStad_btn_pic = (ImageButton) findViewById(R.id.select_stadium_button_home);
        selStad_btn_pic.setImageResource(R.drawable.selectstadiumbutton1);
        payMethd_btn_pic = (ImageButton)findViewById(R.id.pay_methods_button_home);
        payMethd_btn_pic.setImageResource(R.drawable.pay_methods_button2);



        SelectStadiumButton();
        PayMethodButton();
        SettingsButton();
        HelpButton();
        
    }

    public void HelpButton() {

        help_btn_pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.tl.jg.beerhere.beerhere2.HelpActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void SettingsButton() {
        sets_btn_pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.tl.jg.beerhere.beerhere2.SettingsActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void SelectStadiumButton(){
        selStad_btn_pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.tl.jg.beerhere.beerhere2.SelectStadiumActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void PayMethodButton(){
        payMethd_btn_pic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.tl.jg.beerhere.beerhere2.PayMethodsActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
