import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

waiting {
    timeout = 10
}
environments {
    remote {
        driver = {
            def url = new URL(System.getenv("REMOTE_WEB_DRIVER_URL") ?:
                    "http://localhost:4444/wd/hub")
            new RemoteWebDriver(url, DesiredCapabilities.chrome())
        }
    }
}