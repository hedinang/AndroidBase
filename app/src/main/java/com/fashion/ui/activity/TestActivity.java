package com.fashion.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fashion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TestActivity extends AppCompatActivity {
    public Boolean slide = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        FloatingActionButton fab = findViewById(R.id.fab);
        LinearLayout linearLayout = findViewById(R.id.viewB);

        fab.setOnClickListener(v -> {
            if (slide == false) {
                linearLayout.setVisibility(View.GONE);
                slide = true;
            } else {
                linearLayout.setVisibility(View.INVISIBLE);
                slide = false;
            }
        });
    }
}