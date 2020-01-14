import org.openqa.selenium.chrome.ChromeDriver


reportsDir = "target/geb"
environments {
	chrome {
		driver = { new ChromeDriver() }
	}
}