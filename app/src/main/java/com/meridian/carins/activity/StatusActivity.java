package com.meridian.carins.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.meridian.carins.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatusActivity extends Activity {

    FloatingActionButton fab_core, fab_ci, fab_car, fab_3;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    GridView gv;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        init();

        // testing grid
        String[] plants = new String[] {
                "Catalina ironwood",
                "Cabinet cherry",
                "Pale corydalis",
                "Pink corydalis",
                "Belle Isle cress",
                "Land cress",
                "Orange coneflower",
                "Green coneflower",
                "Yellow coneflower",
                "Blue coneflower",
                "Pink coneflower",
                "Brown coneflower",
                "Silver coneflower",
                "Gold coneflower",
                "Coast polypody",
                "Water fern"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        gv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, plantsList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;

                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lp);

                LayoutParams params = (LayoutParams) tv.getLayoutParams();
                params.width = getPixelsFromDPs(StatusActivity.this, 168);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                tv.setText(plantsList.get(position));
                return tv;
            }
        });

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

    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

    public void fabCi() {
        Toast.makeText(StatusActivity.this, "Adding CI...",
                Toast.LENGTH_SHORT).show();
    }

    public void fabCar() {
        Toast.makeText(StatusActivity.this, "Adding Car stuff...",
                Toast.LENGTH_SHORT).show();
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

    public void init() {
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
