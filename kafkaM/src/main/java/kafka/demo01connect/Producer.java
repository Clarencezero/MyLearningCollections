package kafka.demo01connect;

import kafka.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.LOCAL_HOST);
            // prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            // prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            KafkaProducer<String, String> producer = new KafkaProducer<>(prop, new StringSerializer(), new StringSerializer());
            // KafkaProducer<String, String> produce = new KafkaProducer<String, String>(prop);
            ProducerRecord record;
            for (int i = 0; i < 100; i++) {
                record = new ProducerRecord(KafkaConfig.TOPIC, String.valueOf(i), String.valueOf(i));
                producer.send(record);
            }


            producer.close();


            System.out.println("Kafka生产者完成生产");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
