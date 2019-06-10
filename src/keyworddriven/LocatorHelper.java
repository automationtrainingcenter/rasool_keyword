package keyworddriven;

import org.openqa.selenium.By;

public class LocatorHelper {

	public static By locate(String locType, String locValue) {
		By loc = null;
		locType = locType.toLowerCase().trim();
		locValue = locValue.trim();
		switch (locType) {
		case "id":
			loc = By.id(locValue);
			break;
		case "name":
			loc = By.name(locValue);
			break;
		case "linktext":
			loc = By.linkText(locValue);
			break;
		case "partiallinktext":
			loc = By.partialLinkText(locValue);
			break;
		case "xpath":
			loc = By.xpath(locValue);
			break;
		case "css":
			loc = By.cssSelector(locValue);
			break;
		case "tagname":
			loc = By.tagName(locValue);
			break;
		case "classname":
			loc = By.className(locValue);
			break;
		default:
			System.out.println("invalid locator type");
			break;
		}
		return loc;
	}

}
