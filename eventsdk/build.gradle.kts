import groovy.util.Node

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

val VERSION_NAME = "1.0.1"
val VERSION_CODE = 1

android {
    namespace = "com.thingsolver.android.sdk.eventsdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
//    implementation(libs.logging.interceptor)
}

tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
}

tasks.register("testing") {
    println("Test")
    println(ext.properties)
}

fun addDependencies(depsNode: Node, allDependencies: Collection<Dependency>) {
    allDependencies.forEach { dep ->
        val depNode = depsNode.appendNode("dependency")
        depNode.appendNode("groupId", dep.group)
        depNode.appendNode("artifactId", dep.name)
        depNode.appendNode("version", dep.version)
    }
}

gradle.projectsEvaluated {
    tasks.named("publishEventsdkReleasePublicationToMavenRepository") {
        dependsOn(tasks.named("assembleRelease"))
    }
}

publishing {
    publications {
        create<MavenPublication>("eventsdkRelease") {
            groupId = "com.thingsolver.android.sdk"
            artifactId = "event-sdk"
            version = VERSION_NAME

            artifact(tasks["androidSourcesJar"])
            artifact(layout.buildDirectory.file("outputs/aar/eventsdk-release.aar"))

            pom {
                withXml {
                    val depsNode = asNode().appendNode("dependencies")

                    // Access all dependencies of the 'api' configuration
                    addDependencies(depsNode, configurations["api"].incoming.dependencies)
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                username = "aws"
                password = System.getenv("CODEARTIFACT_AUTH_TOKEN")
            }
            url = uri("https://thingsolver-446553675106.d.codeartifact.eu-central-1.amazonaws.com/maven/solver-events-sdk/")
        }
    }
}