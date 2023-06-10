package com.blamejared.gradle.mod.utils.extensions

import com.blamejared.gradle.mod.utils.GMUtils
import org.gradle.api.Project
import org.gradle.api.provider.Property

interface VersionTrackerExtension {
    val endpoint: Property<String>
    val author: Property<String>
    val homepage: Property<String>
    val uid: Property<String>
    val projectName: Property<String>
    val mcVersion: Property<String>

    fun conventionFrom(project: Project) {
        endpoint.convention(GMUtils.locateProperty(project, "versionTrackerAPI"))
        author.convention(GMUtils.locateProperty(project, "versionTrackerAuthor"))
        homepage.convention(GMUtils.locateProperty(project, "versionTrackerHomepage"))
        uid.convention(GMUtils.locateProperty(project, "versionTrackerKey"))
        projectName.convention(GMUtils.locateProperty(project, "versionTrackerProjectName"))
    }
}
