package com.ifit.android.sdk.provider;

import android.app.Activity;
import android.database.Cursor;

import com.ifit.android.sdk.model.Fooddetail;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.main)
public class MyActivity extends Activity {

    @AfterViews
    final void init() {
        Fooddetail fooddetail = new Fooddetail();
    }
}
