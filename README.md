

## Установка из 

1. Колнирование репозитория
2. Навигация в root директорию проекта
3. Чистая установка через Maven
```shell
mvn clean install
```
4. Создание Docker образа
```shell
docker build -t arenadata-iutkin-test:latest .
```
6. Создание docker сети
```shell
docker network create iutkin-test
```
6. Запуск контейнера с Elasticsearch
```shell
docker run -d --name elasticsearch --network iutkin-test -e "xpack.security.enabled=false" -e "discovery.type=single-node" --cap-add IPC_LOCK elasticsearch:8.12.0
```
7. Запустить контейнер 
```shell
docker run --name arenadata-iutkin-test -p 8080:8080 --network iutkin-test -e DATAPROVIDER_API_KEYS=your-key1,your-key2 arenadata-iutkin-test:latest
```