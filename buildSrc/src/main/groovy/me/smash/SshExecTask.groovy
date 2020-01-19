package me.smash

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

class SshExecTask extends DefaultTask {
    @Input
    String command
    @Input
    String username
    @Input
    File keyFile
    @Input
    String remoteHost
    @Input
    int port = 22
    @InputFiles
    FileCollection classpath

    @TaskAction
    void action() {
        ant.taskdef(name: "sshexecJsch", classpath: classpath.asPath, classname: "org.apache.tools.ant.taskdefs.optional.ssh.SSHExec")
        ant.sshexecJsch(host: remoteHost, username: username, keyfile: keyFile.path, command: command, trust: "true", port: port)
    }
}
