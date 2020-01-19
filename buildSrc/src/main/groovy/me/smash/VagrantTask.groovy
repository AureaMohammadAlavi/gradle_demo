package me.smash

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

class VagrantTask extends DefaultTask {
    @Input
    List<String> commands
    @InputDirectory
    File dir

    @TaskAction
    void action() {
        commands.add(0, "vagrant")
        Process process = commands.execute(null, dir)
        process.consumeProcessOutput(System.out, System.err)
        process.waitFor()

        if (process.exitValue() != 0) {
            throw new GradleException("process exited with ${process.exitValue()}")
        }
    }
}
