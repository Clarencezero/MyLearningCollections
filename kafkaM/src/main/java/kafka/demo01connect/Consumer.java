package kafka.demo01connect;

import kafka.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.LOCAL_HOST);
            prop.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfig.TOPIC);
            prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");


            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(prop);
            consumer.subscribe(Collections.singleton(KafkaConfig.TOPIC));

            while (true) {
                ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(1000));
                if (poll == null || poll.isEmpty())
                    // System.out.println("No data");

                    for (ConsumerRecord<String, String> record : poll) {
                        System.out.println("==========从Kafka 获取到的数据");
                        System.out.println("Offset: " + record.offset());
                        System.out.println("Key: " + record.key());
                        System.out.println("value: " + record.value());
                        System.out.println("partition: " + record.partition());
                        System.out.println("timestamp: " + record.timestamp());
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
