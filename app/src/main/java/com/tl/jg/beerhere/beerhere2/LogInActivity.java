package com.tl.jg.beerhere.beerhere2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class LogInActivity extends Activity {

    private static EditText username;
    private static EditText password;
    //private static Button login_btn;
    ImageView imageView4;
    ImageButton login_btn_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //LoginButton();
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView4.setImageResource(R.drawable.logo5);
        login_btn_pic = (ImageButton) findViewById(R.id.imageButton9);
        login_btn_pic.setImageResource(R.drawable.login_button);
        username = (EditText) findViewById(R.id.editText2_username);
        password = (EditText) findViewById(R.id.editText3_password);

        login_btn_pic.setOnClickListener(new View.OnClickListener() {
             public void onClick(View arg0) {

                 if (username.getText().toString().equals("user") &&
                         password.getText().toString().equals("pass")) {
                     Toast.makeText(LogInActivity.this, "User and Passowrd are Correct",
                             Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent("com.tl.jg.beerhere.beerhere2.UserActivity");
                     startActivity(intent);
                 } else {
                     Toast.makeText(LogInActivity.this, "User and Passowrd are Incorrect",
                             Toast.LENGTH_SHORT).show();

                 }

             }
         }
        );


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
