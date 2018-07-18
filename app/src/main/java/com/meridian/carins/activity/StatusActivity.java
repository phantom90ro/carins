package com.meridian.carins.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.meridian.carins.R;
import com.meridian.carins.helper.SQLiteHandler;
import com.meridian.carins.helper.SessionManager;

import java.util.HashMap;

public class StatusActivity extends Activity {

    FloatingActionButton fab_core, fab_ci, fab_car, fab_3;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    TextView txtUid;
    GridView gv;
    boolean isOpen = false;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        init();

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String uid = user.get("uid");
        txtUid.setText(uid);

        // Floating Action Button
        fab_core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab_3.startAnimation(fabClose);
                    fab_car.startAnimation(fabClose);
                    fab_ci.startAnimation(fabClose);
                    fab_core.startAnimation(fabRAntiClockwise);
                    fab_3.setClickable(false);
                    fab_car.setClickable(false);
                    fab_ci.setClickable(false);
                    isOpen = false;
                } else {
                    fab_3.startAnimation(fabOpen);
                    fab_car.startAnimation(fabOpen);
                    fab_ci.startAnimation(fabOpen);
                    fab_core.startAnimation(fabRClockwise);
                    fab_3.setClickable(true);
                    fab_car.setClickable(true);
                    fab_ci.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab_ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabCi();
            }
        });

        fab_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabCar();
            }
        });

        fab_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab3();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void fabCi() {
        Intent intent = new Intent(StatusActivity.this, AddIdentityCardActivity.class);
        startActivity(intent);
        finish();
    }

    public void fabCar() {
        Intent intent = new Intent(StatusActivity.this, AddCarRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    public void fab3() {
        Toast.makeText(StatusActivity.this, "Adding more stuff...",
                Toast.LENGTH_SHORT).show();
    }

    public void goBack() {
        Intent intent = new Intent(StatusActivity.this,
                MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(StatusActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void init() {
        txtUid = findViewById(R.id.uid_get);

        fab_core = findViewById(R.id.fab_core);
        fab_ci = findViewById(R.id.fab_ci);
        fab_car = findViewById(R.id.fab_car);
        fab_3 = findViewById(R.id.fab_3);

        gv = findViewById(R.id.grid_status);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_clockwise);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_anticlockwise);
    }
}