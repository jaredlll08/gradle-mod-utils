plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "com.blamejared"
var buildNumber: String? = System.getenv("BUILD_NUMBER")
version = "1.0.${if (buildNumber != null) buildNumber else "0"}"
base {
    archivesName.set("gradle-mod-utils")
}
repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("gradle-mod-utils") {
            id = "com.blamejared.gradle-mod-utils"
            implementationClass = "com.blamejared.gradle.mod.utils.GradleModUtilsPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class.java) {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}