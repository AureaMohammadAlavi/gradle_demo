package me.smash

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class XmlToHtml extends DefaultTask {
    @InputFile
    File xmlFile
    @InputFile
    File styleFile
    @Input
    @Optional
    Map<String, String> params = new HashMap<>()
    @OutputFile
    File htmlFile


    @TaskAction
    void action() {
        ant.xslt(in: xmlFile.path, out: htmlFile.path, style: styleFile.path) {
            params.each { k, v ->
                ant.param(name: k, expression: v)
            }
        }
    }
}
