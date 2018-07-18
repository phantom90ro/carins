package com.meridian.carins.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meridian.carins.R;

public class AddCarRegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_registration);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void goBack() {
        Intent intent = new Intent(AddCarRegistrationActivity.this,
                StatusActivity.class);
        startActivity(intent);
        finish();
    }
}
