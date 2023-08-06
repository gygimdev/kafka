## 실행 명령어
kafka-topics
kafka-topics.sh
### conduktror 사용할 경우
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092

# kafka 서버 실행하기 
kafka-server-start.sh ~/kafka_2.13-3.5.1/config/kraft/server.properties

## 토픽 생성하기

### localhost
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --create
kafka-topics.sh --bootstrap-server localhost:9092 --topic second_topic --create --partitions 5
kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --create --partitions 5 --replication-factor 3

### conducker
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic first_topic
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic second_topic --partitions 5
# replica를 만드려면 브로커의 수가 충분해야합니다.(1개의 브로커 안에 여러개의 replcia 를 만드는 의미가 없기 때문입니다.)
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic third_topic --partitions 5 --replication-factor 3


## 토픽 desc
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic first_topic --describe

## 토픽 삭제
kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --delete
kafka-topics.sh --command-config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic first_topic --delete
