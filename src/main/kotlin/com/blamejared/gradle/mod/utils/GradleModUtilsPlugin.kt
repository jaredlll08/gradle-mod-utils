package com.blamejared.gradle.mod.utils

import com.blamejared.gradle.mod.utils.extensions.VersionTrackerExtension
import com.blamejared.gradle.mod.utils.tasks.UpdateVersionTracker
import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleModUtilsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val versionTracker = project.extensions.create("versionTracker", VersionTrackerExtension::class.java)
        versionTracker.conventionFrom(project)

        project.tasks.register("updateVersionTracker", UpdateVersionTracker::class.java);
    }

}