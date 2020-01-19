package me.smash

import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class SmokeTestTask extends DefaultTask {
    @Input
    String host
    @Input
    int port
    @Input
    String path

    @TaskAction
    void action() {
        def url = "http://$host:$port"
        def http = new HTTPBuilder(url)
        def html = http.get(path: path) { resp, reader ->
            resp.status
        }

        if (html != HttpURLConnection.HTTP_OK) {
            throw new GradleException("${url}${path} returned $html")
        }
    }
}
