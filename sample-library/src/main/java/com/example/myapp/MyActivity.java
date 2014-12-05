package com.example.myapp;

import android.app.Activity;

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
