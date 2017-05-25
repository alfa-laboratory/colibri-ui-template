package ru.colibri.template.steps.ios;

import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.steps.AbsSteps;
import ru.alfabank.autotest.steps.general.ISystemButtonsClick;
import ru.yandex.qatools.allure.annotations.Step;

import static java.lang.String.format;

@Component
@Qualifier("ios")
public class IOSSystemSteps extends AbsSteps implements ISystemButtonsClick {

    @Step
    @When("выполнено нажатие на Назад")
    public void systemBackClick() {
        String backButton = "AB back";
        driver.findElement(By.xpath(format("//*[contains(@name, '%s')]", backButton))).click();
    }

    @Step
    @When("скрыть клавиатуру")
    public void printMarkup() {
        driver.getKeyboard().sendKeys(Keys.RETURN);
    }
}
