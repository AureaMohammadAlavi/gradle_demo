package me.smash

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

class ScpTask extends DefaultTask {
    @InputFile
    File file
    @Input
    String destinationDir
    @Input
    String username
    @Input
    File keyFile
    @Input
    String remoteHost
    @InputFiles
    FileCollection classpath

    @TaskAction
    void action() {
        ant.taskdef(name: "scpJsch", classpath: classpath.asPath, classname: "org.apache.tools.ant.taskdefs.optional.ssh.Scp")
        ant.scpJsch(file: file.path, todir: "$username@$remoteHost:$destinationDir", keyfile: keyFile.canonicalPath, verbose: "true", trust: "yes")
    }
}
