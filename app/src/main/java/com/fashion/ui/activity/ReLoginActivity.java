package com.fashion.ui.activity;

import android.graphics.Paint;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fashion.R;

public class ReLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_activity_login);
        initScreen();


    }

    void initScreen() {
        TextView forget_password = findViewById(R.id.forget_password);
        TextView other_account = findViewById(R.id.other_account);
        forget_password.setPaintFlags(forget_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        other_account.setPaintFlags(forget_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

}