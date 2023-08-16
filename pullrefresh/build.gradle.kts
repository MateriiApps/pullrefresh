plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.multiplatform)
    id("maven-publish")
    id("signing")
}

android {
    namespace = "dev.materii.pullrefresh"
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 21
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

task<Jar>("emptyJavadocJar") {
    archiveClassifier.set("javadoc")
}

val sonatypeUsername: String? = System.getenv("SONATYPE_USERNAME")
val sonatypePassword: String? = System.getenv("SONATYPE_PASSWORD")

publishing {
    repositories {
        if(sonatypeUsername == null || sonatypePassword == null) {
            mavenLocal()
        } else {
            maven {
                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(tasks["emptyJavadocJar"])

        pom {
            name.set("pullrefresh")
            description.set("Standalone pull to refresh library for Jetpack Compose multiplatform.")
            url.set("https://github.com/MateriiApps/pullrefresh")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/license/mit/")
                }
            }
            developers {
                developer {
                    id.set("wingio")
                    name.set("Wing")
                    url.set("https://wingio.xyz")
                }
            }
            scm {
                url.set("https://github.com/MateriiApps/pullrefresh")
                connection.set("scm:git:github.com/MateriiApps/pullrefresh.git")
                developerConnection.set("scm:git:ssh://github.com/MateriiApps/pullrefresh.git")
            }
        }
    }
}

if (sonatypeUsername != null && sonatypePassword != null) {
    signing {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD"),
        )
        sign(publishing.publications)
    }


    val dependsOnTasks = mutableListOf<String>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        dependsOnTasks.add(name.replace("publish", "sign").replaceAfter("Publication", ""))
        dependsOn(dependsOnTasks)
    }
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    ios()
    iosSimulatorArm64()
    jvmToolchain(17)
    jvm("desktop")
    macosX64()
    macosArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.animation)
            }
        }
    }
}