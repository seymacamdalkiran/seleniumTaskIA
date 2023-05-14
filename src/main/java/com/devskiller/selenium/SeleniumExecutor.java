package com.devskiller.selenium;

import netscape.javascript.JSException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class SeleniumExecutor implements Executor {

    private final WebDriver driver;

    public SeleniumExecutor(WebDriver driver) {
        this.driver = driver;
    }

    /// Page 1
    @Override
    public void SetLoginAndClickNext(String login) {
        String text = driver.findElement(By.xpath("(//div[@class='alert alert-primary'])[1]")).getText();
        int firsIndex = text.indexOf("(")+1;
        int lastIndex = text.indexOf(")");
    //    System.out.println("login = " + login);
        String login2 = text.substring(firsIndex, lastIndex);
      //  System.out.println("login2 = " + login2);
        WebElement inputBox = driver.findElement(By.cssSelector("#emailBox"));
        WebDriverWait wait=new WebDriverWait(driver,7);
        wait.until(ExpectedConditions.visibilityOf(inputBox));
      //  JavascriptExecutor js= (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].setAttribute('value', '" + login2 + "')", inputBox);

        inputBox.sendKeys(login2);
        WebElement nextButton = driver.findElement(By.xpath("//button[text()='Next'][@class='buttonLogin']"));
        nextButton.click();

       // js.executeScript("arguments[0].click();", nextButton);
    }

    /// Page 2
    @Override
    public String OpenCodePageAndReturnCode() {
        WebElement openPage = driver.findElement(By.xpath("//a[text()='open page']"));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", openPage);

        String currentTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        for (String allTab : allTabs) {
            if(!allTab.equals(currentTab)){
                driver.switchTo().window(allTab);
            }
        }
        WebElement code = driver.findElement(By.id("code"));
        WebDriverWait wait=new WebDriverWait(driver,7);
        wait.until(ExpectedConditions.visibilityOf(code));

        return code.getAttribute("value");
    }

    @Override
    public void SetCodeAndClickNext(String code) {
        String currentTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();
        for (String allTab : allTabs) {
            if(!allTab.equals(currentTab)){
                driver.switchTo().window(allTab);
            }
        }
        driver.findElement(By.id("codeBox")).sendKeys(code);
        driver.findElement(By.xpath("//button[text()='Next'][@class='buttonLoginCode']")).click();
    }

    /// Page 3
    @Override
    public void FillMaskedPasswordAndClickLogin(String password) {
        driver.switchTo().frame(0);
        WebElement element = driver.findElement(By.xpath("//div[@class='col-sm']"));
        String text = element.getText();

        JavascriptExecutor js= (JavascriptExecutor) driver;
        String elementText = (String) js.executeScript("return arguments[0].innerText;", element);

      //  System.out.println("text = " + text);
     //   System.out.println("elementText = " + elementText);

        String[] s = text.split("\n");
        String[] s1 = s[1].split(" ");
        String devSkills = s1[2];

        List<WebElement> inputs = driver.findElements(By.xpath("//input[@class='passwdField']"));
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).isEnabled()){
                inputs.get(i).sendKeys(devSkills.substring(i,i+1));
            }
        }
        driver.findElement(By.xpath("//button[.='Log in']")).click();


        //throw new org.apache.commons.lang3.NotImplementedException("Implement this method");
    }

    @Override
    public String GetLoggedInText() {
        return driver.findElement(By.id("loggedIn")).getText();

        // throw new org.apache.commons.lang3.NotImplementedException("Implement this method");
    }

}
