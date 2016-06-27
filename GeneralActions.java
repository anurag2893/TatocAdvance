import org.openqa.selenium.WebDriver;

public class GeneralActions{
	WebDriver driver;
	public void setDriver(WebDriver driver){
		this.driver=driver;
	}
	public void getURL(String url){
		this.driver.get(url);
	}
	public WebDriver getDriver(String browserName){
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "/home/anuraggarg/Downloads/chromedriver");
			return new ChromeDriver();
		}
		if(browserName.equals("firefox")){
			return new FireFoxDriver();
		}
		return null;
	}
}