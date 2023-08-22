package playground.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;


@SpringBootApplication
public class KafkaApplicationCallback {

	private static final Logger logger = LoggerFactory.getLogger(KafkaApplicationCallback.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplicationCallback.class, args);

		/** 프로듀서 설정 */
		logger.info("::: 프로듀서 설정 :::");
		Properties properties = new Properties();

		// a. 클러스터 연결
//		 properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
		properties.setProperty("bootstrap.servers", "cluster.playground.cdkt.io:9092");
		properties.setProperty("security.protocol", "SASL_SSL");
		properties.setProperty("sasl.mechanism", "PLAIN");

		// b. 프로듀서 설정
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

		// 기본 값보다 절대 적게 사용하지 말자.
		// properties.setProperty("batch.size", "400");
		// properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());

		for(int j = 0; j < 10; j++) {
			for (int i = 0; i < 30; i++) {
				/** 프로듀서 생성 */
				logger.info("::: 프로듀서 생성 :::");
				ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test_topic", "hello kafka" + i);

				/** 데이터 전송 */
				logger.info("::: 데이터 전송 :::");
				//		producer.send(producerRecord);
				producer.send(producerRecord, new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						// Executes every time a record successfully sent or an exception is thrown
						if(exception == null) {
							// the record was successfully sent
							logger.info("Received new Metadata \n" +
									"Topic: " + metadata.topic() + "\n" +
									"Partition: " + metadata.partition() + "\n" +
									"Offset: " + metadata.offset() + "\n" +
									"Timestamp: " + metadata.timestamp());
						} else {
							logger.error("Error while producing", exception);
						}
					}
				});
			}
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		/** 프로듀서 flush, close */
		logger.info("::: flush :::");
		// tell the producer to send all data and block until done -- synchronous
		producer.flush();

		// flush and close the producer
		producer.close();
	}
}
