package com.rain.utils.android.robocop
import com.rain.utils.android.robocop.generator.ContentProviderGenerator
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

/**
 * Created by user on 25.11.2014.
 */
public class RoboCopPlugin implements Plugin<Project> {

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
            }
        }
    }

    static void configureVariant(def project, def variant) {
        if (project.robocop.schemaPath == null) {
            project.getLogger().info('RoboCop: Schema path is not defined exiting')
            return;
        }
        def schemaFile = new File(project.robocop.schemaPath)
        if (!schemaFile.exists()) {
            throw new ProjectConfigurationException("Schema file path points to wrong file location", null)
        }

        def aptOutputDir = project.file(new File(project.buildDir, "generated/source/db"))
        def aptOutput = new File(aptOutputDir, variant.dirName)

        variant.addJavaSourceFoldersToModel(aptOutput);

        def schemaPath = schemaFile.getAbsolutePath()
        def generatedSourcePath = aptOutput.canonicalPath

        variant.javaCompile.doFirst {
            aptOutput.mkdirs()
            ContentProviderGenerator.generateContentProvider(schemaPath, generatedSourcePath)
        }
    }

}