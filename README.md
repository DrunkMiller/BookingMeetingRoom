# Booking Meeting Room

Приложение для бронирования переговорных комнат.

Развертывание Docker-контейнера с базой данных Postgres:
1. Переходим в директории docker.
2. Запуск контейнера: docker-compose up
3. Остановка и удаление контейнеров: docker-compose down
4. Просмотр всех образов: docker image list -a
5. Удаление образа: docker rmi -f [id or name image]
6. Перечисление всех контейнеров: docker-compose ps -a 

Доступ к приложению по ip: 0.0.0.0, приложение открыто на порту 8080.

Swagger API UI при запущенном приложении по адресу http://localhost:8080/swagger-ui.html