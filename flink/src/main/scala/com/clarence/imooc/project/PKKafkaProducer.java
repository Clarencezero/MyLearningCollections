package com.clarence.imooc.project;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class PKKafkaProducer {
    public static final String TOPIC = "flinkproject";
    public static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception{
        // 1.配置Kafka
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "template:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // 2.创建Kafka Producer
        KafkaProducer kafkaProducer = new KafkaProducer(prop);

        // 3. Mock数据: 循环向Kafka的Broker里面生产数据
        while (true) {
            StringBuilder sb = new StringBuilder();
            // 创建数据
            sb.append("imooc").append("\t")
                    .append("CN").append("\t")
                    .append(getLevels()).append("\t")
                    .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\t")
                    .append(getIps()).append("\t")
                    .append(getDomains()).append("\t")
                    .append(getTraffic()).append("\t");
            System.out.println(sb.toString());
            kafkaProducer.send(new ProducerRecord(TOPIC, sb.toString()));

            Thread.sleep(1000);
        }
    }

    private static int getTraffic() {
        return new Random().nextInt(10000);

    }

    private static String getDomains() {
        String[] domains = new String[]{"v1.go2yd.com",
                "v2.go2yd.com",
                "v3.go2yd.com",
                "v4.go2yd.com",
                "vmi.go2yd.com"};
        return domains[RANDOM.nextInt(domains.length)];
    }


        /**
         * IP
         * @return
         */
    private static String getIps() {
        String[] ips=new String[]{
                "223.104.18.110",
                "113.101.75.194",
                "27.17.127.135",
                "183.225.139.16",
                "112.1.66.34",
                "175.148.211.190",
                "183.227.58.21",
                "59.83.198.84",
                "117.28.38.28",
                "117.59.39.169"
        };
        return ips[RANDOM.nextInt(ips.length)];
    }

    // 生产level数据
    public static String getLevels() {
        String[] levels = new String[]{"M", "E"};
        return levels[RANDOM.nextInt(2)];
    }
}
