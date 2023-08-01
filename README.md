[![Check formatting and coding standards](https://github.com/AlexBugrimov/well-equipment/actions/workflows/checkstyle.yml/badge.svg)](https://github.com/AlexBugrimov/well-equipment/actions/workflows/checkstyle.yml)

# Оборудование на скважинах

## Task
Программа, работающая с БД SQLite, которая организовывает работу с оборудованием на скважинах со следующими функциями:

* Создание N кол-ва оборудования на скважине. 
  * При выборе этого пункта пользователь указывает кол-во оборудования и имя скважины. 
  * Программа создает указанное кол-во оборудования на скважине с указанным именем. 
  * При создании оборудования каждому присваивается свой id и свое уникальное имя (генерируется программой с использованием латинских букв и цифр). 
  * Скважина также создается программой с указанным именем, если ее еще нет в таблице.

* Вывод общей информации об оборудовании на скважинах. 
  * При выборе этого пункта пользователь указывает имена скважин, разделяя их пробелами или запятыми. 
  * Программа подсчитывает кол-во оборудования на каждой скважине и выдает на экран таблицу вида "имя скважины - кол-во оборудования".

* Экспорт всех данных в xml файл.
При выборе этого пункта пользователь указывает имя файла.
Программа выбирает все скважины из базы и записывает данные по ним и оборудованию в xml формате в виде (одна и та же скважина или оборудование не могут повторяться несколько раз):

```xml
<dbinfo>
    <wellEntity name="АААА"  id="123">
        <equipmentEntity name="EQ0033" id="12"/>
        <equipmentEntity name="EQ0034" id="13"/>
    </wellEntity>
    <wellEntity name="BBBB"  id="124">
        <equipmentEntity name="EQ0038" id="11"/>
        <equipmentEntity name="EQ0039" id="14"/>
    </wellEntity>
</dbinfo>
```

### Дополнительная информация:

* Проект оформлен для сборки при помощи maven.
* Перечисленные пункты реализованы в виде параметров командной строки.
* Для доступа к БД использовался sqllite-jdbc.
* Файл базы данных test.db располагается в каталоге запуска программы.
* Если файла еще нет, программа сама создаст БД и все необходимые таблицы в ней.

### Таблицы для БД:

Well - скважина.
id: уникальный идентификатор записи (первичный ключ), генерируется средствами БД.
name Varchar(32) - имя скважины, уникальное, не может повторяться и быть пустым.

Equipment – оборудование на скважине.
id: уникальный идентификатор записи (первичный ключ), генерируется средствами БД.
name Varchar(32) - имя оборудования, уникальное, не может повторяться и быть пустым.
Well_id: ссылка на Well.id - id скважины, на которой установлено оборудование.