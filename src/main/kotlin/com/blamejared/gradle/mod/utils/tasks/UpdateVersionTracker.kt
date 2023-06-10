package com.blamejared.gradle.mod.utils.tasks

import com.blamejared.gradle.mod.utils.extensions.VersionTrackerExtension
import groovy.json.JsonOutput
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

open class UpdateVersionTracker : DefaultTask() {

    override fun doFirst(action: Action<in Task>): Task {
        return super.doFirst(action)
    }

    @TaskAction
    fun apply() {
        val versionTracker = project.extensions.getByType(VersionTrackerExtension::class.java)
        val endpoint: String = versionTracker.endpoint.get()
        try {
            val body: MutableMap<String?, String?> = HashMap()
            body["author"] = versionTracker.author.get()
            body["projectName"] = versionTracker.projectName.get()
            body["gameVersion"] = versionTracker.mcVersion.get()
            body["projectVersion"] = project.version as String
            body["homepage"] = versionTracker.homepage.get()
            body["uid"] = versionTracker.uid.get()
            val req = URL(endpoint).openConnection() as HttpsURLConnection
            req.requestMethod = "POST"
            req.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            req.setRequestProperty("User-Agent", versionTracker.projectName.get() + " Tracker Gradle")
            req.doOutput = true
            req.outputStream.write(JsonOutput.toJson(body).toByteArray(StandardCharsets.UTF_8))
            project.logger.lifecycle("VersionTracker Status Code: " + req.responseCode)
            BufferedReader(InputStreamReader(req.inputStream)).use { reader ->
                project.logger.lifecycle("VersionTracker Response: " + reader.lines().collect(Collectors.joining("\n")))
            }
        } catch (e: Exception) {
            if (e is MalformedURLException) {
                project.logger.error("Invalid endpoint provided! Provided: `$endpoint`")
            }
            e.printStackTrace()
        }
    }
}
