package kafka.learn.demo1;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 1. the producer consists of a pool of buffer space as well as background I/O thread
 * buffer sapce: holds record that haven't yet been transmitted to the server
 * background I/O: is responsible for truning(转换) these records into requests and transmitting(发射、发送) them to the cluster
 * △ Failure to close the producer after use will leak these resources.
 * 2. the send() method is asynchronous, When is called it adds the record to a buffer of pending record sends and immediately
 *  returns. This allows the producer to batch together individual records for efficiency.
 * 3. the reqeust fail, the producer can automatically retry,
 * 4. Note that records that arrive close together in time will generally batch together even with linger.ms=0
 * so under heavy load batching will occur regardless of the linger configuration; however setting this to something larger than 0 can lead to fewer,
 * more efficient requests when not under maximal load at the cost of a small amount of latency.
 * 5. The buffer.memory controls the total amount of memory available to the producer for buffering.
 *      record faster than they can be transmitted to the server -> this buffer space will be exhausted
 *      -> addititional send calls will block -> the threshold for time to block is determined by max.block.ms(default 60 seconds)
 *      -> throws a TimeoutException
 * 6. The key.serializer and value.serializer instruct how to turn the key and value objects the user provides with their ProducerRecord into bytes.
 *      You can use the included ByteArraySerializer or StringSerializer for simple string or byte types.
 *
 */
public class Producer {
    public static final String TOPIC = "hello";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Properties properties = new Properties();
        // 1. kafka地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // 2. KEY 序列化方式
        // properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 3. VALUE 序列化方式
        // properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 4. ACK应答机制。ALL: 最能保证信息不会丢失,but slowest
        // properties.put("acks", "all");
        //
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);


        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            Future<RecordMetadata> send = kafkaProducer.send(new ProducerRecord<String, String>(TOPIC, Integer.toString(random.nextInt(10000)), Integer.toString(random.nextInt(10000))));
            System.out.println(send.get());
            Thread.sleep(500);
        }

        kafkaProducer.close();
    }
}
