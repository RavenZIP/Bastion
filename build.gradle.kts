plugins {
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
}

group = "io.ravenzip"

version = "0.0.1"