/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/#configuration
*/
import org.openqa.selenium.chrome.ChromeDriver

waiting {
	timeout = 2
}

environments {
	chrome {
		driver = { new ChromeDriver() }
	}
}