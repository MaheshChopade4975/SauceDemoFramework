package StepDefination;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.config;
import utils.screenshotutils;

public class stepDefinations {
	WebDriver driver = Hooks.driver;

	@Given("User launches the SauceDemo application")
	public void user_launches_the_sauce_demo_application() {

		config.loadProperties();
		driver.get(config.get("Url"));
	}

	@When("User verifies the title of the page")
	public void verifyTitle() {
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Swag Labs");
	}

	@When("User logs in with valid credentials")
	public void user_logs_in_with_valid_credentials() throws InterruptedException {
		String username = config.get("username");
		String password = config.get("password");

		driver.findElement(By.id("user-name")).sendKeys(username);
		Thread.sleep(5000);
		driver.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(5000);
		driver.findElement(By.id("login-button")).click();
		Thread.sleep(3000);
		try {
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert text: " + alert.getText());
			alert.accept();
		} catch (NoAlertPresentException e) {
			System.out.println("No alert displayed.");
		}
	}

	@When("User adds multiple items to the cart")
	public void addItemsToCart() throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
	}

	@When("User proceeds to checkout and provides information")
	public void checkoutInfo() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.className("shopping_cart_link")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("checkout")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("first-name")).sendKeys("Shubham");
		Thread.sleep(3000);
		driver.findElement(By.id("last-name")).sendKeys("Shirguppe");
		Thread.sleep(3000);
		driver.findElement(By.id("postal-code")).sendKeys("416202");
		Thread.sleep(3000);
		driver.findElement(By.id("continue")).click();
	}

	@When("User clicks on finish button")
	public void clickFinish() {
		driver.findElement(By.id("finish")).click();
	}

	@Then("Order confirmation message should be displayed as {string}")
	public void verifyOrderConfirmation(String expectedMessage) throws IOException {
		String actualMessage = driver.findElement(By.className("complete-header")).getText();
		Assert.assertEquals(actualMessage, expectedMessage);
		screenshotutils.captureScreenshot(driver, "OrderConfirmation");
	}
}
