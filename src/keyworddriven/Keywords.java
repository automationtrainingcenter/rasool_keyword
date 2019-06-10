package keyworddriven;

import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Keywords {
	WebDriver driver;
	Actions actions;
	private String alertTExt;
	
//	openBrowser
	public void openBrowser(String locType, String locValue, String data) {
		if(data.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", GenericHelper.getFilePath("drivers", "chromedriver.exe"));
			driver = new ChromeDriver();
		}else if(data.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", GenericHelper.getFilePath("drivers", "gekcodriver.exe"));
			driver = new FirefoxDriver();
		}
		actions = new Actions(driver);
		driver.manage().window().maximize();
	}

//	navigate
	public void navigate(String locType, String locValue, String data) {
		driver.get(data);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	setTextBox
	public void setTextBox(String locType, String locValue, String data) {
		driver.findElement(LocatorHelper.locate(locType, locValue)).sendKeys(data);
	}

//	click
	public void click(String locType, String locValue, String data) {
		driver.findElement(LocatorHelper.locate(locType, locValue)).click();

	}

//	delay
	public void delay(String locType, String locValue, String data) {
		long timeInSeconds = Long.parseLong(data);
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	selectOption
	public void selectOption(String locType, String locValue, String data) {
		new Select(driver.findElement(LocatorHelper.locate(locType, locValue)))
		.selectByVisibleText(data);
	}

//	getAlertText
	public void getAlertText(String locType, String locValue, String data) {
		alertTExt = driver.switchTo().alert().getText();
	}

//	acceptAlert
	public void acceptAlert(String locType, String locValue, String data) {
		driver.switchTo().alert().accept();
	}

//	closeBrowser
	public void closeBrowser(String locType, String locValue, String data) {
		if(driver.getWindowHandles().size() > 1) {
			driver.quit();
		}else {
			driver.close();
		}
	}
	
	// switchToWindow
	public void switchToWindow(String locType, String locValue, String data) {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowIds = new ArrayList<>(windowHandles);
		driver.switchTo().window(windowIds.get(Integer.parseInt(data)));
	}
	
	//switchToFrame
	public void switchToFrame(String locType, String locValue, String data) {
		WebElement frame = driver.findElement(LocatorHelper.locate(locType, locValue));
		driver.switchTo().frame(frame);
	}
	
	//switchToDefaultContent
	public void switchToDefaultContent(String locType, String locValue, String data) {
		driver.switchTo().defaultContent();
	}
	
	//moveToElement
	public void moveToElement(String locType, String locValue, String data) {
		WebElement ele = driver.findElement(LocatorHelper.locate(locType, locValue));
		actions.moveToElement(ele).build().perform();
	}
	
	//moveToElementAndClick
	public void moveToElementAndClick(String locType, String locValue, String data) {
		WebElement ele = driver.findElement(LocatorHelper.locate(locType, locValue));
		actions.moveToElement(ele).click().build().perform();
	}
	
	//dragAndDropBy
	/**
	 * this method drag an element to location we specified as x and y coordinate value.
	 * provide x and y values separated by , in test data like 100,200
	 */
	public void dragAndDropBy(String locType, String locValue, String data) {
		WebElement ele = driver.findElement(LocatorHelper.locate(locType, locValue));
		String[] coordinates = data.split(",");
		actions.dragAndDropBy(ele, Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
	}
	
	//verifyAlertText
	public boolean verifyAlertText(String locType, String locValue, String data) {
		if(alertTExt.contains(data)) {
			return true;
		}else {
			return false;
		}
	}

	
}
