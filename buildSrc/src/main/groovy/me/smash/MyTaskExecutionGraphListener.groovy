package me.smash

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener

class MyTaskExecutionGraphListener implements TaskExecutionGraphListener {
    @Override
    void graphPopulated(TaskExecutionGraph graph) {
        if (graph.hasTask(":release")) {
            Task releaseTask = graph.allTasks.find {it.path == ":release"}
            Project project = releaseTask.project

            project.logger.quiet("------------")

            project.ant.propertyfile(file: project.versionFile) {
                entry(key: "release", type: "string", value: "true", operation: "=")
            }
        }
    }
}

