package ru.colibri.ui.template.steps.ios;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.fields.IElement;
import ru.colibri.ui.core.steps.AbsSteps;
import ru.yandex.qatools.allure.annotations.Step;

@Component
@Qualifier("ios")
public class IOSDynamicFieldSteps extends AbsSteps {

    @Step
    @When("очистить \"$field\"")
    public void clearField(@Named("$field") String field) {
        IElement element = getCurrentPage().getElementByName(field);
        WebElement webElement = finder.findWebElement(element);
        Keyboard keyboard = driver.getKeyboard();
        for (int i = 0; i < webElement.getText().length(); i++) {
            keyboard.sendKeys(Keys.BACK_SPACE);
        }
    }
}
