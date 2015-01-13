package com.ifit.android.sdk.provider;

import android.app.Activity;

import com.ifit.android.sdk.model.FoodDetail;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.main)
public class MyActivity extends Activity {

    @AfterViews
    final void init() {
        FoodDetail fooddetail = new FoodDetail();
    }
}
