package com.meridian.carins.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.meridian.carins.R;
import com.meridian.carins.helper.SQLiteHandler;
import com.meridian.carins.helper.SessionManager;

public class MenuActivity extends Activity {

    TextView txtName;
    TextView txtEmail;
    Button btnLogout;
    Button btnProfile;
    Button btnAsig;
    Button btnStatus;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.name);
        txtEmail = findViewById(R.id.email);
        btnLogout = findViewById(R.id.logout_button);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Placeholder button click event
        btnProfile = findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileCheck();
            }
        });

        btnAsig = findViewById(R.id.button2);
        btnAsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asigCheck();
            }
        });

        btnStatus = findViewById(R.id.button3);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusCheck();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setMessage("Do you want to Log Out?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutUser();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from SQLite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Placeholder methods
     * */
    public void profileCheck() {
        Toast.makeText(MenuActivity.this, "Profile",
                Toast.LENGTH_SHORT).show();
    }

    public void asigCheck() {
        Toast.makeText(MenuActivity.this, "Assurance",
                Toast.LENGTH_SHORT).show();
    }

    public void statusCheck() {
        Toast.makeText(MenuActivity.this, "Status",
                Toast.LENGTH_SHORT).show();
    }
}