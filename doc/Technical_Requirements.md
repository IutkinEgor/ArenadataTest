# Техническое задание

Необходимо разработать приложение на Java, которое периодически собирает информацию о криптовалютах из CoinMarketCap, обходит ограничения API-rate, а также производит обработку данных средствами Elastic-a. 

## Требования: 
### Сбор данных: 
Используйте бесплатный аккаунт на CoinMarketCap (https://coinmarketcap.com/) 
для получения данных о криптовалютах. Данные собирать с минимально допустимым 
интервалом (возможно и нужно наткнуться на проблемы с API-rate-ом) 
Реализуйте по возможности (возможно, в виде диаграмм и схем в предпочитаемой 
вами нотации) механизм обхода ограничений API-rate лимитов для минимизации 
риска блокировки запросов. Рассмотрите варианты использования нескольких 
аккаунтов с переключением между ними или паузой между запросами. 
Внимательно обрабатывайте ошибки API-rate и предусмотрите их обработку в коде. 

### Хранение данных: 
* Используйте Elasticsearch для хранения данных о криптовалютах; 
* Реализуйте логику индексации данных в Elasticsearch с учетом обработанных метрик; 

_Если потребуется, для прочих задач использовать Postgres и Hibernate._

### Обработка данных:
Проанализируйте полученные данные и реализуйте дополнительную обработку 
средствами Elastic-a, такую как:
* Вычисление средней цены криптовалюты за последний час;
* Определение криптовалюты с максимальным процентным изменением цены за последний день;

### Тестирование: 
Покройте e2e тестами на Java (найти и использовать любую библиотеку/фреймворк, 
только с открытым исходным кодом). 

### Примечание: 
* Java не ниже 17;
* Код на github-e; 
* Допускается реализовывать вышеописанное на исторических данных; 
* Все разворачивать docker-ом; 
* Не использовать Spring