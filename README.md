### Springboot with Kafka and WebSocket
* https://umar-fajar14.medium.com/spring-boot-kafka-and-websocket-a-practical-approach-to-real-time-messaging-6169f5995fe1
### frontend
* /frontend
* Vite + React
```shell
npm install
```
```shell
npm run dev
```
### backend infra
* /docker
* zookeeper + kafka + kafdrop + kafka ui
* https://devocean.sk.com/blog/techBoardDetail.do?ID=163980
```shell
docker-composer up -d
```
### backend command
```shell
curl -X POST -H "Content-Type: application/json" -d '{"type":"CONNECT","content":"Hello, World!","sender":"Command Line"}' http://localhost:8080/send
```
```shell
curl -X POST -H "Content-Type: application/json" -d '{"type":"CHAT","content":"Hello, World!","sender":"Command Line"}' http://localhost:8080/send
```