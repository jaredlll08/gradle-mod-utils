package com.blamejared.gradle.mod.utils

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

object GMUtils {

    @JvmStatic
    fun updatingVersion(version: String): String {
        val buildNumber = System.getenv(Constants.ENV_BUILD_NUMBER)
        return if (buildNumber != null) {
            "$version.$buildNumber"
        } else version
    }

    @JvmStatic
    fun locateProperty(project: Project, name: String): String? {
        return if (project.hasProperty(name)) {
            project.property(name) as String
        } else System.getenv(name)
    }

}