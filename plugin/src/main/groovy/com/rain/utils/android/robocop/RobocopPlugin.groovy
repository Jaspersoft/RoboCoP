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
        def androidExtension = null;
        if (project.plugins.findPlugin("com.android.application") || project.plugins.findPlugin("android")) {
            androidExtension = project.plugins.getPlugin("com.android.application").extension
            variants = "applicationVariants";
        } else if (project.plugins.findPlugin("com.android.library") || project.plugins.findPlugin("android-library")) {
            androidExtension = project.plugins.getPlugin("com.android.library").extension
            variants = "libraryVariants";
        } else {
            throw new ProjectConfigurationException("The android or android-library plugin must be applied to the project", null)
        }

        project.extensions.create("robocop", RoboCopPluginExtension)

        project.afterEvaluate {
            project.android[variants].all { variant ->
                configureVariant(project, variant, androidExtension)
                if (variant.testVariant) {
                    configureVariant(project, variant.testVariant, androidExtension)
                }
            }
        }
    }

    def configureVariant(def project, def variant, def android) {
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

//        android.sourceSets[sourceSetName(variant)].java.srcDirs.addAll robocopOutput.path
//        variant.javaCompile.source = variant.javaCompile.source.filter {
//            !variant.variantData.extraGeneratedSourceFolders.each { folder ->
//                folder.path.startsWith(robocopOutput.path)
//            }
//        }

        variant.javaCompile.options.compilerArgs += [
                '-sourcepath', robocopOutput
        ]

        def schemaPath = schemaFile.getAbsolutePath()
        def generatedSourcePath = robocopOutput.canonicalPath

        ContentProviderGenerator.generateContentProvider(schemaPath, generatedSourcePath)

        variant.javaCompile.doFirst {
            robocopOutput.mkdirs()
        }
    }

    def sourceSetName(variant) {
        variant.dirName.split('/').last()
    }

}