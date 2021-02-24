package com.fashion.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fashion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FloatingActionButton fab = findViewById(R.id.fab);
        LinearLayout linearLayout  = findViewById(R.id.viewB);
        fab.setOnClickListener(v -> {
            linearLayout.setVisibility(View.GONE);
        });
    }

}