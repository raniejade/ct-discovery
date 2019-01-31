package com.github.raniejade.ct

import com.google.auto.service.AutoService
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

open class CtdExtension {
    var enabled = true
}

class CtdPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("ctd", CtdExtension::class.java)
    }
}

@AutoService(KotlinGradleSubplugin::class)
class CtdSubplugin: KotlinGradleSubplugin<AbstractCompile> {
    override fun apply(project: Project, kotlinCompile: AbstractCompile, javaCompile: AbstractCompile?, variantData: Any?, androidProjectHandler: Any?, kotlinCompilation: KotlinCompilation<KotlinCommonOptions>?): List<SubpluginOption> {
        val extension = project.extensions.findByType(CtdExtension::class.java)
            ?: CtdExtension()

        return listOf(SubpluginOption("enabled", extension.enabled.toString()))
    }

    override fun getCompilerPluginId(): String {
        return "ct-discovery"
    }

    override fun getPluginArtifact(): SubpluginArtifact {
        return SubpluginArtifact(
            "com.github.raniejade",
            "kotlin-plugin",
            "0.1.0"
        )
    }

    override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
        return project.plugins.hasPlugin(CtdPlugin::class.java)
    }

}