package com.fashion.utils;

import android.app.Activity;
import android.content.Intent;

import com.fashion.R;

public class ThemeUtils {
	private static int sTheme;

	public final static int LIGHT = 0;
	public final static int DARK = 1;

	public static void changeToTheme(Activity activity, int theme) {
		sTheme = theme;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
		activity.overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public static void onActivityCreateSetTheme(Activity activity) {
		switch (sTheme) {
		default:
		case LIGHT:
			activity.setTheme(R.style.Theme_Light);
			break;
		case DARK:
			activity.setTheme(R.style.Theme_Dark);
			break;
		}
	}
}