package com.rain.utils.android.robocop;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by user on 25.11.2014.
 */
public class RobocopPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.task("javaTask");
    }

}