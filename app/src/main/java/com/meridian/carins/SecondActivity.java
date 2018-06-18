package com.meridian.carins;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity {

    private Button btnBack;
    private Button btnOption_1, btnOption_2, btnOption_3, btnOption_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnOption_1 = findViewById(R.id.button);
        btnOption_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOption_1();
            }
        });

        btnOption_2 = findViewById(R.id.button2);
        btnOption_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOption_2();
            }
        });

        btnOption_3 = findViewById(R.id.button3);
        btnOption_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOption_3();
            }
        });

        btnOption_4 = findViewById(R.id.button4);
        btnOption_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOption_4();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
        builder.setMessage("Do you want to Log Out?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goBack();
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

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void buttonOption_1() {
        Toast.makeText(SecondActivity.this, "Button Option 1",
                Toast.LENGTH_SHORT).show();
    }

    public void buttonOption_2() {
        Toast.makeText(SecondActivity.this, "Button Option 2",
                Toast.LENGTH_SHORT).show();
    }

    public void buttonOption_3() {
        Toast.makeText(SecondActivity.this, "Button Option 3",
                Toast.LENGTH_SHORT).show();
    }

    public void buttonOption_4() {
        Toast.makeText(SecondActivity.this, "Button Option 4",
                Toast.LENGTH_SHORT).show();
    }
}
