package me.smash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

public class Main {

  public static void main(String[] args) {
    GradleConnector connector = GradleConnector.newConnector();
    ProjectConnection connection = connector.useGradleVersion("5.1.1")
        .forProjectDirectory(new File("/Users/mohammad/Downloads/test/gradle/gradle-demo/web"))
        .connect();
    BuildLauncher buildLauncher = connection.newBuild();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    buildLauncher.forTasks("clean", "check").setStandardOutput(baos).run();
    connection.close();
    System.out.println(new String(baos.toByteArray(), StandardCharsets.UTF_8));
  }

}
