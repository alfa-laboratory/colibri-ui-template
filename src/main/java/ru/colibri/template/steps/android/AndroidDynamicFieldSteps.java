package ru.colibri.template.steps.android;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.fields.IElement;
import ru.alfabank.autotest.core.steps.AbsSteps;
import ru.alfabank.autotest.settings.general.PropertyUtils;
import ru.yandex.qatools.allure.annotations.Step;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Пример класса содержащего шаги для Андроид
 */
@Component
@Qualifier("android")
public class AndroidDynamicFieldSteps extends AbsSteps {

    private static final String DYNAMIC_FIELDS_PAGE_NAME = "Динамические поля";
    private static final String EDIT_TEXT_NAME = "Поле для ввода";
    @Autowired
    private PropertyUtils propertyUtils;

    @Step
    @When("заполнить динамическое поле \"$field\" значением \"$value\"")
    public void sendKeysInDynamicFields(@Named("$field") String field, @Named("$value") String value) {
        IElement parent = getCurrentPage().getElementByName(field);
        IElement nested = pageProvider.getPageByName(DYNAMIC_FIELDS_PAGE_NAME).getElementByName(EDIT_TEXT_NAME);
        finder.findNestedWebElement(parent, nested).sendKeys(value);
    }

    @Step
    @Then("значение динамического поля \"$field\" равно \"$expectedValue\"")
    public void stepCheckValueInDynamicFields(@Named("$field") String field, @Named("$expectedValue") String expectedValue) {
        IElement parent = getCurrentPage().getElementByName(field);
        IElement nested = pageProvider.getPageByName(DYNAMIC_FIELDS_PAGE_NAME).getElementByName(EDIT_TEXT_NAME);
        WebElement elementFound = finder.findNestedWebElement(parent, nested);
        String actualElement = elementFound.getText();
        assertEquals(format("Значение поля [%s] не соответствует ожидаемому [%s]", actualElement, expectedValue), expectedValue, actualElement);
    }

    @Step
    @When("ввод текста с клавиатуры \"$textOrKeyword\" посимвольно")
    public void sendTextDelay(@Named("$textOrKeyword") String textOrKeyword) {
        String text = propertyUtils.injectProperties(textOrKeyword);
        String eachLetter;
        Keyboard keyboard = driver.getKeyboard();
        for (int i = text.length(); i > 0; i--) {
            eachLetter = String.valueOf(text.charAt(i - 1));
            keyboard.sendKeys(eachLetter);
        }
    }

    @Step
    @When("очистить \"$fieldName\"")
    public void clearField(@Named("$fieldName") String fieldName) {
        IElement element = getCurrentPage().getElementByName(fieldName);
        WebElement webElement = finder.findWebElement(element);
        Keyboard keyboard = driver.getKeyboard();
        for (int i = webElement.getText().length(); i > 0; i--) {
            keyboard.sendKeys(Keys.BACK_SPACE);
        }
    }


    @Step
    @Then("элемент \"$fieldName\" присутствует на странице")
    public void stepCheckNotExistField(String fieldName) {
        IElement element = getCurrentPage().getElementByName(fieldName);
        WebElement webElement = finder.findWebElement(element);
        assertThat("Элемент не найден на странице", webElement.isDisplayed());
    }
}