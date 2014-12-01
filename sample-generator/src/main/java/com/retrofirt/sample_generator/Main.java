package com.retrofirt.sample_generator;

import com.rain.utils.android.robocop.generator.ContentProviderGenerator;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String schemaPath = new File("sample-generator/src/main/resources/schema.json").getAbsolutePath();
        String generatedSourcePath = new File("sample-generator/src/main/java/").getAbsolutePath();
        ContentProviderGenerator.generateContentProvider(schemaPath, generatedSourcePath);
    }
}
