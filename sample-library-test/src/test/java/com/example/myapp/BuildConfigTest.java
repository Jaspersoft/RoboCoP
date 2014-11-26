/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.myapp;

import com.example.myapp.test.support.UnitTestSpecification;

import org.junit.Test;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class BuildConfigTest extends UnitTestSpecification {

    @Test
    public void shouldHaveCorrectConfiguration() {
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            assertThat(BuildConfig.DEBUG, is(true));
        } else if ("release".equals(BuildConfig.BUILD_TYPE)) {
            assertThat(BuildConfig.DEBUG, is(false));
        } else {
            fail("build type configuration not tested or supported?");
        }
        new BuildConfig(); // dummy coverage, should be an interface or something else
    }
}
