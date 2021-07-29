# Повелители и планеты (учебный проект)

3141 год.
Вселенная исследована и поделена.
Верховный правитель назначает Повелителей Планет, общее количество которых исчисляется миллионами.
Опытные Повелители могут одновременно управлять несколькими Планетами. Никакой демократии, поэтому одной планетой может править только один Повелитель.
Все это безобразие требует системы учета и надзора.*

Задание:
Разработать Spring Boot приложение на Java.
Приложение должно иметь API и работать с реляционной БД. Для простоты отладки это может быть in-memory БД, например HSQLDB или иная.

Базовые характеристики сущностей.
Повелитель: Имя и Возраст
Планета: Название
Один Повелитель может управлять несколькими Планетами, но одной Планетой может править только один Повелитель.

Необходимо разработать структуру таблиц для хранения Повелителей и Планет и связь между ними.

Поддержать методы API:
- Добавить нового Повелителя
- Добавить новую Планету
- Назначить Повелителя управлять Планетой
- Уничтожить Планету
- Найти всех Повелителей бездельников, которые прохлаждаются и не управляют никакими Планетами
- Отобразить ТОП 10 самых молодых Повелителей

Написать тесты для этого функционала

Код расположить в GitHub.

Дополнительно (будет большим плюсом, но не обязательно):
- Создать примитивный web интерфейс, в котором будут работать все методы API (красота, дизайн, usability оцениваться НЕ будут).
- Написать тест на Selenium, который будет проверять работу интерфейса.