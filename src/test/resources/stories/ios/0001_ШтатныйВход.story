Meta:
@oldRegistration

Narrative:

TESTAM-111 Авторизация с устройства без TouchID первый вход

As a user
I want to perform an action
So that I can achieve a business goal

Scenario: Логин
Given приложение запущено
Then загружена страница "Страница логина"
When поле "Логин" заполняется значением "#userName#"
When поле "Пароль" заполняется значением "#password#"
When выполнено нажатие на "Войти"
Then загружена страница "Уведомления"
When выполнено нажатие на "Нет"
Then загружена страница "Главный экран"

Examples:
|password  |
|#password#|