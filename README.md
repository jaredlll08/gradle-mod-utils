# gradle-mod-utils

# Using

In `settings.gradle.kts` as the following lines:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
    }
}
```

In `build.gradle.kts` add the following lines

```kotlin
plugins {
    id("net.blamejared.modtemplate") version "version_here"
}
```

# Options

```kotlin
// Used to update a Forge Version Tracker, only used by the `updateVersionTracker` task 
// Note: Currently uses a proprietary closed source system.
// All of these values can be provided by the secrets file or environment variables
versionTracker {
    // Minecraft Version of this project.
    mcVersion.set("1.16.5")
    // Endpoint to post the new version to.
    // Property key: "`versionTrackerAPI`".
    // NOTE: This is printed in the log if a `MalformedURLException` is thrown!
    endpoint.set("https://updates.example.com")
    // Version Tracker project name.
    // Property key: "`versionTrackerProjectName`"
    // Optional, will be replaced by `projectName` from `modTemplate` if not provided.
    projectName.set("example")
    // Project Homepage To be displayed in the output json.
    // Property key: "`versionTrackerAuthor`".
    // Optional, will be replaced by `curseHomepage` if not provided.
    homepage.set("https://example.com")
    // Version tracker username.
    // Property key: "`versionTrackerHomepage`".
    author.set("Username")
    // Version Tracker password.
    // Property key: "`versionTrackerKey`".
    uid.set("00000000-0000-0000-0000-000000000000")
}
```