package com.n11.test.steps;

import com.n11.test.base.BaseTest;
import com.thoughtworks.gauge.Step;
import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.Keys;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepImplementation extends BaseTest {

    private Object ArrayList;

    @Step({"Go to <url> "})
    public void goToUrl(String url) {
        driver.get(url);
        logger.info(" going to " + url + " address ");
        assertEquals("https://www.n11.com", url);
    }

    @Step({"Send enter to <element> element"})
    public void sendEnter(String element) {
        driver.findElement(By.xpath(element)).sendKeys(Keys.ENTER);
        logger.info(" enter sended to element ");
    }

    @Step({"Click <element> element by css"})
    public void clickElementWithCss(String element) {
        driver.findElement(By.cssSelector(element)).click();
    }

    @Step({"Click <element> element by xpath"})
    public void clickElementWithXpath(String element) throws IOException {
        driver.findElement(By.xpath(element)).click();
        logger.info(" clicked element ");
        takeScreenshot();
    }

    @Step({"Get <element> element text"})
    public void getElementText(String element) throws IOException {
        driver.findElement(By.xpath(element)).getText();
        logger.info(" text is found ");
        takeScreenshot();
    }

    @Step({"Wait a <int> second "})
    public void waitBySeconds(int seconds) {
        try {
            logger.info(" waiting for " + seconds + " seconds ");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step({"Find <element> element by xpath and write <text> text"})
    public void writeText(String element, String text) throws IOException {
        driver.findElement(By.xpath(element)).sendKeys(text);
        logger.info(" text writing ");
        takeScreenshot();
    }

    @Step("Take a screenshot")
    public void takeScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(".gauge/screenshots/lastpng"));
    }

    @Step("Go back to previous page")
    public void previousPage() throws IOException {
        driver.navigate().to("https://www.n11.com/arama?q=ayfon");
        logger.info(" back to previous page ");
        takeScreenshot();
    }

    @Step("Scroll Down")
    public void scrollDown() throws IOException {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        takeScreenshot();
    }

    @Step({"Get cheapest price"})
    public void getPrices() throws IOException {
        int basket = 1;
        String selector = "//div[@class='priceArea']";
        int pricesCount = driver.findElements(By.xpath(selector)).size();
        System.out.println("Count of price: " + pricesCount);
        for (int i = 0; i < pricesCount; i++) {
            ArrayList<WebElement> Menu = new ArrayList<WebElement>(driver.findElements(By.xpath(selector)));
            WebElement price = Menu.get(i);
            String priceWithoutTL = price.getText();
            String[] array = priceWithoutTL.split("TL");
            for (int j = 0; j < array.length; j++) {
            }
            for (int k = 0; k < array.length; k++) {
                String newPrice = array[k];
                String newPrice2 = newPrice.replace(",", "");
                String newPrice3 = newPrice2.replace(".", "");
                String newPrice4 = newPrice3.replace(" ", "");
                int finalPrice = Integer.parseInt(newPrice4);
                System.out.println(" Price of product is " + finalPrice + " TL ");
                ArrayList<Integer> lowToHigh = new ArrayList<Integer>(finalPrice);
                Collections.sort(lowToHigh);
                findMinimum(lowToHigh, 0);
                System.out.println(" Cheapest product is ---> " + finalPrice + " TL ");
            }
        }
        clickElementWithXpath("(//span[@class='spinnerUp spinnerArrow'])[3]");
        System.out.println("increase cheapest product count plus 1");
        clickElementWithXpath("(//span[@class='spinnerUp spinnerArrow'])[3]");
        System.out.println("increase cheapest product count  plus 1");
    }


    @Step("Sort list")
    public void findMinimum(ArrayList<Integer> a, int start) throws IOException {
        int min = start;
        for (int i = start; i < a.size(); i++) {
            if (a.get(i) < a.get(min)) {
                min = i;
                System.out.println("minimum price is :" + i);
            }
            Assertions.fail("List cannot sorted");
        }
    }
    @Step("Export list to Excel")
    public void exportLinks () throws Exception {
        File file = new File("src/test/java/excel/excel.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sh = wb.createSheet();
        sh.createRow(0).createCell(0).setCellValue("Links");
        try {
            FileOutputStream fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info(" exporting to excel ");
    }
}



