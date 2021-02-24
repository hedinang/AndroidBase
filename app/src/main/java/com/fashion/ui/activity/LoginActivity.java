package com.fashion.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fashion.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initScreen();

    }

    void initScreen() {
        TextView forget_password = findViewById(R.id.forget_password);
        TextView other_account = findViewById(R.id.create_account);
        forget_password.setPaintFlags(forget_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        other_account.setPaintFlags(forget_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(v -> {
            if (password.getText().toString().equals("daicadung")) {


                Intent intent = new Intent(LoginActivity.this, ReLoginActivity.class);
                startActivity(intent);
            }
        });
    }

}