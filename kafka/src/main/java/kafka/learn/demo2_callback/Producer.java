package kafka.learn.demo2_callback;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log4j2
public class Producer {
    public static final String TOPIC = "hello";
    public static final String BOOTSTRAP_SERVER = "localhost:9092";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 配置KAFKA
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.ACKS_CONFIG, "all");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(prop);

        String love = "我爱你, 张汶沣女士!这是我亲你的第";
        for (int i = 0; i < 10; i++) {
            // 2. 设置待发送的RECORD
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(TOPIC, love + i + "次", love + i + "次");

            // 3. SEND
            Future<RecordMetadata> send = kafkaProducer.send(record, (metadata, exception) -> {
                if (metadata != null) {
                    log.info("号外号外:  Partition: {}, offset: {}, keySize: {}, valueSize:{}", metadata.partition(), metadata.offset(), metadata.serializedKeySize(), metadata.serializedValueSize());
                }

                if (exception != null) {
                    log.error("KAFKA 异常!!!!! {}", exception);
                }
            });

            System.out.println(send.get());
        }


        // 4. close the producer

        kafkaProducer.close();

    }
}
