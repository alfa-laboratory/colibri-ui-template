package ru.colibri.ui.template.steps.ios;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import ru.colibri.ui.core.steps.AbsSteps;
import ru.yandex.qatools.allure.annotations.Step;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

/**
 * Пример класса содержащего шаги для iOS
 */
@Component
public class IOSDateSteps extends AbsSteps {
    private SimpleDateFormat dateFormat;
    private Date date;
    private String day;

    @Step
    @When("выполнено нажатие на текущую дату в формате \"$format\" в календаре")
    public void tapToCurrentDateOnCalendar(@Named("$format") String format) {
        dateFormat = new SimpleDateFormat(format);
        date = new Date();
        day = dateFormat.format(date);
        driver.findElement(By.xpath(format("//*[@name = '%s' and ancestor::*[@name='CalendarView']]", day))).click();
    }

    @Step
    @When("выполнено нажатие на текущую дату в формате \"$format\"")
    public void tapToCurrentDate(@Named("$format") String format) {
        dateFormat = new SimpleDateFormat(format);
        date = new Date();
        day = dateFormat.format(date);
        driver.findElement(By.xpath(format("//*[contains(@name, '%s')]", day))).click();
    }

    @Step
    @Then("на экране есть дата в формате \"$format\"")
    public void checkDate(@Named("format") String format) {
        dateFormat = new SimpleDateFormat(format);
        date = new Date();
        day = dateFormat.format(date);
        driver.findElement(By.xpath(format("//*[contains(@name, '%1$s') or contains(@text,'%1$s')]", day)));
    }
}
