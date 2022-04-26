docker container stop $(docker ps -aq)
docker container prune -f
docker network prune -f
docker volume prune -f
docker-compose up