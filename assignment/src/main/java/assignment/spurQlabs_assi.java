package assignment;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class spurQlabs_assi {

	private WebDriver driver;

	@BeforeClass
	public void setUp() {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in/");
	}

	@BeforeMethod
	public void resetState() {
		driver.get("https://www.amazon.in/");
	}

	@Test(priority = 1)
	//STEP1
	public void testSearchNonExistingProduct() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("ld345tsxslfer....--qq");
		driver.findElement(By.id("nav-search-submit-button")).click();

		WebElement noResultsMessage = driver.findElement(By.xpath("//span[contains(text(), 'No results for')]"));
		Assert.assertTrue(noResultsMessage.isDisplayed(), "No results message is displayed.");

		clearSearchBox();
	}

	@Test(priority = 2)
	//STEP2
	public void testSearchExistingProduct() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Laptop");
		driver.findElement(By.id("nav-search-submit-button")).click();

		WebElement firstResult = driver.findElement(By.cssSelector(".s-main-slot .s-result-item"));
		Assert.assertTrue(firstResult.getText().contains("Laptop"), "Search results contain 'Laptop'.");

		clearSearchBox();
	}

	@Test(priority = 3)
	//STEP3
	public void testAddToCart() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Laptop");
		driver.findElement(By.id("nav-search-submit-button")).click();

		// Wait for search results to load
		WebElement fourthResult = driver.findElements(By.cssSelector(".s-main-slot .s-result-item")).get(3);
		fourthResult.click();

		// method to add cart process
		driver.findElement(By.id("add-to-cart-button")).click();

		// Verify product is added to the cart
		WebElement cartIcon = driver.findElement(By.id("nav-cart"));
		cartIcon.click();

		WebElement cartProduct = driver.findElement(By.cssSelector(".sc-product-title"));
		Assert.assertTrue(cartProduct.isDisplayed(), "Product is displayed in the cart.");

		// Optional: Verify the correct quantity and price in the cart
		WebElement cartQuantity = driver.findElement(By.cssSelector(".sc-item-quantity"));
		Assert.assertTrue(cartQuantity.getText().contains("1"), "Quantity is correct.");

		driver.navigate().back(); // Navigate back to the homepage
	}

	@Test(priority = 4)
	//STEP4
	public void testUpdateQuantity() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Laptop");
		driver.findElement(By.id("nav-search-submit-button")).click();

		WebElement fourthResult = driver.findElements(By.cssSelector(".s-main-slot .s-result-item")).get(3);
		fourthResult.click();

		WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
		addToCartButton.click();

		WebElement cartIcon = driver.findElement(By.id("nav-cart"));
		cartIcon.click();

		WebElement quantityDropdown = driver.findElement(By.cssSelector(".a-dropdown-prompt"));
		quantityDropdown.click();

		// second item added

		WebElement quantityOption = driver.findElement(By.xpath("//a[text()='2']"));
		quantityOption.click();

		WebElement quantityValue = driver.findElement(By.cssSelector(".a-dropdown-prompt"));
		Assert.assertEquals(quantityValue.getText(), "2", "Quantity was updated correctly.");

		driver.navigate().back(); // Navigate back to home page
	}

	@Test(priority = 5)
		public void testRemoveProductFromCart() throws InterruptedException {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("Laptop");
		driver.findElement(By.id("nav-search-submit-button")).click();

		WebElement fourthResult = driver.findElements(By.cssSelector(".s-main-slot .s-result-item")).get(3);
		fourthResult.click();

		WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
		addToCartButton.click();
		Thread.sleep(2000);
		WebElement cartIcon = driver.findElement(By.id("nav-cart"));
		cartIcon.click();

		// removed added item in addcart
		//STEP5

		WebElement removeButton = driver.findElement(By.cssSelector(".a-declarative .sc-action-delete"));
		removeButton.click();

		WebElement emptyCartMessage = driver
				.findElement(By.xpath("//h2[contains(text(), 'Your Amazon Cart is empty')]"));
		Assert.assertTrue(emptyCartMessage.isDisplayed(), "Cart is empty.");

		// Navigate back to home page

		driver.navigate().back();
	}

	private void clearSearchBox() {
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.clear();
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
