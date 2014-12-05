package com.rain.utils.android.robocop

import com.rain.utils.android.robocop.generator.ContentProviderGenerator
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
/**
 * Created by user on 25.11.2014.
 */
public class RoboCopPlugin implements Plugin<Project> {

    static final String ROBOCOP_TASK_NAME = 'generateContentProvider'

    @Override
    void apply(Project project) {
        def variants = null;

        if (project.plugins.findPlugin("com.android.application") || project.plugins.findPlugin("android")) {
            variants = "applicationVariants";
        } else if (project.plugins.findPlugin("com.android.library") || project.plugins.findPlugin("android-library")) {
            variants = "libraryVariants";
        } else {
            throw new ProjectConfigurationException("The android or android-library plugin must be applied to the project", null)
        }

        project.extensions.create("robocop", RoboCopPluginExtension)

        project.afterEvaluate {
            project.android[variants].all { variant ->
                configureVariant(project, variant)
                if (variant.testVariant) {
                    configureVariant(project, variant.testVariant)
                }
            }
        }
    }

    def configureVariant(def project, def variant) {
        assertPreconditions(project)
        def outputDir = project.file(new File(project.buildDir, "generated/source/db"))
        def sourceOutputDir = new File(outputDir, variant.dirName)

        def taskName = "${ROBOCOP_TASK_NAME}${variant.getName()}"
        def generateTask = project.tasks.create(name: taskName) << {
            def schemaFile = new File(project.robocop.schemaPath)
            def schemaPath = schemaFile.getAbsolutePath()
            def generatedSourcePath = sourceOutputDir.canonicalPath
            ContentProviderGenerator.generateContentProvider(schemaPath, generatedSourcePath)
        }

        variant.registerJavaGeneratingTask(generateTask, sourceOutputDir)
        variant.javaCompile.doFirst {
            sourceOutputDir.mkdirs()
        }
    }

    def assertPreconditions(def project) {
        if (project.robocop.schemaPath == null) {
            project.getLogger().info('RoboCop: Schema path is not defined. Exiting!')
            return;
        }
        def schemaFile = new File(project.robocop.schemaPath)
        if (!schemaFile.exists()) {
            throw new ProjectConfigurationException("Schema file path points to wrong file location", null)
        }
    }

}