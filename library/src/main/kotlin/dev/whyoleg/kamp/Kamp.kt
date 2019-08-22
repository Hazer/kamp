package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.initialization.*

@KampDSL
fun Settings.kamp(
    versions: BuiltInVersions = BuiltIn.Default.versions,
    block: KampSettings.() -> Unit
): Unit = KampSettings(versions).apply(block).configure(this)

@KampDSL
fun Project.kampBuild(
    versions: BuiltInVersions = BuiltIn.Default.versions,
    block: KampBuild.() -> Unit
): Unit = KampBuild(versions).apply(block).configure(this)

@KampDSL
fun Project.kampRoot(
    versions: BuiltInVersions = BuiltIn.Default.versions,
    block: KampRoot.() -> Unit
): Unit = KampRoot(versions).apply(block).configure(this)

@KampDSL
fun Project.kamp(
    versions: BuiltInVersions = BuiltIn.Default.versions,
    configuration: ProjectConfiguration,
    block: KampMultiPlatformExtension.() -> Unit
): Unit = KampMultiPlatformExtension(configuration, versions).apply(block).configure(this)

@KampDSL
fun Project.kampJvm(
    versions: BuiltInVersions = BuiltIn.Default.versions,
    configuration: ProjectConfiguration,
    block: KampJvmExtension.() -> Unit
): Unit = KampJvmExtension(configuration, versions).apply(block).configure(this)
