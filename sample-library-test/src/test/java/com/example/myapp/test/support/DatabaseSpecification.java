package com.example.myapp.test.support;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Before;
import org.robolectric.Robolectric;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;

public class DatabaseSpecification extends UnitTestSpecification {

    private Context context;

    @Before
    public void initContext() {
        context = Robolectric.application;
        resetDatabase();
    }

    public void resetDatabase() {
        File dbFile = context.getDatabasePath(null);
        if (dbFile.exists()) {
            context.deleteDatabase(dbFile.getAbsolutePath());
        }
    }

    public Context getContext() {
        return context;
    }

    public ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    protected int columnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }

    protected void assertNewUri(Uri uri) {
        assertNotNull(uri);
        assertThat(Integer.valueOf(uri.getLastPathSegment()), greaterThan(0));
    }

}