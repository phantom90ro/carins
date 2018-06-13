package com.meridian.carins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnLogIn;
    private EditText etEmail, etPassword;
    private Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogIn = findViewById(R.id.log_in_button);
        etEmail = findViewById(R.id.email_input);
        etPassword = findViewById(R.id.password_input);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().equals("admin") &&
                        etPassword.getText().toString().equals("root")) {
                    Toast.makeText(MainActivity.this, "Login Successful",
                            Toast.LENGTH_SHORT).show();
                    check = true;
                } else {
                    Toast.makeText(MainActivity.this, "Login Failed",
                            Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if(check) {
                    openApp();
                }
            }
        });
    }

    public void openApp() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
