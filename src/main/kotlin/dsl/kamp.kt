package dev.whyoleg.kamp.dsl

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.plugin.*
import org.gradle.api.*
import org.gradle.api.initialization.*
import org.gradle.api.reflect.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
fun Project.kamp(block: KampMultiPlatformExtension.() -> Unit): Unit = internalKamp(block)

@KampDSL
fun Project.kampJvm(block: KampJvmExtension.() -> Unit): Unit = internalKamp(block)


@Suppress("UNCHECKED_CAST")
internal inline fun <reified Ext : KotlinProjectExtension, KampExt : KampExtension<Ext>> Project.internalKamp(
    crossinline block: KampExt.() -> Unit
) {
    apply {
        it.plugin(
            when (Ext::class) {
                KotlinMultiplatformExtension::class -> PluginName.kotlinMpp
                KotlinJvmProjectExtension::class    -> PluginName.kotlinJvm
                else                                -> error("Platform is not supported")
            }
        )
    }
    println()
    println("Setup plugin with kotlin ${getKotlinPluginVersion()}")
    println()
    extensions.configure(object : TypeOf<Ext>() {}) {
        (when (it) {
            is KotlinMultiplatformExtension -> (KampMultiPlatformExtension(it, this) as KampExt).apply(block)
            is KotlinJvmProjectExtension    -> (KampJvmExtension(it, this) as KampExt).apply(block)
            else                            -> error("Platform is not supported")
        }).configure()
    }
}

fun Settings.kamp(block: KampSettings.() -> Unit) {
    KampSettings(this).apply(block).configure()
}
