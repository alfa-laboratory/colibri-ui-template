package ru.colibri.template.steps.ios;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.exception.NotValueOnPickerException;
import ru.alfabank.autotest.core.fields.Element;
import ru.alfabank.autotest.core.fields.IElement;
import ru.alfabank.autotest.settings.general.PropertyUtils;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

@Component
public class PickerWheelSteps extends BaseIOSPickerWheelSteps {

    private String oldValue = "";
    @Autowired
    private PropertyUtils propertyUtils;

    @Step
    @When("выбрать \"$item\" в \"$field\"")
    public void findAccount(@Named("$item") String item, @Named("$field") String field) {
        String pageName = "Выбор счета";
        String buttonName = "Готово";
        item = propertyUtils.injectProperties(item);
        IElement cell = getCurrentPage().getElementByName(field);
        finder.findWebElement(cell).click();
        setFirstPickerWheelValue();
        IElement element = pageProvider.getPageByName(pageName).getElementByName(buttonName);
        finder.findWebElement(element).click();
        boolean found = checkSelected(field, item);
        while (!found) {
            IElement accountElement = getCurrentPage().getElementByName(field);
            finder.findWebElement(accountElement).click();
            setNextPickerWheelValue();
            element = pageProvider.getPageByName(pageName).getElementByName(buttonName);
            finder.findWebElement(element).click();
            found = checkSelected(field, item);
        }
    }


    private boolean checkSelected(String elementName, String account) {
        IElement parent = getCurrentPage().getElementByName(elementName);
        IElement nested = new Element("Любой элемент", "", "", "", "//*", false);
        List<WebElement> nestedWebElements = finder.findNestedWebElements(parent, nested);
        Assert.assertTrue("Список пуст. Проверьте локаторы.", nestedWebElements.size() > 0);
        String name = "";
        for (WebElement webElement : nestedWebElements) {
            name = webElement.getAttribute("name");
            if ((name != null) && name.contains(account)) {
                return true;
            }
        }
        if (name.equals(oldValue)) {
            throw new NotValueOnPickerException(account);
        }
        oldValue = name;
        return false;
    }
}
