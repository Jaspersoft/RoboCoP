package com.example.myapp.test.support.shadows;

import android.app.Application;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;
import java.io.IOException;

/**
 * Just relocate the database file to a hard defined position.
 */
@Implements(Application.class)
public class CustomShadowApplication extends ShadowApplication {

    private static final String alternativeDatabasePath = "build/resources/unit-test.db";
    private File database = new File(alternativeDatabasePath);

    @Override
    @Implementation
    public File getDatabasePath(String name) {
        if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create mock db file", e);
            }
        }
        return database;
    }
}