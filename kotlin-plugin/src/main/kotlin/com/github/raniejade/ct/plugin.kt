package com.github.raniejade.ct

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.ClassBuilderFactory
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.getAllSuperclassesWithoutAny
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin

internal val KEY_ENABLED = CompilerConfigurationKey<Boolean>("enabled")

@AutoService(CommandLineProcessor::class)
class CtdCommandLineProcessor: CommandLineProcessor {
    override val pluginId: String = "ct-discovery"
    override val pluginOptions: Collection<AbstractCliOption> = listOf(
        CliOption("enabled", "<true|false>", "whether plugin is enabled")
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            "enabled" -> configuration.put(KEY_ENABLED, value.toBoolean())
            else -> error("Unexpected config option ${option.optionName}")
        }
    }
}

@AutoService(ComponentRegistrar::class)
class CtdComponentRegistrar: ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        if (configuration[KEY_ENABLED] == false) {
            return
        }

        ClassBuilderInterceptorExtension.registerExtension(
            project,
            CtdExtension()
        )
    }
}

class CtdExtension: ClassBuilderInterceptorExtension {
    override fun interceptClassBuilderFactory(interceptedFactory: ClassBuilderFactory, bindingContext: BindingContext, diagnostics: DiagnosticSink): ClassBuilderFactory {
        return object: ClassBuilderFactory by interceptedFactory {
            override fun newClassBuilder(origin: JvmDeclarationOrigin): ClassBuilder {
                val classDescriptor = checkNotNull(origin.descriptor) as ClassDescriptor
                error(classDescriptor.getAllSuperclassesWithoutAny())
                return interceptedFactory.newClassBuilder(origin)
            }
        }
    }

}