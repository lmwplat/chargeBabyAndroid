package com.liumw.chargebaby.ui.navi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liumw.chargebaby.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class FinishActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            finishAndRemoveTask();
        } else {
            finish();
        }
    }

    public static void exitApplication(Context context) {
        Intent intent = new Intent(context, FinishActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        context.startActivity(intent);
    }
}
