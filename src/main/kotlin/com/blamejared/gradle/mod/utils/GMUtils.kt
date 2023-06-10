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

    @JvmStatic
    fun smallChangelog(project: Project, repository: String): String {
        var changelog = "Unavailable"
        try {
            val stdout = ByteArrayOutputStream()
            val gitHash = System.getenv("GIT_COMMIT") ?: "HEAD"
            val gitPrevHash = System.getenv("GIT_PREVIOUS_SUCCESSFUL_COMMIT") ?: "HEAD~10"
            val commitLink = "$repository/commit/"
            val revRange = "$gitPrevHash...$gitHash"

            project.exec {
                this.commandLine("git").args("log", "--pretty=tformat:- [%s]($commitLink%H) - %aN ", revRange).setStandardOutput(stdout)
            }
            changelog = stdout.toString().trim()
        } catch (ignored: Exception) {
        }


        return changelog.take(1500)
    }


}