package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.dependency.*
import dev.whyoleg.kamp.base.target.Target
import dev.whyoleg.kamp.builder.*
import org.jetbrains.kotlin.gradle.plugin.*

interface DependencyHandler : KotlinDependencyHandler {
    fun add(artifact: Any)
}

fun KotlinDependencyHandler.modules(type: DependencySetType, dependencies: Iterable<ModuleDependency>) {
    val handler = when (type) {
        DependencySetType.implementation -> Helper.implementation(this)
        DependencySetType.api            -> Helper.api(this)
        DependencySetType.runtimeOnly    -> Helper.runtimeOnly(this)
        DependencySetType.compileOnly    -> Helper.compileOnly(this)
    }
    dependencies.forEach {
        handler.add(handler.project(it.name))
    }
}

fun KotlinDependencyHandler.libraries(type: DependencySetType, target: Target, dependencies: Iterable<LibraryDependency>) {
    val handler = when (type) {
        DependencySetType.implementation -> Helper.implementation(this)
        DependencySetType.api            -> Helper.api(this)
        DependencySetType.runtimeOnly    -> Helper.runtimeOnly(this)
        DependencySetType.compileOnly    -> Helper.compileOnly(this)
    }
    dependencies.forEach { dependency ->
        val (_, rawPostfix) = dependency.targets.find { it.target.name == target.name } ?: return
        val (group, name, rawVersion) = dependency.raw
        val postfix = rawPostfix?.let { "-$it" } ?: ""
        val version = rawVersion?.let { ":$it" } ?: ""
        handler.add("${group.name}:$name$postfix$version")
    }
}

private typealias DependencyConverter = KotlinDependencyHandler.() -> DependencyHandler

private object Helper {
    val api: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                api(artifact)
            }
        }
    }

    val implementation: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                implementation(artifact)
            }
        }
    }

    val runtimeOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                runtimeOnly(artifact)
            }
        }
    }

    val compileOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                compileOnly(artifact)
            }
        }
    }
}