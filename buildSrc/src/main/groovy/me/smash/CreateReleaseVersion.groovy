package me.smash

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction


class CreateReleaseVersion extends DefaultTask {
    @Input
    boolean release
    @OutputFile
    File destFile

    @TaskAction
    void action() {
        ant.propertyfile(file: destFile) {
            entry(key: "release", type: "string", value: "true", operation: "=")
        }
    }
}
