package me.smash;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SampleTest {

  private WebDriver webDriver;
  @Before
  public void setUp() throws MalformedURLException {
    webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
        new ChromeOptions());
  }

  @After
  public void tearDown() {
    webDriver.quit();

  }

  @Test
  public void test() throws MalformedURLException {

    webDriver.get("http://localhost:8090");

    assertEquals("----", webDriver.getTitle());
    webDriver.close();
  }

}
