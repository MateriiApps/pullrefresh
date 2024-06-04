plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

allprojects {
    group = "dev.materii.pullrefresh"
    version = "1.4.0-beta03"

    repositories {
        repositories {
            google()
            mavenCentral()
        }
    }
}