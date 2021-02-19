package com.nanobookv2.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.nanobookv2.R;
import com.nanobookv2.core.ui.BaseActivity;
import com.nanobookv2.ui.activity.MainActivity;
import com.nanobookv2.ui.activity.WelcomeActivity;

public class Navigator {
    public static void push(BaseActivity context, Class<? extends BaseActivity> desIntent) {
        Intent intent = new Intent(context, desIntent);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static void push1(BaseActivity context, Class<? extends Activity> desIntent) {
        Intent intent = new Intent(context, desIntent);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static void push(BaseActivity context, Class<? extends BaseActivity> desIntent, Bundle bundle) {
        Intent intent = new Intent(context, desIntent);
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static void pushToWelcome(BaseActivity context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.finish();
        ActivityCompat.finishAffinity(context);
    }

    public static void pushToMain(BaseActivity context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.finish();
        ActivityCompat.finishAffinity(context);
    }
}
