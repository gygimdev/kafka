package playground.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;


@SpringBootApplication
public class KafkaApplication {

	private static final Logger logger = LoggerFactory.getLogger(KafkaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);

		/** 프로듀서 설정 */
		logger.info("::: 프로듀서 설정 :::");
		Properties properties = new Properties();

		// a. 클러스터 연결
		// properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
		properties.setProperty("bootstrap.servers", "cluster.playground.cdkt.io:9092");
		properties.setProperty("security.protocol", "SASL_SSL");
		properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"10coOvudYtXZyestPxE8W1\" password=\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiIxMGNvT3Z1ZFl0WFp5ZXN0UHhFOFcxIiwib3JnYW5pemF0aW9uSWQiOjc1NDY3LCJ1c2VySWQiOjg3ODAyLCJmb3JFeHBpcmF0aW9uQ2hlY2siOiI4ODk3NzI5Ni00NzdiLTRjZTEtYTljMy1iMDgxZDdhNjk1ZjIifX0.E6Hcz7V1axQwPsSEUqHWjn1g19Tm3zR7x7JY16-Pd30\";");
		properties.setProperty("sasl.mechanism", "PLAIN");

		// b. 프로듀서 설정
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());

		/** 프로듀서 생성 */
		logger.info("::: 프로듀서 생성 :::");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test_topic", "hello kafka");

		/** 데이터 전송 */
		logger.info("::: 데이터 전송 :::");
		producer.send(producerRecord);

		/** 프로듀서 flush, close */
		logger.info(":::  :::");
		// tell the producer to send all data and block until done -- synchronous
		producer.flush();

		// flush and close the producer
		producer.close();
	}
}
