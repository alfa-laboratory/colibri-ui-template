Meta:
@regressCycle
@smokeCycle

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: scenario description
Then загружена страница "Главный экран"
When выполнено нажатие на "Шаблоны"
Then загружена страница "Платежи и переводы"
When скролл внутри "Список шаблонов" до "<templateName>"
When горизонтальный свайп "<templateName>" вправо


Examples:
|templateName  |
|АКАДО интернет|