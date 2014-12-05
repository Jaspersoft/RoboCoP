package com.rain.utils.android.robocop.generator;

/**
 * Created with IntelliJ IDEA.
 * User: dustin
 * Date: 1/22/14
 * Time: 11:21 AM
 */
public class ContentProviderGenerator {

    private ContentProviderGenerator() {
        throw new AssertionError();
    }

    public static void generateContentProvider(String schemaPath, String generatedSourcePath) {
        ContentProviderWriter writer = new ContentProviderWriter();
        writer.createContentProvider(schemaPath, generatedSourcePath);
    }

}
