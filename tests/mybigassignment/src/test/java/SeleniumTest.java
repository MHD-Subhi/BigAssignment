import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.support.ui.*;

public class SeleniumTest {
    
	private WebDriver driver;
	private WebDriverWait wait;
	
	//XPaths
	private By loginPageButtonLocator = By.xpath("//a[contains(@href,'/home/login/?next=/')]");
	private By mailBoxLocator = By.xpath("//input[@name='username']");
	private By passwordBoxLocator = By.xpath("//input[@name='password']");
	private By loginButtonLocator = By.xpath("//input[@value='login']");
	private By searchBarLocator = By.xpath("//input[@type='text' and @name='q']");
	private By showAdvancedSearchOptionsLocator = By.xpath("//a[text()='Show advanced search options']");
	private By fileNameCheckBoxLocator = By.xpath("//input[@type='checkbox' and @name='a_filename']");
	private By searchButtonLocator = By.xpath("//input[@type='submit' and @value='search' and @id='search_submit']");
	private By peoplePageButtonLocator = By.xpath("//a[@href='/people/' and @id='site_nav_people']");
	private By logOutButtonLocator = By.xpath("//a[text() = 'Log Out']");

    @Before
    public void setup() throws MalformedURLException{
        ChromeOptions options = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
		
		this.wait = new WebDriverWait(driver, 10);
    }
	
	  private WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    @Test
    public void testSelenium() {
        this.driver.get("https://freesound.org/");
		
		//Fill simple form and send (Login)
		WebElement loginPageButton = waitAndReturnElement(loginPageButtonLocator);
		loginPageButton.click();
		
		WebElement mailBox = waitAndReturnElement(mailBoxLocator);
		mailBox.sendKeys("mhdsubhinezam@gmail.com");
		
		WebElement passwordBox = waitAndReturnElement(passwordBoxLocator);
		passwordBox.sendKeys("$^&fhJ467");
		
		WebElement loginButton = waitAndReturnElement(loginButtonLocator);
		loginButton.click();
		
		//Form sending with user (search) - Part 1
		WebElement searchBar = waitAndReturnElement(searchBarLocator);
		
		//Reading the page title
		String title = this.driver.getTitle();
		System.out.println(title);
		
		//Form sending with user (search) Part 2
		searchBar.sendKeys("car\n");
		
		WebElement showAdvancedSearchOptions = waitAndReturnElement(showAdvancedSearchOptionsLocator);
		showAdvancedSearchOptions.click();
		
		WebElement fileNameCheckBox = waitAndReturnElement(fileNameCheckBoxLocator);
		fileNameCheckBox.click();
		
		WebElement searchButton = waitAndReturnElement(searchButtonLocator);
		searchButton.click();
		
		//Static Page test
		WebElement peoplePageButton = waitAndReturnElement(peoplePageButtonLocator);
		peoplePageButton.click();
		
		WebElement contentElement = waitAndReturnElement(By.id("content_full"));
		String pageTitle = contentElement.findElement(By.tagName("h1")).getText();
		String expectedPageTitle = "People";
		assertTrue("Assertion Failed: You are not on the People page", pageTitle.equals(expectedPageTitle));
		
		//Logout
		WebElement logOutButton = waitAndReturnElement(logOutButtonLocator);
		logOutButton.click();
		
		WebElement element = waitAndReturnElement(By.xpath("//a[text() = 'Log In']"));
		boolean isElementPresent = element != null;
		assertTrue("You are still logged-in", isElementPresent);

    }

	
	@After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

}
