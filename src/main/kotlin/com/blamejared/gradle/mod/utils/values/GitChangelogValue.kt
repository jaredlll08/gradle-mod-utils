package com.blamejared.gradle.mod.utils.values

import org.gradle.api.provider.Property
import org.gradle.api.provider.ValueSource
import org.gradle.api.provider.ValueSourceParameters
import org.gradle.process.ExecOperations
import java.io.ByteArrayOutputStream
import javax.inject.Inject

abstract class GitChangelogValue : ValueSource<String, GitChangelogValue.Parameters> {
    interface Parameters : ValueSourceParameters {
        val repository: Property<String>
    }

    @get:Inject
    abstract val execOperations: ExecOperations

    override fun obtain(): String? {
        val stdout = ByteArrayOutputStream()
        val gitHash = System.getenv("GIT_COMMIT") ?: "HEAD"
        val explicitPrevHash = System.getenv("GIT_PREVIOUS_SUCCESSFUL_COMMIT")

        val commitLink = "${parameters.repository.get()}/commit/"

        execOperations.exec {
            commandLine("git")
            val baseArgs = mutableListOf("log", "--pretty=tformat:- [%s]($commitLink%H) - %aN")

            if (explicitPrevHash != null) {
                baseArgs.add("$explicitPrevHash...$gitHash")
            } else {
                baseArgs.add("-n")
                baseArgs.add("10")
                baseArgs.add(gitHash)
            }

            args(baseArgs)
            standardOutput = stdout
        }

        return stdout.toString().trim().take(1500)
    }
}
