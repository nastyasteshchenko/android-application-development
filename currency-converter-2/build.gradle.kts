// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    kotlin("jvm") version "1.9.0" apply false

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23" apply false
}
