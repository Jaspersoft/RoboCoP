package com.example.myapp.database;

import android.database.Cursor;
import android.net.Uri;

import com.example.myapp.test.support.DatabaseSpecification;
import com.ifit.android.sdk.database.table.FooddetailTable;
import com.ifit.android.sdk.model.Fooddetail;
import com.ifit.android.sdk.provider.IfitDatabaseProvider;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FooddetailTest extends DatabaseSpecification {
    @Test
    public void test_insert() {
        Fooddetail fooddetail = new Fooddetail();
        fooddetail.setFoodName("Apple");
        fooddetail.setFoodType("Fruit");
        fooddetail.setFoodUrl("https://www.apple.com/");

        Uri resultUri = getContentResolver()
                .insert(IfitDatabaseProvider.FOOD_DETAIL_CONTENT_URI,
                        fooddetail.getContentValues());
        assertNewUri(resultUri);

        Cursor cursor = getContentResolver()
                .query(resultUri, FooddetailTable.ALL_COLUMNS, null, null, null);
        cursor.moveToFirst();

        String resultName = cursor.getString(columnIndex(cursor, FooddetailTable.FOOD_NAME));
        assertThat(resultName, is("Apple"));
        String resultType = cursor.getString(columnIndex(cursor, FooddetailTable.FOOD_TYPE));
        assertThat(resultType, is("Fruit"));
        String resultUrl = cursor.getString(columnIndex(cursor, FooddetailTable.FOOD_URL));
        assertThat(resultUrl, is("https://www.apple.com/"));
    }
}
