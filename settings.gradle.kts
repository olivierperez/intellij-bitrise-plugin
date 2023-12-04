pluginManagement {
    plugins {
        kotlin("jvm").version("1.9.21")
        id("org.jetbrains.compose").version("1.5.11")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "BitrisePlugin"
