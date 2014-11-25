package com.rain.utils.android.robocop;

import org.gradle.api.tasks.TaskAction;

/**
 * Created by user on 25.11.2014.
 */
public class RobocopTask {

    @TaskAction
    public void javaTask() {
        System.out.println("Hello from MyJavaTask");
    }

}