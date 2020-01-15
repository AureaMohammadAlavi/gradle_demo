import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

waiting {
    timeout = 10
}
reportsDir = "build/test/funcTest/geb"
environments {
    remote {
        driver = { new RemoteWebDriver(new URL("http://blueo-selenium:4444/wd/hub"), DesiredCapabilities.chrome()) }
    }
}
