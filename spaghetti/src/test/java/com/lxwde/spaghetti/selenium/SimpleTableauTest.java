package com.lxwde.spaghetti.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Ignore
public class SimpleTableauTest {

    private List<WebDriver> drivers = Collections.synchronizedList(new ArrayList<>());
    private List<Double> durations = Collections.synchronizedList(new ArrayList<>());
    private List<Double> durationsWithCache = Collections.synchronizedList(new ArrayList<>());
    private final int NUM_OF_CLIENTS = 30;
    private ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_CLIENTS);

    private CountDownLatch latch = new CountDownLatch(NUM_OF_CLIENTS);

    @Before
    public void setUp() {
        drivers.clear();
        durations.clear();
        System.setProperty("webdriver.chrome.driver","lib/chromedriver");
        // System.setProperty("webdriver.safari.driver", "/user/bin/safaridriver"); // don't need any further config

        for (int i = 0; i < NUM_OF_CLIENTS; i++) {
            navigateToNativeTableauPage(new ChromeDriver(), true);

        }
    }

    @After
    public void tearDown() {
        for(WebDriver driver:drivers) {
            driver.quit();
        }

        System.out.println(durations);
        System.out.println(durationsWithCache);
    }

    @Test
    public void testLogin() {
        for(int i = 0; i < NUM_OF_CLIENTS; i++) {
            final int x  = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        navigateToNativeTableauPage(drivers.get(x), false);


                    } catch(Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });

        }

        try {
            latch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void navigateToNativeTableauPage(WebDriver driver, boolean needLogin) {
        //DesiredCapabilities DesireCaps = new DesiredCapabilities();
        //DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C://phantomjs.exe");
        //WebDriver driver = new PhantomJSDriver(DesireCaps);

        // WebDriver driver = new ChromeDriver();
        // WebDriver driver = new SafariDriver();
        // WebDriver driver = new HtmlUnitDriver();
        drivers.add(driver);

        StopWatch stopWatch = new StopWatch();
        //stopWatch.start();
        driver.get("http://121.40.123.43/#/views/App/sheet0?:iid=1");


        // System.out.println(driver.getPageSource());
        if (needLogin) {
            fluentWait(driver, By.tagName("button"), 10, 1);
            WebElement btn = (new WebDriverWait(driver, 100))
                    .until(ExpectedConditions.elementToBeClickable(By.tagName("button")));

            List<WebElement> elements = driver.findElements(By.tagName("input"));
            System.out.println(elements);
            elements.get(0).sendKeys("Publisher_Zhang");
            elements.get(1).sendKeys("zhang123456");

            btn.click();
        }

        stopWatch.start();

        fluentWait(driver, By.tagName("iframe"), 10, 1);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.tagName("iframe")));

        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

        fluentWait(driver, By.id("tabZoneId1"), 10, 1);
        WebElement element = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.id("tabZoneId1")));

        stopWatch.stop();
        if (needLogin) {
            durations.add(stopWatch.getTotalTimeSeconds());
        } else {
            durationsWithCache.add(stopWatch.getTotalTimeSeconds());
        }

    }

    @Test
    public void testFetch() {

        for(int i = 0; i < NUM_OF_CLIENTS; i++) {
            executor.execute(() -> {
                try{
                    WebDriver driver = new ChromeDriver();
                    drivers.add(driver);
                    // driver.get("http://121.40.123.43/#/views/App/sheet0?:iid=1");
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    driver.get("http://121.40.123.43:8088/tableau?sheet=App/sheet0");


                    System.out.println(driver.getPageSource());
                    driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

                    WebElement element = (new WebDriverWait(driver, 100))
                            .until(ExpectedConditions.elementToBeClickable(By.id("tabZoneId1")));

                    stopWatch.stop();
                    durations.add(stopWatch.getTotalTimeSeconds());
                    WebElement ele = driver.findElement(By.id("primaryContentLink"));
                    System.out.println(ele);

                } catch(Exception e) {
                    e.printStackTrace();
                } finally{
                    latch.countDown();
                }
            });

        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public WebElement fluentWait(WebDriver driver, final By locator, int timeout, int pollingInterval) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(pollingInterval, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(driver1 -> driver1.findElement(locator));

        return  foo;
    };
}
