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
    webDriver = new RemoteWebDriver(new URL("http://172.18.0.4:4444/wd/hub"),
        new ChromeOptions());
  }

  @After
  public void tearDown() {
    if (webDriver != null) {
      webDriver.quit();
    }
  }

  @Test
  public void test() throws MalformedURLException {

    webDriver.get("http://172.18.0.3:8090");

    assertEquals("----", webDriver.getTitle());
    webDriver.close();
  }

}
