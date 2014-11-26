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

//        def robocopConfiguration = project.configurations.compile
//        def robocopTestConfiguration = project.configurations.androidTestCompile

        project.extensions.create("robocop", RoboCopPluginExtension)

        project.afterEvaluate {
            project.android[variants].all { variant ->
                configureVariant(project, variant)
//                if (variant.testVariant) {
//                    configureVariant(project, variant.testVariant, robocopTestConfiguration)
//                }
            }
        }
    }

    static void configureVariant(def project, def variant) {
        if (project.robocop.schemaPath == null) {
            project.getLogger().info('RoboCop: Schema path is not defined. Exiting!')
            return;
        }
        def schemaFile = new File(project.robocop.schemaPath)
        if (!schemaFile.exists()) {
            throw new ProjectConfigurationException("Schema file path points to wrong file location", null)
        }

        def robocopOutputDir = project.file(new File(project.buildDir, "generated/source/db"))
        def robocopOutput = new File(robocopOutputDir, variant.dirName)

        variant.addJavaSourceFoldersToModel(robocopOutput);

//        def processorPath = configuration.getAsPath()
//        variant.javaCompile.options.compilerArgs += [
//                '-processorpath', processorPath,
//                '-s', robocopOutput
//        ]

//        project.robocop.robocopArguments.variant = variant
//        project.robocop.robocopArguments.project = project
//        project.robocop.robocopArguments.android = project.android
//
//        variant.javaCompile.options.compilerArgs += project.robocop.arguments()

        def schemaPath = schemaFile.getAbsolutePath()
        def generatedSourcePath = robocopOutput.canonicalPath

        variant.javaCompile.doFirst {
            robocopOutput.mkdirs()
            ContentProviderGenerator.generateContentProvider(schemaPath, generatedSourcePath)
        }
    }

}