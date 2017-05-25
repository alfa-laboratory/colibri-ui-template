package ru.colibri.template.steps.android;

import org.jbehave.core.annotations.Given;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.alfabank.autotest.core.settings.DriversSettings;
import ru.alfabank.autotest.steps.general.AbsServiceSteps;
import ru.alfabank.autotest.steps.general.ISystemButtonsClick;
import ru.alfabank.autotest.steps.general.PagesSteps;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by dolinskiyaleksandr on 13.03.17.
 */
@Component
public class AndroidServiceSteps extends AbsServiceSteps {
    @Autowired
    private DriversSettings driversSettings;

    @Autowired
    private PagesSteps pagesSteps;

    @Autowired
    @Qualifier("android")
    private ISystemButtonsClick systemButtonsClick;


    private String alertLocator = "//*[@text='Закрыть']";


    @Override
    protected ISystemButtonsClick getISystemButtonsClickBean() {
        return systemButtonsClick;
    }


    @Override
    protected DriversSettings getDriversSettings() {
        return driversSettings;
    }

    @Override
    protected String getAlertLocator() {
        return alertLocator;
    }

    @Step
    @Given("вернуться на \"$screenName\"")
    public void goToScreen(@org.jbehave.core.annotations.Named("$screenName") String screenName) {
        goToMain(screenName);
    }

    protected void returnCycle(String screenName) {
        boolean up = true;

        do {
            try {
                //обрабатываем аллерт
                finder.findWebElement(By.xpath(getAlertLocator())).click();
            } catch (Exception ignored) {
            }
            try {
                //проверяем что загружена главная
                pagesSteps.pageLoaded(screenName);
                up = false;
            } catch (Exception e) {
                try {
                    //если не загружена, пытаемся нажать кнопку "Назад"
                    getISystemButtonsClickBean().systemBackClick();
                } catch (Exception ignored) {

                }
            }

        }
        while (up);
    }
}
